package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评分知识库表
 */
@Entity
@Table(name = "dict_score_knowledge")
@Data
public class ScoreKnowledgeDictionary implements Serializable {

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
     * 评分类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 风险等级
     */
    @Column(name = "level")
    private String level;

    /**
     * 最低分
     */
    @Column(name = "score_knowledge_min")
    private int scoreKnowledgeMin;

    /**
     * 最高分
     */
    @Column(name = "score_knowledge_max")
    private int scoreKnowledgeMax;

    /**
     * 内容
     */
    @Column(name = "description")
    private String description;

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
