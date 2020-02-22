import org.telegram.telegrambots.meta.api.objects.Location;
import java.io.Serializable;


public class Place implements Serializable {
    private String name;
    private PlaceType type;
    private CousinType cousin;
    private Location location;
    private int averageCheck;
    private String description;


    Place(String name, PlaceType type, String description, Location location,
          int averageCheck, CousinType cousin) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.location = location;
        this.averageCheck = averageCheck;
        this.cousin = cousin;
    }

    public String getName() {
        return name;
    }
    public Location getLocation() {
        return location;
    }
    public PlaceType getType() {
        return type;
    }
    public String getDescription() {
        return description;
    }
    public int getAverageCheck() { return averageCheck; }
    public CousinType getCousin() { return cousin; }

    public double getDistance(Location location) {
        float r = 6371;
        double dist = r * 2 * Math.asin(Math.sqrt((Math.pow(Math.sin((location.getLatitude()
                - this.location.getLatitude()) / 2), 2)
                + Math.cos(location.getLatitude()) * Math.cos(this.location.getLatitude())
                * Math.pow(Math.sin((location.getLongitude() - this.location.getLongitude()) / 2), 2))));
        return dist;
    }
}
