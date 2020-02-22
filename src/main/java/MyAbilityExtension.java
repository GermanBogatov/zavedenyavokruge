import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;


public class MyAbilityExtension implements AbilityExtension {
    private SilentSender silent;
    DBContext db;


    public MyAbilityExtension(SilentSender silent, DBContext db) {
        this.silent = silent;
        this.db = db;
    }

    public Ability start() {
        return Ability
                .builder()
                .name("start")
                .info("start+help")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    DBManager dbManager = new DBManager(db);
                    silent.send("/typefilter - to filter places by three types: restaurant, bar, cafe" +
                            "\n/cousinfilter - to filter places by cousin: italian, american, japanese, russian"+
                            "\n/checkfilter - to filter places by average check", ctx.chatId());
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
                    DBManager dbManager = new DBManager(db);
                    dbManager.searchByType(ctx.firstArg().toUpperCase(), ctx.update().getMessage().getLocation());
                    for (Place place : dbManager.searchByType(ctx.firstArg().toUpperCase(),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck()  + "\nCousin: " +
                                place.getCousin().toString().toLowerCase() + "\n" + place.getLocation(), ctx.chatId());
                    }
                })
                .build();
    }

    public Ability filterByCousin() {
        return Ability
                .builder()
                .name("cousinfilter")
                .info("filter places by cousin")
                .input(1)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    DBManager dbManager = new DBManager(db);
                    for (Place place : dbManager.searchByCousin(ctx.firstArg().toUpperCase(),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck() + "\nCousin: " +
                                place.getCousin().toString().toLowerCase() + "\n" + place.getLocation(), ctx.chatId());
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
                    DBManager dbManager = new DBManager(db);
                    for (Place place : dbManager.searchByCheck(Integer.parseInt(ctx.firstArg()),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck() + "\nCousin: " +
                                place.getCousin().toString().toLowerCase() , ctx.chatId());
                        //silent.execute(new SendLocation(ctx.chatId(), place.getLocation().getLongitude(), place.getLocation().getLatitude()));
                    }
                })
                .build();
    }
   /* private void addPlace(PlaceType type) {
        Reply.of(update -> {
            String name = "", description = "";
            //Location location = update.getMessage().getLocation();
            silent.send("Name:", update.getMessage().getChatId());
            name = update.getMessage().getText();
            silent.execute(Keyboard.addKeyboard(new String[]{"description"}, update,
                    "tap the button below"));
            switch (update.getMessage().getText()) {
                case ("description"):
                    silent.send("Description:", update.getMessage().getChatId());
                    description = update.getMessage().getText();
                    silent.execute(Keyboard.addKeyboard(new String[]{"location"}, update,
                            "tap the button below"));
                    break;
                case ("location"):
                    silent.send("send a location with map", update.getMessage().getChatId());
                    Place place = new Place(name, type, description, update.getMessage().getLocation());
                    dbManager.updateDB(place);
                    break;
            }
        }, update -> Arrays.asList(Constants.keyboardWords).contains(update.getMessage().getText()));
    }*/

}
