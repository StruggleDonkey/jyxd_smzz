package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 床位统计表
 */
@Entity
@Table(name = "table_bed_use_statistics")
@Data
public class BedUseStatistics implements Serializable {

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
     * 病人id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 统计时间
     */
    @Column(name = "statistics_date")
    private Date statisticsDate;

    /**
     * 入床时间
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 出床时间
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 床位code
     */
    @Column(name = "bed_code")
    private String bedCode;

    /**
     * 使用时长
     */
    @Column(name = "time_long")
    private float timeLong;

    /**
     * 是否在床 （1：在床   2：不在）
     */
    @Column(name = "state")
    private int state;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
