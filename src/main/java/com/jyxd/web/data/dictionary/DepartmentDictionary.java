package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 科室字典表
 */
@Entity
@Table(name = "dict_department")
@Data
public class DepartmentDictionary implements Serializable {
    /**
     * 序列
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID7
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;

    /**
     * 科室code
     */
    @Column(name = "department_code")
    private String departmentCode;

    /**
     * 科室名称
     */
    @Column(name = "department_name")
    private String departmentName;

    /**-
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;
}
