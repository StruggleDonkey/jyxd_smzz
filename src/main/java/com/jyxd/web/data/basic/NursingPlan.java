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
     * (startDate-0
     * startDate-1
     * startDate-2
     * startDate-3
     * startDate-4
     * startDate-5
     * startDate-6
     * startDate-7
     * startDate-8
     * startDate-9
     * startDate-10
     * startDate-11
     * startDate-12
     * startDate-13
     * startDate-14
     * startDate-15
     * startDate-16
     * startDate-17
     * startDate-18
     * startDate-19
     * startDate-20
     * startDate-21
     * startDate-22
     * startDate-23
     * startDate-24
     * startDate-25
     * startDate-26
     * startDate-27
     * startTime-0
     * startTime-1
     * startTime-2
     * startTime-3
     * startTime-4
     * startTime-5
     * startTime-6
     * startTime-7
     * startTime-8
     * startTime-9
     * startTime-10
     * startTime-11
     * startTime-12
     * startTime-13
     * startTime-14
     * startTime-15
     * startTime-16
     * startTime-17
     * startTime-18
     * startTime-19
     * startTime-20
     * startTime-21
     * startTime-22
     * startTime-23
     * startTime-24
     * startTime-25
     * startTime-26
     * startTime-27
     * plan-0-0
     * plan-1-0
     * plan-2-0
     * plan-3-0
     * plan-4-0
     * plan-5-0
     * plan-6-0
     * plan-7-0
     * plan-7-1
     * plan-8-0
     * plan-9-0
     * plan-10-0
     * plan-11-0
     * plan-11-1
     * plan-11-2
     * plan-12-0
     * plan-12-1
     * plan-12-2
     * plan-13-0
     * plan-14-0
     * plan-15-0
     * plan-16-0
     * plan-17-0
     * plan-17-1
     * plan-17-2
     * plan-17-3
     * plan-17-4
     * plan-18-0
     * plan-18-1
     * plan-18-2
     * plan-18-3
     * plan-18-4
     * plan-18-5
     * plan-18-6
     * plan-19-0
     * plan-19-1
     * plan-19-2
     * plan-19-3
     * plan-20-0
     * plan-20-1
     * plan-21-0
     * plan-21-1
     * plan-21-2
     * plan-21-3
     * plan-21-4
     * plan-22-0
     * plan-23-0
     * plan-24-0
     * plan-24-1
     * plan-25-0
     * plan-26-0
     * plan-27-0
     * signature-0
     * signature-1
     * signature-2
     * signature-3
     * signature-4
     * signature-5
     * signature-6
     * signature-7
     * signature-8
     * signature-9
     * signature-10
     * signature-11
     * signature-12
     * signature-13
     * signature-14
     * signature-15
     * signature-16
     * signature-17
     * signature-18
     * signature-19
     * signature-20
     * signature-21
     * signature-22
     * signature-23
     * signature-24
     * signature-25
     * signature-26
     * signature-27
     * stopDate-0
     * stopDate-1
     * stopDate-2
     * stopDate-3
     * stopDate-4
     * stopDate-5
     * stopDate-6
     * stopDate-7
     * stopDate-8
     * stopDate-9
     * stopDate-10
     * stopDate-11
     * stopDate-12
     * stopDate-13
     * stopDate-14
     * stopDate-15
     * stopDate-16
     * stopDate-17
     * stopDate-18
     * stopDate-19
     * stopDate-20
     * stopDate-21
     * stopDate-22
     * stopDate-23
     * stopDate-24
     * stopDate-25
     * stopDate-26
     * stopDate-27
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
