package me.iblitzkriegi.vixio.util.wrapper;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.SelfUser;

public class Bot {
    private String name;
    private JDA jda;
    private SelfUser selfUser;

    public Bot(String name, JDA jda){
        this.name = name;
        this.jda = jda;
        this.selfUser = jda.getSelfUser();
    }

    public Bot(JDA jda){
        this.jda = jda;
        this.selfUser = jda.getSelfUser();
        this.name = null;
    }

    // Getters \\
    public JDA getJDA(){
        return this.jda;
    }

    public SelfUser getSelfUser() {
        return selfUser;
    }
    public String getName(){
        return this.name;
    }
}
