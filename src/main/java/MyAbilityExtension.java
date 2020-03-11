import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;


public class MyAbilityExtension implements AbilityExtension {
    private SilentSender silent;
    private DBContext db;
    private DBManager dbManager;

    MyAbilityExtension(SilentSender silent, DBContext db) {
        this.silent = silent;
        this.db = db;
        dbManager = new DBManager(db);
    }

    public Ability start() {
        return Ability
                .builder()
                .name("start")
                .info("start+help")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {

                    silent.send("/typefilter - to filter places by three types: restaurant, bar, cafe" +
                            "\n/cuisinefilter - to filter places by cousin: italian, american, japanese, russian" +
                            "\n/checkfilter - to filter places by average check" +"\nTo find nearest place in some area just send a point on map", ctx.chatId());
                })
                .build();
    }

    public Ability filterByType() {
        return Ability
                .builder()
                .name("typefilter")
                .info("filter places by type")
                .input(1)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {

                    dbManager.searchByType(ctx.firstArg().toUpperCase(), ctx.update().getMessage().getLocation());
                    for (Place place : dbManager.searchByType(ctx.firstArg().toUpperCase(),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck()  + "\nCuisine: " + place.getCousin().toString().toLowerCase(), ctx.chatId());
                        SendLocation sendLocation = new SendLocation().setLatitude(place.getLocation().getLatitude());
                        sendLocation.setLongitude(place.getLocation().getLongitude());
                        silent.execute(sendLocation.setChatId(ctx.chatId()));
                    }
                })
                .build();
    }

    public Ability filterByCousin() {
        return Ability
                .builder()
                .name("cuisinefilter")
                .info("filter places by cuisine")
                .input(1)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {

                    for (Place place : dbManager.searchByCousin(ctx.firstArg().toUpperCase(),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck() + "\nnCuisine: " + place.getCousin().toString().toLowerCase(), ctx.chatId());
                        SendLocation sendLocation = new SendLocation().setLatitude(place.getLocation().getLatitude());
                        sendLocation.setLongitude(place.getLocation().getLongitude());
                        silent.execute(sendLocation.setChatId(ctx.chatId()));

                    }
                })
                .build();
    }

    public Ability filterByCheck() {
        return Ability
                .builder()
                .name("checkfilter")
                .info("filter places by average check")
                .input(1)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {

                    for (Place place : dbManager.searchByCheck(Integer.parseInt(ctx.firstArg()),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck() + "\nCuisine: " +
                                place.getCousin().toString().toLowerCase() , ctx.chatId());
                        SendLocation sendLocation = new SendLocation().setLatitude(place.getLocation().getLatitude());
                        sendLocation.setLongitude(place.getLocation().getLongitude());
                        silent.execute(sendLocation.setChatId(ctx.chatId()));
                         }
                })
                .build();
    }
    public Reply location() {
        return Reply.of(update -> {
            silent.send("Nearest place in this area:", update.getMessage().getChatId());
            Place place = dbManager.findNearest(update.getMessage().getLocation());
            silent.send(place.getName()
                    +"\n" + place.getDescription() + "\nAverage check: "
                    + place.getAverageCheck() + "\nCuisine: " +
                    place.getCousin().toString().toLowerCase(), update.getMessage().getChatId());
            SendLocation sendLocation = new SendLocation().setLatitude(place.getLocation().getLatitude());
            sendLocation.setLongitude(place.getLocation().getLongitude());
            silent.execute(sendLocation.setChatId(update.getMessage().getChatId()));
        },Flag.LOCATION);
    }

}
