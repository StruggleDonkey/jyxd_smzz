package com.jyxd.web.data.log;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ICU日志表
 */
@Entity
@Table(name = "l_icu_log")
@Data
public class IcuLog implements Serializable {

    /**
     * 序列
     */
    private static final long    serialVersionUID = 1L;

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
     *  本次住院号
     */
    @Column(name = "visit_code")
    private String visitCode;

    /**
     * patient_key
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 事件英文名
     */
    @Column(name = "log_type")
    private String logType;

    /**
     * 事件模板主键
     */
    @Column(name = "item_id")
    private String itemId;

    /**
     * 发生时间
     */
    @Column(name = "happen_time")
    private Date happenTime;

    /**
     * 事件发生描述
     */
    @Column(name = "happen_content")
    private String happenContent;

    /**
     * 事件发生记录人
     */
    @Column(name = "happen_operator_code")
    private String happenOperatorCode;

    /**
     * 事件终止类型(0：非计划 1：计划)
     */
    @Column(name = "finish_type")
    private int finishType;

    /**
     * 终止时间
     */
    @Column(name = "finish_time")
    private Date finishTime;

    /**
     * 事件终止描述
     */
    @Column(name = "finish_content")
    private String finishContent;

    /**
     * 事件终止记录人
     */
    @Column(name = "finish_operator_code")
    private String finishOperatorCode;

    /**
     * 同步数据类型code
     */
    @Column(name = "syn_value")
    private String synValue;

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
