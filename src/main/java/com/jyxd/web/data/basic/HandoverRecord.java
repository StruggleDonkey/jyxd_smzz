package com.jyxd.web.data.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 交接班记录明细表
 */
@Entity
@Table(name = "table_handover_record")
@Data
public class HandoverRecord implements Serializable {

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
     * 交接班主键
     */
    @Column(name = "handover_id")
    private String handoverId;

    /**
     * 住院号
     */
    @Column(name = "visit_id")
    private String visitId;

    /**
     * 本次住院号
     */
    @Column(name = "visit_code")
    private String visitCode;

    /**
     * 病人主键id
     */
    @Column(name = "patient_id")
    private String patientId;

    /**
     * 组号
     */
    @Column(name = "group_no")
    private String groupNo;

    /**
     * 行号
     */
    @Column(name = "row_no")
    private String rowNo;

    /**
     * 子行号
     */
    @Column(name = "sub_row_no")
    private String subRowNo;

    /**
     * 子行数据code
     */
    @Column(name = "sub_row_code")
    private String subRowCode;

    /**
     * 项目code
     * (床号：bedName
     * 姓名：name
     * 性别（男 女）：sex
     * 年龄：age
     * 住院号：visitId
     * 来源科室：department
     * 入科时间：visitTime
     * 手术时间：operationTime
     * 过敏史：allergies
     * 诊断：diagnosisName
     * 生命体征时间：vitalSignTime
     * 当前体征-体温：temperature
     * 当前体征-脉搏：pulse
     * 当前体征-心率：hr
     * 当前体征-呼吸：br
     * 当前体征-SpO₂：spo2
     * 当前体征-有创血压：ibp
     * 当前体征-无创血压：bp
     * 当前体征-cvp：cvp
     * 当前体征-cvp（mmHg₂O）：cvpmmHg
     * 最大体征-体温：maxtemperature
     * 最大体征-脉搏：maxpulse
     * 最大体征-心率：maxhr
     * 最大体征-呼吸：maxbr
     * 最大体征-SpO₂：maxspo2
     * 最大体征-有创血压：maxibp
     * 最大体征-无创血压：maxbp
     * 最大体征-cvp：maxcvp
     * 最大体征-cvp（mmHg₂O）：maxcvpmmHg
     * 最小体征-体温：mintemperature
     * 最小体征-脉搏：minpulse
     * 最小体征-心率：minhr
     * 最小体征-呼吸：minbr
     * 最小体征-SpO₂：minspo2
     * 最小体征-有创血压：minibp
     * 最小体征-无创血压：minbp
     * 最小体征-cvp：mincvp
     * 最小体征-cvp（mmHg₂O）：mincvpmmHg
     * 总入量：intake
     * 总出量：output
     * 人工气道方式-气管插管：tracheaInsert
     * 人工气道方式-气管切开：tracheaCut
     * 呼吸机型号：ventilatorModel
     * 呼吸机模式：breathePattern
     * 呼吸机插管深度：tubeDepth
     * 呼吸机吸氧方式：oxygenMethod
     * 呼吸机流量：oxygenFlow
     * 静脉置管-CVC：cvcDescription
     * 静脉置管-PVC：pvcDescription
     * 静脉置管-PICC：piccDescription
     * 静脉置管-PORT：portDescription
     * 静脉置管-留·置针：leftNeedleDescription
     * 动脉置管：artery
     *
     * )
     */
    @Column(name = "code")
    private String code;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作人code
     */
    @Column(name = "operator_code")
    private String operatorCode;

    /**
     * 状态（0：禁用 1：正常 -1：删除）
     */
    @Column(name = "status")
    private int status;

}
