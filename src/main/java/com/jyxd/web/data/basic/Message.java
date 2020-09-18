package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息通知表
 */
@Entity
@Table(name = "table_message")
@Data
public class Message implements Serializable {

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
     * 消息类型 0：告警，1：交班，2：医嘱提醒
     */
    @Column(name = "message_type")
    private int messageType;

    /**
     * 消息标题
     */
    @Column(name = "message_title")
    private String messageTitle;

    /**
     * 消息内容
     */
    @Column(name = "message_content")
    private String messageContent;

    /**
     * 消息所属病人
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     *
     */
    @Column(name = "commit_user_id")
    private String commitUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

    /**
     * 处理人主键
     */
    @Column(name = "handle_user_id")
    private String handleUserId;

    /**
     * 处理人姓名
     */
    @Column(name = "handle_user_name")
    private String handleUserName;

    /**
     * 处理情况
     */
    @Column(name = "handle_description")
    private String handleDescription;

    /**
     * 处理时间
     */
    @Column(name = "handle_time")
    private Date handleTime;
}
