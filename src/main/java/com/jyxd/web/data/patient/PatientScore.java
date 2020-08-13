package com.jyxd.web.data.patient;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 病人评分表
 */
@Entity
@Table(name = "p_patient_score")
@Data
public class PatientScore implements Serializable {

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
     * 评分类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 评分时间
     */
    @Column(name = "score_time")
    private Date scoreTime;

    /**
     * 分数
     */
    @Column(name = "score")
    private int score;

    /**
     * 知识库主键
     */
    @Column(name = "score_knowledge_id")
    private String scoreKnowledgeId;

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
     * 绑定护理单时间
     */
    @Column(name = "report_time")
    private Date reportTime;

    /**
     * 绑定评估单时间
     */
    @Column(name = "assessment_time")
    private Date assessmentTime;

    /**
     * 护理措施
     */
    @Column(name = "nursing_step")
    private String nursingStep;

    /**
     * 其他措施
     */
    @Column(name = "other_step")
    private String otherStep;

    /**
     * 签名
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 预计病死率
     */
    @Column(name = "mortality_rate")
    private String mortalityRate;
}
