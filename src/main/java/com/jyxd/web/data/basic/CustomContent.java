package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 自定义字段数据内容表
 */
@Entity
@Table(name = "table_custom_content")
@Data
public class CustomContent {
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
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 关联表
     */
    @Column(name = "associated_table")
    private String associatedTable;

    /**
     * 自定义字段内容一
     */
    @Column(name = "content_one")
    private String contentOne;

    /**
     * 自定义字段内容二
     */
    @Column(name = "content_two")
    private String contentTwo;

    /**
     * 自定义字段内容三
     */
    @Column(name = "content_three")
    private String contentThree;

    /**
     * 自定义字段内容四
     */
    @Column(name = "content_four")
    private String contentFour;

    /**
     * 自定义字段内容五
     */
    @Column(name = "content_five")
    private String contentFive;

    /**
     * 自定义字段内容六
     */
    @Column(name = "content_six")
    private String contentSix;

    /**
     * 自定义字段内容七
     */
    @Column(name = "content_seven")
    private String contentSeven;

    /**
     * 自定义字段内容八
     */
    @Column(name = "content_eight")
    private String contentEight;

    /**
     * 自定义字段内容九
     */
    @Column(name = "content_nine")
    private String contentNine;

    /**
     * 自定义字段内容十
     */
    @Column(name = "content_ten")
    private String contentTen;

    /**
     * 记录时间
     */
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
