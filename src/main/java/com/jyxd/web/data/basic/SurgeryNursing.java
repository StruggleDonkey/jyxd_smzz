package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 围手术期护理基本信息表
 */
@Entity
@Table(name = "table_surgery_nursing")
@Data
public class SurgeryNursing implements Serializable {

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
     * 维护主键
     */
    @Column(name = "maintenance_id")
    private String maintenanceId;

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
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 记录时间
     */
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 项目code
     * (记录时间：dataTime
     * 入院日期：visitDate
     * 术前诊断：diagnosis
     * 手术日期：operationDate
     * 手术名称：operationName
     * 体温：temperature
     * 脉搏：pulse
     * 心率：hr
     * 呼吸：br
     * 血压：bp
     * 术前宣教：presurgeryTeach
     * 备皮-有：preparedSkin  值为 1
     * 备皮-无：preparedSkin   值为 2
     * 膀胱排空-是：bladderEmpty 值为 1
     * 膀胱排空-否：bladderEmpty 值为 2
     * 检查化验单齐全-是：inspectionAndAssaySheetCompleted  值为 1
     * 检查化验单齐全-否：inspectionAndAssaySheetCompleted  值为 2
     * 心理状态-正常：mentalStatus 值为 1
     * 心理状态-紧张：mentalStatus  值为 2
     * 心理状态-焦虑：mentalStatus  值为 3
     * 静脉通道部位：venousAccessSite
     * 抗生素皮试：antibioticSkinTest
     * 患者离开病房时间：leaveTime
     * 手术期-病房护士签名：leaveNurseSignature
     * 手术后-患者返回病房时间：backTime
     * 手术后-病房护士签名：backNurseSignature
     * 手术后-止血带压迫时间-时长：repressionDuration
     * 手术后-止血带压迫时间-开始时间：repressBeginTime
     * 手术后-止血带压迫时间-结束时间：repressEndTime
     * )
     */
    @Column(name = "code")
    private String code;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作人code
     */
    @Column(name = "operator_code")
    private String operatorCode;

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

}
