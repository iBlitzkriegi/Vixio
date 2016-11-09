package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import me.iblitzkriegi.vixio.registration.DocsStuff;
import me.iblitzkriegi.vixio.registration.TypesAndConverter;
import me.iblitzkriegi.vixio.registration.VixioAnnotationParser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static me.iblitzkriegi.vixio.registration.VixioAnnotationParser.classes;

/**
 * Created by Blitz on 10/30/2016.
 */

public class Vixio extends JavaPlugin {
    public static long startupTime;
    public static File pluginFile;
    public void onEnable() {
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
        DocsStuff.setUpSyntaxes();
        Bukkit.getServer().getWorldContainer().getAbsolutePath();
        this.getLogger().info("Successfully loaded " + classes + " classes.");
        this.getLogger().info("Loaded on OS: " + System.getProperty("os.name") + ". Attempting to download dependencies for MusicPlayer now.");
//        if(System.getProperty("os.name").contains("Linux")){
//            try {
//                downloadFile("https://www.dropbox.com/s/rmho3poq37p0f1q/ffmpeg.exe?dl=1", this.getDataFolder().getAbsolutePath().replaceFirst("Vixio", ""));
//                downloadFile("https://www.dropbox.com/s/upm87x3art0ktd8/ffprobe.exe?dl=1", this.getDataFolder().getAbsolutePath().replaceFirst("Vixio", ""));
//                downloadFile("https://www.dropbox.com/s/okpfiwbgjf64kvs/youtube-dl?dl=1", this.getDataFolder().getAbsolutePath().replaceFirst("Vixio", ""));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }


}



