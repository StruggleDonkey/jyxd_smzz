package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 关键事件表
 */
@Entity
@Table(name = "table_key_event")
@Data
public class KeyEvent implements Serializable {

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
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 事件发生时间
     */
    @Column(name = "event_time")
    private Date eventTime;

    /**
     * 事件等级（1：低  2：中  ：3：高）
     */
    @Column(name = "event_level")
    private int eventLevel;

    /**
     * 事件类型
     */
    @Column(name = "event_type")
    private String eventType;

    /**
     * 事件内容
     */
    @Column(name = "event_content")
    private String eventContent;

    /**
     * 是否显示到时间轴（0：不显示  1：显示）
     */
    @Column(name = "is_show")
    private int isShow;

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
