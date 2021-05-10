package com.jyxd.web.his.data.commmon;

import lombok.Data;

import java.util.Date;

@Data
public class PatientRegistryRt {

    private String SourceSystem;//消息来源

    private String MessageID;//消息ID

    private String PATPatientID;//患者主索引 不能为空

    private String PATPatientName ;//患者姓名 不能为空

    private Date PATDob ;//患者出生日期    YYYY-MM-DD hh:mm:ss

    private String PATSexCode ;//患者性别代码 不能为空

    private String PATMaritalStatusCode ;//患者婚姻状况代码

    private String PATNationCode ;//患者民族代码

    private String PATCountryCode ;//患者国籍代码

    private Date PATDeceasedDate ;//患者死亡日期 YYYY-MM-DD

    private Date PATDeceasedTime ;//患者死亡时间 患者死亡时间

    private String PATHealthCardID ;//患者健康卡号

    private String PATMotherID ;//患者母亲ID

    private String PATOccupationCode ;//患者职业代码

    private String PATOccupationDesc ;//患者职业描述

    private String PATWorkPlaceName ;//患者工作单位名称

    private String PATWorkPlaceTelNum ;//患者工作单位电话号码

    private String PATTelephone ;//患者联系电话

    private String PATRemarks ;//备注

    private String UpdateUserCode ;//最后更新人编码

    private String UpdateUserDesc ;//最后更新人描述

    private Date UpdateDate ;//最后更新日期 YYYY-MM-DD

    private Date UpdateTime ;//最后更新时间 hh:mm:ss

    private String PATAddressType ;//地址类别代码

    private String PATAddressDesc ;//地址

    private String PATHouseNum ;//地址-门牌号码

    private String PATVillage ;//地址-村（街、路、弄等）

    private String PATCountryside ;//地址-乡（镇、街道办事处）

    private String PATCountyCode ;//地址-县（区）代码

    private String PATCountyDesc ;//地址-县（区）描述

    private String PATCityCode ;//地址-市（地区）代码

    private String PATCityDesc ;//地址-市（地区）代描述

    private String PATProvinceCode ;//地址-省（自治区、直辖市）代码

    private String PATProvinceDesc ;//地址-省（自治区、直辖市）描述

    private String PATPostalCode ;//地址邮政编码

    private String PATIdentityNum ;//患者证件号码

    private String PATIdTypeCode ;//患者证件类别代码

    private String PATIdTypeDesc ;//患者证件类别描述

    private String PATRelationCode ;//患者联系人关系代码

    private String PATRelationDesc ;//患者联系人关系描述

    private String PATRelationName ;//患者联系人姓名

    private String PATRelationPhone ;//患者联系人电话

    private String PATRelationAddressDesc ;//患者联系人地址

    private String PATRelationHouseNum ;//患者联系人地址z-门牌号码

    private String PATRelationVillage ;//患者联系人地址-村（街、路、弄等）

    private String PATRelationCountryside ;//患者联系人地址-乡（镇、街道办事处）

    private String PATRelationCountyCode ;//患者联系人地址-县（区）代码

    private String PATRelationCountyDesc ;//患者联系人地址-县（区）描述

    private String PATRelationCityCode ;//患者联系人地址-市（地区）代码

    private String PATRelationCityDesc ;//患者联系人地址-市（地区）描述

    private String PATRelationProvinceCode ;//患者联系人地址-省（自治区、直辖市）代码

    private String PATRelationProvinceDesc ;//患者联系人地址-省（自治区、直辖市）描述
}
