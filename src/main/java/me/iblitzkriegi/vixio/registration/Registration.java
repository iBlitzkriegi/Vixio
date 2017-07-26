package me.iblitzkriegi.vixio.registration;

/**
 * Created by Blitz on 7/22/2017.
 */
// Vixio.registerCondition(cls, patterns).setName().setDesc();
public class Registration {

    private String vName;
    private String vDesc;
    private String vExample;
    private Class<?> vClass;
    private String[] vSyntaxes;
    public Registration(Class<?> cls, String... syntaxes){
        vClass = cls;
        vSyntaxes = syntaxes;
    }
    public Registration setName(String s){
        vName = s;
        return this;
    }
    public Registration setDesc(String s){
        vDesc = s;
        return this;
    }
    public Registration setExample(String s){
        vExample = s;
        return this;
    }
    public String getName(){
        return vName;
    }
    public String getDesc(){
        return vDesc;
    }
    public Class<?> getVClass(){
        return vClass;
    }
    public String[] getSyntaxes(){
        return vSyntaxes;
    }
    public String getExample(){
        return vExample;
    }


}
