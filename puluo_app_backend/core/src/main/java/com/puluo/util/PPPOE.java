/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puluo.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 *
 * @author Peter
 */

public class PPPOE {
    public static Log log = LogFactory.getLog(PPPOE.class);
                                            //public static Logger log = Logger.getLogger(PPPOE.class);//FIXME:private final static 

    // read an input-stream into a String

    static String loadStream(InputStream in) throws IOException {//FIXME:尽量私有化
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }
        return buffer.toString();
    }

    /**
     * 执行CMD命令,并返回String字符串
     */
    public static boolean executeCmd(String strCmd) throws Exception {//FIXME:异常可能性需要细明

        try {
            Process ps = Runtime.getRuntime().exec(strCmd);
            System.out.print(loadStream(ps.getInputStream()));//FIXME:Log
            System.err.print(loadStream(ps.getErrorStream()));
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    /**
     * 连接ADSL
     */
    public static boolean Connect() throws Exception {//FIXME:方法首字母小写
        log.debug("Connecting PPPOE....");
        
        boolean ret = executeCmd("pon dsl-provider");
        if(ret){
            log.debug("Connecting is success");
        }else{
            log.error("Connecting fails");
        }
        return ret;
    }

    /**
     * 断开ADSL
     */
    public static boolean Disconnect() throws Exception {
        log.debug("Connecting PPPOE....");
        boolean ret = executeCmd("poff dsl-provider");
        if(ret){
            log.debug("Disconnecting is success");
        }else{
            log.error("Disconnecting fails");
        }
        return ret;
    }
    
    public static String GetIP() throws SocketException {
        /*
        try {
            InetAddress thisIP = InetAddress.getLocalHost();
            String IP = thisIP.getHostAddress();
            System.out.println("IP: " + IP);
            return IP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
         */
        Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();//FIXME:泛型
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
            InetAddress  ip = (InetAddress) ni.getInetAddresses().nextElement();
            //System.out.println(ni.getName());
            /*
            if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                System.out.println("=" + ip.getHostAddress());
                return ip.getHostAddress();
            } else {
                ip = null;
            }
             */
            if(ni.getName().indexOf("ppp0") >= 0){
                System.out.println("=" + ip.getHostAddress());
                return ip.getHostAddress();
            }else{
                ip = null;
            }
        }
        return "Error! Not find IP";
    }

    public static void Reconnect() throws Exception {
        PPPOE.Disconnect();
        Thread.sleep(2000);
        PPPOE.Connect();
        Thread.sleep(2000);
    }
    

    public static void main(String[] args) throws Exception {
        PPPOE.Disconnect();
        Thread.sleep(2000);
        PPPOE.Connect();
        Thread.sleep(2000);
        PPPOE.GetIP();
        //PPPOE.executeCmd("ifconfig");
        //Thread.sleep(2000);
        //PPPOE.Disconnect();
        //Thread.sleep(2000);
        //PPPOE.Connect();
        //Thread.sleep(10000);
        //PPPOE.GetIP();
        //PPPOE.executeCmd("ifconfig");
    }
}
