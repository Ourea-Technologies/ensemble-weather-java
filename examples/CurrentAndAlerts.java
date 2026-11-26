// Current conditions and the active NWS alerts for a location.
import com.oureawx.ensembleweather.Client;
import com.oureawx.ensembleweather.LocationId;
import com.oureawx.ensembleweather.types.ActiveAlert;
import com.oureawx.ensembleweather.types.AlertsResponse;
import com.oureawx.ensembleweather.types.CurrentResponse;
import com.oureawx.ensembleweather.types.UnitSystem;

public class CurrentAndAlerts {
  public static void main(String[] args) {
    Client client = Client.builder().build();
    LocationId meadows = LocationId.of("or", "mt-hood-meadows");

    CurrentResponse current = client.getCurrent(meadows);
    current
        .now()
        .ifPresentOrElse(
            c ->
                System.out.println(
                    "Now at "
                        + current.location().name()
                        + ": "
                        + c.temperature()
                        + " "
                        + (current.units() == UnitSystem.IMPERIAL ? "°F" : "°C")
                        + ", "
                        + c.text()
                        + ", wind "
                        + c.windSpeed()
                        + " ("
                        + c.sourceAsString()
                        + ")"),
            () -> System.out.println("No current conditions are servable right now."));

    AlertsResponse alerts = client.getAlerts(meadows);
    System.out.println(alerts.count() + " active alert(s)");
    for (ActiveAlert alert : alerts.items()) {
      System.out.println("  " + alert.severityAsString() + ": " + alert.headline());
    }
  }
}
