package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ICU日志模板表
 */
@Entity
@Table(name = "dict_icu_log_template_item")
@Data
public class IcuLogTemplateItemDictionary implements Serializable {

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
     * 日志类型主键
     */
    @Column(name = "icu_log_template_id")
    private String icuLogTemplateId;

    /**
     * 日志名称
     */
    @Column(name = "icu_log_template_item_name")
    private String icuLogTemplateItemName;

    /**
     * 日志英文名称(统计用)
     */
    @Column(name = "icu_log_template_item_type")
    private String icuLogTemplateItemType;

    /**
     * 日志形式(0：发生记录 1：过程记录)
     */
    @Column(name = "log_form")
    private int logForm;

    /**
     * 是否允许非计划终止(0：否 1：是)
     */
    @Column(name = "allow_unplanned")
    private int allowUnplanned;

    /**
     * 是否同步至护理记录(0：否  1：是)
     */
    @Column(name = "syn_nursing")
    private int synNursing;

    /**
     * 同步护理措施模板内容(日志开始)
     */
    @Column(name = "nursing_begin_content")
    private String nursingBeginContent;

    /**
     * 同步护理措施模板内容(日志结束)
     */
    @Column(name = "nursing_end_content")
    private String nursingEndContent;

    /**
     * 同步护理措施模板内容(日志非计划结束)
     */
    @Column(name = "nursing_unplanned_end_content")
    private String nursingUnplannedEndContent;

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

    /**
     * 排序
     */
    @Column(name = "sort_num")
    private int sortNum;

    /**
     * 操作人code
     */
    @Column(name = "operator_code")
    private String operatorCode;

    /**
     * 同步数据到表名
     */
    @Column(name = "syn_table")
    private String synTable;

    /**
     * 同步数据类型code
     */
    @Column(name = "syn_code")
    private String synCode;

    /**
     * 同步数据类型值列表
     */
    @Column(name = "syn_value_select")
    private String synValueSelect;

    /**
     * 同步数据类型值
     */
    @Column(name = "syn_value")
    private String synValue;

    /**
     * 同步方式(0：追加，1：覆盖)
     */
    @Column(name = "syn_method")
    private int synMethod;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
