package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 医嘱执行表
 */
@Entity
@Table(name = "table_med_order_exec")
@Data
public class MedOrderExec implements Serializable {

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
     * 医嘱编码（同步时使用）(唯一)
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 医嘱同批次主键
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
     * 规格
     */
    @Column(name = "specs")
    private String specs;

    /**
     * 是否为药嘱（0：否  1：是）
     */
    @Column(name = "drug_type")
    private int drugType;

    /**
     * 补液类型（如：晶体液、胶体液等）
     */
    @Column(name = "order_attr")
    private String orderAttr;

    /**
     * 计划执行时间
     */
    @Column(name = "default_time_point")
    private Date defaultTimePoint;

    /**
     * 执行开始时间
     */
    @Column(name = "start_time_point")
    private Date startTimePoint;

    /**
     * 执行完成时间
     */
    @Column(name = "complete_time_point")
    private Date completeTimePoint;

    /**
     * 药品一次使用量
     */
    @Column(name = "dosage")
    private String dosage;

    /**
     * 单位
     */
    @Column(name = "dosage_units")
    private String dosageUnits;

    /**
     * 执行总量
     */
    @Column(name = "all_dosage")
    private String allDosage;

    /**
     * 医嘱类型，0：临时医嘱；1：长期医嘱
     */
    @Column(name = "repeat_indicator")
    private int repeatIndicator;

    /**
     * 给药途径
     */
    @Column(name = "use_mode")
    private String useMode;

    /**
     * 用药类别
     */
    @Column(name = "class_type")
    private String classType;

    /**
     * 频次，如：qd,tid等
     */
    @Column(name = "frequency")
    private String frequency;

    /**
     * 流速
     */
    @Column(name = "perform_speed")
    private String performSpeed;

    /**
     * 流速单位
     */
    @Column(name = "speed_units")
    private String speedUnits;

    /**
     * 责任护士编码
     */
        @Column(name = "nurse_code")
    private String nurseCode;

    /**
     * 执行状态，0：未执行；1：执行中；2：执行完毕；3：交班
     */
    @Column(name = "order_status")
    private int orderStatus;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 记录最后修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否已同步到护理单，0：未同步；1：已同步；
     */
    @Column(name = "is_sync")
    private int isSync;

    /**
     * 医嘱每天执行次数 默认1
     */
    @Column(name = "order_exec_num")
    private int orderExecNum;

    /**
     * 剩余同步次数 默认1
     */
    @Column(name = "sync_num")
    private int syncNum;

    /**
     * 最新同步时间
     */
    @Column(name = "recent_sync_time")
    private Date recentSyncTime;

}
