package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 质控统计表
 */
@Entity
@Table(name = "table_quality_control_statistics")
@Data
public class QualityControlStatistics implements Serializable {

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
     * 统计项目code
     */
    @Column(name = "statistic_code")
    private String statisticCode;

    /**
     * 统计值
     */
    @Column(name = "statistic_value")
    private float statisticValue;

    /**
     * 统计项中文名称
     */
    @Column(name = "statistic_chinese_name")
    private String statisticChineseName;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
