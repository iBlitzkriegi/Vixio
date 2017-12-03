package me.iblitzkriegi.vixio.util;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.DiscordEventHandler;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 11/8/2017.
 */
public class DiscordEventCompare extends SkriptEvent{
    private int theInt;
    private static Class<?> event;

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        this.event = Vixio.getInstance().jdaEvents.get(i);
        theInt = i;
        return true;
    }

    @Override
    public boolean check(Event event) {
        if(event instanceof DiscordEventHandler){
            String t = Vixio.getInstance().jdaEvents.get(theInt).getSimpleName();
            String r = ((DiscordEventHandler) event).getJdaEvent().getClass().getSimpleName();
            if(t.equalsIgnoreCase(r)){
                return true;
            }else{
                return false;
            }

        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return Vixio.getPattern(event.getClass());
    }

    public static Class getEvent(){
        return event;
    }
}
