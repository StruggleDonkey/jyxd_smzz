package com.jyxd.web.his.data.patient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.Date;

/**
 * his患者基本信息接收 实体类
 * 患者基本信息新增及更新时调用
 * 服务编码	T0004
 */
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)//防止重复序列化
@JacksonXmlRootElement(localName ="Request")
@Data
public class PatientRequest {

    @JacksonXmlProperty(localName = "Header")
    private Header Header;

    @JacksonXmlProperty(localName = "Body")
    private Body Body;

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class Header{
        @JacksonXmlProperty(localName = "SourceSystem")
        private String SourceSystem;//消息来源
        @JacksonXmlProperty(localName = "MessageID")
        private String MessageID;//消息ID
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class Body{
        @JacksonXmlProperty(localName = "PatientRegistryRt",isAttribute = true)
        private PatientRegistryRt PatientRegistryRt;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PatientRegistryRt{
        @JacksonXmlProperty(localName = "PATPatientID")
        private String PATPatientID;//患者主索引 不能为空
        @JacksonXmlProperty(localName = "PATPatientName")
        private String PATPatientName ;//患者姓名 不能为空
        @JacksonXmlProperty(localName = "PATDob")
        private Date PATDob ;//患者出生日期    YYYY-MM-DD hh:mm:ss
        @JacksonXmlProperty(localName = "PATSexCode")
        private String PATSexCode ;//患者性别代码 不能为空
        @JacksonXmlProperty(localName = "PATMaritalStatusCode")
        private String PATMaritalStatusCode ;//患者婚姻状况代码
        @JacksonXmlProperty(localName = "PATNationCode")
        private String PATNationCode ;//患者民族代码
        @JacksonXmlProperty(localName = "PATCountryCode")
        private String PATCountryCode ;//患者国籍代码
        @JacksonXmlProperty(localName = "PATDeceasedDate")
        private Date PATDeceasedDate ;//患者死亡日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "PATDeceasedTime")
        private Date PATDeceasedTime ;//患者死亡时间 患者死亡时间
        @JacksonXmlProperty(localName = "PATHealthCardID")
        private String PATHealthCardID ;//患者健康卡号
        @JacksonXmlProperty(localName = "PATMotherID")
        private String PATMotherID ;//患者母亲ID
        @JacksonXmlProperty(localName = "PATOccupationCode")
        private String PATOccupationCode ;//患者职业代码
        @JacksonXmlProperty(localName = "PATOccupationDesc")
        private String PATOccupationDesc ;//患者职业描述
        @JacksonXmlProperty(localName = "PATWorkPlaceName")
        private String PATWorkPlaceName ;//患者工作单位名称
        @JacksonXmlProperty(localName = "PATWorkPlaceTelNum")
        private String PATWorkPlaceTelNum ;//患者工作单位电话号码

        @JacksonXmlProperty(localName = "PATAddressList")
        private PATAddressList PATAddressList;//封装类

        @JacksonXmlProperty(localName = "PATRelation")
        private PATRelation PATRelation;//封装类

        @JacksonXmlProperty(localName = "PATTelephone")
        private String PATTelephone ;//患者联系电话
        @JacksonXmlProperty(localName = "PATRemarks")
        private String PATRemarks ;//备注
        @JacksonXmlProperty(localName = "UpdateUserCode")
        private String UpdateUserCode ;//最后更新人编码
        @JacksonXmlProperty(localName = "UpdateUserDesc")
        private String UpdateUserDesc ;//最后更新人描述
        @JacksonXmlProperty(localName = "UpdateDate")
        private Date UpdateDate ;//最后更新日期 YYYY-MM-DD
        @JacksonXmlProperty(localName = "UpdateTime")
        private Date UpdateTime ;//最后更新时间 hh:mm:ss
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PATAddressList{
        @JacksonXmlProperty(localName = "PATAddress")
        public PATAddress PATAddress ;//封装类
        @JacksonXmlProperty(localName = "PATIdentity")
        public PATIdentity PATIdentity ;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PATAddress{
        @JacksonXmlProperty(localName = "PATAddressType")
        private String PATAddressType ;//地址类别代码
        @JacksonXmlProperty(localName = "PATAddressDesc")
        private String PATAddressDesc ;//地址
        @JacksonXmlProperty(localName = "PATHouseNum")
        private String PATHouseNum ;//地址-门牌号码
        @JacksonXmlProperty(localName = "PATVillage")
        private String PATVillage ;//地址-村（街、路、弄等）
        @JacksonXmlProperty(localName = "PATCountryside")
        private String PATCountryside ;//地址-乡（镇、街道办事处）
        @JacksonXmlProperty(localName = "PATCountyCode")
        private String PATCountyCode ;//地址-县（区）代码
        @JacksonXmlProperty(localName = "PATCountyDesc")
        private String PATCountyDesc ;//地址-县（区）描述
        @JacksonXmlProperty(localName = "PATCityCode")
        private String PATCityCode ;//地址-市（地区）代码
        @JacksonXmlProperty(localName = "PATCityDesc")
        private String PATCityDesc ;//地址-市（地区）代描述
        @JacksonXmlProperty(localName = "PATProvinceCode")
        private String PATProvinceCode ;//地址-省（自治区、直辖市）代码
        @JacksonXmlProperty(localName = "PATProvinceDesc")
        private String PATProvinceDesc ;//地址-省（自治区、直辖市）描述
        @JacksonXmlProperty(localName = "PATPostalCode")
        private String PATPostalCode ;//地址邮政编码
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PATIdentity{
        @JacksonXmlProperty(localName = "PATIdentityNum")
        private String PATIdentityNum ;//患者证件号码
        @JacksonXmlProperty(localName = "PATIdTypeCode")
        private String PATIdTypeCode ;//患者证件类别代码
        @JacksonXmlProperty(localName = "PATIdTypeDesc")
        private String PATIdTypeDesc ;//患者证件类别描述
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PATRelation{
        @JacksonXmlProperty(localName = "PATRelationCode")
        private String PATRelationCode ;//患者联系人关系代码
        @JacksonXmlProperty(localName = "PATRelationDesc")
        private String PATRelationDesc ;//患者联系人关系描述
        @JacksonXmlProperty(localName = "PATRelationName")
        private String PATRelationName ;//患者联系人姓名
        @JacksonXmlProperty(localName = "PATRelationPhone")
        private String PATRelationPhone ;//患者联系人电话

        @JacksonXmlProperty(localName = "PATRelationAddress")
        public PATRelationAddress PATRelationAddress ;//封装类
    }

    @Data
    @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)
    public static class PATRelationAddress{
        @JacksonXmlProperty(localName = "PATRelationAddressDesc")
        private String PATRelationAddressDesc ;//患者联系人地址
        @JacksonXmlProperty(localName = "PATRelationHouseNum")
        private String PATRelationHouseNum ;//患者联系人地址-门牌号码
        @JacksonXmlProperty(localName = "PATRelationVillage")
        private String PATRelationVillage ;//患者联系人地址-村（街、路、弄等）
        @JacksonXmlProperty(localName = "PATRelationCountryside")
        private String PATRelationCountryside ;//患者联系人地址-乡（镇、街道办事处）
        @JacksonXmlProperty(localName = "PATRelationCountyCode")
        private String PATRelationCountyCode ;//患者联系人地址-县（区）代码
        @JacksonXmlProperty(localName = "PATRelationCountyDesc")
        private String PATRelationCountyDesc ;//患者联系人地址-县（区）描述
        @JacksonXmlProperty(localName = "PATRelationCityCode")
        private String PATRelationCityCode ;//患者联系人地址-市（地区）代码
        @JacksonXmlProperty(localName = "PATRelationCityDesc")
        private String PATRelationCityDesc ;//患者联系人地址-市（地区）描述
        @JacksonXmlProperty(localName = "PATRelationProvinceCode")
        private String PATRelationProvinceCode ;//患者联系人地址-省（自治区、直辖市）代码
        @JacksonXmlProperty(localName = "PATRelationProvinceDesc")
        private String PATRelationProvinceDesc ;//患者联系人地址-省（自治区、直辖市）描述
    }
}
