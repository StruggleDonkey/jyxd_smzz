package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ICU主要疾病诊断类型表
 */
@Entity
@Table(name = "dic_diagnose_coefficient")
@Data
public class DiagnoseCoefficientDictionary implements Serializable {

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
     * 模板类型名称
     */
    @Column(name = "diagnose_type_name")
    private String diagnoseTypeName;

    /**
     * 是否手术（0：否 1：是）
     */
    @Column(name = "operation")
    private int operation;

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
