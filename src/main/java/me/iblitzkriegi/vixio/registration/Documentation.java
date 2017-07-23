package me.iblitzkriegi.vixio.registration;

import me.iblitzkriegi.vixio.Vixio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Blitz on 7/22/2017.
 */
public class Documentation {
    public static void setupSyntaxFile(){
        File file = new File(Vixio.getInstance().getDataFolder(), "Syntaxes.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }
        }catch (IOException x){

        }
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("-=Effects=-");
            bw.newLine();
            for(Registration reg : Vixio.effects){
                bw.write("name: " + reg.getName());
                bw.newLine();
                bw.write("\tsyntax: " + reg.getSyntaxes()[0]);
                bw.newLine();
                bw.write("\tdescription: " + reg.getDesc());
                bw.newLine();
                bw.write("\texample: " + reg.getExample());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            fw.close();

            /*
            name :
            syntax = yoursyntax
            description : yourdesc
            example : yourdesc
            */


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
