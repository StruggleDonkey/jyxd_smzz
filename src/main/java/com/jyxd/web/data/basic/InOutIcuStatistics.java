package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 出入科统计表
 */
@Entity
@Table(name = "table_in_out_icu_statistics")
@Data
public class InOutIcuStatistics implements Serializable {

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
     * 统计时间
     */
    @Column(name = "statistics_date")
    private Date statisticsDate;

    /**
     * 原有患者数
     */
    @Column(name = "old_in_num")
    private int oldInNum;

    /**
     * 现有患者数
     */
    @Column(name = "current_in_num")
    private int currentInNum;

    /**
     * 转入患者数
     */
    @Column(name = "new_in_num")
    private int newInNum;

    /**
     * 转出患者数
     */
    @Column(name = "out_num")
    private int outNum;

    /**
     * 出院患者数
     */
    @Column(name = "out_exit_num")
    private int outExitNum;

    /**
     * 转科患者数
     */
    @Column(name = "out_trans_num")
    private int outTransNum;

    /**
     * 死亡患者数
     */
    @Column(name = "out_death_num")
    private int outDeathNum;

    /**
     * 放弃患者数
     */
    @Column(name = "out_abandon_num")
    private int outAbandonNum;

    /**
     * 转院患者数
     */
    @Column(name = "out_trans_hosp_num")
    private int outTransHospNum;

    /**
     * 48小时重回科室患者数
     */
    @Column(name = "two_day_in_again_num")
    private int twoDayInAgainNum;
}
