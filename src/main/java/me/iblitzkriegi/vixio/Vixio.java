package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.util.Metrics;
import net.dv8tion.jda.core.JDA;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class Vixio extends JavaPlugin {
    private static Vixio instance;
    private static SkriptAddon addonInstance;
    public static List<Registration> conditions = new ArrayList<>();
    public static List<Registration> events = new ArrayList<>();
    public static List<Registration> effects = new ArrayList<>();
    public static HashMap<String, JDA> bots = new HashMap<>();
    public static List<JDA> jdas = new ArrayList<>();
    public static List<JDA> tokens = new ArrayList<>();
    @Override
    public void onEnable(){
        Metrics metrics = new Metrics(this);
        addonInstance = Skript.registerAddon(this);
        instance = this;
        Documentation.setupSyntaxFile();
        Vixio.registerEffect(EffLogin.class, "(login|connect) to discord account with token %string% [named %-string%]")
                .setName("Connect effect")
                .setDesc("Login to a bot account with a token")
                .setExample("COMING SOON");

    }
    public static Vixio getInstance(){
        return instance;
    }
    public static SkriptAddon getAddonInstance(){
        return addonInstance;
    }
    public static Registration registerCondition(Class<? extends Condition> cond, String... patterns){
        Skript.registerCondition(cond, patterns);
        Registration registration = new Registration(cond, patterns);
        conditions.add(registration);
        return registration;
    }
    public static Registration registerEvent(String name, Class type, Class clazz, String... patterns){
        Skript.registerEvent(name, type, clazz, patterns);
        Registration registration = new Registration(clazz, patterns).setName(name);
        events.add(registration);
        return registration;
    }
    public static Registration registerEffect(Class<? extends Effect> eff, String... patterns){
        Skript.registerEffect(eff, patterns);
        Registration reg = new Registration(eff, patterns);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        System.out.println("registered " + reg.getSyntaxes()[0]);
        effects.add(reg);
        return reg;
    }
    //    public static <E extends SkriptEvent> SkriptEventInfo<E> registerEvent(String name, Class<E> c, Class<? extends Event>[] events, String... patterns) {


}
