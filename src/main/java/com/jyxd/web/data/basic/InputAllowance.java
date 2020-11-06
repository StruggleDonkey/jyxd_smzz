package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 入量余量表
 */
@Entity
@Table(name = "table_input_allowance")
@Data
public class InputAllowance implements Serializable {

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
     * 入量类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 医嘱主键
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 班别(0:日班 1:晚班 2:夜班)
     */
    @Column(name = "shift")
    private int shift;

    /**
     * 记录时间
     */
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 使用剂量
     */
    @Column(name = "dosage")
    private String dosage;

    /**
     * 剩余剂量
     */
    @Column(name = "allowance_dosage")
    private String allowanceDosage;

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
