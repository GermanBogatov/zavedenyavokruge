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

    /*public List<Place> searchByType(PlaceType type, CousinType cousin, Location location,
                    int averageCheck )
            throws ThereIsNotSuchPlaceTypeException, ThereIsNotSuchCousinTypeException {
        List<Place> result = new ArrayList<>();
        switch (type) {
            case BAR:
                switch (cousin) {
                    case AMERICAN:
                        result = placeTypeMap
                                .get(PlaceType.BAR)
                                .get(CousinType.AMERICAN);
                        break;
                    case ITALIAN:
                        result = placeTypeMap
                                .get(PlaceType.BAR)
                                .get(CousinType.ITALIAN);
                        break;
                    case JAPANESE:
                        result = placeTypeMap
                                .get(PlaceType.BAR)
                                .get(CousinType.JAPANESE);
                        break;
                    case RUSSIAN:
                        result = placeTypeMap
                                .get(PlaceType.BAR)
                                .get(CousinType.RUSSIAN);
                        break;
                }
                throw new ThereIsNotSuchCousinTypeException();
            case RESTAURANT:
                switch (cousin) {
                    case AMERICAN:
                        result = placeTypeMap
                                .get(PlaceType.RESTAURANT)
                                .get(CousinType.AMERICAN);
                        break;

                    case ITALIAN:
                        result = placeTypeMap
                                .get(PlaceType.RESTAURANT)
                                .get(CousinType.ITALIAN);
                        break;
                    case JAPANESE:
                        result = placeTypeMap
                                .get(PlaceType.RESTAURANT)
                                .get(CousinType.JAPANESE);
                    case RUSSIAN:
                        result = placeTypeMap
                                .get(PlaceType.RESTAURANT)
                                .get(CousinType.RUSSIAN);
                        break;
                }
                throw new ThereIsNotSuchCousinTypeException();
            case CAFE:
                switch (cousin) {
                    case AMERICAN:
                        result = placeTypeMap
                                .get(PlaceType.CAFE)
                                .get(CousinType.AMERICAN);
                        break;
                    case ITALIAN:
                        result = placeTypeMap
                                .get(PlaceType.CAFE)
                                .get(CousinType.ITALIAN);
                        break;
                    case JAPANESE:
                        result = placeTypeMap
                                .get(PlaceType.CAFE)
                                .get(CousinType.JAPANESE);
                        break;
                    case RUSSIAN:
                        result = placeTypeMap
                                .get(PlaceType.CAFE)
                                .get(CousinType.RUSSIAN);
                        break;
                }
                throw new ThereIsNotSuchCousinTypeException();
        }
        if (!result.isEmpty()) {
            result.removeIf(place -> (place.getDistance(location) > 3000 || place.getAverageCheck() > averageCheck
            || place.getRate() < 3));
            return result;
        }
        throw new ThereIsNotSuchPlaceTypeException();
    }*/

    public void updateDB(Place place) {
        //placeTypeMap.get(place.getType()).get(place.getCousin()).add(place);
    }

}

