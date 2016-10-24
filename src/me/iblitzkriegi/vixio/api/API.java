package me.iblitzkriegi.vixio.api;

import me.iblitzkriegi.vixio.effects.effGenerals.EffLogin;
import net.dv8tion.jda.JDA;

/**
 * Created by Blitz on 10/14/2016.
 */
public class API {
    private static API instance;
    private API(){instance = this;}
    public static API getAPI(){
        if(instance == null){
            return new API();
        }else{
            return instance;
        }
    }
    public JDA getJDA(){
        return EffLogin.s;
    }
}
