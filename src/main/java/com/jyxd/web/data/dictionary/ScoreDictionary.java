package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评分字典表
 */
@Entity
@Table(name = "dict_score")
@Data
public class ScoreDictionary implements Serializable {

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
     * 评分类型（确保唯一 和 评分知识库表、评分项字典表关联）
     */
    @Column(name = "type")
    private String type;

    /**
     * 评分分类   (重症相关、儿科相关、麻醉相关)
     */
    @Column(name = "score_type")
    private String scoreType;

    /**
     * 评分名称
     */
    @Column(name = "score_name")
    private String scoreName;

    /**
     * 评分描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 排序
     */
    @Column(name = "sort_num")
    private int sortNum;

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

    /**
     * 是否同步至护理单(0：否  1：是)
     */
    @Column(name = "syn_report")
    private int synReport;

    /**
     * 关联护理单类型
     */
    @Column(name = "report_type")
    private String reportType;

    /**
     * 护理单关联类型code
     */
    @Column(name = "report_code")
    private String reportCode;

    /**
     * 是否同步至护理记录(0：否  1：是)
     */
    @Column(name = "syn_nursing")
    private int synNursing;

    /**
     * 是否同步至评估单(0：否  1：是)
     */
    @Column(name = "syn_assessment")
    private int synAssessment;

    /**
     * 关联评估单类型
     */
    @Column(name = "assessment_type")
    private String assessmentType;

    /**
     * 评估单关联类型code
     */
    @Column(name = "assessment_code")
    private String assessmentCode;

    /**
     * 图例
     */
    @Column(name = "legend")
    private String legend;

    /**
     * 是否带统计(0：不带 1：带)
     */
    @Column(name = "has_statistics")
    private int hasStatistics;

    /**
     * 日期格式  默认'YYYY-MM-DD HH:mm:ss'
     */
    @Column(name = "date_type")
    private String dateType;

    /**
     * 评分规则
     */
    @Column(name = "scoreRule")
    private String scoreRule;

    /**
     * 评估时机
     */
    @Column(name = "score_opportunity")
    private String scoreOpportunity;

    /**
     * 护理措施
     */
    @Column(name = "nursing_step")
    private String nursingStep;

    /**
     * 是否录入护理措施（0：否 1：是）
     */
    @Column(name = "show_nursing")
    private int showNursing;

    /**
     * 是否录入其他措施（0：否 1：是）
     */
    @Column(name = "show_other_step")
    private int showOtherStep;

    /**
     * 是否录入签名（0：否 1：是）
     */
    @Column(name = "show_signature")
    private int showSignature;

    /**
     * 是否允许智能评分(0:否，1:是)
     */
    @Column(name = "show_smart_score")
    private int showSmartScore;
}
