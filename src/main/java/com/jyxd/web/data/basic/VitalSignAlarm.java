package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 生命体征告警设置
 */
@Entity
@Table(name = "table_vital_sign_alarm")
@Data
public class VitalSignAlarm implements Serializable {

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
     * 类型
     */
    @Column(name = "setting_type")
    private String settingType;

    /**
     * 值
     */
    @Column(name = "setting_content")
    private String settingContent;

    /**
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * '表名'
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 模板
     */
    @Column(name = "template")
    private String template;

    /**
     * 单位
     */
    @Column(name = "unit")
    private String unit;

    /**
     * 是否同步至护理单(1：是；0：否)
     */
    @Column(name = "is_sync")
    private int isSync;

    /**
     * 是否告警(1：是；0：否)
     */
    @Column(name = "is_alarm")
    private int isAlarm;

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
