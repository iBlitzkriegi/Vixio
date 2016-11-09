package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.utils.AvatarUtil;
import org.bukkit.event.Event;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord ]set avatar of %string% to %string%")
public class EffSetAvatar extends Effect {
    Expression<String> vBot;
    Expression<String> vUrl;
    @Override
    protected void execute(Event e) {
        System.out.println("SALKDJAKSJDNASKJ");
        cacheImage(vUrl.getSingle(e), "Death2america", e);
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vUrl = (Expression<String>) expr[1];
        return true;
    }
    public synchronized void cacheImage(String url, String name, Event e){
        try {
            System.out.println("BUG");
            File imgf = new File(name);
            BufferedImage img = ImageIO.read(new URL(url));
            JDA jda = bots.get(vBot.getSingle(e));
            jda.getAccountManager().setAvatar(AvatarUtil.getAvatar(img)).update();
            imgf.delete();
        } catch (MalformedURLException x) {
            x.printStackTrace();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}
