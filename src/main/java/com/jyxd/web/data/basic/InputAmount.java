package com.jyxd.web.data.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 入量表
 */
@Entity
@Table(name = "table_input_amount")
@Data
public class InputAmount implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dataTime;

    /**
     * 入量类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 医嘱编码（同步时使用）
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 医嘱主键
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 子医嘱主键
     */
    @Column(name = "order_sub_no")
    private String orderSubNo;

    /**
     * 医嘱名称
     */
    @Column(name = "order_name")
    private String orderName;

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
     * 医嘱嘱托
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 给药途径
     */
    @Column(name = "use_mode")
    private String useMode;

    /**
     * 核对人签名（）
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 核对人签名（交接班使用）
     */
    @Column(name = "check_signature")
    private String checkSignature;

    /**
     * 病情记录
     */
    @Column(name = "illness_records",length = 2048)
    private String illnessRecords;

    /**
     * 平衡（总入量-总出量）
     */
    @Column(name = "balance")
    private String balance;

}
