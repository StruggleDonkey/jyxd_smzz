package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员排班表
 */
@Entity
@Table(name = "table_schedual")
@Data
public class Schedual implements Serializable {

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
     * 员工工号
     */
    @Column(name = "work_number")
    private String workNumber;

    /**
     * 排班时间
     */
    @Column(name = "schedual_date")
    private Date schedualDate;

    /**
     * 班别(1:日班 2:晚班 3:夜班)
     */
    @Column(name = "shift")
    private int shift;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作人code
     */
    @Column(name = "operator_code")
    private String operatorCode;

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

}
