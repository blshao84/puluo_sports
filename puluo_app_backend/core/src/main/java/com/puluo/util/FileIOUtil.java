/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.puluo.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
 
public class FileIOUtil {
	public static byte[] read(String filename) throws IOException {  
		  
        File f = new File(filename);  
        if (!f.exists()) {  
            throw new FileNotFoundException(filename);  
        }  
  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());  
        BufferedInputStream in = null;  
        try {  
            in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while (-1 != (len = in.read(buffer, 0, buf_size))) {  
                bos.write(buffer, 0, len);  
            }  
            return bos.toByteArray();  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            bos.close();  
        }  
    }  
	
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