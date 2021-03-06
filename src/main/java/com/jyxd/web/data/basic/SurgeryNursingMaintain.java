package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 围手术期护理记录表
 */
@Entity
@Table(name = "table_surgery_nursing_maintain")
@Data
public class SurgeryNursingMaintain implements Serializable {

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
     * 维护主键
     */
    @Column(name = "maintenance_id")
    private String maintenanceId;

    /**
     * 维护记录主键
     */
    @Column(name = "maintenance_record_id")
    private String maintenanceRecordId;

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
     * 项目code
     * (T(℃)：temperature
     * HR次/分：hr
     * R次/分：br
     * BPmmHg：bp
     * SpO₂%：spo2
     * 穿刺点渗血：bleed
     * 末梢循环：tipCirculation
     * 静脉置管-名称：veinCatheterName
     * 静脉置管-护理：veinCatheterNursing
     * 特殊用药：specialMedication
     * 病情观察及护理措施：nursingMeasure
     * 签名：signature
     * 记录时间: dataTime
     * )
     */
    @Column(name = "code")
    private String code;

    /**
     * 内容
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

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

}
