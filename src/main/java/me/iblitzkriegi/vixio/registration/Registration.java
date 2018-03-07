package me.iblitzkriegi.vixio.registration;

import ch.njol.util.StringUtils;

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

    public Registration(Class<?> cls, String... syntaxes) {
        clazz = cls;
        this.syntaxes = syntaxes;
    }

    public Registration setExample(String s) {
        this.example = s;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Registration setName(String s) {
        this.name = s;
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public Registration setDesc(String s) {
        this.desc = s;
        return this;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String[] getSyntaxes() {
        return this.syntaxes;
    }

    public String getExample() {
        return this.example;
    }

    public Registration setExample(String... s) {
        return setExample(StringUtils.join(s, "\n"));
    }

    public String getUserFacing() {
        return this.userFacing;
    }

    public Registration setUserFacing(String s) {
        this.userFacing = s;
        return this;
    }


}
