package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.CustomContentDao;
import com.jyxd.web.dao.basic.CustomFieldDao;
import com.jyxd.web.dao.basic.InOutAmountDao;
import com.jyxd.web.data.basic.CustomContent;
import com.jyxd.web.data.basic.CustomField;
import com.jyxd.web.util.HttpCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jyxd.web.util.DateUtil.*;
import static com.jyxd.web.util.DateUtil.getLaterHoursDate;
import static com.jyxd.web.util.ObjectUtil.castList;
import static com.jyxd.web.util.ObjectUtil.objectStrIsNull;

@Service
public class InOUtAmountStatisticsService {

    @Autowired
    private InOutAmountDao inOutAmountDao;

    @Autowired
    private CustomFieldDao customFieldDao;

    @Autowired
    private CustomContentDao customContentDao;

    /**
     * 出入量统计
     *
     * @return
     */
    public String statistics(Map<String, Object> map) throws ParseException {
        isMapNull(map);
        //统计时刻
        String countingTime = String.valueOf(map.get("countingTime"));
        //小结间隔
        Long countingHours = Long.valueOf(String.valueOf(map.get("countingHours")));
        Long summaryHours = 0L;
        //是否需要大结，null不需要，false 为还未大结，true为已经大结
        if (!objectStrIsNull(map.get("summaryHours"))) {
            summaryHours = Long.valueOf(String.valueOf(map.get("summaryHours")));
        }
        //查询数据结束时间
        Date endDataTime = getLaterHoursDate(new Date(), Long.valueOf(String.valueOf(map.get("hours"))));
        map.put("endTime", yyyyMMddHHmmssSdfToString(endDataTime));
        JSONObject inOutAmountJson = getInOutAmountList(map);
        Object data = inOutAmountJson.get("data");
        List<Map> inOutAmountMapList = castList(data, Map.class);
        List<Map> newOutAmountMapList = new ArrayList<>();
        //第一条数据
        Map oneData = inOutAmountMapList.get(0);
        //第一条数据的时间
        Date oneStartTime = yyyyMMddHHmmssSdfToDate(oneData.get("data_date") + " " + oneData.get("data_time") + ":00");
        //计算出第一次统计结束的时间
        Date oneEndTime = calculateOneStatisticsEndTime(String.valueOf(oneData.get("data_date")), String.valueOf(oneData.get("data_time")), countingTime);
        System.out.println("开始时间：" + yyyyMMddHHmmssSdfToString(oneStartTime));
        System.out.println("结束时间：" + yyyyMMddHHmmssSdfToString(oneEndTime));
        int oneStatisticsCount = 0;
        List<Map> statisticsList = new ArrayList<>();
        for (int i = 0; i < inOutAmountMapList.size(); i++) {
            Map inOutAmountMap = inOutAmountMapList.get(i);
            if (objectStrIsNull(inOutAmountMap.get("data_date"))) {
                continue;
            }
            if (objectStrIsNull(inOutAmountMap.get("data_time"))) {
                continue;
            }
            Date date = yyyyMMddHHmmssSdfToDate(inOutAmountMap.get("data_date") + " " + inOutAmountMap.get("data_time") + ":00");
            if (oneStartTime.getTime() <= date.getTime() && date.getTime() <= oneEndTime.getTime()) {
                statisticsList.add(inOutAmountMap);
                newOutAmountMapList.add(inOutAmountMap);
            } else {
                oneStatisticsCount = i;
                break;
            }
        }
        System.out.println("第一次结束后的下标：" + oneStatisticsCount);
        //插入第一次节点总结
        newOutAmountMapList.add(mapDataTransition(addStatisticsDate(statisticsList), inOutAmountMapList.get(oneStatisticsCount), null, null, countingTime));
        if (oneStatisticsCount > 0) {
            //小结计算集合
            Map<String, Integer> calculateCountingHoursAccumulationMap = new HashMap<>();
            //初始化小结数据
            accumulationInitialize(calculateCountingHoursAccumulationMap);
            //总结计算集合
            Map<String, Integer> calculateSummaryHoursAccumulationMap = new HashMap<>();
            //初始化总结数据
            accumulationInitialize(calculateSummaryHoursAccumulationMap);
            //获取前一条数据
            Map oneEndInOutAmountMap = inOutAmountMapList.get(oneStatisticsCount - 1);
            String oneEndDataDateStr = String.valueOf(oneEndInOutAmountMap.get("data_date"));
            String oneEndDataTimeStr = String.valueOf(oneEndInOutAmountMap.get("data_time"));
            //获取前一条数据时间
            Date oneEndDateTime = yyyyMMddHHmmssSdfToDate(oneEndDataDateStr + " " + oneEndDataTimeStr + ":00");
            //获取当前数据
            Date inOutAmountMapDateTime = yyyyMMddHHmmssSdfToDate(inOutAmountMapList.get(oneStatisticsCount).get("data_date") + " " + inOutAmountMapList.get(oneStatisticsCount).get("data_time") + ":00");
            //获取计算下一个小结时间节点
            Date countingHoursEndTime = getLaterHoursDate(oneEndTime, calculateStartTime(oneEndDateTime, inOutAmountMapDateTime, countingHours));
            //获取计算下一个总结时间节点
            Date summaryHoursEndTime = null;
            if (Objects.nonNull(summaryHours) && summaryHours != 0) {
                summaryHoursEndTime = getLaterHoursDate(oneEndTime, calculateStartTime(oneEndDateTime, inOutAmountMapDateTime, summaryHours));
            }
            for (int i = oneStatisticsCount; i < inOutAmountMapList.size() && oneStatisticsCount != 0; i++) {
                Map inOutAmountMap = inOutAmountMapList.get(i);
                Map inOutAmountNextMap = inOutAmountMap;
                if (i < inOutAmountMapList.size() - 1) {
                    inOutAmountNextMap = inOutAmountMapList.get(i + 1);
                }
                //提取原数据中的数值
                Map<String, Integer> stringIntegerMap = extractNum(inOutAmountMap, findCustomContent(inOutAmountMap));
                //计算小结累加值
                calculateAccumulation(calculateCountingHoursAccumulationMap, stringIntegerMap);
                //如果存在总结时计算总结累加值
                if (Objects.nonNull(summaryHoursEndTime)) {
                    calculateAccumulation(calculateSummaryHoursAccumulationMap, stringIntegerMap);
                }
                //获取下一条数据的时间
                Date inOutAmountNextMapDateTime = yyyyMMddHHmmssSdfToDate(inOutAmountNextMap.get("data_date") + " " + inOutAmountNextMap.get("data_time") + ":00");
                //将原数据放入新集合
                newOutAmountMapList.add(inOutAmountMap);
                //判断如果下次时间大于节点结束时间则进入小结总结
                if (inOutAmountNextMapDateTime.getTime() > countingHoursEndTime.getTime() && i != inOutAmountMapList.size() - 1) {
                    //转换统计数值为前端数据
                    Map mapDataTransition = mapDataTransition(calculateCountingHoursAccumulationMap, inOutAmountMap, countingHours, null, countingTime);
                    //小结数据放入新集合中
                    newOutAmountMapList.add(mapDataTransition);
                    //小结结束，初始化计算数据
                    accumulationInitialize(calculateCountingHoursAccumulationMap);
                    //计算下一次时间
                    countingHoursEndTime = getLaterHoursDate(countingHoursEndTime, calculateStartTime(countingHoursEndTime, inOutAmountNextMapDateTime, countingHours));
                } else if (i == inOutAmountMapList.size() - 1) {
                    //最后一条数据直接进行小结结束，不在判断是否是小于下一条数据
                    //转换统计数值为前端数据
                    Map mapDataTransition;
                    if (Objects.nonNull(summaryHoursEndTime)) {
                        //如果需要总结时，转换总结数据为前端所需数据，将总结数据放入集合
                        mapDataTransition = mapDataTransition(calculateSummaryHoursAccumulationMap, inOutAmountMap, null, summaryHours, countingTime);
                        newOutAmountMapList.add(mapDataTransition);
                    } else {
                        mapDataTransition = mapDataTransition(calculateCountingHoursAccumulationMap, inOutAmountMap, countingHours, null, countingTime);
                        //小结数据放入新集合中
                        newOutAmountMapList.add(mapDataTransition);
                    }
                    break;
                }
                //如果总结时间不存在，则直接返回
                if (Objects.isNull(summaryHoursEndTime)) {
                    continue;
                }
                if (inOutAmountNextMapDateTime.getTime() > summaryHoursEndTime.getTime()) {
                    //转换统计数值为前端数据
                    Map mapDataTransition = mapDataTransition(calculateSummaryHoursAccumulationMap, inOutAmountMap, null, summaryHours, countingTime);
                    //总结数据放入新集合中
                    newOutAmountMapList.add(mapDataTransition);
                    //总结结束，初始化计算数据
                    accumulationInitialize(calculateSummaryHoursAccumulationMap);
                    //计算下一次总结时间
                    summaryHoursEndTime = getLaterHoursDate(summaryHoursEndTime, calculateStartTime(summaryHoursEndTime, inOutAmountNextMapDateTime, summaryHours));
                }
            }
        }
        inOutAmountJson.put("data", JSONArray.fromObject(newOutAmountMapList));
        return inOutAmountJson.toString();
    }

    /**
     * 计算小结数据
     */
    private void calculateCountingHours(Map inOutAmountMap, Map inOutAmountNextMap, Map<String, Integer> calculateCountingHoursAccumulationMap,
                                        Long countingHours, List<Map> newOutAmountMapList,
                                        Date countingHoursEndTime, Date inOutAmountMapDateTime) throws ParseException {
       /* Map<String, Integer> stringIntegerMap = extractNum(inOutAmountMap, findCustomContent(inOutAmountMap));
        calculateAccumulation(calculateCountingHoursAccumulationMap, stringIntegerMap);
        Date inOutAmountNextMapDateTime = yyyyMMddHHmmssSdfToDate(inOutAmountNextMap.get("data_date") + " " + inOutAmountNextMap.get("data_time") + ":00");
        inOutAmountMapDateTime = yyyyMMddHHmmssSdfToDate(inOutAmountMap.get("data_date") + " " + inOutAmountMap.get("data_time") + ":00");
        newOutAmountMapList.add(inOutAmountMap);
        if (inOutAmountNextMapDateTime.getTime() > countingHoursEndTime.getTime()) {
            Map mapDataTransition = mapDataTransition(calculateCountingHoursAccumulationMap, inOutAmountMap, countingHours, null, null);
            newOutAmountMapList.add(mapDataTransition);
            //小结结束，初始化计算数据
            accumulationInitialize(calculateCountingHoursAccumulationMap);
            //计算下一次时间
            countingHoursEndTime = getLaterHoursDate(countingHoursEndTime, calculateStartTime(countingHoursEndTime, inOutAmountMapDateTime, countingHours));
        }*/
    }

    /**
     * 计算出中间相差几个时段间隔
     *
     * @param startTime
     * @param nextTime
     * @param countingHoursOrSummaryHours
     * @return
     */
    private long calculateStartTime(Date startTime, Date nextTime, long countingHoursOrSummaryHours) {
        Long difference = nextTime.getTime() - startTime.getTime();
        System.out.println("前一条数据时间：" + startTime);
        System.out.println("后一条数据时间：" + startTime);
        System.out.println("相差时间为：" + difference);
        System.out.println("转为小时计时：" + difference / 1000 / (60 * 60));
        difference = difference / 1000 / (60 * 60);
        if (difference % countingHoursOrSummaryHours > 0) {
            Long divisor = (difference / countingHoursOrSummaryHours) + 1;
            System.out.println("有余数计算隔断时间为：" + divisor + "-- countingHoursOrSummaryHours = :" + countingHoursOrSummaryHours);
            return divisor * countingHoursOrSummaryHours;
        }
        Long divisor = difference / countingHoursOrSummaryHours;
        System.out.println("无余数计算隔断时间为：" + divisor + "-- countingHoursOrSummaryHours = :" + countingHoursOrSummaryHours);
        return divisor * countingHoursOrSummaryHours;
    }


    /**
     * 计算第一次统计结束时间
     *
     * @return
     */
    private Date calculateOneStatisticsEndTime(String oneDateDay, String oneDateTime, String countingTime) throws ParseException {
        Date oneDateTimeDate = hhmmSdfToDate(oneDateTime);
        Date countingTimeDate = hhmmSdfToDate(countingTime);
        if (oneDateTimeDate.getTime() > countingTimeDate.getTime()) {
            Date date = yyyyMMddHHmmssSdfToDate(oneDateDay + " " + countingTime);
            return getLaterHoursDate(date, 24L);
        }
        return yyyyMMddHHmmssSdfToDate(oneDateDay + " " + countingTime);
    }

    /**
     * 转换为前端数据
     *
     * @return
     */
    private Map<String, Object> mapDataTransition(Map<String, Integer> statisticsMap,
                                                  Map<String, Object> mapDate, Long countingHours,
                                                  Long summaryHours, String countingTime) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : mapDate.entrySet()) {
            if (StringUtils.equals("dosage", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("dosage"));
                continue;
            }
            if (StringUtils.equals("allowanceDosage", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("allowanceDosage"));
                continue;
            }
            if (StringUtils.equals("piss", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("piss"));
                continue;
            }
            if (StringUtils.equals("faces", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("faces"));
                continue;
            }
            if (StringUtils.equals("drainage", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("drainage"));
                continue;
            }
            if (StringUtils.equals("balance", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("balance"));
                continue;
            }
            if (StringUtils.equals("content_one", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentOne"));
                continue;
            }
            if (StringUtils.equals("content_two", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentTwo"));
                continue;
            }
            if (StringUtils.equals("content_three", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentThree"));
                continue;
            }
            if (StringUtils.equals("content_four", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentFour"));
                continue;
            }
            if (StringUtils.equals("content_five", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentFive"));
                continue;
            }
            if (StringUtils.equals("content_six", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentSix"));
                continue;
            }
            if (StringUtils.equals("content_seven", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentSeven"));
                continue;
            }
            if (StringUtils.equals("content_eight", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentEight"));
                continue;
            }
            if (StringUtils.equals("content_nine", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentNine"));
                continue;
            }
            if (StringUtils.equals("content_ten", entry.getKey())) {
                map.put(entry.getKey(), statisticsMap.get("contentTen"));
                continue;
            }
            if (StringUtils.equals("signature", entry.getKey())) {
                map.put(entry.getKey(), "");
                continue;
            }
            if (StringUtils.equals("data_date", entry.getKey())) {
                map.put(entry.getKey(), "-:-");
                continue;
            }

            if (Objects.isNull(countingHours) && Objects.isNull(summaryHours)) {
                if (StringUtils.equals("data_time", entry.getKey())) {
                    map.put(entry.getKey(), countingTime);
                    continue;
                }
                if (StringUtils.equals("order_name", entry.getKey())) {
                    map.put("order_name", countingTime + "前总结");
                    continue;
                }
            } else if (Objects.nonNull(summaryHours)) {
                if (StringUtils.equals("data_time", entry.getKey())) {
                    map.put(entry.getKey(), hhmmssSdfToString(getLaterHoursDate(hhmmSdfToDate(countingTime), summaryHours)));
                    continue;
                }
                if (StringUtils.equals("order_name", entry.getKey())) {
                    map.put("order_name", summaryHours + "小时总结");
                    continue;
                }
            } else if (Objects.nonNull(countingHours)) {
                if (StringUtils.equals("data_time", entry.getKey())) {
                    map.put(entry.getKey(), hhmmssSdfToString(getLaterHoursDate(hhmmSdfToDate(countingTime), countingHours)));
                    continue;
                }
                if (StringUtils.equals("order_name", entry.getKey())) {
                    map.put("order_name", countingHours + "小时小结");
                    continue;
                }
            }
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * 计算第一次节点总结
     *
     * @param statisticsList
     * @throws ParseException
     */
    private Map<String, Integer> addStatisticsDate(List<Map> statisticsList) throws ParseException {
        Map<String, Integer> calculateAccumulationMap = new HashMap<>();
        calculateAccumulationMap = accumulationInitialize(calculateAccumulationMap);
        for (Map statistics : statisticsList) {
            Map<String, Integer> stringIntegerMap = extractNum(statistics, findCustomContent(statistics));
            calculateAccumulation(calculateAccumulationMap, stringIntegerMap);
        }
        return calculateAccumulationMap;
    }

    /**
     * 查询自定义数据
     *
     * @param statisticsMap
     * @return
     */
    private CustomContent findCustomContent(Map<String, Object> statisticsMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", statisticsMap.get("patient_id"));
        map.put("associatedTable", "table_in_out_amount");
        map.put("dataTime", statisticsMap.get("data_date") + " " + statisticsMap.get("data_time") + ":00");
        List<CustomContent> list = customContentDao.getCustomContentByTime(map);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 初始化原始参数
     *
     * @param calculateAccumulationMap
     * @return
     */
    private Map accumulationInitialize(Map<String, Integer> calculateAccumulationMap) {
        calculateAccumulationMap.put("dosage", 0);
        calculateAccumulationMap.put("allowanceDosage", 0);
        calculateAccumulationMap.put("piss", 0);
        calculateAccumulationMap.put("faces", 0);
        calculateAccumulationMap.put("drainage", 0);
        calculateAccumulationMap.put("balance", 0);
        calculateAccumulationMap.put("contentOne", 0);
        calculateAccumulationMap.put("contentTwo", 0);
        calculateAccumulationMap.put("contentThree", 0);
        calculateAccumulationMap.put("contentFour", 0);
        calculateAccumulationMap.put("contentFive", 0);
        calculateAccumulationMap.put("contentSix", 0);
        calculateAccumulationMap.put("contentSeven", 0);
        calculateAccumulationMap.put("contentEight", 0);
        calculateAccumulationMap.put("contentNine", 0);
        calculateAccumulationMap.put("contentTen", 0);
        return calculateAccumulationMap;
    }

    /**
     * 计算累加
     *
     * @return
     */
    private Map<String, Integer> calculateAccumulation
    (Map<String, Integer> calculateAccumulationMap, Map<String, Integer> stringIntegerMap) {
        Integer dosage = stringIntegerMap.get("dosageCount");
        calculateAccumulationMap.put("dosage", dosage + calculateAccumulationMap.get("dosage"));
        Integer allowanceDosageCount = stringIntegerMap.get("allowanceDosageCount");
        calculateAccumulationMap.put("allowanceDosage", allowanceDosageCount + calculateAccumulationMap.get("allowanceDosage"));
        Integer piss = stringIntegerMap.get("pissCount");
        calculateAccumulationMap.put("piss", piss + calculateAccumulationMap.get("piss"));
        Integer faces = stringIntegerMap.get("facesCount");
        calculateAccumulationMap.put("faces", faces + calculateAccumulationMap.get("faces"));
        System.out.println("大便数据和为：" + calculateAccumulationMap.get("faces"));
        Integer drainage = stringIntegerMap.get("drainageCount");
        calculateAccumulationMap.put("drainage", drainage + calculateAccumulationMap.get("drainage"));
        Integer balance = stringIntegerMap.get("balanceCount");
        calculateAccumulationMap.put("balance", balance + calculateAccumulationMap.get("balance"));
        int contentOne = getCustomData(stringIntegerMap.get("contentOne"));
        calculateAccumulationMap.put("contentOne", contentOne + calculateAccumulationMap.get("contentOne"));

        int contentTwo = getCustomData(stringIntegerMap.get("contentTwo"));
        calculateAccumulationMap.put("contentTwo", contentTwo + calculateAccumulationMap.get("contentTwo"));

        int contentThree = getCustomData(stringIntegerMap.get("contentThree"));
        calculateAccumulationMap.put("contentThree", contentThree + calculateAccumulationMap.get("contentThree"));

        int contentFour = getCustomData(stringIntegerMap.get("contentFour"));
        calculateAccumulationMap.put("contentFour", contentFour + calculateAccumulationMap.get("contentFour"));

        int contentFive = getCustomData(stringIntegerMap.get("contentFive"));
        calculateAccumulationMap.put("contentFive", contentFive + calculateAccumulationMap.get("contentFive"));

        int contentSix = getCustomData(stringIntegerMap.get("contentSix"));
        calculateAccumulationMap.put("contentSix", contentSix + calculateAccumulationMap.get("contentSix"));

        int contentSeven = getCustomData(stringIntegerMap.get("contentSeven"));
        calculateAccumulationMap.put("contentSeven", contentSeven + calculateAccumulationMap.get("contentSeven"));

        int contentEight = getCustomData(stringIntegerMap.get("contentEight"));
        calculateAccumulationMap.put("contentEight", contentEight + calculateAccumulationMap.get("contentEight"));

        int contentNine = getCustomData(stringIntegerMap.get("contentNine"));
        calculateAccumulationMap.put("contentNine", contentNine + calculateAccumulationMap.get("contentNine"));

        int contentTen = getCustomData(stringIntegerMap.get("contentTen"));
        calculateAccumulationMap.put("contentTen", contentTen + calculateAccumulationMap.get("contentTen"));
        return calculateAccumulationMap;
    }

    /**
     * 获取自定义数据
     *
     * @return
     */
    private int getCustomData(Integer customDate) {
        if (Objects.isNull(customDate)) {
            return 0;
        }
        return customDate;
    }


    /**
     * 提取数值
     *
     * @return
     */
    private Map<String, Integer> extractNum(Map statistics, CustomContent customContent) {
        //实入量
        int dosageCount = objectToInt(statistics.get("dosage"));
        //余量
        int allowanceDosageCount = objectToInt(statistics.get("allowance_dosage"));
        //出量(尿)
        int pissCount = objectToInt(statistics.get("piss"));
        //出量(便)
        int facesCount = objectToInt(statistics.get("faces"));
        //引流量
        int drainageCount = objectToInt(statistics.get("drainage"));
        //平衡(总入量-总出量)
        int balanceCount = objectToInt(statistics.get("balance"));
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("dosageCount", dosageCount);
        countMap.put("allowanceDosageCount", allowanceDosageCount);
        countMap.put("pissCount", pissCount);
        countMap.put("facesCount", facesCount);
        countMap.put("drainageCount", drainageCount);
        countMap.put("balanceCount", balanceCount);
        if (Objects.isNull(customContent)) {
            return countMap;
        }
        if (!objectStrIsNull(customContent.getContentOne()) && isNumeric(customContent.getContentOne())) {
            countMap.put("contentOne", Integer.valueOf(customContent.getContentOne()));
        }
        if (!objectStrIsNull(customContent.getContentTwo()) && isNumeric(customContent.getContentTwo())) {
            countMap.put("contentTwo", Integer.valueOf(customContent.getContentTwo()));
        }
        if (!objectStrIsNull(customContent.getContentThree()) && isNumeric(customContent.getContentThree())) {
            countMap.put("contentThree", Integer.valueOf(customContent.getContentThree()));
        }
        if (!objectStrIsNull(customContent.getContentFour()) && isNumeric(customContent.getContentFour())) {
            countMap.put("contentFour", Integer.valueOf(customContent.getContentFour()));
        }
        if (!objectStrIsNull(customContent.getContentFive()) && isNumeric(customContent.getContentFive())) {
            countMap.put("contentFive", Integer.valueOf(customContent.getContentFive()));
        }
        if (!objectStrIsNull(customContent.getContentSix()) && isNumeric(customContent.getContentSix())) {
            countMap.put("contentSix", Integer.valueOf(customContent.getContentSix()));
        }
        if (!objectStrIsNull(customContent.getContentSeven()) && isNumeric(customContent.getContentSeven())) {
            countMap.put("contentSeven", Integer.valueOf(customContent.getContentSeven()));
        }
        if (!objectStrIsNull(customContent.getContentEight()) && isNumeric(customContent.getContentEight())) {
            countMap.put("contentEight", Integer.valueOf(customContent.getContentEight()));
        }
        if (!objectStrIsNull(customContent.getContentNine()) && isNumeric(customContent.getContentNine())) {
            countMap.put("contentNine", Integer.valueOf(customContent.getContentNine()));
        }
        if (!objectStrIsNull(customContent.getContentTen()) && isNumeric(customContent.getContentTen())) {
            countMap.put("contentTen", Integer.valueOf(customContent.getContentTen()));
        }
        return countMap;
    }

    /**
     * object转Int
     *
     * @param object
     * @return
     */
    private int objectToInt(Object object) {
        if (Objects.isNull(object)) {
            return 0;
        }
        if (StringUtils.isEmpty(String.valueOf(object)) || StringUtils.isBlank(String.valueOf(object))) {
            return 0;
        }
        if (!isNumeric(String.valueOf(object))) {
            return 0;
        }
        return Integer.valueOf(String.valueOf(object));
    }

    /**
     * 判断所需参数是否都存在
     *
     * @param map
     */
    private void isMapNull(Map<String, Object> map) {
        if (Objects.isNull(map)) {
            throw new IllegalArgumentException("参数为空");
        }
        if (objectStrIsNull(map.get("hours"))) {
            throw new IllegalArgumentException("查询结束时间为空");
        }
        if (objectStrIsNull(map.get("startTime"))) {
            throw new IllegalArgumentException("查询开始时间为空");
        }
        if (objectStrIsNull(map.get("countingTime"))) {
            throw new IllegalArgumentException("统计时刻为空");
        }
        if (objectStrIsNull(map.get("countingHours"))) {
            throw new IllegalArgumentException("间隔小结为空");
        }
        if (objectStrIsNull(map.get("patientId"))) {
            throw new IllegalArgumentException("患者id为空");
        }
    }

    /*public static void main(String[] args) throws ParseException {
        String s = "{\"code\":200,\n" +
                "\"data\":\n" +
                "[\n" +
                "{\"order_no\":\"\",\"dosage\":\"3\",\"signature\":\"\",\n" +
                "\"remark\":\"\",\"allowance_dosage\":\"\",\n" +
                "\"type\":1,\"order_name\":\"\",\"order_sub_no\":\"\",\n" +
                "\"balance\":\"\",\"data_time\":\"10:45\",\n" +
                "\"id\":\"fc007a05436b4fe19b527103acf48f1b\",\n" +
                "\"operator_code\":\"55555\",\"create_time\":\"2021-05-14 10:45:46\",\n" +
                "\"呕吐物\":\"66\",\"灌肠液\":\"20\",\"data_date\":\"2021-05-14\",\n" +
                "\"check_signature\":\"\",\"piss\":\"\",\"order_code\":\"\",\n" +
                "\"patient_id\":\"5aec44cbd0df4a089d16c6eb54f753d5\",\n" +
                "\"drainage\":\"\",\"visit_id\":\"376426\",\"illness_records\":\"\",\n" +
                "\"faces\":\"\",\"visit_code\":\"6271010\",\"status\":1}\n" +
                "]\n" +
                ",\"msg\":\"查询成功\"}";
        JSONObject jsonObject = JSONObject.fromObject(s);
        Object data = jsonObject.get("data");
        List<Map> mapList = castList(data, Map.class);
        mapList.forEach(map -> {
            // System.out.println(map.get("operator_code"));
        });

        Date laterHoursDate = getLaterHoursDate(new Date(), 2L);
        String s1 = yyyyMMddHHmmssSdfToString(laterHoursDate);
        // System.out.println(s1);

        // System.out.println(mapList.size());


        Map<String, Object> map = new HashMap<>();
        map.put("countingTime", "2021-05-14 17:43:00");
        map.put("countingHours", 10L);
        Date date = yyyyMMddHHmmssSdfToDate((String) map.get("countingTime"));
        Long countingHours = (Long) map.get("countingHours");
        System.out.println(countingHours);

        InOUtAmountStatisticsService i = new InOUtAmountStatisticsService();

        //System.out.println(i.isNumeric("456"));

        i.test();

    }*/

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 查询数据
     *
     * @param map
     * @return
     */
    public JSONObject getInOutAmountList(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map == null || !map.containsKey("patientId") || StringUtils.isEmpty(map.get("patientId").toString())) {
            json.put("code", HttpCode.NO_PATIENT_CODE.getCode());
            json.put("msg", "请选择病人");
            return json;
        }
        if (map != null && map.containsKey("start")) {
            int totalCount = inOutAmountDao.getInOutAmountNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        //先查询有哪些自定义字段，放入查询条件中
        Map<String, Object> m = new HashMap<>();
        m.put("status", 1);
        m.put("associatedTable", "table_in_out_amount");//关联表名
        List<CustomField> list = customFieldDao.queryList(m);
        if (list != null && list.size() > 0) {
            map.put("list", list);
        }
        List<Map<String, Object>> amountList = inOutAmountDao.queryInOutAmountList(map);
        if (amountList != null && amountList.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(amountList));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json;
    }
}
