package com.jyxd.web.data.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 监护仪数据采集表
 */
@Entity
@Table(name = "table_monitor_data")
@Data
public class MonitorData implements Serializable {

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
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 监护仪code
     */
    @Column(name = "monitorCode")
    private String monitorCode;

    /**
     * 床位code
     */
    @Column(name = "bedCode")
    private String bedCode;

    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 心率
     */
    @Column(name = "hr")
    private String hr;

    /**
     * 体温
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 脉搏
     */
    @Column(name = "pulse")
    private String pulse;

    /**
     * 心律
     */
    @Column(name = "heartRate")
    private String heartRate;

    /**
     * 呼吸
     */
    @Column(name = "br")
    private String br;

    /**
     * 血氧饱和度
     */
    @Column(name = "spo2")
    private String spo2;

    /**
     * 血糖
     */
    @Column(name = "bloodSugar")
    private String bloodSugar;

    /**
     * CVPcmH₂O
     */
    @Column(name = "cvp")
    private String cvp;

    /**
     * CVPmmHg₂O
     */
    @Column(name = "cvpmmHg")
    private String cvpmmHg;

    /**
     * 无创血压
     */
    @Column(name = "bp")
    private String bp;

    /**
     * 有创血压
     */
    @Column(name = "ibp")
    private String ibp;
}
