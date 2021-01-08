package com.jyxd.web.data.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 重症护理计划表
 */
@Entity
@Table(name = "table_nursing_plan")
@Data
public class NursingPlan implements Serializable {

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
     * 评估单主键
     */
    @Column(name = "assessment_id")
    private String assessmentId;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 项目code
     * (startDate_0
     * startDate_1
     * startDate_2
     * startDate_3
     * startDate_4
     * startDate_5
     * startDate_6
     * startDate_7
     * startDate_8
     * startDate_9
     * startDate_10
     * startDate_11
     * startDate_12
     * startDate_13
     * startDate_14
     * startDate_15
     * startDate_16
     * startDate_17
     * startDate_18
     * startDate_19
     * startDate_20
     * startDate_21
     * startDate_22
     * startDate_23
     * startDate_24
     * startDate_25
     * startDate_26
     * startDate_27
     * startTime_0
     * startTime_1
     * startTime_2
     * startTime_3
     * startTime_4
     * startTime_5
     * startTime_6
     * startTime_7
     * startTime_8
     * startTime_9
     * startTime_10
     * startTime_11
     * startTime_12
     * startTime_13
     * startTime_14
     * startTime_15
     * startTime_16
     * startTime_17
     * startTime_18
     * startTime_19
     * startTime_20
     * startTime_21
     * startTime_22
     * startTime_23
     * startTime_24
     * startTime_25
     * startTime_26
     * startTime_27
     * plan_0_0
     * plan_1_0
     * plan_2_0
     * plan_3_0
     * plan_4_0
     * plan_5_0
     * plan_6_0
     * plan_7_0
     * plan_7_1
     * plan_8_0
     * plan_9_0
     * plan_10_0
     * plan_11_0
     * plan_11_1
     * plan_11_2
     * plan_12_0
     * plan_12_1
     * plan_12_2
     * plan_13_0
     * plan_14_0
     * plan_15_0
     * plan_16_0
     * plan_17_0
     * plan_17_1
     * plan_17_2
     * plan_17_3
     * plan_17_4
     * plan_18_0
     * plan_18_1
     * plan_18_2
     * plan_18_3
     * plan_18_4
     * plan_18_5
     * plan_18_6
     * plan_19_0
     * plan_19_1
     * plan_19_2
     * plan_19_3
     * plan_20_0
     * plan_20_1
     * plan_21_0
     * plan_21_1
     * plan_21_2
     * plan_21_3
     * plan_21_4
     * plan_22_0
     * plan_23_0
     * plan_24_0
     * plan_24_1
     * plan_25_0
     * plan_26_0
     * plan_27_0
     * signature_0
     * signature_1
     * signature_2
     * signature_3
     * signature_4
     * signature_5
     * signature_6
     * signature_7
     * signature_8
     * signature_9
     * signature_10
     * signature_11
     * signature_12
     * signature_13
     * signature_14
     * signature_15
     * signature_16
     * signature_17
     * signature_18
     * signature_19
     * signature_20
     * signature_21
     * signature_22
     * signature_23
     * signature_24
     * signature_25
     * signature_26
     * signature_27
     * stopDate_0
     * stopDate_1
     * stopDate_2
     * stopDate_3
     * stopDate_4
     * stopDate_5
     * stopDate_6
     * stopDate_7
     * stopDate_8
     * stopDate_9
     * stopDate_10
     * stopDate_11
     * stopDate_12
     * stopDate_13
     * stopDate_14
     * stopDate_15
     * stopDate_16
     * stopDate_17
     * stopDate_18
     * stopDate_19
     * stopDate_20
     * stopDate_21
     * stopDate_22
     * stopDate_23
     * stopDate_24
     * stopDate_25
     * stopDate_26
     * stopDate_27
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
