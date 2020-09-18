package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务上次执行的时间
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
     * 时间
     */
    @Column(name = "time")
    private Date time;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

}
