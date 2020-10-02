package me.iblitzkriegi.vixio.util;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShardReadyEvent extends ListenerAdapter {

    public static int readyEvents;

    @Override
    public void onReady(ReadyEvent event) {
        readyEvents++;
        System.out.println(readyEvents);
    }

}
