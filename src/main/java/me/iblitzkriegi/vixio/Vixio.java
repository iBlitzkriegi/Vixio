package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.*;
import ch.njol.skript.registrations.Converters;
import ch.njol.skript.util.Color;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.registration.VixioConverters;
import me.iblitzkriegi.vixio.registration.VixioTypes;
import me.iblitzkriegi.vixio.util.*;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
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


    public Vixio() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onEnable() {
        try {
            getAddonInstance().loadClasses("me.iblitzkriegi.vixio", "effects", "events", "scopes", "expressions");
            Vixio.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        Metrics mertrics = new Metrics(this);
        Documentation.setupSyntaxFile();
    }

    public static Vixio getInstance() {
        if (instance == null) {
            return null;
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
        VixioTypes.register();
        VixioConverters.register();
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


    public Registration registerPropertyExpression(final Class<? extends Expression> c, final Class<?> type, final String property, final String fromType) {
        Skript.registerExpression(c, type, ExpressionType.PROPERTY, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
        Registration registration = new Registration(c, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
        expressions.add(registration);
        return registration;
    }

}
