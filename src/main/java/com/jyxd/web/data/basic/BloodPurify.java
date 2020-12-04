package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 血液净化基本信息表
 */
@Entity
@Table(name = "table_blood_purify")
@Data
public class BloodPurify implements Serializable {

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
     * 导管维护主键
     */
    @Column(name = "maintenance_id")
    private String maintenanceId;

    /**
     * 记录时间
     */
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 项目code
     * (穿刺部位左：punctureLeft
     * 穿刺部位右：punctureRight
     * 股静脉：thighVein
     * 颈静脉：neckVein
     * 锁骨下静脉：clavicleVein
     * 内瘘：innerVein
     * 开始时间：beginTime
     * 结束时间：endTime
     * 共计时间：duration
     * 预冲液：preWashLiquid
     * 抗凝剂-肝素：heparin
     * 抗凝剂-低分子肝素：lowMolecularHeparin
     * 抗凝剂-阿加曲班：agatroban
     * 抗凝剂-枸橼酸抗凝剂：citrate
     * 封管液：sealLiquid
     * 风管时间：sealTime
     * 治疗模式-CVVH:cvvh
     * 治疗模式-CVVHD:cvvhd
     * 治疗模式-CVVHDF:cvvhdf
     * 治疗模式-PE:pe
     * 治疗模式-HP:hp
     * 开始治疗者：beginTreater
     * 查对者：checker
     * 结束治疗者：endTreater
     * 医生签字：doctorSignature
     * 记录时间：dataTime)
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
