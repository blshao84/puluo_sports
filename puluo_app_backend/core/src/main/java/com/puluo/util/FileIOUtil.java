/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puluo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
 
public class FileIOUtil {
    public static String read(String fileName, String encoding) {
        StringBuilder fileContent = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, encoding);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
                fileContent.append(System.getProperty("line.separator"));
            }
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

    public static void write(String fileContent, String fileName, String encoding) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
            osw.write(fileContent);
            osw.flush();
            osw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void mkdir(String dirName){
        File f = new File(dirName);
        f.mkdirs();//FIXME:没成功判断
    }
    
    public static void main(String[] args){
        FileIOUtil.write("Hello, World~~~\ntest", "/var/www/html/test.txt", "UTF-8");
        System.out.println(FileIOUtil.read("/var/www/html/test.txt", "UTF-8"));
        FileIOUtil.mkdir("/var/www/html/test");
        FileIOUtil.mkdir("/var/www/html/test2");
        //FileIOUtil.write("Hello, World!", "/var/www/html/test2/test.txt", "UTF-8");
        System.out.println(FileIOUtil.read("/var/www/html/test2/test.txt", "UTF-8"));
    }
} 