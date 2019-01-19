package me.iblitzkriegi.vixio;

import me.iblitzkriegi.vixio.expressions.ExprLastError;
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
            ExprLastError.lastError = toLog;
        }
    }

    public void warn(String error) {
        log(error);
    }

    public void needsPerm(Bot bot, String action, String permission) {
        log("Vixio tried to run the action " + action + " with bot " + bot.getName() + " but was missing the " + permission + " permission!");
    }

    public void cantOpenPrivateChannel() {
        log("Vixio attempted to open a private channel but was ratelimited.");
    }
}
