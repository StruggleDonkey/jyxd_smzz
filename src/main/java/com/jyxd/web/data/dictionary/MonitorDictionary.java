package com.jyxd.web.data.dictionary;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 监护仪字典表
 */
@Entity
@Table(name = "dic_monitor")
@Data
public class MonitorDictionary implements Serializable {

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
     * 监护仪code
     */
    @Column(name = "monitor_code")
    private String monitorCode;

    /**
     * 监护仪名称
     */
    @Column(name = "monitor_name")
    private String monitorName;

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

    /**
     * 监护仪类型
     */
    @Column(name = "monitor_type")
    private String monitorType;

    /**
     * 监护仪IP
     */
    @Column(name = "monitor_ip")
    private String monitorIp;

    /**
     * 监护仪端口
     */
    @Column(name = "monitor_port")
    private String monitorPort;
}
