package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.CustomContentDao;
import com.jyxd.web.dao.basic.CustomFieldDao;
import com.jyxd.web.dao.basic.InOutAmountDao;
import com.jyxd.web.data.basic.CustomContent;
import com.jyxd.web.data.basic.CustomField;
import com.jyxd.web.data.basic.Schedual;
import com.jyxd.web.data.dto.StatisticsDTO;
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
        Date countingTime = hhmmssSdfToDate((String) map.get("countingTime"));
        Long countingHours = (Long) map.get("countingTime");
        Long summaryHours = 0L;
        if (!objectStrIsNull(map.get("summaryHours"))) {
            summaryHours = (Long) map.get("summaryHours");
        }
        Date endTime = getLaterHoursDate(new Date(), Long.valueOf(String.valueOf(map.get("hours"))));
        map.put("endTime", endTime);
        JSONObject inOutAmountJson = getInOutAmountList(map);
        Object data = inOutAmountJson.get("data");
        List<Map> inOutAmountMapList = castList(data, Map.class);
        List<Map> newOutAmountMapList = inOutAmountMapList;
        //最后一条数据
        Map finallyData = inOutAmountMapList.get(inOutAmountMapList.size() - 1);
        String finallyDateDate = (String) finallyData.get("data_date");
        String finallyDateTime = (String) finallyData.get("data_time");


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
            Date dateTime = hhmmssSdfToDate((String) inOutAmountMap.get("data_time"));
            if (dateTime.getTime() <= countingTime.getTime()) {
                statisticsList.add(inOutAmountMap);
            }
            if (dateTime.getTime() > countingTime.getTime()) {
                oneStatisticsCount = i;
                break;
            }
        }
        //插入第一次节点总结
        newOutAmountMapList.add(oneStatisticsCount, mapDataTransition(addStatisticsDate(statisticsList), inOutAmountMapList.get(oneStatisticsCount - 1)));

        System.out.println("计算后的数据：" + newOutAmountMapList);

        for (int i = oneStatisticsCount; i < inOutAmountMapList.size() && oneStatisticsCount != 0; i++) {

        }
        inOutAmountJson.put("data", JSONArray.fromObject(newOutAmountMapList));
        return inOutAmountJson.toString();
    }

    //addStatisticsDate(inOutAmountMap, countingTime, countingHours, summaryHours, statisticsList, i, statisticsCount);

    /**
     * 转换为前端数据
     *
     * @return
     */
    private Map<String, Object> mapDataTransition(Map<String, Integer> statisticsMap, Map<String, Object> mapDate) {
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : mapDate.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
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
        map.put("dataTime", statisticsMap.get("data_data") + " " + statisticsMap.get("data_time"));
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
    private Map<String, Integer> calculateAccumulation(Map<String, Integer> calculateAccumulationMap, Map<String, Integer> stringIntegerMap) {
        Integer dosage = stringIntegerMap.get("dosageCount");
        calculateAccumulationMap.put("dosage", dosage + calculateAccumulationMap.get("dosage"));
        Integer allowanceDosageCount = stringIntegerMap.get("allowanceDosageCount");
        calculateAccumulationMap.put("allowanceDosage", allowanceDosageCount + calculateAccumulationMap.get("allowance_dosage"));
        Integer piss = stringIntegerMap.get("pissCount");
        calculateAccumulationMap.put("piss", piss + calculateAccumulationMap.get("piss"));
        Integer faces = stringIntegerMap.get("facesCount");
        calculateAccumulationMap.put("faces", faces + calculateAccumulationMap.get("faces"));
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
        if (!objectStrIsNull(customContent.getContentOne()) && isNumeric(customContent.getContentOne())) {
            countMap.put("contentOne", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentTwo()) && isNumeric(customContent.getContentTwo())) {
            countMap.put("contentTwo", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentThree()) && isNumeric(customContent.getContentThree())) {
            countMap.put("contentThree", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentFour()) && isNumeric(customContent.getContentFour())) {
            countMap.put("contentFour", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentFive()) && isNumeric(customContent.getContentFive())) {
            countMap.put("contentFive", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentSix()) && isNumeric(customContent.getContentSix())) {
            countMap.put("contentSix", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentSeven()) && isNumeric(customContent.getContentSeven())) {
            countMap.put("contentSeven", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentEight()) && isNumeric(customContent.getContentEight())) {
            countMap.put("contentEight", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentNine()) && isNumeric(customContent.getContentNine())) {
            countMap.put("contentNine", balanceCount);
        }
        if (!objectStrIsNull(customContent.getContentTen()) && isNumeric(customContent.getContentTen())) {
            countMap.put("contentTen", balanceCount);
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
        if (StringUtils.isEmpty((String) object) || StringUtils.isBlank((String) object)) {
            return 0;
        }
        return Integer.valueOf((String) object);
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

    public static void main(String[] args) throws ParseException {
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

    }

    public void test() {

        Integer u = 1;
        Map map = new HashMap();
        map.put("456", 1);
        for (int i = 0; i < 3; i++) {

            i(map);
            System.out.println(map.get("456"));

        }

    }


    private void i(Map map) {
        // map.put("456", 1);
        Object o = map.get("456");
        Integer integer = Integer.valueOf(String.valueOf(o));
        map.put("456", 1 + integer);
    }

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
        List<Map<String, Object>> amountList = inOutAmountDao.getInOutAmountList(map);
        if (amountList != null && amountList.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(amountList));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json;
    }
}
