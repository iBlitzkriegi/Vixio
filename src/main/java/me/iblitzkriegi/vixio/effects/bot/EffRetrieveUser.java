package me.iblitzkriegi.vixio.effects.bot;

import ch.njol.skript.lang.*;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

public class EffRetrieveUser extends Effect {

    static {
        Vixio.getInstance().registerEffect(EffRetrieveUser.class, "(retrieve|grab) [the] user with id %string% [and store (them|the user) in %-objects%]")
                .setName("Retrieve User by ID")
                .setDesc("Retrieve a User via their ID on Discord. This should be a fallback option! Use the user with id expression to get users normally. This searches all of discord, not just your accessible users.")
                .setExample("retrieve user with id \"65156156156156\" and store them in {_message}");
    }

    private Expression<String> user;
    private Variable<?> varExpr;
    private VariableString varName;
    @Override
    protected void execute(Event e) {
        //TODO Implement last retrieved user expression
        String user = this.user.getSingle(e);
        if (Vixio.getInstance().botHashMap.isEmpty() || user == null) {
            return;
        }
        User retrievedUser = null;
        for (JDA jda : Vixio.getInstance().botHashMap.keySet()) {
            try {
                retrievedUser = jda.retrieveUserById(user).complete(true);
            } catch (RateLimitedException e1) {
                e1.printStackTrace();
            } catch (NumberFormatException x) {
                if (varExpr != null) {
                    Util.storeInVar(varName, varExpr, null, e);
                    return;
                }
            } catch (ErrorResponseException x) {
                if (varExpr != null) {
                    Util.storeInVar(varName, varExpr, null, e);
                    return;
                }
            }
            if (varExpr != null) {
                Util.storeInVar(varName, varExpr, retrievedUser, e);
            }
            return;
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "retrieve user with id " + user.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<String>) exprs[0];
        if (exprs[1] instanceof Variable) {
            varExpr = (Variable<?>) exprs[1];
            varName = SkriptUtil.getVariableName(varExpr);
        }
        return true;
    }
}
