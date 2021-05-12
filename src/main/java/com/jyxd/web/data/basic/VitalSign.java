package com.jyxd.web.data.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 生命体征表
 */
@Entity
@Table(name = "table_vital_sign")
@Data
public class VitalSign implements Serializable {

    /**
     * 序列
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", length = 32, nullable = false)
    @GeneratedValue(generator = "system-uuid")
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
     * （神志：consciousness
     * 瞳孔大小左：pupilLeft
     * 瞳孔大小右：pupilRight
     * 瞳孔反射左：lightReflectionLeft
     * 瞳孔反射右：lightReflectionRight
     * 体温：temperature
     * 心率：hr
     * 脉搏：pulse
     * 心律：heartRate
     * 呼吸：br
     * 有创血压：ibp
     * 无创血压：bp
     * SpO₂：spo2
     * CVPcmH₂O：cvp
     * CVPmmHg：cvpmmHg
     * 血糖：bloodSugar
     * 人工气道方式：airWayStyle
     * 气囊压力cmH₂O：airbagPressure
     * 插管深度cm：rsDepth
     * 机械通气-模式：rsModel
     * 机械通气-氧浓度：rsOxygen
     * 机械通气-潮气量：rsMoisture
     * 机械通气-PC：pc
     * 机械通气-频率：rsFrequency
     * 机械通气-PS：ps
     * 机械通气-PEEP：peep
     * 机械通气-IPAP：ipap
     * 机械通气-EPAP：epap
     * 机械通气-BPM：bpm
     * 机械通气-I/E：ie
     * 呼吸音左：breathSoundLeft
     * 呼吸音右：breathSoundRight
     * 吸氧-方式：inhaleOxygenStyle
     * 吸氧-L/分：inhaleOxygenConcentration
     * 吸氧-氧浓度：inhaleOxygenConcentration2
     * 签名：signature
     * 移交签名 handoverSignature
     * glasgow昏迷评分：glasgow
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

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private Map<String, Object> glasgowMap;
}
