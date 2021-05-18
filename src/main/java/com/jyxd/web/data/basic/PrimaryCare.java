package com.jyxd.web.data.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础护理表(新版-吕梁医院使用)
 */
@Entity
@Table(name = "table_primary_care")
@Data
public class PrimaryCare implements Serializable {

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
     * 肌力-左上肢
     */
    @Column(name = "strength_left_top")
    private String strengthLeftTop;

    /**
     * 肌力-左下肢
     */
    @Column(name = "strength_left_down")
    private String strengthLeftDown;

    /**
     * 肌力-右上肢
     */
    @Column(name = "strength_right_top")
    private String strengthRightTop;

    /**
     * 肌力-右下肢
     */
    @Column(name = "strength_right_down")
    private String strengthRightDown;

    /**
     * 口腔护理（0：否   1：是）
     */
    @Column(name = "oral")
    private String oral;

    /**
     * 尿道口护理（0：否   1：是）
     */
    @Column(name = "urethral")
    private String urethral;

    /**
     * 翻身
     */
    @Column(name = "roll_over")
    private String rollOver;

    /**
     * 拍背（0：否   1：是）
     */
    @Column(name = "take_back")
    private String takeBack;

    /**
     * 按摩（0：否   1：是）
     */
    @Column(name = "massage")
    private String massage;

    /**
     * 鼻饲（0：否   1：是）
     */
    @Column(name = "nasal_feeding")
    private String nasalFeeding;

    /**
     * 湿化吸痰（0：否   1：是）
     */
    @Column(name = "wet_phlegm")
    private String wetPhlegm;

    /**
     * 痰液性状
     */
    @Column(name = "phlegm_character")
    private String phlegmCharacter;

    /**
     * 振动排痰（0：否   1：是）
     */
    @Column(name = "row_character")
    private String rowCharacter;

    /**
     * 雾化吸入（0：否   1：是）
     */
    @Column(name = "atomization_inhalation")
    private String atomizationInhalation;

    /**
     * 冰毯冰帽（0：否   1：是）
     */
    @Column(name = "blanket_ice_cap")
    private String blanketIceCap;

    /**
     * 皮肤护理（0：否   1：是）
     */
    @Column(name = "skin")
    private String skin;

    /**
     * 双下肢气压泵治疗（0：否   1：是）
     */
    @Column(name = "lower_limbs_cure")
    private String lowerLimbsCure;

    /**
     * 胃管-长度
     */
    @Column(name = "tube_length")
    private String tubeLength;

    /**
     * 胃管-通畅与否（0：否   1：是）
     */
    @Column(name = "tube_unobstructed")
    private String tubeUnobstructed;

    /**
     * 尿管（0：否   1：是）
     */
    @Column(name = "urine_tube")
    private String urineTube;

    /**
     * 引流管-右侧胸腔闭式引流（0：否   1：是）
     */
    @Column(name = "drainage_tube_one")
    private String drainageTubeOne;

    /**
     * 引流管-硬膜下引流管（0：否   1：是）
     */
    @Column(name = "drainage_tube_two")
    private String drainageTubeTwo;

    /**
     * 引流管-腰大池引流管（0：否   1：是）
     */
    @Column(name = "drainage_tube_three")
    private String drainageTubeThree;

    /**
     * 引流管-VSD负压引流（0：否   1：是）
     */
    @Column(name = "drainage_tube_four")
    private String drainageTubeFour;

    /**
     * 引流管-左侧脑室引流管（0：否   1：是）
     */
    @Column(name = "drainage_tube_five")
    private String drainageTubeFive;

    /**
     * 引流管-膀胱造瘘者（0：否   1：是）
     */
    @Column(name = "drainage_tube_six")
    private String drainageTubeSix;

    /**
     * 风险评估-深静脉血栓评估
     */
    @Column(name = "risk_assessment_one")
    private String riskAssessmentOne;

    /**
     * 风险评估-压疮评分
     */
    @Column(name = "risk_assessment_two")
    private String riskAssessmentTwo;

    /**
     * 风险评估-跌倒坠床评分
     */
    @Column(name = "risk_assessment_three")
    private String riskAssessmentThree;

    /**
     * 风险评估-意外拔管评分
     */
    @Column(name = "risk_assessment_four")
    private String riskAssessmentFour;

    /**
     * 风险评估-疼痛评分
     */
    @Column(name = "risk_assessment_five")
    private String riskAssessmentFive;

    /**
     * 风险评估-镇静评分
     */
    @Column(name = "risk_assessment_six")
    private String riskAssessmentSix;

    /**
     * 风险评估-镇痛评分
     */
    @Column(name = "risk_assessment_seven")
    private String riskAssessmentSeven;

    /**
     * 签名
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 签名（交接班签名）
     */
    @Column(name = "signature_again")
    private String signatureAgain;

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
