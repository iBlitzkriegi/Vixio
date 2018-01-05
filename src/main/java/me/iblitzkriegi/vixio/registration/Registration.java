package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.lang.Effect;
import ch.njol.util.StringUtils;

import java.util.ArrayList;

/**
 * Created by Blitz on 7/22/2017.
 */
// Vixio.registerCondition(cls, patterns).setName().setDesc();
public class Registration {

    private String name;
    private String desc;
    private String example;
    private Class<?> clazz;
    private String[] syntaxes;
    private String userFacing;

    public Registration(Class<?> cls, String... syntaxes){
        clazz = cls;
        this.syntaxes = syntaxes;
    }

    public Registration(Class clazz, ArrayList<String> patterns) {
        this.clazz = clazz;
        this.syntaxes = patterns.toArray(new String[0]);
    }

    public Registration setName(String s){
        this.name = s;
        return this;
    }
    public Registration setDesc(String s){
        this.desc = s;
        return this;
    }
    public Registration setExample(String s){
        this.example = s;
        return this;
    }

    // makes documenting scopes (and other multi line examples) easier
    public Registration setExample(String... s) {
        return setExample(StringUtils.join(s, "\n"));
    }

    public Registration setUserFacing(String s){
        this.userFacing = s;
        return this;
    }

    public String getName(){
        return this.name;
    }
    public String getDesc(){
        return this.desc;
    }
    public Class<?> getClazz(){
        return clazz;
    }
    public String[] getSyntaxes(){
        return this.syntaxes;
    }
    public String getExample(){
        return this.example;
    }
    public String getUserFacing(){return this.userFacing;}


}
