package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 出量表
 */
@Entity
@Table(name = "table_output_amount")
@Data
public class OutputAmount implements Serializable {

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
     * 记录时间
     */
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 出量类型
     */
    @Column(name = "output_type")
    private String outputType;

    /**
     * 出量名称
     */
    @Column(name = "output_name")
    private String outputName;

    /**
     * 剂量
     */
    @Column(name = "dosage")
    private String dosage;

    /**
     * 单位
     */
    @Column(name = "dosage_units")
    private String dosageUnits;

    /**
     * 速度
     */
    @Column(name = "speed")
    private String speed;

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

    /**
     * 核对人签名
     */
    @Column(name = "check_signature")
    private String checkSignature;

    /**
     * 尿
     */
    @Column(name = "piss")
    private String piss;

    /**
     * 排泄物（便）
     */
    @Column(name = "faces")
    private String feces;

    /**
     * 引流量
     */
    @Column(name = "drainage")
    private String drainage;

}
