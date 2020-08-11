package com.jyxd.web.data.patient;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 病人表
 */
@Entity
@Table(name = "p_patient")
@Data
public class Patient implements Serializable {

    /**
     * 序列
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;

    /**
     * 住院号
     */
    @Column(name = "visit_id")
    private String visitId;

    /**
     * 本次住院号
     */
    @Column(name = "visit_code")
    private String visitCode;

    /**
     * 住院次数
     */
    @Column(name = "visit_num")
    private int visitNum;

    /**
     * 住院时间
     */
    @Column(name = "visit_time")
    private Date visitTime;

    /**
     * 入科时间
     */
    @Column(name = "enter_time")
    private Date enterTime;

    /**
     * 出科时间
     */
    @Column(name = "exit_time")
    private Date exitTime;

    /**
     * 死亡时间
     */
    @Column(name = "death_time")
    private Date deathTime;

    /**
     * 在院标志（0：出科；1：在科）
     */
    @Column(name = "flag")
    private int flag;

    /**
     * 出科方式
     */
    @Column(name = "exit_type")
    private String exitType;

    /**
     * 病人姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 性别(0：女 1：男)
     */
    @Column(name = "sex")
    private int sex;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private String birthday;

    /**
     * 年龄
     */
    @Column(name = "age")
    private String age;

    /**
     * 体重
     */
    @Column(name = "weight")
    private String weight;

    /**
     * 身高
     */
    @Column(name = "height")
    private String height;

    /**
     * 民族
     */
    @Column(name = "race")
    private String race;

    /**
     * 婚姻状况
     */
    @Column(name = "marital_state")
    private String maritalState;

    /**
     * 血型
     */
    @Column(name = "blood_type")
    private String bloodType;

    /**
     * 手术单id
     */
    @Column(name = "operation_id")
    private String operationId;

    /**
     * 诊断编码
     */
    @Column(name = "diagnosis_code")
    private String diagnosisCode;

    /**
     * 诊断名称
     */
    @Column(name = "diagnosis_name")
    private String diagnosisName;

    /**
     * 手术编码
     */
    @Column(name = "operation_code")
    private String operationCode;

    /**
     * 手术名称
     */
    @Column(name = "operation_name")
    private String operationName;

    /**
     * 手术时间
     */
    @Column(name = "operation_time")
    private Date operationTime;

    /**
     * 主管医生工号
     */
    @Column(name = "doctor_code")
    private String doctorCode;

    /**
     * 主管护士工号
     */
    @Column(name = "nurse_code")
    private String nurseCode;

    /**
     * 过敏详细情况描述
     */
    @Column(name = "allergies")
    private String allergies;

    /**
     * 护理级别名称
     */
    @Column(name = "nursing_level")
    private String nursingLevel;

    /**
     * 病情
     */
    @Column(name = "illness_state")
    private String illnessState;

    /**
     * 阳性
     */
    @Column(name = "positive")
    private String positive;

    /**
     * 转入科室编码
     */
    @Column(name = "department_code")
    private String departmentCode;

    /**
     * 转出科室编码
     */
    @Column(name = "to_department_code")
    private String toDepartmentCode;

    /**
     * 病区编码
     */
    @Column(name = "ward_code")
    private String wardCode;

    /**
     * 床号
     */
    @Column(name = "bed_code")
    private String bedCode;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否非计划（0：否 1：是）
     */
    @Column(name = "unplanned")
    private int unplanned;

    /**
     * 是否开始采集（0：否  1：是）
     */
    @Column(name = "is_collect")
    private int isCollect;

    /**
     * 采集开关修改时间
     */
    @Column(name = "modify_collect_time")
    private Date modifyCollectTime;

    /**
     * 采集频率
     */
    @Column(name = "frequency")
    private String frequency;

    /**
     * 采集频率修改时间
     */
    @Column(name = "modify_frequency_time")
    private Date modifyFrequencyTime;

    /**
     * 数据状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 紧急联系人
     */
    @Column(name = "contact_man")
    private String contactMan;

    /**
     * 紧急联系电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 入科信息确认(0:未确认，1:已确认)
     */
    @Column(name = "is_confirmed")
    private int isConfirmed;

    /**
     * 是否急诊术后转入(0:否，1:是)
     */
    @Column(name = "is_emergency_operate")
    private int isEmergencyOperate;

    /**
     * 是否已脑死亡(0:否，1:是)
     */
    @Column(name = "is_brain_death")
    private int isBrainDeath;

    /**
     * 是否因器官捐献而收治(0:否，1:是)
     */
    @Column(name = "is_organ_donation")
    private int isOrganDonation;

    /**
     * 患者入ICU主要疾病分类
     */
    @Column(name = "disease_classification")
    private String diseaseClassification;

    /**
     * 入ICU诊断为感染性休克(0:否，1:是)
     */
    @Column(name = "is_septic_shock")
    private int isSepticShock;
}
