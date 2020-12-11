package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 交接班记录明细表
 */
@Entity
@Table(name = "table_handover_record")
@Data
public class HandoverRecord implements Serializable {

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
     * 交接班主键
     */
    @Column(name = "handover_id")
    private String handoverId;

    /**
     * 住院号
     */
    @Column(name = "visit_id")
    private String visitId;

    /**
     * 本次住院号
     */
    @Column(name = "visit_code")
    private String visitCode;

    /**
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 组号
     */
    @Column(name = "group_no")
    private String groupNo;

    /**
     * 行号
     */
    @Column(name = "row_no")
    private String rowNo;

    /**
     * 子行号
     */
    @Column(name = "sub_row_no")
    private String subRowNo;

    /**
     * 子行数据code
     */
    @Column(name = "sub_row_code")
    private String subRowCode;

    /**
     * 项目code
     */
    @Column(name = "code")
    private String code;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

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
