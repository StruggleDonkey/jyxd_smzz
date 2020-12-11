package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 医嘱执行同步表
 */
@Entity
@Table(name = "table_med_order_exec_sync")
@Data
public class MedOrderExecSync implements Serializable {

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
     * 医嘱主键
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 计划执行时间
     */
    @Column(name = "default_time_point")
    private Date defaultTimePoint;

    /**
     * 记录时间
     */
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 入量类型
     */
    @Column(name = "order_type")
    private String orderType;

    /**
     * 给药途径
     */
    @Column(name = "use_mode")
    private String useMode;

    /**
     * 责任护士编码
     */
    @Column(name = "nurse_code")
    private String nurseCode;

    /**
     * 护理记录内容
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


}
