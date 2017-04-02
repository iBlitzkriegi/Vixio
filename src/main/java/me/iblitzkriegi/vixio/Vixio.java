package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import me.iblitzkriegi.vixio.registration.DocsStuff;
import me.iblitzkriegi.vixio.registration.TypesAndConverter;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import me.iblitzkriegi.vixio.util.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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
        Metrics metrics = new Metrics(this);
        java.util.Date date = new java.util.Date();
        startupTime = date.getTime();
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
        this.getLogger().info("Successfully loaded " + classes + " classes.");
    }
}



