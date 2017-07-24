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
    public static void setupSyntaxes() {
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
        try{
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("View the docs online at http://vixio.space/docs!");
            bw.newLine();
            bw.write("-=Conditions=-");
            bw.newLine();
            int condSize = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vCondTitle.entrySet()){
                bw.write(VixioAnnotationParser.vCondSyntax.get(s.getValue()));
                bw.newLine();
                condSize++;
            }
            int effSize = 0;
            bw.write("-=Effects-=");
            bw.newLine();
            for(Map.Entry<String, String> s : VixioAnnotationParser.vEffTitle.entrySet()){
                bw.write(VixioAnnotationParser.vEffSyntax.get(s.getValue()));
                bw.newLine();
                effSize++;
            }
            int exprSize = 0;
            bw.write("-=Expressions-=");
            bw.newLine();
            for(Map.Entry<String, String> s : VixioAnnotationParser.vExprTitle.entrySet()){
                bw.write(VixioAnnotationParser.vExprSyntax.get(s.getValue()));
                bw.newLine();
                exprSize++;
            }
            int evntSize = 0;
            bw.write("-=Events-=");
            bw.newLine();
            for(Map.Entry<String, String> s : VixioAnnotationParser.vEventTitle.entrySet()){
                bw.write(VixioAnnotationParser.vEventSyntax.get(s.getValue()));
                bw.newLine();
                evntSize++;
            }
            bw.write("-=Statistics=-");
            bw.newLine();
            bw.write("Conditions: " + condSize);
            bw.newLine();
            bw.write("Events: " + evntSize);
            bw.newLine();
            bw.write("Effects: "+ effSize);
            bw.newLine();
            bw.write("Expressions: " + exprSize);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        betaDocs();
    }
   
    private static void setupSyntaxjson(){
        File file = new File(Vixio.pluginFile, "syntaxes.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }
        } else {
            file.delete();
        }
		
		// Start of file
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("{");
        } catch (IOException e) { e.printStackTrace(); }
		
		// Generate conditions
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\"conditions\": [");
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vCondTitle.entrySet()){
				bw.write("{");
				bw.write("\"name\": \"" + VixioAnnotationParser.vCondShowroom.get(s.getValue()) + "\",");
				bw.write("\"description\": \"" + VixioAnnotationParser.vCondDesc.get(s.getValue()) + "\",");
				bw.write("\"syntax\": \"" + VixioAnnotationParser.vCondSyntax.get(s.getValue()) + "\",");
				bw.write("\"example\": \"" + VixioAnnotationParser.vCondExample.get(s.getValue()) + "\"");
                if(i != VixioAnnotationParser.vCondTitle.size() - 1) {
                    bw.write("},");
                    i++;
                }else{
                    bw.write("}");
                    i++;
                }
            }
            bw.write("],");
            bw.flush();
            bw.close();
        } catch (IOException e) { e.printStackTrace(); }
		
		// Generate events
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\"events\": [");
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vEffTitle.entrySet()){
				bw.write("{");
				bw.write("\"name\": \"" + VixioAnnotationParser.vEvntShowroom.get(s.getValue()) + "\",");
				bw.write("\"description\": \"" + VixioAnnotationParser.vEvntDesc.get(s.getValue()) + "\",");
				bw.write("\"syntax\": \"" + VixioAnnotationParser.vEvntSyntax.get(s.getValue()) + "\",");
				bw.write("\"example\": \"" + VixioAnnotationParser.vEvntExample.get(s.getValue()) + "\"");
                if(i != VixioAnnotationParser.vEventTitle.size() - 1) {
                    bw.write("},");
                    i++;
                }else{
                    bw.write("}");
                    i++;
                }
            }
            bw.write("],");
            bw.flush();
            bw.close();
        } catch (IOException e) { e.printStackTrace(); }
		
		// Generate effects
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\"effects\": [");
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vEffTitle.entrySet()){
				bw.write("{");
				bw.write("\"name\": \"" + VixioAnnotationParser.vEffShowroom.get(s.getValue()) + "\",");
				bw.write("\"description\": \"" + VixioAnnotationParser.vEffDesc.get(s.getValue()) + "\",");
				bw.write("\"syntax\": \"" + VixioAnnotationParser.vEffSyntax.get(s.getValue()) + "\",");
				bw.write("\"example\": \"" + VixioAnnotationParser.vEffExample.get(s.getValue()) + "\"");
                if(i != VixioAnnotationParser.vEffTitle.size() - 1) {
                    bw.write("},");
                    i++;
                }else{
                    bw.write("}");
                    i++;
                }
            }
            bw.write("],");
            bw.flush();
            bw.close();
        } catch (IOException e) { e.printStackTrace(); }
		
		// Generate expressions
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\"expressions\": [");
            int i = 0;
            for(Map.Entry<String, String> s : VixioAnnotationParser.vExprTitle.entrySet()){
				bw.write("{");
				bw.write("\"name\": \"" + VixioAnnotationParser.vExprShowroom.get(s.getValue()) + "\",");
				bw.write("\"description\": \"" + VixioAnnotationParser.vExprDesc.get(s.getValue()) + "\",");
				bw.write("\"syntax\": \"" + VixioAnnotationParser.vExprSyntax.get(s.getValue()) + "\",");
				bw.write("\"example\": \"" + VixioAnnotationParser.vExprExample.get(s.getValue()) + "\"");
                if(i != VixioAnnotationParser.vExprTitle.size() - 1) {
                    bw.write("},");
                    i++;
                }else{
                    bw.write("}");
                    i++;
                }
            }
            bw.write("]");
            bw.flush();
            bw.close();
        } catch (IOException e) { e.printStackTrace(); }
		
		// End of file
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
			bw.write("}");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
