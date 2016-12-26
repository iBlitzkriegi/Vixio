package me.iblitzkriegi.vixio.effects.effBotSetters;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Icon;
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
@EffectAnnotation.Effect(syntax = "[discord] set avatar of %string% to %string%")
public class EffSetAvatar extends Effect {
    Expression<String> vBot;
    Expression<String> vUrl;
    @Override
    protected void execute(Event e) {
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
    private void cacheImage(String url,String name, Event e){
        try {
            JDA jda = EffLogin.bots.get(vBot.getSingle(e));
            jda.getGuildById("t").getMember(jda.getSelfUser()).getRoles();
            File imgf = new File(name + "." + "png");
            BufferedImage img = ImageIO.read(new URL(url));
            ImageIO.write(img, "png", imgf);
            jda.getSelfUser().getManager().setAvatar(Icon.from(imgf)).queue();
            imgf.delete();
        } catch (MalformedURLException x) {
            x.printStackTrace();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}
