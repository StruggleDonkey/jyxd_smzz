package com.jyxd.web.data.user;



import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台用户表
 */
@Entity
@Table(name = "table_user")
@Data
public class User implements Serializable {

    /**
     * 序列
     */
    private static final long    serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;

    /**
     * 员工工号
     */
    @Column(name = "work_number")
    private String workNumber;

    /**
     *  登录账号
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 姓名简拼
     */
    @Column(name = "simplicity")
    private String simplicity;

    /**
     * 性别(0：女 1：男)
     */
    @Column(name = "sex")
    private int sex;

    /**
     * 类型编码
     */
    @Column(name = "user_type_code")
    private String userTypeCode;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 状态(0：禁用 1：启用 -1：删除)
     */
    @Column(name = "status")
    private int status;

    /**
     * 入职时间
     */
    @Column(name = "enter_time")
    private Date enterTime;

    /**
     * 离职时间
     */
    @Column(name = "exit_time")
    private Date exitTime;

    /**
     * 是否参与排班（0：不参与 1：参与）
     */
    @Column(name = "is_schedual")
    private int isShedual;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
