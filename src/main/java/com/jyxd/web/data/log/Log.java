package com.jyxd.web.data.log;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志表
 */
@Entity
@Table(name = "l_log")
@Data
public class Log implements Serializable {

    /**
     * 序列
     */
    private static final long    serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;

    /**
     * 操作类型   新增 修改 删除 同步 确认
     */
    @Column(name = "operate_type")
    private String operateType;

    /**
     *  操作时间
     */
    @Column(name = "operate_time")
    private Date operateTime;

    /**
     * 操作人code
     */
    @Column(name = "operator_code")
    private String operatorCode;

    /**
     * 菜单code
     */
    @Column(name = "menu_code")
    private String menuCode;

    /**
     * 操作功能
     */
    @Column(name = "type")
    private String type;

    /**
     * 操作内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 操作功能
     */
    @Column(name = "visit_id")
    private String visitId;

    /**
     * 本次住院号
     */
    @Column(name = "visit_code")
    private String visitCode;

    /**
     * 本次入科病人号
     */
    @Column(name = "patient_id")
    private String patientId;
}
