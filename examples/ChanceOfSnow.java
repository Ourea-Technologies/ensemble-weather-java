// Several probabilities in one call: each answer carries its reference class
// (variable, threshold, window and the members that voted).
import com.oureawx.ensembleweather.Client;
import com.oureawx.ensembleweather.ForecastOptions;
import com.oureawx.ensembleweather.ProbabilityTerm;
import com.oureawx.ensembleweather.Threshold;
import com.oureawx.ensembleweather.Window;
import com.oureawx.ensembleweather.types.Aggregation;
import com.oureawx.ensembleweather.types.ForecastResponse;
import com.oureawx.ensembleweather.types.ProbabilityResult;
import com.oureawx.ensembleweather.types.UnitSystem;
import com.oureawx.ensembleweather.types.Variable;

public class ChanceOfSnow {
  public static void main(String[] args) {
    Client client = Client.builder().build();
    ForecastResponse forecast =
        client.getForecast(
            "or/mt-hood-meadows",
            ForecastOptions.builder()
                .units(UnitSystem.IMPERIAL)
                .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(6)))
                .probability(
                    ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(12))
                        .over(Window.hours(72)))
                .probability(
                    ProbabilityTerm.atMost(Variable.TEMPERATURE, Threshold.fahrenheit(32))
                        .over(Window.between(6, 18))
                        .withAggregation(Aggregation.ALL))
                .build());

    // The answers come back in the order asked, whatever order the server used.
    for (ProbabilityResult p : forecast.probabilities()) {
      String chance =
          p.value().isPresent()
              ? Math.round(p.value().getAsDouble() * 100) + "%"
              : "not available (the forecast does not reach the window's end)";
      System.out.println(
          "P("
              + p.variableAsString()
              + " "
              + p.opAsString()
              + " "
              + p.threshold()
              + ", hours "
              + p.windowStartHours()
              + "-"
              + p.windowEndHours()
              + ", "
              + p.aggregationAsString()
              + ") = "
              + chance
              + " from "
              + p.members()
              + " members");
    }
  }
}
