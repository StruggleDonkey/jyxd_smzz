package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 费用明细表
 */
@Entity
@Table(name = "table_cost_details")
@Data
public class CostDetails implements Serializable {

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
     * 费用id（唯一）
     */
    @Column(name = "cost_id")
    private String costId;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 费用编码
     */
    @Column(name = "cost_code")
    private String costCode;

    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     * 规格
     */
    @Column(name = "specs")
    private String specs;

    /**
     * 规格单位
     */
    @Column(name = "specs_units")
    private String specsUnits;

    /**
     * 数量
     */
    @Column(name = "amount")
    private String amount;

    /**
     * 单价
     */
    @Column(name = "price")
    private String price;

    /**
     * 应收费用
     */
    @Column(name = "receivable")
    private String receivable;

    /**
     * 实收费用
     */
    @Column(name = "actual_receipts")
    private String actualReceipts;

    /**
     * 费用分类
     */
    @Column(name = "cost_type")
    private String costType;

    /**
     * 收费时间
     */
    @Column(name = "charge_time")
    private Date chargeTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
