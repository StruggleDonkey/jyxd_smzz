package com.jyxd.web.hl7.socket;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * hl7 工具类
 * 将数据转化为可视化数据
 */
public class Hl7Util {

    private static Logger logger= LoggerFactory.getLogger(Hl7Util.class);

    public static Map<String,Object> toHl7(String msg){
        Map<String,Object> map=new HashMap<>();
        try {
            PipeParser pipeParser = new PipeParser();
            Message message = pipeParser.parse(msg);


            Terser terser = new Terser(message);
            System.out.println("terser-------------------------------------:"+terser.toString());
            String sex=terser.get("/.PID-8");//性别
            System.out.println("性别:"+sex);
            String obx0=terser.get("/.OBX(0)-5");//obx
            System.out.println("obx0:"+obx0);
            String obx1=terser.get("/.OBX(1)-5");//obx
            System.out.println("obx1:"+obx1);
            String givenName = terser.get("/.PID-5-2");//姓名
            System.out.println("姓名:"+givenName);
            String birthDate=terser.get("/.PID-7");
            SimpleDateFormat fomater = new SimpleDateFormat("yyyyMMddHHmmss");
            String ybkh=terser.get("/.PID-3-1");//就诊编号
            System.out.println("医保卡号:"+ybkh);
            String hzfl=terser.get("/.PV1-2");
            String zybj=hzfl.equals("I")?"1":"0";//住院标记
            System.out.println("是否住院:"+zybj);
            String departmentCode="FSK001";//挂号科室
            String PatientType="100";//就诊类型
            String tel=terser.get("/.PID-13-7");//电话号码
            System.out.println("电话号码:"+tel);
            String zybh=terser.get("/.PV1-19");//住院编号
            System.out.println("住院编号:"+zybh);
            String cwh=terser.get("/.PV1-3-3");//床位号
            System.out.println("床位号:"+cwh);
            String ghzq="FSK001";//挂号诊区
            String jcbw=terser.get("/.OBR-4");//检查部位
            System.out.println("检查部位:"+jcbw+terser.get("/.OBR-4-2"));
            String sqdID=terser.get("/.OBR-2");//申请单ID
            System.out.println("申请单ID:"+sqdID);

            map.put("obx0",obx0);
            map.put("obx1",obx1);

        }catch (Exception e){
            logger.error("Hl7Util--出错了出错了："+e);
        }
            return map;
    }

}
