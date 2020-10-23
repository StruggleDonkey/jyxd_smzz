package com.jyxd.web.data.basic;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统配置表
 */
@Entity
@Table(name = "sys_properties")
@Data
public class SystemProperties implements Serializable {

    /**
     * 序列
     */
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键ID
     */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    /**
     * key
     */
    @Column(name = "sys_key")
    private String sysKey;

    /**
     * value
     */
    @Column(name = "sys_value")
    private String sysValue;

    /**
     * 备注说明
     */
    @Column(name = "remark")
    private String remark;
}
