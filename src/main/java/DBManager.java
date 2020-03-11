import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.*;
import java.util.List;

public class DBManager {
    private List<Place> placeList = new ArrayList<>();

    DBManager(DBContext db) {
        // placeList = db.getList("placeList");
        placeList.add(new Place("Mama Roma", PlaceType.RESTAURANT, "aaa",
                59.9457F,30.2878F, 600, CousinType.ITALIAN));
        placeList.add(new Place("Sushi White", PlaceType.RESTAURANT, "aaa",
                59.8907F,30.3597F, 800, CousinType.JAPANESE));
        placeList.add(new Place("Nora", PlaceType.CAFE, "aaa",
                59.9154F,30.3138F, 100, CousinType.ITALIAN));
        placeList.add(new Place("Utka", PlaceType.BAR, "aaa",
                59.9406F,30.3410F, 400, CousinType.ITALIAN));
        placeList.add(new Place("Maneken Pis", PlaceType.BAR, "aaa",
                59.9589F,30.3178F, 1600, CousinType.AMERICAN));
        placeList.add(new Place("Teremok", PlaceType.CAFE, "aaa",
                59.8323F,30.3489F, 1600, CousinType.RUSSIAN));
    }

    public List<Place> searchByType(String type, Location location) {
        List<Place> result = new ArrayList<>();
        result.addAll(placeList);
        result.removeIf(place -> (!place.getType().toString().equals(type)));
               // || place.getDistance(location) > 3000

        return result;
    }

    public List<Place> searchByCousin (String cousin, Location location) {
        List<Place> result = new ArrayList<>();
        result.addAll(placeList);
        result.removeIf(place -> (!place.getCousin().toString().equals(cousin)));
                //|| place.getDistance(location) > 3000

        return result;
    }

    public List<Place> searchByCheck (int averageCheck, Location location) {
        List<Place> result = new ArrayList<>();
        result.addAll(placeList);
        result.removeIf(place -> ( place.getAverageCheck() > averageCheck));
        //|| place.getDistance(location) > 3000

        return result;
    }
    public Place findNearest(Location location) {
        Place nearest = placeList.get(0);
        for (Place place : placeList) {
            if (place.getDistance(location) <= nearest.getDistance(location))
                nearest = place;
        }
        return nearest;
    }

    public void updateDB(Place place) {
        //placeTypeMap.get(place.getType()).get(place.getCousin()).add(place);
    }

}

