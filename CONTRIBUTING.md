# Contributing

Thanks for helping make Ourea better. Bug reports, feature ideas, docs fixes
and pull requests are all welcome, including small ones: a typo fix or one
more test is a real contribution.

We aim to give every new issue and pull request a first response within a few
business days. We are a small team, so a full review can take longer, but you
will hear from us.

## From clone to a green build

You need a JDK 17 or newer to run Gradle; the build provisions its own
toolchain (JDK 25 compiles, with `--release 11`) from the JDKs you have
installed, so install JDK 25 too, plus 11, 17 or 21 to test on them.

```sh
git clone https://github.com/Ourea-Technologies/ensemble-weather-java
cd ensemble-weather-java
./gradlew check        # format check, Error Prone + NullAway, SpotBugs, tests, vectors, examples
```

That one command is what CI runs. The pieces:

| Command                          | What it does                                                                    |
| -------------------------------- | ------------------------------------------------------------------------------- |
| `./gradlew format`               | formats every Java file with google-java-format                                 |
| `./gradlew formatCheck`          | fails on an unformatted file (CI)                                               |
| `./gradlew compileJava`          | compiles with Error Prone, NullAway, `-Xlint:all -Werror` and doclint           |
| `./gradlew spotbugsMain`         | SpotBugs with Find Security Bugs on the shipped bytecode                        |
| `./gradlew test`                 | unit tests, the 43 shared vectors and the spec-drift tests                      |
| `./gradlew test -PtestJdk=11`    | the same on another JDK (11, 17, 21, 25)                                        |
| `./gradlew runExamples`          | every program in `examples/` against a local mock API                           |
| `./gradlew mockApi`              | serves that mock on `http://127.0.0.1:8787`, for running examples by hand       |
| `./gradlew javadoc`              | the API reference, with doclint (`-Xdoclint:all -Werror`)                       |
| `./gradlew build`                | everything above plus the jar, sources jar and javadoc jar                      |
| `./gradlew checkDependencies`    | fails if a runtime dependency has no justification below                        |
| `scripts/sync-spec --check`      | fails if `spec/` differs from the spec commit in `spec/VERSION`                 |

Nothing in the build or the tests calls the real API. To try an example by
hand, run `./gradlew mockApi` in one terminal and, in another,
`OUREA_BASE_URL=http://127.0.0.1:8787 java -cp build/libs/ensemble-weather-0.1.0.jar:<jackson-core jar> examples/Quickstart.java`.

## What makes a good bug report

Use the [bug report form](https://github.com/Ourea-Technologies/ensemble-weather-java/issues/new?template=bug_report.yml).
The most useful reports have:

- the SDK version (`EnsembleWeatherClient.VERSION`) and the Java version and OS;
- a minimal snippet that shows the problem, and what you expected instead;
- the `requestId()` from the exception or `metadata()`, if there is one: it
  lets us find the request in our logs.

**Never paste an API key** into an issue, a log or a snippet. The SDK never
prints it, so its own output is safe to share.

## Feature ideas

Open a [feature request](https://github.com/Ourea-Technologies/ensemble-weather-java/issues/new?template=feature_request.yml):
start with the problem you are solving, then the API you would like. We decide
by what helps the most developers with the least friction, and say why either
way. The API's behaviour (operations, parameters, probabilities, errors) is
defined in [ensemble-weather-spec](https://github.com/Ourea-Technologies/ensemble-weather-spec),
shared by every Ourea SDK, so a request that needs a server change may be moved
there and labelled `needs-spec`.

## How the code is laid out

- `src/main/java/com/oureawx/ensembleweather/`: the client, its options, the
  probability and total terms, the exceptions and the cache interface.
- `.../model/`: the immutable response models, one class per schema of the
  OpenAPI spec, with builders. They were first drafted from the spec by a
  throwaway script and are maintained by hand; no generator is part of the build.
- `.../internal/`: not exported by the module. The canonical query
  (`Query`), the `prob`/`totals` wire form (`Terms`), the request engine
  (retries, redirects, cache, errors: `Engine`), JSON (`Json`, `JsonFields`,
  `Decoders`) and the `java.net.http` transport.
- `src/test/`: unit tests, `VectorsTest` (runs every file in `spec/vectors`),
  `SpecDriftTest`, and the mock API and example runner.
- `spec/`: a pinned copy of ensemble-weather-spec. Never edit it; run
  `scripts/sync-spec <ref>` to move the pin.

The rules every change keeps: the SDK never computes statistics (no summing or
interpolating quantiles, no deriving probabilities); every request is sent in
its canonical form; the API key is never logged, put in a message or sent to
another host.

## Commits and pull requests

- One logical change per commit, with an imperative summary line ("Add the
  stations iterator") and a short body saying what and why.
- `./gradlew check` must pass. Add a test with every fix (red first).
- Update `CHANGELOG.md` under "Unreleased". Outside contributors are credited
  there by GitHub handle.
- History on `main` stays linear: rebase rather than merge.

## Dependencies

The SDK has as few runtime dependencies as possible: each one is a
supply-chain risk and an install cost. HTTP is the JDK's `java.net.http`, and
there is no Kotlin runtime. `./gradlew checkDependencies` (part of `check`)
fails if the runtime classpath gains a dependency that has no line here, or if
a line here names something that is not a dependency.

<!-- runtime-dependencies:start -->
- `com.fasterxml.jackson.core:jackson-core`: Java has no JSON parser built in. jackson-core is the streaming parser alone (no data binding, no transitive dependencies, a JPMS module, Java 8+ bytecode). The SDK builds plain `JsonValue` trees from its tokens and maps them onto the models by hand, so no JSON can ever name a class to instantiate: polymorphic deserialisation is impossible, not merely disabled. Its `StreamReadConstraints` bound nesting depth, number and string length, and duplicate keys are refused. We chose it over jackson-databind (much larger, and reflective binding is the attack surface we want to avoid) and over a hand-written parser (a JSON parser is easy to get subtly wrong, and jackson-core is one of the most scrutinised on the JVM).
<!-- runtime-dependencies:end -->

Compile-only: `org.jspecify:jspecify` (the `@Nullable`/`@NullMarked`
annotations NullAway checks; `requires static`, not needed at run time).
Build and test only, pinned by the version catalog and the Gradle lockfiles:
Error Prone, NullAway, SpotBugs, Find Security Bugs, google-java-format, JUnit
5 and SnakeYAML Engine (the drift tests read the OpenAPI YAML). Refresh the
locks after changing a version with `./gradlew dependencies --write-locks`.

## Releasing (maintainers)

1. Bump `version` in `build.gradle.kts` and `EnsembleWeatherClient.VERSION`
   (a test checks they match), move "Unreleased" in `CHANGELOG.md` to the new
   version, and merge.
2. Tag `vX.Y.Z` on `main` and push the tag. `release.yml` rebuilds, runs every
   check again, signs the artifacts, uploads them to the Maven Central
   Portal and creates the GitHub release with the changelog section, an SBOM
   and build provenance.

Maven Central has no OIDC trusted publishing, so the release job uses these
repository secrets (set them in the `maven-central` environment; never commit
them):

| Secret                         | What it is                                                              |
| ------------------------------ | ----------------------------------------------------------------------- |
| `MAVEN_CENTRAL_USERNAME`       | the Central Portal user token's username (not the account login)        |
| `MAVEN_CENTRAL_PASSWORD`       | the Central Portal user token's password                                |
| `SIGNING_KEY`                  | the ASCII-armoured GPG private key that signs the artifacts             |
| `SIGNING_KEY_PASSWORD`         | that key's passphrase                                                   |

The `com.oureawx` namespace is verified in the Central Portal by a DNS TXT
record on oureawx.com, and the signing key's public half must be on a public
keyserver (`keys.openpgp.org`). Javadoc is hosted by javadoc.io automatically
once the artifact is on Central.

## Conduct

This project follows the [Contributor Covenant](CODE_OF_CONDUCT.md).
