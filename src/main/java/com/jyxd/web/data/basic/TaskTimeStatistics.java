package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 统计任务表
 */
@Entity
@Table(name = "table_task_time_statistics")
@Data
public class TaskTimeStatistics implements Serializable {

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
     * 统计类型
     */
    @Column(name = "statistics_type")
    private String statisticsType;

    /**
     * 统计执行时间
     */
    @Column(name = "statistic_date")
    private Date statisticDate;


}
