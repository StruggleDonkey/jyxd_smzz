package com.jyxd.web.data.user;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色权限表
 */
@Entity
@Table(name = "table_access")
@Data
public class Access implements Serializable {

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
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     *  权限类型 0：菜单 1：操作
     */
    @Column(name = "type")
    private int type;

    /**
     * 是菜单code
     */
    @Column(name = "menu_code")
    private String menuCode;

    /**
     * 状态(0：禁用 1：启用 -1：删除)
     */
    @Column(name = "status")
    private int status;

    /**
     * 操作code
     */
    @Column(name = "operate_code")
    private String operateCode;
}
