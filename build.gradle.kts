// SPDX-License-Identifier: BSD-2-Clause
// Build for com.oureawx:ensemble-weather. See CONTRIBUTING.md for the tasks.

import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.vanniktech.maven.publish.JavaLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar
import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
  `java-library`
  jacoco
  alias(libs.plugins.errorprone)
  alias(libs.plugins.spotbugs)
  alias(libs.plugins.maven.publish)
}

group = "com.oureawx"
version = "0.1.0"

// The JDK that compiles (Error Prone needs a recent javac); the bytecode and
// the API targeted are Java 11 through `--release 11`.
val buildJdk = 25
// Tests run on this JDK: `-PtestJdk=11` (CI runs 11, 17, 21 and 25).
val testJdk = providers.gradleProperty("testJdk").map { it.toInt() }.getOrElse(buildJdk)

java { toolchain { languageVersion = JavaLanguageVersion.of(buildJdk) } }

repositories { mavenCentral() }

dependencyLocking { lockAllConfigurations() }

buildscript { configurations.classpath { resolutionStrategy.activateDependencyLocking() } }

// examples/: single-file programs in the default package, so each runs as `java Quickstart.java`.
val examples: SourceSet =
  sourceSets.create("examples") {
    java.setSrcDirs(listOf("examples"))
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
  }

configurations[examples.implementationConfigurationName].extendsFrom(configurations.implementation.get())

configurations[examples.runtimeOnlyConfigurationName].extendsFrom(configurations.runtimeOnly.get())

val googleJavaFormat: Configuration = configurations.create("googleJavaFormat")

dependencies {
  implementation(libs.jackson.core)
  compileOnly(libs.jspecify)
  "examplesCompileOnly"(libs.jspecify)

  testImplementation(platform(libs.junit.bom))
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.platform.launcher)
  testImplementation(libs.snakeyaml.engine)
  testCompileOnly(libs.jspecify)

  googleJavaFormat(libs.google.java.format)

  errorprone(libs.errorprone.core)
  errorprone(libs.nullaway)
  spotbugsPlugins(libs.findsecbugs)
}

spotbugs { toolVersion = libs.versions.spotbugs.get() }

val strictLint = listOf("-Xlint:all", "-Werror")

tasks.withType<JavaCompile>().configureEach {
  options.release = 11
  options.encoding = "UTF-8"
  options.compilerArgs.addAll(strictLint)
  options.errorprone {
    disableWarningsInGeneratedCode = true
    // NullAway checks every @NullMarked package (all of them: package-info.java).
    check("NullAway", CheckSeverity.ERROR)
    option("NullAway:OnlyNullMarked", "true")
  }
}

tasks.compileJava {
  options.compilerArgs.addAll(
    listOf(
      "-Xdoclint:all/protected",
      "-Xdoclint/package:com.oureawx.ensembleweather,com.oureawx.ensembleweather.model",
    )
  )
}

tasks.named<JavaCompile>("compileExamplesJava") {
  // The examples are single-file programs meant for `java Quickstart.java`.
  options.errorprone.check("DefaultPackage", CheckSeverity.OFF)
}

tasks.named<JavaCompile>("compileTestJava") {
  // Tests assert on nulls deliberately; NullAway guards the shipped code.
  options.errorprone.check("NullAway", CheckSeverity.OFF)
}

tasks.withType<Javadoc>().configureEach {
  val o = options as StandardJavadocDocletOptions
  o.encoding = "UTF-8"
  o.addStringOption("-release", "11")
  o.addBooleanOption("Xdoclint:all", true)
  o.addBooleanOption("Werror", true)
  o.windowTitle = "Ourea ensemble-weather ${project.version} API"
  o.docTitle = "Ourea ensemble-weather ${project.version}"
}

tasks.test {
  useJUnitPlatform()
  javaLauncher = javaToolchains.launcherFor { languageVersion = JavaLanguageVersion.of(testJdk) }
  // The vectors and the drift tests read the pinned spec.
  systemProperty("ourea.spec.dir", layout.projectDirectory.dir("spec").asFile.absolutePath)
  systemProperty("ourea.project.dir", layout.projectDirectory.asFile.absolutePath)
  systemProperty("ourea.project.version", project.version.toString())
  environment.remove("OUREA_API_KEY")
  environment.remove("OUREA_BASE_URL")
  testLogging { events("failed") }
}

// Runs every program in examples/ against a local mock of the API (never the
// real one). The mock and the runner live in the test sources.
val runExamples =
  tasks.register<JavaExec>("runExamples") {
    group = "verification"
    description = "Runs every example against a local mock API."
    dependsOn(examples.classesTaskName)
    classpath = sourceSets.test.get().runtimeClasspath
    mainClass = "com.oureawx.ensembleweather.examples.ExampleRunner"
    javaLauncher = javaToolchains.launcherFor { languageVersion = JavaLanguageVersion.of(testJdk) }
    args(
      layout.projectDirectory.dir("spec").asFile.absolutePath,
      examples.runtimeClasspath.asPath,
    )
    environment.remove("OUREA_API_KEY")
    environment.remove("OUREA_BASE_URL")
  }

tasks.named("check") { dependsOn(runExamples) }

tasks.jacocoTestReport {
  dependsOn(tasks.test)
  reports {
    xml.required = true
    html.required = true
  }
}

// One example, against whatever OUREA_BASE_URL says (the live API by default):
// `./gradlew runExample -Pexample=Quickstart`. CI's nightly live run uses it.
tasks.register<JavaExec>("runExample") {
  group = "application"
  description = "Runs one example (-Pexample=Name)."
  classpath = examples.runtimeClasspath
  mainClass = providers.gradleProperty("example").orElse("Quickstart")
}

// The nightly contract run against the live API (live.yml): `./gradlew liveContract`.
tasks.register<JavaExec>("liveContract") {
  group = "verification"
  description = "Runs the read-only contract checks against the live API."
  classpath = sourceSets.test.get().runtimeClasspath
  mainClass = "com.oureawx.ensembleweather.examples.LiveContract"
}

// A local mock of the API for trying the examples by hand: `./gradlew mockApi`.
tasks.register<JavaExec>("mockApi") {
  group = "application"
  description = "Serves a mock of the API on http://127.0.0.1:8787 until stopped."
  classpath = sourceSets.test.get().runtimeClasspath
  mainClass = "com.oureawx.ensembleweather.examples.MockApiServer"
  args(layout.projectDirectory.dir("spec").asFile.absolutePath, "8787")
}

// google-java-format, run directly (check mode in CI): `./gradlew formatCheck`, `./gradlew format`.
val javaSources =
  fileTree(".") {
    include("src/**/*.java", "examples/**/*.java")
    exclude("build/**", ".scratch/**", "spec/**")
  }

val gjfJvmArgs =
  listOf("api", "code", "file", "parser", "tree", "util").map {
    "--add-exports=jdk.compiler/com.sun.tools.javac.$it=ALL-UNNAMED"
  }

val formatCheck =
  tasks.register<JavaExec>("formatCheck") {
    group = "verification"
    description = "Fails if a Java file is not formatted with google-java-format."
    classpath = googleJavaFormat
    mainClass = "com.google.googlejavaformat.java.Main"
    jvmArgs(gjfJvmArgs)
    javaLauncher = javaToolchains.launcherFor { languageVersion = JavaLanguageVersion.of(buildJdk) }
    inputs.files(javaSources)
    argumentProviders.add(
      CommandLineArgumentProvider {
        listOf("--dry-run", "--set-exit-if-changed") + javaSources.files.map { it.path }.sorted()
      }
    )
  }

tasks.register<JavaExec>("format") {
    group = "formatting"
    description = "Formats every Java file with google-java-format."
    classpath = googleJavaFormat
    mainClass = "com.google.googlejavaformat.java.Main"
    jvmArgs(gjfJvmArgs)
    javaLauncher = javaToolchains.launcherFor { languageVersion = JavaLanguageVersion.of(buildJdk) }
    argumentProviders.add(
      CommandLineArgumentProvider { listOf("--replace") + javaSources.files.map { it.path }.sorted() }
    )
  }

tasks.named("check") { dependsOn(formatCheck) }

tasks.withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
  effort = Effort.MAX
  reportLevel = Confidence.LOW
  excludeFilter = layout.projectDirectory.file("config/spotbugs/exclude.xml")
  reports.create("html") { required = true }
  reports.create("xml") { required = true }
}

// SpotBugs guards the shipped bytecode; tests and examples are not shipped.
tasks.named("spotbugsTest") { enabled = false }

tasks.named("spotbugsExamples") { enabled = false }

// SDK-STANDARD "Dependencies": every runtime dependency has a justification
// line in CONTRIBUTING.md, and every justified one is a dependency.
val checkDependencies =
  tasks.register("checkDependencies") {
    group = "verification"
    description = "Fails if a runtime dependency has no justification in CONTRIBUTING.md."
    val contributing = layout.projectDirectory.file("CONTRIBUTING.md")
    inputs.file(contributing)
    // Every jar on the runtime classpath, transitive ones included (a BOM has no jar).
    val runtime =
      configurations.runtimeClasspath.map { c ->
        c.incoming.artifacts.artifacts
          .mapNotNull { (it.id.componentIdentifier as? ModuleComponentIdentifier) }
          .map { "${it.group}:${it.module}" }
          .toSortedSet()
      }
    doLast {
      val text = contributing.asFile.readText()
      val section =
        Regex("<!-- runtime-dependencies:start -->([\\s\\S]*?)<!-- runtime-dependencies:end -->")
          .find(text)
          ?.groupValues
          ?.get(1) ?: throw GradleException("CONTRIBUTING.md has no runtime-dependencies list")
      val justified =
        Regex("^- `([^`:]+:[^`:]+)`: \\S", RegexOption.MULTILINE)
          .findAll(section)
          .map { it.groupValues[1] }
          .toSortedSet()
      val deps = runtime.get()
      val problems =
        (deps - justified).map { "runtime dependency $it has no justification in CONTRIBUTING.md" } +
          (justified - deps).map { "CONTRIBUTING.md justifies $it, which is not a dependency" }
      if (problems.isNotEmpty()) throw GradleException(problems.joinToString("\n"))
      logger.lifecycle("runtime dependencies, each justified in CONTRIBUTING.md: $deps")
    }
  }

tasks.named("check") { dependsOn(checkDependencies) }

tasks.jar {
  manifest {
    attributes(
      "Implementation-Title" to "Ourea ensemble-weather",
      "Implementation-Version" to project.version,
      "Implementation-Vendor" to "Ourea Technologies",
    )
  }
}

mavenPublishing {
  configure(JavaLibrary(javadocJar = JavadocJar.Javadoc(), sourcesJar = SourcesJar.Sources()))
  coordinates("com.oureawx", "ensemble-weather", project.version.toString())
  publishToMavenCentral(automaticRelease = true)
  signAllPublications()
  pom {
    name = "Ourea ensemble-weather"
    description =
      "Official Java SDK for the Ourea probabilistic weather API: ensemble weather forecasts, " +
        "probabilities and window totals (ourea, weather, forecast, ensemble, probabilistic)."
    url = "https://github.com/Ourea-Technologies/ensemble-weather-java"
    inceptionYear = "2026"
    licenses {
      license {
        name = "BSD-2-Clause"
        url = "https://opensource.org/license/bsd-2-clause"
        distribution = "repo"
      }
    }
    organization {
      name = "Ourea Technologies"
      url = "https://oureawx.com"
    }
    developers {
      developer {
        id = "ourea"
        name = "Ourea Technologies"
        url = "https://oureawx.com"
      }
    }
    scm {
      url = "https://github.com/Ourea-Technologies/ensemble-weather-java"
      connection = "scm:git:https://github.com/Ourea-Technologies/ensemble-weather-java.git"
      developerConnection = "scm:git:ssh://git@github.com/Ourea-Technologies/ensemble-weather-java.git"
    }
    issueManagement {
      system = "GitHub"
      url = "https://github.com/Ourea-Technologies/ensemble-weather-java/issues"
    }
  }
}

// `assemble` (and so `build`) makes the three jars Maven Central needs.
tasks.named("assemble") { dependsOn("plainJavadocJar") }
