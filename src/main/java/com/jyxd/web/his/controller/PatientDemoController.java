package com.jyxd.web.his.controller;

import com.jyxd.web.his.data.commmon.BodyData;
import com.jyxd.web.his.data.commmon.CommonResponse;
import com.jyxd.web.his.data.commmon.HeaderData;
import com.jyxd.web.his.data.patient.PatientRequest;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PatientDemoController {


    @PostMapping(value = "/getHisPatientMessage", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public CommonResponse test(@RequestBody PatientRequest patientRequest){
        CommonResponse commonResponse=new CommonResponse();
        try {
            System.out.println(patientRequest.toString());
            HeaderData h=new HeaderData();
            h.setMessageID("1111");
            h.setSourceSystem("测试");
            commonResponse.setHeader(h);
            BodyData b=new BodyData();
            b.setResultCode("0");
            b.setResultContent("成功");
            commonResponse.setBody(b);

        }catch (Exception e){
            System.out.println(e);
        }
        return commonResponse;
    }

    @RequestMapping(value = "/hip")
    @ResponseBody
    public String hip(@RequestBody(required=false) String map){
        String responseTxt = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!-- type: hsb.DhcEns.Message.Request  id: 3901199 -->\n" +
                "<Request xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:s=\"http://www.w3.org/2001/XMLSchema\">\n";
        if(map==null){
            responseTxt += "<Response>\n<Header>\n<SourceSystem>0"+"</SourceSystem>\n";
            responseTxt += "<MessageID>0"+"</MessageID>\n</Header>\n";
            responseTxt += "<Body>\n" +
                    "<ResultCode>1</ResultCode>\n" +
                    "<ResultContent>"+"测试"+"</ResultContent>\t\n" +
                    "</Body>\n" +
                    "</Response>\n"+
                    "</Request>";
            return responseTxt;
        }
       //System.out.println(map);



        //System.out.println(map);
        List<JSONObject> jsonList=new ArrayList<JSONObject>();
        String[] msgs=new String[]{"-", "-"};;

        boolean ret=getElementsAsJson(map,"InputStream/Request",jsonList,msgs);

        if( ret){
            System.out.println(jsonList.get(0));

            JSONObject obj = jsonList.get(0).getJSONObject("Header");
            responseTxt += "<Response>\n<Header>\n<SourceSystem>"+obj.get("SourceSystem")+"</SourceSystem>\n";
            responseTxt += "<MessageID>"+obj.get("MessageID")+"</MessageID>\n</Header>\n";
            responseTxt += "<Body>\n" +
                    "<ResultCode>0</ResultCode>\n" +
                    "<ResultContent>成功</ResultContent>\t\n" +
                    "</Body>\n" +
                    "</Response>\n"+
                    "</Request>";
        }
        else {
            System.out.println(msgs[0]);
            responseTxt += "<Response>\n<Header>\n<SourceSystem>0"+"</SourceSystem>\n";
            responseTxt += "<MessageID>0"+"</MessageID>\n</Header>\n";
            responseTxt += "<Body>\n" +
                    "<ResultCode>1</ResultCode>\n" +
                    "<ResultContent>"+msgs[0]+"</ResultContent>\t\n" +
                    "</Body>\n" +
                    "</Response>\n"+
                    "</Request>";
        }

        return responseTxt;
    }


    //依据XML内容，根据标签路由定义，返回最后标签指向的内容，以json对象返回
    //xmlText: 入参，原始XML字符串;
    //pathStr: 入参，标签路由定义，必须逐级排列，根标签不写，如 "InputStream/Header/SourceSystem"，遇到数组不再分解直接返回数组对象;
    //jsonList: 出参，返回的json对象集合，目前只放在jsonList[0]里;
    //msgs: 出参，执行结果的文字提示信息，只放在msgs[0]里；
    //返回值: 成功返回true，失败返回false。msgs[0]存放成功或错误提示信息
    private boolean getElementsAsJson(String xmlText, String  pathStr, List<JSONObject> jsonList,String[] msgs) {

        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json;

        msgs[0]="Success";
        jsonList.clear();
        //将xml转成JSON
        try{
            json = xmlSerializer.read(xmlText);
        }
        catch (Exception ex) {
            System.out.println((msgs[0]=ex.getMessage()));
            return false;
        }
        //将JSON转成JSONObject,方便处理,去掉空数组[]
        String jsonText=json.toString();
        jsonText = jsonText.replace("[]","\"\"");
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.fromObject(jsonText);
            System.out.println("---------------");
            System.out.println(jsonObject);
            System.out.println("---------------");
        }
        catch (Exception ex) {
            System.out.println((msgs[0]=ex.getMessage()));
            return false;
        }

        //分解标签,获得路由表
        List<String> pathList= Arrays.asList(pathStr.split("/"));
        Integer pathCount=pathList.size();
        if( pathCount<1 ){
            msgs[0]="No path specified!";
            return false;
        }
        Object obj;
        //沿着路径路由获得最终的json对象
        for(Integer i=0;i<pathCount;i++) {
            //取得当前路径名内容
            obj = jsonObject.get(pathList.get(i));
            if(obj==null){
                System.out.println(pathList.get(i) + " does not exist!");
                if(i==pathCount-1)
                    return false;
                else continue;
            }
            //如果是字符串，需要再次转换成JSON
            if(obj instanceof String) {
                if( i==pathCount-1){
                    jsonObject.clear();
                    jsonObject.put(pathList.get(i),obj.toString());
                    jsonList.add(jsonObject);
                    return true;
                }
                try {
                    jsonText="<Request>"+obj.toString()+"</Request>";
                    json = xmlSerializer.read(jsonText);
                }
                catch (Exception ex) {
                    System.out.println((msgs[0]=ex.getMessage()));
                    return false;
                }
                jsonText=json.toString().replace("[]","\"\"");
                //jsonText = jsonText.replace("[]","\"\"");
                jsonObject = JSONObject.fromObject(jsonText);
            }
            else if(obj instanceof JSONArray) {
                jsonObject.clear();
                jsonObject.put(pathList.get(i),((JSONArray) obj).toArray());
                jsonList.add(jsonObject);
                return true;
            }
            else jsonObject=JSONObject.fromObject(obj);
            //已到最后，返回
            if( i==pathCount-1){
                jsonList.add(jsonObject);
                System.out.println("=======================");
                System.out.println(jsonList);
                return true;
            }
        }
        return true;
    }

}
