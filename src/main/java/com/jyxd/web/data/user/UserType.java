package com.jyxd.web.data.user;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 平台用户类型表
 */
@Entity
@Table(name = "table_user_type")
@Data
public class UserType implements Serializable {

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
     * 类型编码
     */
    @Column(name = "user_type_code")
    private String userTypeCode;

    /**
     *  类型名称
     */
    @Column(name = "user_type_name")
    private String userTypeName;

    /**
     * 状态(0：禁用 1：启用 -1：删除)
     */
    @Column(name = "status")
    private int status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
