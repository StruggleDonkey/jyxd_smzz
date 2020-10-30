package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务表
 */
@Entity
@Table(name = "table_quartz_task")
@Data
public class QuartzTask implements Serializable {

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
     * job name
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * job group
     */
    @Column(name = "job_group")
    private String jobGroup;

    /**
     * 任务号
     */
    @Column(name = "task_name")
    private String taskName;

    /**
     * 类型 （同步任务、统计任务、系统任务）
     */
    @Column(name = "type")
    private String type;

    /**
     * 计划（定时时间）
     */
    @Column(name = "cron")
    private String cron;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 状态 -1：删除 0:暂停  1:正常  2:等待  3:阻塞  4:错误
     */
    @Column(name = "status")
    private int status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
