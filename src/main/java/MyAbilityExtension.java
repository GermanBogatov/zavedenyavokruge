import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
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
                            "\n/cuisinefilter - to filter places by cousin: italian, american, japanese, russian"+
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

                    dbManager.searchByType(ctx.firstArg().toUpperCase(), ctx.update().getMessage().getLocation());
                    for (Place place : dbManager.searchByType(ctx.firstArg().toUpperCase(),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck()  + "\nCuisine: " +
                                place.getCousin().toString().toLowerCase() + "\n" + place.getLocation(), ctx.chatId());
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
                                + place.getAverageCheck() + "\nnCuisine: " +
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

                    for (Place place : dbManager.searchByCheck(Integer.parseInt(ctx.firstArg()),
                            ctx.update().getMessage().getLocation())) {
                        silent.send(place.getName() +"\n" + place.getDescription() + "\nAverage check: "
                                + place.getAverageCheck() + "\nCuisine: " +
                                place.getCousin().toString().toLowerCase() , ctx.chatId());
                        silent.execute(new SendLocation(place.getLocation().getLongitude(), place.getLocation().getLatitude()));
                         }
                })
                .build();
    }

}
