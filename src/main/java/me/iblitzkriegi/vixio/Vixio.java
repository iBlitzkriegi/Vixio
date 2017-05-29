package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import me.iblitzkriegi.vixio.commands.VixioCMD;
import me.iblitzkriegi.vixio.registration.DocsStuff;
import me.iblitzkriegi.vixio.registration.TypesAndConverter;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import me.iblitzkriegi.vixio.util.GuildMusicManager;
import me.iblitzkriegi.vixio.util.Metrics;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.classes;

/**
 * Created by Blitz on 10/30/2016.
 */

public class Vixio extends JavaPlugin {
    public static void main(String[] args){

    }
    public static AudioPlayerManager playerManager;
    public static Map<String, GuildMusicManager> musicManagers = new HashMap<>();
    public static Map<AudioPlayer, Guild> reverseGuilds = new HashMap<>();

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
        Skript.registerAddon(this);
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

}



