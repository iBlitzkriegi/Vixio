package me.iblitzkriegi.vixio.registration;

import me.iblitzkriegi.vixio.Vixio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Blitz on 11/2/2016.
 */
public class DocsStuff {
    public static void setupSyntaxes(){
        File oldfile = new File(Vixio.pluginFile, "Syntaxs.txt");
        if(oldfile.exists()){
            oldfile.delete();
        }
        File file = new File(Vixio.pluginFile, "Syntaxes.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
        }
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("{\n\t\"conditions\":{");
            bw.newLine();
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vCondTitle.entrySet()){
                if(i != VixioAnnotationParser.vCondTitle.size() - 1) {
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vCondShowroom.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vCondSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vCondExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vCondDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t},");
                    bw.newLine();
                    i++;
                }else{
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vCondShowroom.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vCondSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vCondExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vCondDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t}");
                    bw.newLine();
                    i++;
                }
            }
            bw.write("\t},");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\t\"events\":{");
            bw.newLine();
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vEventTitle.entrySet()){
                if(i != VixioAnnotationParser.vEventTitle.size() - 1) {
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vEvntShowroom.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vEventSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vEventExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vEventDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t},");
                    bw.newLine();
                    i++;
                }else {
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vEvntShowroom.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vEventSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vEventExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vEventDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t}");
                    bw.newLine();
                    i++;
                }
            }
            bw.write("\t},");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\t\"effects\":{");
            bw.newLine();
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vEffTitle.entrySet()){
                if(i != VixioAnnotationParser.vEffTitle.size() - 1) {
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vEffShowroom.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vEffSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vEffExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vEffDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t},");
                    bw.newLine();
                    i++;
                }else{
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vEffTitle.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vEffSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vEffExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vEffDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t}");
                    bw.newLine();
                    i++;
                }
            }
            bw.write("\t},");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\t\"expressions\":{");
            bw.newLine();
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vExprTitle.entrySet()){
                if(i != VixioAnnotationParser.vExprTitle.size() - 1) {
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vExprShowroom.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vExprSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vExprExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vExprDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t},");
                    bw.newLine();
                    i++;
                }else{
                    bw.write("\t\t\"" + s.getValue() + "\":{");
                    bw.newLine();
                    bw.write("\t\t\t\"title\":\"" + VixioAnnotationParser.vExprTitle.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"syntax\":\"" + VixioAnnotationParser.vExprSyntax.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"example\":\"" + VixioAnnotationParser.vExprExample.get(s.getValue()) + "\",");
                    bw.newLine();
                    bw.write("\t\t\t\"description\":\"" + VixioAnnotationParser.vExprDesc.get(s.getValue()) + "\"");
                    bw.newLine();
                    bw.write("\t\t}");
                    bw.newLine();
                    i++;
                }
            }
            bw.write("\t}");
            bw.newLine();
            bw.write("}");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

