package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import me.iblitzkriegi.vixio.registration.*;
import me.iblitzkriegi.vixio.util.Metrics;
import me.iblitzkriegi.vixio.util.ParseOrderWorkaround;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.MusicStorage;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.JDA;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class Vixio extends JavaPlugin {
    // Instances \\
    public static Vixio instance;
    public static SkriptAddon addonInstance;
    // Registration \\
    public List<Registration> conditions = new ArrayList<>();
    public List<Registration> events = new ArrayList<>();
    public List<Registration> effects = new ArrayList<>();
    public List<Registration> expressions = new ArrayList<>();
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
                    .loadClasses("me.iblitzkriegi.vixio", "effects", "events", "scopes",
                            "expressions", "commands", "changers", "literals", "conditions")
                    .setLanguageFileDirectory("lang");
            Vixio.setup();
            AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager);
            AudioSourceManagers.registerLocalSource(playerManager);
            this.playerManager = playerManager;
            Util.youtubeSourceManager = new YoutubeAudioSourceManager();
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
        Documentation.setupSyntaxFile();
        this.getCommand("vixio").setExecutor(new VixioCommand());

    }

    public Registration registerCondition(Class<? extends Condition> cond, String... patterns) {
        Skript.registerCondition(cond, patterns);
        Registration registration = new Registration(cond, patterns);
        conditions.add(registration);
        return registration;
    }

    public Registration registerEvent(String name, Class type, Class clazz, String... patterns) {
        Skript.registerEvent(name, type, clazz, patterns);
        Registration registration = new Registration(clazz, patterns);
        events.add(registration);
        return registration;
    }

    public Registration registerEffect(Class<? extends Effect> eff, String... patterns) {
        Skript.registerEffect(eff, patterns);
        Registration reg = new Registration(eff, patterns);
        effects.add(reg);
        return reg;
    }

    public Registration registerExpression(Class<? extends Expression> expr, Class<?> returntype, ExpressionType exprtype, String... patterns) {
        Skript.registerExpression(expr, returntype, exprtype, patterns);
        Registration registration = new Registration(expr, patterns);
        expressions.add(registration);
        return registration;
    }

    public Registration registerPropertyExpression(final Class<? extends Expression> c, final Class<?> returnType, final String property, final String fromType) {
        String[] patterns = {
                "[the] " + property + "[s] of %" + fromType + "%",
                "%" + fromType + "%'[s] " + property + "[s]"
        };
        Skript.registerExpression(c, returnType, ExpressionType.PROPERTY, patterns);
        Registration registration = new Registration(c, patterns);
        expressions.add(registration);
        return registration;
    }

    public Registration registerPropertyCondition(final Class<? extends PropertyCondition> c, final String property, final String fromType) {
        PropertyCondition.register(c, property, fromType);
        String[] patterns = {
                "%" + fromType + "% (is|are) " + property,
                "%" + fromType + "% (isn't|is not|aren't|are not) " + property
        };
        Registration registration = new Registration(c, patterns);
        conditions.add(registration);
        return registration;
    }

}
