package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ICU日志模板类型表
 */
@Entity
@Table(name = "dict_icu_log_template")
@Data
public class IcuLogTemplateDictionary implements Serializable {

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
     * 日志类型名称
     */
    @Column(name = "template_name")
    private String templateName;

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
