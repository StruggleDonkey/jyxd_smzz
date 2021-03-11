package com.jyxd.web.hl7.socketdemo;

import com.jyxd.web.hl7.Wang1Jianchashenqing;
import com.jyxd.web.hl7.socket.ForeverData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private OutputStream os = null;
    private InputStream is = null;

    /*public static void main(String[] args) {
        new Client().new SocketThread("localhost",4601).start();
    }*/

    /**
     * 发送心跳包
     */
    public void sendHeartbeat() {
        try {
            String heartbeat = "heart:00001;";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(10 * 1000);// 10s发送一次心跳
                            os.write(heartbeat.getBytes());
                            os.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SocketThread extends Thread {

        private String ip="";
        private int port=4601;

        public SocketThread(String ip,int port){
            ip=this.ip;
            port=this.port;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            //sendHeartbeat();
            while (true) {
                try {
                    if (socket == null || socket.isClosed()) {
                        socket = new Socket(ip, port); // 连接socket
                        os = socket.getOutputStream();

                        //将socket 放到全局变量里
                        ForeverData.map.put("A",socket);
                        System.out.println("成功放到全局变量里");
                        System.out.println(ForeverData.map);

                    }
                    Thread.sleep(100);
                    is = socket.getInputStream();
                    int size = is.available();
                    if (size <= 0) {
                        if ((System.currentTimeMillis() - startTime) > 3 * 10 * 1000) { // 如果超过30秒没有收到服务器发回来的信息，说明socket连接可能已经被远程服务器关闭
                            socket.close(); // 这时候可以关闭socket连接
                            startTime = System.currentTimeMillis();
                            System.out.println("关闭了一个客户端");
                            break;//跳出while循环
                        }
                        continue;
                    } else {
                        startTime = System.currentTimeMillis();
                    }
                    byte[] resp = new byte[size];
                    is.read(resp);
                    String response = new String(resp, "utf-8");
                    System.out.println(response);

                    Wang1Jianchashenqing.toHl7(response.trim().replace("<CR>","\r"));


                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                        is.close();
                        os.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}