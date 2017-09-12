package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.lang.Condition;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import me.iblitzkriegi.vixio.commands.VixioCMD;
import me.iblitzkriegi.vixio.registration.DocsStuff;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.TypesAndConverter;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import me.iblitzkriegi.vixio.util.Metrics;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.Effect;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.classes;
import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.vCondShowroom;

/**
 * Created by Blitz on 10/30/2016.
 */

public class Vixio extends JavaPlugin {
    public static void main(String[] args){

    }
    public static AudioPlayerManager playerManager;
    public static Map<String, GuildMusicManager> musicManagers = new HashMap<>();
    public static Map<AudioPlayer, Guild> reverseGuilds = new HashMap<>();
    public static ArrayList<Documentation> conditionObjects = new ArrayList<>();
    public static ArrayList<Documentation> effectObjects = new ArrayList<>();

    public static long startupTime;
    public static File pluginFile;
    private static Plugin p;
    public void onEnable() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
        Metrics metrics = new Metrics(this);
        java.util.Date date = new java.util.Date();
        startupTime = date.getTime();
        p = this;
        SkriptAddon addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("me.iblitzkriegi.vixio", "conditions");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TypesAndConverter.setupTypes();
        VixioAnnotationParser parser = new VixioAnnotationParser();
        try {
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }
        pluginFile = this.getDataFolder();
        DocsStuff.setupSyntaxes();
        p.getConfig().addDefault("api-key", "enter-api-key-here");
        p.getConfig().options().copyDefaults(true);
        p.saveConfig();
        this.getCommand("vixio").setExecutor(new VixioCMD());

        this.getLogger().info("Successfully loaded " + classes + " classes.");
    }
    public static Plugin getPl(){
        return p;
    }
    public static Documentation registerCondition(Class<? extends Condition> cond, String... patterns){
        Skript.registerCondition(cond, patterns);
        Documentation docs = new Documentation(cond, patterns);
        conditionObjects.add(docs);
        return docs;
    }
    public static Documentation registerEffect(Class<? extends ch.njol.skript.lang.Effect> eff, String... patterns){
        Skript.registerEffect(eff, patterns);
        Documentation docs = new Documentation(eff, patterns);
        effectObjects.add(docs);
        return docs;
    }

}



