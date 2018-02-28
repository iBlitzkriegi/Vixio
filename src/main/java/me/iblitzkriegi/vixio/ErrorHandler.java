package me.iblitzkriegi.vixio;

import me.iblitzkriegi.vixio.util.wrapper.Bot;

import java.util.logging.Logger;

public class ErrorHandler {

    public static ErrorHandler instance;
    public Logger logger = Vixio.getInstance().getLogger();
    public boolean theUserCares = Vixio.getInstance().getConfig().getBoolean("enable warnings", true);

    public ErrorHandler() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    public static ErrorHandler getInstance() {
        if (instance == null) {
            instance = new ErrorHandler();
            return instance;
        }
        return instance;
    }

    public void log(String toLog) {
        if (theUserCares) {
            logger.info(toLog);
        }
    }

    public void warn(String error) {
        log(error);
    }

    public void needsPerm(Bot bot, String action, String permission) {
        log("Vixio tried to run the action " + action + " with bot " + bot.getName() + " but was missing the " + permission + " permission!");
    }

    public void botCantFind(Bot bot, String object, String id) {
        log("Vixio tried to find a " + object + " with the id " + id + " with the bot " + bot.getName() + " but the bot was unable to find it.");
    }

    public void cantFindBot(String bot, String action) {
        log("Vixio tried to find a bot by " + bot + " to " + action + " but could not find the bot.");
    }

    public void noBotProvided(String action) {
        Vixio.getErrorHandler().warn("Vixio attempted to " + action + " but no bot was provided!");
    }
}
