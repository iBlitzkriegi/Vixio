package me.iblitzkriegi.vixio.util.enums;

import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;

import java.util.logging.Logger;

public class VixioErrorHandler {
    public static VixioErrorHandler instance;
    public Logger logger = Vixio.getInstance().getLogger();

    public VixioErrorHandler() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    public static VixioErrorHandler getInstance() {
        if (instance == null) {
            instance = new VixioErrorHandler();
            return instance;
        }
        return instance;
    }

    public void warn(String error) {
        logger.info(error);
    }

    public void needsPerm(Bot bot, String action, String permission) {
        logger.info("Vixio tried to run the action " + action + " with bot " + bot.getName() + " but was missing the " + permission + " permission!");
    }

    public void botCantFind(Bot bot, String object, String id) {
        logger.info("Vixio tried to find a " + object + " with the id " + id + " with the bot " + bot.getName() + " but the bot was unable to find it.");
    }

    public void cantFindBot(String bot, String action) {
        logger.info("Vixio tried to find a bot by " + bot + " to " + action + " but could not find the bot.");
    }

    public void noBotProvided(String action) {
        Vixio.getErrorHandler().warn("Vixio attempted to " + action + " but no bot was provided!");
    }
}
