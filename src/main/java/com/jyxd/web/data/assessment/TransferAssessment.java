package com.jyxd.web.data.assessment;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 转科交接表
 */
@Entity
@Table(name = "table_transfer_assessment")
@Data
public class TransferAssessment implements Serializable {

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
     * 评估单主键
     */
    @Column(name = "assessment_id")
    private String assessmentId;

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
     * 记录时间
     */
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 班别(0:日班 1:晚班 2:夜班)
     */
    @Column(name = "shift")
    private int shift;

    /**
     * 项目code
     * （转出科室：sourceDepartment
     * 床号：bedName
     * 姓名：name
     * 年龄：age
     * 性别：sex
     * 诊断：diagnosisName
     * 转入科室：targetDepartment
     * 佩戴腕带：wristTape （0：否 1：是）
     * 病情-体征监测时间：syncTime
     * 病情-T：t
     * 病情-P：p
     * 病情-R：r
     * 病情-BP：bp
     * 病情-spo2：spo2
     * 病情-意识：consciousness  （1：清醒 2：嗜睡 3：谵妄 4：浅昏迷 5：神昏迷 6：其他）
     * 病情-意识-其他-内容：otherConsciousness
     * 静脉输液-类型：infusionPunctureType  （1：周围静脉钢针 2：周围静脉置管 3：深静脉置管 4：PICC）
     * 静脉输液-通畅：infustionClear  （1：是  2：否）
     * 静脉输液-静脉输液：isInfustioning  （1：无  2：有）
     * 静脉输液-输液通路：infustionPathNum
     * 静脉输液-输液中药液：infustionDrug
     * 静脉输液-剩余约：infustionLeftOver
     * 皮肤完整性：skinComplete  （1：完整 2：压疮 3：破损 4：其他）
     * 皮肤完整性-情况描述：skinOtherDescription
     * 各种管路-人工气道：hasArtificialAirway（1 ：有 2：无）
     * 各种管路-人工气道-有：artificialAirwayType（1 ：气管插管 2：气管切开）
     * 各种管路-引流管：hasDrainageTube（1 ：无 2：有）
     * 各种管路-引流管-有：drainageTubeList  （1：导尿管 2：盆腔引流管 3：皮下引流管 4：T管 5：头部引流管 6：颈部引流管 7：胸腔引流管 8：腹腔引流管 9：其他   传参时传 以逗号分隔的字符串，如 1,2,4,5）
     * 各种管路-引流管-有-内容：otherDrainageTube
     * 其他-病例：hasMedicalRecord （1：无 2：有）
     * 其他-病人特殊物品：hasSpecialThing （1：无 2：有）
     * 其他-病人特殊物品-内容：specialThing
     * 其他-特殊交接：specialTransfer
     * 转出科护士签字：transferNurseSignature
     * 出科时间：transferTime
     * 转入科护士签字：inNurseSignature
     * 入科时间：inTime
     * ）
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
