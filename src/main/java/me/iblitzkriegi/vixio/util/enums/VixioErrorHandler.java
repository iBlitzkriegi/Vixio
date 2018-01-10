package me.iblitzkriegi.vixio.util.enums;

import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;

import java.util.logging.Logger;

public class VixioErrorHandler {
    public Logger logger = Vixio.getInstance().getLogger();
    public static VixioErrorHandler instance;

    public VixioErrorHandler() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    public void warn(VixioError error, String... values){

    }

    public void warn(VixioError error, Bot bot, String... values){
        if (error == VixioError.BOT_NO_PERMISSION){
            if  (values.length == 2){
                needsPerm(error, bot, values[1], values[0]);
            }
        }
    }

    public void needsPerm(VixioError vixioError, Bot bot, String action, String permission){
        logger.info("Vixio tried to run the action " + action + " with bot " + bot.getName() + " but was missing the " + permission + " permission!");
    }

    public void warn(String... errors){

    }

    public static VixioErrorHandler getInstance() {
        if (instance == null) {
            instance = new VixioErrorHandler();
            return instance;
        }
        return instance;
    }
}
