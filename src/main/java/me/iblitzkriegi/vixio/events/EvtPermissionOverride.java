package me.iblitzkriegi.vixio.events;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.events.base.BaseEvent;
import me.iblitzkriegi.vixio.events.base.SimpleVixioEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.StoreChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent;

public class EvtPermissionOverride extends BaseEvent<GenericPermissionOverrideEvent> {

    static {
        BaseEvent.register("guild permissions overrided",
                EvtPermissionOverride.class, GuildPermissionOverrideEvent.class, "guild permissions (override[d]|chang[ed])")
                .setName("Guild Permissions Override")
                .setDesc("Fired when a permission change in a text channel, category, or voice channel that the bot can see.")
                .setUserFacing("(guild|server) permissions (override[d]|chang[ed]) [seen] [by %-string%]")
                .setExample("on guild permissions changed seen by \"a bot\":");
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, GuildChannel.class, new Getter<GuildChannel, GuildPermissionOverrideEvent>() {
            @Override
            public GuildChannel get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getChannel();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, ChannelType.class, new Getter<ChannelType, GuildPermissionOverrideEvent>() {
            @Override
            public ChannelType get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getChannelType();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, Category.class, new Getter<Category, GuildPermissionOverrideEvent>() {
            @Override
            public Category get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getCategory();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, TextChannel.class, new Getter<TextChannel, GuildPermissionOverrideEvent>() {
            @Override
            public TextChannel get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getTextChannel();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, VoiceChannel.class, new Getter<VoiceChannel, GuildPermissionOverrideEvent>() {
            @Override
            public VoiceChannel get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getVoiceChannel();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, Member.class, new Getter<Member, GuildPermissionOverrideEvent>() {
            @Override
            public Member get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getMember();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, IPermissionHolder.class, new Getter<IPermissionHolder, GuildPermissionOverrideEvent>() {
            @Override
            public IPermissionHolder get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getPermissionHolder();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, PermissionOverride.class, new Getter<PermissionOverride, GuildPermissionOverrideEvent>() {
            @Override
            public PermissionOverride get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getPermissionOverride();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, Role.class, new Getter<Role, GuildPermissionOverrideEvent>() {
            @Override
            public Role get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getRole();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, StoreChannel.class, new Getter<StoreChannel, GuildPermissionOverrideEvent>() {
            @Override
            public StoreChannel get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().getStoreChannel();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, Bot.class, new Getter<Bot, GuildPermissionOverrideEvent>() {
            @Override
            public Bot get(GuildPermissionOverrideEvent event) {
                return Util.botFrom(event.getJDAEvent().getJDA());
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, Boolean.class, new Getter<Boolean, GuildPermissionOverrideEvent>() {
            @Override
            public Boolean get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().isMemberOverride();
            }
        }, 0);
        EventValues.registerEventValue(GuildPermissionOverrideEvent.class, Boolean.class, new Getter<Boolean, GuildPermissionOverrideEvent>() {
            @Override
            public Boolean get(GuildPermissionOverrideEvent event) {
                return event.getJDAEvent().isRoleOverride();
            }
        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] exprs, int matchedPattern, SkriptParser.ParseResult parser) {
        return super.init(exprs, matchedPattern, parser);
    }

    @Override
    public boolean check(GenericPermissionOverrideEvent e) {
        return true;
    }

    public class GuildPermissionOverrideEvent extends SimpleVixioEvent<GenericPermissionOverrideEvent> {
    }

}
