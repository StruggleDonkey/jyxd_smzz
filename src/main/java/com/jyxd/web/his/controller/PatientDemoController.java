package com.jyxd.web.his.controller;

import com.jyxd.web.his.data.TicketRequest;
import com.jyxd.web.his.data.patient.PatientRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class PatientDemoController {


    @PostMapping(value = "/getHisPatientMessage", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public PatientRequest test(@RequestBody TicketRequest ticketRequest){
        PatientRequest patientRequest=new PatientRequest();
        try {
            PatientRequest.Header header=new PatientRequest.Header();
            header.setSourceSystem("接口一");
            header.setMessageID("001");

            PatientRequest.Body body=new PatientRequest.Body();

            PatientRequest.PatientRegistryRt patientRegistryRt=new PatientRequest.PatientRegistryRt();

            patientRegistryRt.setPATCountryCode("中国");
            patientRegistryRt.setPATDeceasedDate(new Date());
            patientRegistryRt.setPATHealthCardID("14563685962");

            PatientRequest.PATAddressList patAddressList=new PatientRequest.PATAddressList();


            PatientRequest.PATAddress patAddress=new PatientRequest.PATAddress();
            patAddress.setPATAddressDesc("娃哈哈");
            patAddress.setPATCityCode("999");

            PatientRequest.PATIdentity patIdentity=new PatientRequest.PATIdentity();
            patAddress.setPATCityDesc("啊啊啊啊啊");
            patAddress.setPATHouseNum("大房子");

            PatientRequest.PATRelation patRelation=new PatientRequest.PATRelation();
            patRelation.setPATRelationCode("EEEEEEEEEE");

            PatientRequest.PATRelationAddress patRelationAddress=new PatientRequest.PATRelationAddress();
            patRelationAddress.setPATRelationCityCode("的就爱你");
            patRelationAddress.setPATRelationCountyDesc("哦哦哦哦哦哦");

            patRelation.setPATRelationAddress(patRelationAddress);
            patientRegistryRt.setPATRelation(patRelation);
            patAddressList.setPATIdentity(patIdentity);
            patAddressList.setPATAddress(patAddress);
            patientRegistryRt.setPATAddressList(patAddressList);
            body.setPatientRegistryRt(patientRegistryRt);
            patientRequest.setHeader(header);
            patientRequest.setBody(body);

            System.out.println(patientRequest.toString());

        }catch (Exception e){
            System.out.println(e);
        }
        return patientRequest;
    }

}
