package com.jyxd.web.data.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础护理表
 */
@Entity
@Table(name = "table_basic_nursing")
@Data
public class BasicNursing implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "data_time")
    private Date dataTime;

    /**
     * 项目code
     * (引流管名称：drainageName
     * 引流管外露cm：drainageDepth
     * 引流管挤压：hasExtrude
     * 引流管通畅：isClear
     * 引流管状态：hasReplaced
     * 引流管-引流液颜色：drainageColor
     * 静脉名称：veinDrainageName
     * 静脉外露cm：veinDrainageDepth
     * 静脉维护：hasVeinNursed
     * 静脉通畅：isVeinClear
     * 静脉状态：veinRemove
     * 静脉换药：veinDressingChange
     * 动脉部位：arteryPosition
     * 动脉通畅：isArteryClear
     * 护理-气管插管护理：intubationNursing
     * 护理-气管切开护理：tracheotomyNursing
     * 护理-雾化吸入：hasAtomizeInhale
     * 护理-吸痰：hasSuckedSputum
     * 护理-痰液分度：sputumDegree
     * 护理-痰色：sputumColor
     * 护理-口腔：hasCleanedMouth
     * 护理-尿道口：hasCleanedUrethraHole
     * 护理-会阴：hasCleanedPerineum
     * 护理-约束部位：constraintPosition
     * 护理-约束部位情况：constraintPositionCondition
     * 护理-拍背：hasClapBack
     * 护理-振动排痰：hasVibrateExcreteSputum
     * 护理-气压治疗：airPressureTreatment
     * 护理-皮肤：isSkinOk
     * 护理-皮肤护理：skinNursing
     * 护理-体位：bodyPosition
     * 护理-基础护理：basicNursing
     * 签名：signature
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
