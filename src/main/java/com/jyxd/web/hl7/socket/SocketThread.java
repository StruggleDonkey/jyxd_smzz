package com.jyxd.web.hl7.socket;

import com.jyxd.web.data.basic.Monitor;
import com.jyxd.web.service.basic.MonitorService;
import com.jyxd.web.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

/**
 * socket 客户端
 */
public class SocketThread extends Thread {

    private static MonitorService monitorService;//先声明一个service层对象

    private static Logger logger= LoggerFactory.getLogger(SocketThread.class);

    private Socket socket = null;
    private OutputStream os = null;
    private InputStream is = null;

    private String ip="";
    private int port=4601;

    public SocketThread(String ip,int port){
        this.ip=ip;
        this.port=port;
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();
        while (true) {
            try {
                if (socket == null || socket.isClosed()) {
                    socket = new Socket(ip, port); // 连接socket
                    os = socket.getOutputStream();

                    //将socket 放到全局变量里
                    ForeverData.map.put(ip,socket);
                    logger.info("成功放到全局变量里:"+ip+":"+port);
                    logger.info("此时监护仪socket客户端全局变量为:"+ForeverData.map);
                }

                Thread.sleep(100);

                is = socket.getInputStream();
                int size = is.available();
                if (size <= 0) {
                    if ((System.currentTimeMillis() - startTime) > 3 * 10 * 1000) { // 如果超过30秒没有收到服务器发回来的信息，说明socket连接可能已经被远程服务器关闭
                        socket.close(); // 这时候可以关闭socket连接
                        startTime = System.currentTimeMillis();
                        InetAddress address=socket.getInetAddress();
                        logger.info("长时间未传输数据的监护仪ip为:"+address.getHostAddress());
                        logger.info("此时监护仪socket客户端全局变量为："+ForeverData.map.toString());
                        logger.info(""+ForeverData.map.containsKey(address.getHostAddress()));
                        if(ForeverData.map.get(address.getHostAddress())!=null){
                            ForeverData.map.remove(address.getHostAddress());
                            logger.info("此时监护仪socket客户端全局变量为："+ForeverData.map);
                        }
                        logger.info("关闭了一个客户端");
                        break;//跳出while循环
                    }
                    continue;
                } else {
                    startTime = System.currentTimeMillis();
                }
                byte[] resp = new byte[size];
                is.read(resp);
                String response = new String(resp, "utf-8");
                System.out.println("++++++++++++++++++++++"+response);
                Map<String,Object> map=Hl7Util.toHl7(response.trim().replace("<CR>","\r"));//打印数据
                if(map!=null){

                    Monitor monitor=new Monitor();
                    monitor.setArtd(map.get("obx0").toString());
                    monitor.setArtm(map.get("obx1").toString());
                    monitor.setCreattime(new Date());
                    if(monitorService==null){
                        //获取service 通过SpringUtil 类 获取service
                         monitorService = (MonitorService) SpringUtil.getBean(MonitorService.class);
                    }
                    monitorService.insert(monitor);
                }

            } catch (Exception e) {
                logger.info("---------未连接到监护仪--------");
                logger.info(""+e);
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
