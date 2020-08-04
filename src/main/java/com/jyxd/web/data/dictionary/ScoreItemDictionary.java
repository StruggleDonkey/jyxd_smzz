package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评分项字典表
 */
@Entity
@Table(name = "dict_score_item")
@Data
public class ScoreItemDictionary implements Serializable {

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
     * 评分字典表主键ID
     */
    @Column(name = "score_id")
    private String scoreId;

    /**
     * 评分类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 评分项名称
     */
    @Column(name = "score_item_name")
    private String scoreItemName;

    /**
     * 分数
     */
    @Column(name = "score")
    private int score;

    /**
     * 条件最小值
     */
    @Column(name = "score_item_min")
    private String scoreItemMin;

    /**
     * 条件最大值
     */
    @Column(name = "score_item_max")
    private String scoreItemMax;

    /**
     * 描述
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
     * 展现形式(0：多选  1：单选)
     */
    @Column(name = "mode")
    private int mode;

    /**
     * 是否按数量评分(0：否  1：是)
     */
    @Column(name = "by_count")
    private int byCount;

    /**
     * 是否隐藏标题(0：否  1：是)
     */
    @Column(name = "hide_title")
    private int hideTitle;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 排列方式(0：横向排列  1：纵向排列)
     */
    @Column(name = "show_mode")
    private int showMode;

    /**
     * 强制水平排列(0：否  1：是)
     */
    @Column(name = "force_horizon")
    private int forceHorizon;

    /**
     * 影响该组数据翻倍
     */
    @Column(name = "double_score")
    private int doubleScore;

    /**
     * 组号
     */
    @Column(name = "group_num")
    private String groupNum;

    /**
     * 计算分数
     */
    @Column(name = "calculate_num")
    private double calculateNum;

    /**
     * 计算规则
     */
    @Column(name = "calculate_rule")
    private String calculateRule;

    /**
     * 组内互斥号
     */
    @Column(name = "item_group_exclusion_num")
    private String itemGroupExclusionNum;

    /**
     * 组间互斥号
     */
    @Column(name = "group_exclusion_num")
    private String groupExclusionNum;

    /**
     * 快速录入对应字段名称
     */
    @Column(name = "fast_column_name")
    private String fastColumnName;
}
