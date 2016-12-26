package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import me.iblitzkriegi.vixio.events.EvntGuildMessageReceive;
import me.iblitzkriegi.vixio.registration.DocsStuff;
import me.iblitzkriegi.vixio.registration.TypesAndConverter;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import me.iblitzkriegi.vixio.util.Metrics;
import me.iblitzkriegi.vixio.util.MultiBotGuildCompare;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sun.audio.AudioPlayer;

import java.io.File;
import java.io.IOException;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.classes;

/**
 * Created by Blitz on 10/30/2016.
 */

public class Vixio extends JavaPlugin {
    public static void main(String[] args){

    }

    public static long startupTime;
    public static File pluginFile;
    public void onEnable() {
        java.util.Date date = new java.util.Date();
        startupTime = date.getTime();
        Skript.registerAddon(this);
        TypesAndConverter.setupTypes();
        VixioAnnotationParser parser = new VixioAnnotationParser();
        startMetrics();
        try {
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }
        pluginFile = this.getDataFolder();
        DocsStuff.setUpSyntaxes();
        this.getLogger().info("Successfully loaded " + classes + " classes.");

    }
    private void startMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
    }


}



