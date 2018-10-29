package me.iblitzkriegi.vixio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptEvent;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.registration.TypeComparators;
import me.iblitzkriegi.vixio.registration.TypeConverters;
import me.iblitzkriegi.vixio.registration.Types;
import me.iblitzkriegi.vixio.util.Metrics;
import me.iblitzkriegi.vixio.util.ParseOrderWorkaround;
import me.iblitzkriegi.vixio.util.audio.MusicStorage;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.JDA;

/**
 * Created by Blitz on 7/22/2017.
 */
public class Vixio extends JavaPlugin {
    // Instances \\
    public static Vixio instance;
    public static SkriptAddon addonInstance;
    // Registration \\
    public List<Registration> syntaxElements = new ArrayList<>();

    // JDA Related \\
    public HashMap<JDA, Bot> botHashMap = new HashMap<>();
    public HashMap<String, Bot> botNameHashMap = new HashMap<>();

    public AudioPlayerManager playerManager;
    public HashMap<AudioPlayer, MusicStorage> musicStorage = new HashMap<>();


    public Vixio() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    public static Vixio getInstance() {
        if (instance == null) {
            Vixio vixio = new Vixio();
            return vixio;
        }
        return instance;
    }

    public static SkriptAddon getAddonInstance() {
        if (addonInstance == null) {
            addonInstance = Skript.registerAddon(getInstance());
        }
        return addonInstance;
    }

    private static void setup() {
        Types.register();
        TypeConverters.register();
        TypeComparators.register();
    }

    public static ErrorHandler getErrorHandler() {
        return ErrorHandler.getInstance();
    }

    @Override
    public void onEnable() {
        try {
            getAddonInstance()
                    .loadClasses("me.iblitzkriegi.vixio", "effects", "events", "sections",
                            "expressions", "commands", "changers", "literals", "conditions")
                    .setLanguageFileDirectory("lang");
            Vixio.setup();
            AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager);
            AudioSourceManagers.registerLocalSource(playerManager);
            this.playerManager = playerManager;
            ParseOrderWorkaround.reorderSyntax();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        getConfig().addDefault("enable warnings", true);
        getConfig().options().copyDefaults(true);
        saveConfig();
        Metrics metrics = new Metrics(this);
        Documentation.generateDocs();
        this.getCommand("vixio").setExecutor(new VixioCMD());

    }

    public Registration registerCondition(Class<? extends Condition> cond, String... patterns) {
        Skript.registerCondition(cond, patterns);
        Registration registration = new Registration("Conditions", cond, patterns);
        syntaxElements.add(registration);
        return registration;
    }


    public Registration registerSection(Class type, String... patterns) {
        return registerSection(null, type, null, patterns);
    }

    public Registration registerSection(String name, Class type, String... patterns) {
        return registerSection(name, type, null, patterns);
    }

    @SuppressWarnings("unchecked")
    public Registration registerSection(String name, Class type, Class<? extends Event>[] events, String... patterns) {
        if (SkriptEvent.class.isAssignableFrom(type)) {
            Skript.registerEvent(name, type, events == null ? new Class[0] : events, patterns);
        } else if (Condition.class.isAssignableFrom(type)) {
            if (events != null) {
                throw new IllegalStateException("Condition sections do not have an event!");
            }
            if (name != null) {
                throw new IllegalStateException("Condition sections do not have a name!");
            }
            Skript.registerCondition(type, patterns);
        }
        Registration registration = new Registration("Sections", type, patterns).setEvents(events);
        syntaxElements.add(registration);
        return registration;
    }

    public Registration registerEvent(String name, Class<? extends SkriptEvent> type, Class<? extends Event> clazz, String... patterns) {
        Skript.registerEvent(name, type, clazz, patterns);
        Registration registration = new Registration("Events", clazz, patterns).setEvents(clazz);
        syntaxElements.add(registration);
        return registration;
    }

    public Registration registerEffect(Class<? extends Effect> eff, String... patterns) {
        Skript.registerEffect(eff, patterns);
        Registration reg = new Registration("Effects", eff, patterns);
        syntaxElements.add(reg);
        return reg;
    }

    public Registration registerExpression(Class<? extends Expression> expr, Class<?> returntype, ExpressionType exprtype, String... patterns) {
        Skript.registerExpression(expr, returntype, exprtype, patterns);
        Registration registration = new Registration("Expressions", expr, patterns);
        syntaxElements.add(registration);
        return registration;
    }

    public Registration registerPropertyExpression(final Class<? extends Expression> c, final Class<?> returnType, final String property, final String fromType) {
        String[] patterns = {
                "[the] " + property + "[s] of %" + fromType + "%",
                "%" + fromType + "%'[s] " + property + "[s]"
        };
        Skript.registerExpression(c, returnType, ExpressionType.PROPERTY, patterns);
        Registration registration = new Registration("Expressions", c, patterns);
        syntaxElements.add(registration);
        return registration;
    }

    public Registration registerPropertyCondition(final Class<? extends PropertyCondition> c, final String property, final String fromType) {
        PropertyCondition.register(c, property, fromType);
        String[] patterns = {
                "%" + fromType + "% (is|are) " + property,
                "%" + fromType + "% (isn't|is not|aren't|are not) " + property
        };
        Registration registration = new Registration("Conditions", c, patterns);
        syntaxElements.add(registration);
        return registration;
    }

}
