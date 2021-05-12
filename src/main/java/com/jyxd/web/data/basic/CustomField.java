package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 自定义字段名称表
 */
@Entity
@Table(name = "table_custom_field")
@Data
public class CustomField {
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
     * 自定义名称
     */
    @Column(name = "filed_name")
    private String filedName;

    /**
     * 关联字段code
     */
    @Column(name = "filed_code")
    private String filedCode;

    /**
     * 关联表
     */
    @Column(name = "associated_table")
    private String associatedTable;

    /**
     * 操作人code
     */
    @Column(name = "operator_code")
    private String operatorCode;

    /**
     * 是否启用（0：禁用 1：启用）
     */
    @Column(name = "status")
    private int status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
