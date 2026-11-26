// Rank Oregon's ski areas by the next 5 days' snow, then show the season so far
// (observed precipitation and its percent of normal) at the top one.
import com.oureawx.ensembleweather.Client;
import com.oureawx.ensembleweather.ExploreOptions;
import com.oureawx.ensembleweather.LocationId;
import com.oureawx.ensembleweather.types.ExploreItem;
import com.oureawx.ensembleweather.types.ExploreMetric;
import com.oureawx.ensembleweather.types.ExploreResponse;
import com.oureawx.ensembleweather.types.LocationType;
import com.oureawx.ensembleweather.types.SeasonResponse;

public class SnowRankings {
  public static void main(String[] args) {
    Client client = Client.builder().build();

    ExploreResponse top =
        client.explore(
            ExploreMetric.SNOW_5D,
            ExploreOptions.builder().state("or").type(LocationType.SKI_AREA).limit(5).build());
    System.out.println("Next 5 days' snow (" + top.unit() + "), most first:");
    for (ExploreItem item : top.items()) {
      System.out.println("  " + item.rank() + ". " + item.location().name() + ": " + item.value());
    }
    if (top.items().isEmpty()) {
      return;
    }

    ExploreItem first = top.items().get(0);
    LocationId where =
        LocationId.of(first.location().state(), first.location().slug().orElseThrow());
    SeasonResponse season = client.getSeason(where);
    season
        .precipitation()
        .ifPresentOrElse(
            p ->
                System.out.println(
                    "Water year "
                        + season.waterYear()
                        + " at "
                        + first.location().name()
                        + ": "
                        + p.amount()
                        + " "
                        + p.unit()
                        + (p.percentOfNormal().isPresent()
                            ? " (" + p.percentOfNormal().getAsInt() + "% of normal)"
                            : "")),
            () -> System.out.println("No observed season total there yet."));
  }
}
