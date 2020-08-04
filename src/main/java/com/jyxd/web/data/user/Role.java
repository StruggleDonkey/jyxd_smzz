package com.jyxd.web.data.user;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 */
@Entity
@Table(name = "table_role")
@Data
public class Role implements Serializable {

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
     *  角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 是否受数据有效期控制（0：否 1：是）
     */
    @Column(name = "data_valid")
    private int dataValid;

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
