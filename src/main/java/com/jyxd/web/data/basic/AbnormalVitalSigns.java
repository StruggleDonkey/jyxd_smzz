package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 异常生命体征表
 */
@Entity
@Table(name = "table_abnormal_vital_signs")
@Data
public class AbnormalVitalSigns implements Serializable {

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
     * 数据时间
     */
    @Column(name = "time")
    private Date time;

    /**
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 数据类型(0：监护仪异常数据；1：单据里得异常数据)
     */
    @Column(name = "data_type")
    private int dataType;

    /**
     * 中心静脉压
     */
    @Column(name = "central_venous_pressure")
    private String centralVenousPressure;

    /**
     * 心率
     */
    @Column(name = "heart_rate")
    private String heartRate;

    /**
     * 血氧饱和度
     */
    @Column(name = "oxygen_saturation")
    private String oxygenSaturation;

    /**
     * 体温
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 血压
     */
    @Column(name = "blood_pressure")
    private String bloodPressure;

    /**
     * 呼吸
     */
    @Column(name = "breathing")
    private String breathing;

    /**
     * 异常生命体征code
     */
    @Column(name = "abnormal_code")
    private String abnormalCode;

    /**
     * 是否处理(0：未处理，1：已处理)
     */
    @Column(name = "is_processed")
    private int isProcessed;

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
