import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

class Keyboard {

    private static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    static SendMessage addKeyboard(String[] text, Update update, String answer) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        for (String s : text) {
            keyboardRow.add(s);
            keyboard.add(keyboardRow);
            keyboardRow = new KeyboardRow();
        }
        keyboard.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setText(answer);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}