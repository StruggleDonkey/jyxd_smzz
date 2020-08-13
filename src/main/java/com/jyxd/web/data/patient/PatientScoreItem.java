package com.jyxd.web.data.patient;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 病人评分明细表
 */
@Entity
@Table(name = "p_patient_score_item")
@Data
public class PatientScoreItem implements Serializable {

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
     * 评分表主键id
     */
    @Column(name = "patient_score_id")
    private String patientScoreId;

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

    /**s
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
     * 评分项主键
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 值
     */
    @Column(name = "content")
    private String content;

    /**
     * 附加值
     */
    @Column(name = "extra_content")
    private String extraContent;

}
