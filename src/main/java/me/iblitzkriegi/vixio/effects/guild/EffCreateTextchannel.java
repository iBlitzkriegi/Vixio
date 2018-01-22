package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffCreateTextchannel extends Effect{
    static {
        Vixio.getInstance().registerEffect(EffCreateTextchannel.class,
                "create text[(-| )]channel named %string% [in %guild%] [(as|with) %bot/string%] ", "create voice[(-| )]channel named %string% [in %guild%] [(as|with) %bot/string%]")
                .setName("Create channel")
                .setDesc("Create ")
                .setExample("Coming Soon!");
    }
    private Expression<String> name;
    private Expression<Guild> guild;
    private Expression<Object> bot;
    private boolean not;
    @Override
    protected void execute(Event e) {
        String name = this.name.getSingle(e);
        if (name == null || name.isEmpty()) {
            return;
        }
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return;
        }
        Object object = this.bot.getSingle(e);
        if (object == null) {
            return;
        }
        Bot bot = Util.botFrom(object);
        if (bot == null) {
            return;
        }
        try {
            if (Util.botIsConnected(bot, guild.getJDA())) {
                if (not) {
                    guild.getController().createTextChannel(name).queue();
                    return;
                }
                guild.getController().createVoiceChannel(name).queue();
                return;
            }
            Guild bindingGuild = bot.getJDA().getGuildById(guild.getId());
            if (bindingGuild == null) {
                Vixio.getErrorHandler().botCantFind(bot, "guild", guild.getId());
                return;
            }
            if (not) {
                bindingGuild.getController().createTextChannel(name).queue();
                return;
            }
            bindingGuild.getController().createVoiceChannel(name).queue();
        }catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, x.getPermission().getName(), "create channel");
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create channel named " + name.toString(e, debug) + " in " + guild.toString(e, debug) + " as " + bot.toString(e,debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        not = matchedPattern == 0;
        return true;
    }
}
