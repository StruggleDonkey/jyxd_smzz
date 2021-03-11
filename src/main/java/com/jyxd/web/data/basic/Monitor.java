package com.jyxd.web.data.basic;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 监护仪抓取数据存放临时表
 */
@Entity
@Table(name = "table_monitor")
@Data
public class Monitor implements Serializable {

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
     * 血压（低压）
     */
    @Column(name = "bplow")
    private String bplow;

    /**
     * 血压（高压）
     */
    @Column(name = "bphight")
    private String bphight;

    /**
     *
     */
    @Column(name = "bis")
    private String bis;

    /**
     *
     */
    @Column(name = "ficotwo")
    private String ficotwo;

    /**
     *
     */
    @Column(name = "etcotwo")
    private String etcotwo;

    /**
     * 呼吸参数
     */
    @Column(name = "resp")
    private String resp;

    /**
     * 体温
     */
    @Column(name = "temp")
    private String temp;

    /**
     * 血氧饱和度
     */
    @Column(name = "spotwo")
    private String spotwo;

    /**
     *
     */
    @Column(name = "pulse")
    private String pulse;

    /**
     * 心率
     */
    @Column(name = "hr")
    private String hr;

    /**
     * 中心静脉压
     */
    @Column(name = "cvp")
    private String cvp;

    /**
     *
     */
    @Column(name = "artm")
    private String artm;

    /**
     *
     */
    @Column(name = "artd")
    private String artd;

    /**
     *
     */
    @Column(name = "arts")
    private String arts;

    /**
     *
     */
    @Column(name = "nibpm")
    private String nibpm;

    /**
     *
     */
    @Column(name = "nibpd")
    private String nibpd;

    /**
     *
     */
    @Column(name = "nibps")
    private String nibps;

    /**
     *
     */
    @Column(name = "monitorcode")
    private String monitorcode;

    /**
     * 时间
     */
    @Column(name = "creattime")
    private Date creattime;
}
