package util;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Helper class to read and write files.
 */
public class ReadWriteUtil {
    public static String readFile(String path){
        StringBuffer buffer = new StringBuffer();
        try{
            InputStream inStr = new FileInputStream(path);
            InputStreamReader inStrReader = new InputStreamReader(inStr, StandardCharsets.UTF_8);
            BufferedReader bufferreader = new BufferedReader(inStrReader);
            String line;
            while((line=bufferreader.readLine())!=null){
                buffer.append(line);
                buffer.append("\n");
            }
            bufferreader.close();
            inStrReader.close();
            inStr.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static void writeFile(String path,String s){
        try{
            File newfile = new File(path);
            newfile.createNewFile();
            FileOutputStream fos=new FileOutputStream(newfile);
            OutputStreamWriter osw=new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw=new BufferedWriter(osw);
            bw.write(s);
            bw.close();
            osw.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeFileAppend(String path, String s){
        try{
            File newfile = new File(path);
            File fileParent = newfile.getParentFile();
            if(!fileParent.exists()) {
                fileParent.mkdirs();
            }
            newfile.createNewFile();
            FileOutputStream fos=new FileOutputStream(newfile,true);
            OutputStreamWriter osw=new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw=new BufferedWriter(osw);
            bw.write(s);
            bw.close();
            osw.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
