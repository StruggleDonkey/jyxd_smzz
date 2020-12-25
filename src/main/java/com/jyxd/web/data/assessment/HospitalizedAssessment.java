package com.jyxd.web.data.assessment;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 入院护理评估单
 */
@Entity
@Table(name = "table_hospitalized_assessment")
@Data
public class HospitalizedAssessment implements Serializable {

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
     * （联系人及电话：contactPerson
     *  民族：nation  （通用字典）
     *  职业：occupation   （通用字典）
     *  婚姻状况：maritalStatus  （通用字典）
     *  文化程度：education  （通用字典）
     *  入院时间：visitTime
     *  入院诊断：visitDiagnosis
     *  入院方式：visitMethod  （1:步行 2：轮椅 3：平车 4：其他）
     *  入院方式-其他-内容：visitMethodOther
     *  既往史：pastHistory （0：无  1：有）
     *  既往史-高血压：hypertension （选中传 1）
     *  既往史-心脏病：heartDisease    （选中传 1）
     *  既往史-糖尿病：diabetes    （选中传 1）
     *  既往史-脑血管病：cerebralVascularDisease    （选中传 1）
     *  既往史-精神病：mentalDisease   （选中传 1）
     *  既往史-手术史：operationHistory    （选中传 1）
     *  既往史-手术史-内容：operationHistoryContent
     *  既往史-其他：pastHistoryOther
     *  既往史-其他-内容：pastHistoryOtherContent
     *  过敏史：allergyHistory （0：无  1：有）
     *  过敏史-药物：allergyDrug
     *  过敏史-食物：allergyFood
     *  过敏史-其他：allergyOther
     *  护理评估-时间：measureTime
     *  护理评估-T：visitT
     *  护理评估-P：visitP
     *  护理评估-R：visitR
     *  护理评估-BP：visitBP
     *  护理评估-体重：visitWeight
     *  神志：mind （1：清楚 2：嗜睡 3：意识模糊 4：昏睡 5：浅昏迷 6：中昏迷 7：深昏迷 8：痴呆 ）
     *  沟通方式-语言：communicationLanguage （选中传 1）
     *  沟通方式-文字：communicationFont （选中传 1）
     *  沟通方式-手势：communicationGesture （选中传 1）
     *  沟通方式-无法评估：communicationNo （选中传 1）
     *  理解能力-良好：comprehensionAbilityGood （选中传 1）
     *  理解能力-一般：comprehensionAbilityCommonly （选中传 1）
     *  理解能力-差：comprehensionAbilityBad （选中传 1）
     *  理解能力-无法评估：comprehensionAbilityNo （选中传 1）
     *  视力：vision （0：异常 1：正常）
     *  视力-异常-内容：visionAbnormal
     *  听力：hearing（0：异常 1：正常）
     *  听力-异常-内容：hearingAbnormal
     *  义齿：falseTooth （0：无 1：有）
     *  口腔黏膜：oralMucosa （0：破损 1：完整 2：其他）
     *  口腔黏膜-其他-内容：oralMucosaOther
     *  皮肤-正常：skinNormal    （选中传 1）
     *  皮肤-水肿：skinEdema    （选中传 1）
     *  皮肤-黄疸：skinJaundice    （选中传 1）
     *  皮肤-苍白：skinPale    （选中传 1）
     *  皮肤-发绀：skinCyanosis    （选中传 1）
     *  皮肤-皮疹：skinRash    （选中传 1）
     *  皮肤-瘀斑：skinEcchymosis    （选中传 1）
     *  皮肤-破损：skinDamaged    （选中传 1）
     *  皮肤-其他：skinOther    （选中传 1）
     *  皮肤-其他-内容：skinOtherContent
     *  压疮：pressureSore （0：无 1：有）
     *  压疮-部位：pressureSorePosition
     *  压疮-分期：pressureSoreLevel
     *  压疮-范围：pressureSoreRange
     *  小便情况-正常：urineNormal （选中传 1）
     *  小便情况-失禁：urineIncontinence （选中传 1）
     *  小便情况-尿频：urineFrequent （选中传 1）
     *  小便情况-尿急：urineUrgency （选中传 1）
     *  小便情况-尿痛：urinePain （选中传 1）
     *  小便情况-尿少：urineLittle （选中传 1）
     *  小便情况-尿滞留：urineRetention （选中传 1）
     *  小便情况-血尿：urineBlood （选中传 1）
     *  小便情况-蛋白尿：urineProtein （选中传 1）
     *  小便情况-保留尿管：urineRetentionCatheter （选中传 1）
     *  小便情况-造口：urineStoma （选中传 1）
     *  小便情况-其他：urineOther （选中传 1）
     *  小便情况-其他-内容：urineOtherContent
     *  大便-正常：shitNormal （选中传 1）
     *  大便-失禁：shitIncontinence （选中传 1）
     *  大便-便秘：shitConstipation （选中传 1）
     *  大便-便血：shitBlood （选中传 1）
     *  大便-腹泻：shitDiarrhea （选中传 1）
     *  大便-腹泻-内容：shitDiarrheaNum
     *  大便-造口：shitStoma （选中传 1）
     *  大便-其他：shitOther （选中传 1）
     *  大便-其他-内容：shitOtherContent
     *  情绪状态-稳定：emotionStable   （选中传 1）
     *  情绪状态-焦虑：emotionAnxious   （选中传 1）
     *  情绪状态-紧张：emotionNervous   （选中传 1）
     *  情绪状态-恐惧：emotionFear   （选中传 1）
     *  情绪状态-其他：emotionOther   （选中传 1）
     *  情绪状态-其他-内容：emotionOtherContent
     *  吸烟：smoke    （0：否 1：是）
     *  吸烟-内容：smokeContent
     *  饮酒：drink （0：否 1：是）
     *  饮酒-内容：drinkContent
     *  饮食习惯-咸：dietSalty （选中传 1）
     *  饮食习惯-甜：dietSweet （选中传 1）
     *  饮食习惯-辛辣：dietPungent （选中传 1）
     *  饮食习惯-油腻：dietGreasy （选中传 1）
     *  饮食习惯-清淡：dietLight （选中传 1）
     *  饮食习惯-其他：dietOther （选中传 1）
     *  饮食习惯-其他-内容：dietOtherContent
     *  忌食：dietAvoid
     *  睡眠-正常：sleepNormal   （选中传 1）
     *  睡眠-多梦：sleepLotDream   （选中传 1）
     *  睡眠-易醒：sleepToWakeUp   （选中传 1）
     *  睡眠-入睡困难：sleepHard   （选中传 1）
     *  睡眠-每日睡眠：sleepHour
     *  睡眠-药物辅助：sleepDrugAssisted （0：无 1：有）
     *  睡眠-药物辅助-内容：sleepDrugAssistedContent （0：无 1：有）
     *  评分时间：scoreTime
     *  Braden评分：bradenScore
     *  Morse评分：morseScore
     *  Autar-DVT评分：autarDvtScore
     *  ADL评分：adlScore
     *  家属态度：familyAttitude （1：关心 2：不关心 3：过于关心 4：无人照顾 5：不配合）
     *  饮食状况：dietaryStatus （1：一般 2：良好 3：较差）
     *  自理能力：selfCareAbility （1：完全自理 2：部分自理 3：不能自理）
     *  隔离状况：isolationStatus （通用字典）
     *  有无呕吐：vomit  （0：无 1：有）
     *  发育程度：developmentalDegree （通用字典）
     *  健康状况：health （0：不健康 1：健康）
     *  传染性疾病：infectiousDiseases （0：无 1：有）
     *  入院宣教-床位医生：bedsideDoctor （选中传 1）
     *  入院宣教-责任护士：responsibleNurse （选中传 1）
     *  入院宣教-病房环境：wardEnvironment （选中传 1）
     *  入院宣教-病房制度：wardSystem （选中传 1）
     *  入院宣教-探视规定：visitingRegulations （选中传 1）
     *  入院宣教-膳食安排：dietaryArrangement （选中传 1）
     *  入院宣教-心里疏导：psychologicalCounseling （选中传 1）
     *  入院宣教-禁止外出：noGoingOut （选中传 1）
     *  入院宣教-腕带佩戴：wristStrap （选中传 1）
     *  入院宣教-其他：inHospitalOther （选中传 1）
     *  入院宣教-其他-内容：inHospitalOtherContent
     *  皮肤护理：skinNursing
     *  营养护理：nutritionNursing
     *  体位护理：postureNursing
     *  心里护理：mentalNursing  （通用字典）
     *  饮食护理：dietNursing  （通用字典）
     *  安全护理：safetyNursing  （通用字典）
     *  异常情况描述：abnormalDescription
     *  通知医生时间：informDoctorTime
     *  护士签名：nurseSignature
     *  审核护士签名：examineNurseSignature
     *  护理计划及措施-时间：examineTime
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
