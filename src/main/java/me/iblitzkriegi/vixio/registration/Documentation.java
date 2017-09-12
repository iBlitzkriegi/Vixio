package me.iblitzkriegi.vixio.registration;

/**
 * Created by Blitz on 7/21/2017.
 */
public class Documentation {
    private String vName;
    private String vExample;
    private String vDesc;
    private Class vClass;
    private String[] vSyntaxes;
    public Documentation(Class cls, String[] syntaxes){
        vClass = cls;
        vSyntaxes = syntaxes;
    }
    public Documentation setName(String name){
        vName = name;
        return this;
    }
    public Documentation setDesc(String desc){
        vDesc = desc;
        return this;
    }
    public Documentation setExample(String example){
        vExample = example;
        return this;
    }

    public String getName(){
        return vName;
    }

    public String getDesc(){
        return vDesc;
    }

    public String getExample(){
        return vExample;
    }

    public String[] getSyntaxes(){
        return vSyntaxes;
    }

    public Class getVClass(){
        return vClass;
    }







}
