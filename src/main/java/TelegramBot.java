import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.bots.DefaultBotOptions;


public class TelegramBot extends AbilityBot {
    private static final String BOT_NAME = "@zavedenyavokruge";
    private static final String BOT_TOKEN = System.getenv("TOKEN");
    public static String action = "";

    TelegramBot(DefaultBotOptions botOptions) {
        super(BOT_TOKEN, BOT_NAME, botOptions);
    }

    public AbilityExtension ability() {
        return new MyAbilityExtension(silent, db);
    }

    public int creatorId() {
        return 0;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}