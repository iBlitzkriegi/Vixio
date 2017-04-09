package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 10/30/2016.
 */
@EffectAnnotation.Effect(
        name = "UploadFile",
        title = "Upload File",
        desc = "Upload a File to a channel",
        syntax = "[discord ]upload file %string% [with message %-string%] to channel [with id] %string% as [bot] %string%",
        example = "SOON"
)
public class EffSendFile extends Effect {
    private Expression<String> sFilePath;
    private Expression<String> sMsg;
    private Expression<String> sChannel;
    private Expression<String> sBot;
    @Override
    protected void execute(Event e) {
        if(bots.get(sBot.getSingle(e))!=null) {
            JDA jda = bots.get(sBot.getSingle(e));
            File file = new File(sFilePath.getSingle(e));
            if(file.exists()){
                try {
                    if(sMsg.getSingle(e)==null) {
                        jda.getTextChannelById(sChannel.getSingle(e)).sendFile(file, null).queue();
                    }else{
                        MessageBuilder m = new MessageBuilder();
                        m.append(sMsg.getSingle(e));
                        Message send = m.build();
                        jda.getTextChannelById(sChannel.getSingle(e)).sendFile(file, send).queue();

                    }
                } catch (IOException e1) {

                }

            }
        }else{
            Skript.warning("Could not find bot with the name: " + sBot.getSingle(e));
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        sFilePath = (Expression<String>) expr[0];
        sMsg = (Expression<String>) expr[1];
        sChannel = (Expression<String>) expr[2];
        sBot = (Expression<String>) expr[3];
        return true;
    }
}
