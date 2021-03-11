package com.jyxd.web.hl7.socketdemo;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket socket = null;
    InetAddress inetAddress=null;//接收客户端的连接

    public ServerThread(Socket socket,InetAddress inetAddress) {
        this.socket = socket;
        this.inetAddress=inetAddress;
    }

    @Override
    public void run() {
        InputStream inputStream = null;//字节输入流
        InputStreamReader inputStreamReader = null;//将一个字节流中的字节解码成字符
        BufferedReader bufferedReader = null;//为输入流添加缓冲
        OutputStream outputStream = null;//字节输出流
        OutputStreamWriter writer = null;//将写入的字符编码成字节后写入一个字节流
        try {
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String info = null;//临时

            /*//循环读取客户端信息
            while ((info = bufferedReader.readLine()) != null) {
                //获取客户端的ip地址及发送数据
                System.out.println("服务器端接收："+"{'from_client':'"+socket.getInetAddress().getHostAddress()+"','data':'"+info+"'}");
            }*/

            socket.shutdownInput();//关闭输入流


            for (int i = 0; i <1100 ; i++) {

                try {
                    //响应客户端请求
                    System.out.println("i = " +i);
                    outputStream = socket.getOutputStream();
                            writer = new OutputStreamWriter(outputStream, "UTF-8");
                    writer.write("MSH|^~\\&|Mindray|Gateway|||||ORU^R01|2|P|2.3.1| <CR>\n" +
                            "PID|||M1015_00010||张^三||20091112|M|||^^^^||| <CR>\n" +
                            "PV1||I|^^ICU&Bed5&3232241659&0&0|||||||||||||||A||||||||||||||\n" +
                            "||||||||||||20091201111211 <CR>\n" +
                            "OBR||||Mindray Monitor|||20091203121631| <CR>\n" +
                            "OBX||NM|52^Height||169.0||||||F <CR>\n" +
                            "OBX||NM|51^Weight||59.0||||||F <CR>\n" +
                            "OBX||CE|2302^BloodType||1^A||||||F <CR>\n" +
                            "OBX||CE|2303^PACE_Switch||0^关||||||F <CR>\n" +
                            "OBX||NM|101^HR|2101|60||||||F <CR>\n" +
                            "OBX||NM|151^RR|2102|20||||||F <CR>\n" +
                            "OBX||NM|200^T1|2104|37.00||||||F <CR>\n" +
                            "OBX||NM|201^T2|2104|37.20||||||F <CR>\n" +
                            "OBX||NM|202^Td|2104|0.20000||||||F <CR>\n" +
                            "OBX||NM|160^SpO2|2103|98||||||F <CR>\n" +
                            "OBX||NM|213^TB|2108|37.20||||||F <CR>\n" +
                            "OBX||NM|500^ART-Sys|2116|120||||||F <CR>\n" +
                            "OBX||NM|501^ART-Mean|2116|93||||||F <CR>\n" +
                            "OBX||NM|502^ART-Dia|2116|80||||||F <CR>\n" +
                            "OBX||NM|503^PA-Sys|2117|20||||||F <CR>\n" +
                            "OBX||NM|504^PA-Mean|2117|12||||||F <CR>\n" +
                            "OBX||NM|505^PA-Dia|2117|8||||||F <CR>\n" +
                            "OBX||NM|506^Ao-Sys|2130|120||||||F <CR>\n" +
                            "OBX||NM|507^Ao-Mean|2130|93||||||F <CR>\n" +
                            "OBX||NM|508^Ao-Dia|2130|80||||||F <CR>\n" +
                            "OBX||NM|515^FAP-Sys|2133|120||||||F <CR>\n" +
                            "OBX||NM|516^FAP-Mean|2133|93||| <CR>\n" +
                            "OBX||NM|171^Dia|2105|80||||||F||APERIODIC|20091203120508 <CR>\n" +
                            "OBX||NM|172^Mean|2105|93||||||F||APERIODIC|20091203120508 <CR>\n" +
                            "OBX||NM|170^Sys|2105|120||||||F||APERIODIC|20091203120508 <CR>");//向客户端发送数据
                    writer.flush();//清空缓冲区数据
                    Thread.sleep(10*1000);
                }catch (Exception e){

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (writer != null) {
                    writer.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}