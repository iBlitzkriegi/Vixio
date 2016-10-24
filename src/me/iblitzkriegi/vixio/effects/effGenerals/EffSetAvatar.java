package me.iblitzkriegi.vixio.effects.effGenerals;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.dv8tion.jda.utils.AvatarUtil;
import org.bukkit.event.Event;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/23/2016.
 */
public class EffSetAvatar extends Effect {
    private Expression<String> url;
    @Override
    protected void execute(Event e) {
        cacheImage(url.getSingle(e), "Death2america");
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        url = (Expression<String>) expr[0];
        return true;
    }
    public synchronized static void cacheImage(String url, String name){
        try {
            File imgf = new File(name);
            BufferedImage img = ImageIO.read(new URL(url));
            getAPI().getJDA().getAccountManager().setAvatar(AvatarUtil.getAvatar(img)).update();

            imgf.delete();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
