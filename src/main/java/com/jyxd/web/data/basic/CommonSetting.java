package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 通用设置表
 */
@Entity
@Table(name = "table_common_setting")
@Data
public class CommonSetting implements Serializable {

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
     * 名称
     */
    @Column(name = "setting_name")
    private String settingName;

    /**
     * 类型
     */
    @Column(name = "setting_type")
    private String settingType;

    /**
     * 子类型
     */
    @Column(name = "setting_sub_type")
    private String settingSubType;

    /**
     * 值
     */
    @Column(name = "setting_content")
    private String settingContent;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 排序
     */
    @Column(name = "sort_num")
    private int sortNum;

}
