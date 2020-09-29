package me.iblitzkriegi.vixio.effects.bot.scope;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.scopes.ScopeMakeBot;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.event.Event;

public class EffEnableIntent extends Effect {

    static {
        Vixio.getInstance().registerEffect(EffEnableIntent.class,
                "enable [the] %gatewayintent% [gateway] intent [for bot]")
                .setName("Enable Gateway Intent")
                .setDesc("This is used specifically to enable specific gateway intents in the create bot scope.")
                .setExample(
                        "discord command neeko:",
                        "\ttrigger:",
                        "\t\tcreate vixio bot:",
                        "\t\t\tenable the guild members intent",
                        "\t\t\tlogin to \"YAHITAMUH\" with the name \"Neeko\""
                );
    }

    private Expression<GatewayIntent> gatewayIntent;

    @Override
    protected void execute(Event e) {
        GatewayIntent gatewayIntent = this.gatewayIntent.getSingle(e);
        if (gatewayIntent == null) {
            return;
        }
        ScopeMakeBot.jdaBuilder.enableIntents(gatewayIntent);
        System.out.println(gatewayIntent);
        if (gatewayIntent == GatewayIntent.GUILD_MEMBERS) {
            ScopeMakeBot.jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "enable the " + gatewayIntent.toString() + " intent";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!EffectSection.isCurrentSection(ScopeMakeBot.class)) {
            Skript.warning("The enable intents effect may only be used within the create bot scope");
            return false;
        }
        gatewayIntent = (Expression<GatewayIntent>) exprs[0];
        return true;
    }
}
