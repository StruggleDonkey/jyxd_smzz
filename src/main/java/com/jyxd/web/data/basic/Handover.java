package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 交班记录主表
 */
@Entity
@Table(name = "table_handover")
@Data
public class Handover implements Serializable {

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
     * 交班日期
     */
    @Column(name = "info_date")
    private Date infoDate;

    /**
     * 班别(0:日班 1:晚班 2:夜班)
     */
    @Column(name = "shift")
    private int shift;

    /**
     * 交接班时间
     */
    @Column(name = "handover_time")
    private Date handoverTime;

    /**
     * 交班时间跨度开始时间
     */
    @Column(name = "handover_begin_time")
    private Date handoverBeginTime;

    /**
     * 交班时间跨度结束时间
     */
    @Column(name = "handover_end_time")
    private Date handoverEndTime;

    /**
     * 交班人
     */
    @Column(name = "send_operator")
    private String sendOperator;

    /**
     * 交班备注
     */
    @Column(name = "send_description")
    private String sendDescription;

    /**
     * 接班备注
     */
    @Column(name = "receive_description")
    private String receiveDescription;

    /**
     * 状态（-1:删除，0:草稿箱，1:已交班，2:已接班）
     */
    @Column(name = "status")
    private int status;

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
