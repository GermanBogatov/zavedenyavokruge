import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.*;
import java.util.List;

public class DBManager {
    private List<Place> placeList = new ArrayList<>();

    DBManager(DBContext db) {
        // placeList = db.getList("placeList");
        placeList.add(new Place("Mama Roma", PlaceType.RESTAURANT, "aaa",
                new Location(), 600, CousinType.ITALIAN));
        placeList.add(new Place("Sushi White", PlaceType.RESTAURANT, "aaa",
                new Location(), 800, CousinType.JAPANESE));
        placeList.add(new Place("Nora", PlaceType.CAFE, "aaa",
                new Location(), 100, CousinType.ITALIAN));
        placeList.add(new Place("Utka", PlaceType.BAR, "aaa",
                new Location(), 400, CousinType.ITALIAN));
        placeList.add(new Place("Maneken Pis", PlaceType.BAR, "aaa",
                new Location(), 1600, CousinType.AMERICAN));
        placeList.add(new Place("Teremok", PlaceType.CAFE, "aaa",
                new Location(), 1600, CousinType.RUSSIAN));
    }

    public List<Place> searchByType(String type, Location location) {
        List<Place> result = placeList;
        result.removeIf(place -> (!place.getType().toString().equals(type)));
               // || place.getDistance(location) > 3000

        return result;
    }

    public List<Place> searchByCousin (String cousin, Location location) {
        List<Place> result = placeList;
        result.removeIf(place -> (!place.getCousin().toString().equals(cousin)));
                //|| place.getDistance(location) > 3000

        return result;
    }

    public List<Place> searchByCheck (int averageCheck, Location location) {
        List<Place> result = placeList;
        result.removeIf(place -> ( place.getAverageCheck() > averageCheck));
        //|| place.getDistance(location) > 3000

        return result;
    }


    public void updateDB(Place place) {
        //placeTypeMap.get(place.getType()).get(place.getCousin()).add(place);
    }

}

