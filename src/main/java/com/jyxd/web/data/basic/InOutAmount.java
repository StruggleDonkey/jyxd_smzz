package com.jyxd.web.data.basic;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 出入量表（二表合一）
 */
@Entity
@Table(name = "table_in_out_amount")
@Data
public class InOutAmount implements Serializable {

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
     * 入量-药物内容
     */
    @Column(name = "order_name")
    private String orderName;

    /**
     * 入量-实入量
     */
    @Column(name = "dosage")
    private String dosage;

    /**
     * 入量-余量
     */
    @Column(name = "allowance_dosage")
    private String allowanceDosage;

    /**
     * 出量-尿
     */
    @Column(name = "piss")
    private String piss;

    /**
     * 出量-排泄物（便）
     */
    @Column(name = "faces")
    private String faces;

    /**
     * 出量-引流量
     */
    @Column(name = "drainage")
    private String drainage;

    /**
     * 医嘱嘱托
     */
    @Column(name = "remark")
    private String remark;

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
     * 类型（1：主医嘱同步数据  2：子医嘱同步数据）
     */
    @Column(name = "type")
    private int type;
}
