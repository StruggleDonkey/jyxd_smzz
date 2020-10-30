package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务执行的时间
 */
@Entity
@Table(name = "table_quartz_time")
@Data
public class QuartzTime implements Serializable {

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
     * 定时任务主键ID
     */
    @Column(name = "quartztask_id")
    private String quartzTaskId;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 下次执行时间
     */
    @Column(name = "next_time")
    private Date nextTime;

    /**
     * 执行时长
     */
    @Column(name = "time_length")
    private String timeLength;

    /**
     * 状态 1：正常
     */
    @Column(name = "status")
    private int status;
}
