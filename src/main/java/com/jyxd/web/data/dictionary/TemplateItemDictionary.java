package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 护理模板表
 */
@Entity
@Table(name = "dict_template_item")
@Data
public class TemplateItemDictionary implements Serializable {

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
     * 模板类型主键ID
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * 模板名称
     */
    @Column(name = "template_item_name")
    private String templateItemName;

    /**
     * 标题简拼
     */
    @Column(name = "spell")
    private String spell;

    /**
     * 模板内容
     */
    @Column(name = "content")
    private String content;

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
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
