package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.InOutAmount;
import com.jyxd.web.data.basic.InputAmount;
import com.jyxd.web.service.basic.InOutAmountService;
import com.jyxd.web.service.basic.InputAllowanceService;
import com.jyxd.web.service.basic.InputAmountService;
import com.jyxd.web.service.basic.OutputAmountService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.MathDoubleUtil;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value = "/inputAmount")
public class InputAmountController {

    private static Logger logger = LoggerFactory.getLogger(InputAmountController.class);

    @Autowired
    private InputAmountService inputAmountService;

    @Autowired
    private OutputAmountService outputAmountService;

    @Autowired
    private InputAllowanceService inputAllowanceService;

    @Autowired
    private InOutAmountService inOutAmountService;

    /**
     * 增加一条入量表记录
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody InputAmount inputAmount) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "添加失败");
        inputAmount.setId(UUIDUtil.getUUID());
        inputAmount.setCreateTime(new Date());
        inputAmountService.insert(inputAmount);
        json.put("code", HttpCode.OK_CODE.getCode());
        json.put("msg", "添加成功");
        return json.toString();
    }

    /**
     * 更新入量表记录状态
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg", "更新失败");
        if (map != null && map.containsKey("id") && map.containsKey("status")) {
            InputAmount inputAmount = inputAmountService.queryData(map.get("id").toString());
            if (inputAmount != null) {
                inputAmount.setStatus((int) map.get("status"));
                inputAmountService.update(inputAmount);
                json.put("msg", "更新成功");
            } else {
                json.put("msg", "更新失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 编辑入量表记录单
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg", "编辑失败");
        if (map != null && map.containsKey("id") && map.containsKey("status") && map.containsKey("bedName")) {
            InputAmount inputAmount = inputAmountService.queryData(map.get("id").toString());
            if (inputAmount != null) {
                inputAmount.setStatus((int) map.get("status"));
                inputAmountService.update(inputAmount);
                json.put("msg", "编辑成功");
            } else {
                json.put("msg", "编辑失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());

        return json.toString();
    }

    /**
     * 删除入量表记录
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg", "删除失败");
        if (map.containsKey("id")) {
            InputAmount inputAmount = inputAmountService.queryData(map.get("id").toString());
            if (inputAmount != null) {
                inputAmount.setStatus(-1);
                inputAmountService.update(inputAmount);
                json.put("msg", "删除成功");
            } else {
                json.put("msg", "删除失败，没有这个对象。");
                return json.toString();
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据主键id查询入量表记录
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData", method = RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map != null && map.containsKey("id")) {
            InputAmount inputAmount = inputAmountService.queryData(map.get("id").toString());
            if (inputAmount != null) {
                json.put("msg", "查询成功");
                json.put("data", JSONObject.fromObject(inputAmount));
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询入量表记录列表（也可以不分页）
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map != null && map.containsKey("start")) {
            int totalCount = inputAmountService.queryNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        List<InputAmount> list = inputAmountService.queryList(map);
        if (list != null && list.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(list));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理单--入量--保存一条入量记录
     *
     * @param inputAmount
     * @return
     */
    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    @ResponseBody
    public String saveData(@RequestBody InputAmount inputAmount) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "新增失败");
        //有id 则编辑  无id 则新增
        if (inputAmount != null && StringUtils.isNotEmpty(inputAmount.getId())) {
            inputAmount.setCreateTime(new Date());
            inputAmountService.update(inputAmount);
            json.put("msg", "编辑成功");
        } else {
            inputAmount.setId(UUIDUtil.getUUID());
            inputAmount.setOrderNo(UUIDUtil.getUUID());//医嘱主键  自定义生成一个医嘱主键 用于新增子医嘱使用
            inputAmount.setCreateTime(new Date());
            inputAmountService.insert(inputAmount);
            json.put("msg", "新增成功");
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书--护理单--入量--根据病人id查询入量列表
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getListByPatientId", method = RequestMethod.POST)
    @ResponseBody
    public String getListByPatientId(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map != null && map.containsKey("patientId")) {
            if (map.containsKey("start")) {
                int totalCount = inputAmountService.getNumByPatientId(map);
                map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
                json.put("totalCount", totalCount);
            }
            List<Map<String, Object>> list = inputAmountService.getListByPatientId(map);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).put("allowance_dosage", "");//余量
                    if (StringUtils.isNotEmpty(list.get(i).get("order_no").toString())) {
                        map.put("orderNo", list.get(i).get("order_no").toString());
                        List<String> str = inputAllowanceService.getAllowanceDosageByOrderNo(map);
                        if (str != null && str.size() > 0) {
                            list.get(i).put("allowance_dosage", StringUtils.join(str, '/'));//StringUtils.join(list,'/') list遍历成字符串拼接 /
                        }
                    }
                }
                json.put("data", JSONArray.fromObject(list));
                json.put("code", HttpCode.OK_CODE.getCode());
                json.put("msg", "查询成功");
            }
        } else {
            json.put("code", HttpCode.NO_PATIENT_CODE.getCode());
            json.put("msg", "请先选择病人");
        }
        return json.toString();
    }

    /**
     * 护理文书--护理单--入量--子医嘱
     *
     * @param inputAmount
     * @return
     */
    @RequestMapping(value = "/saveSonData", method = RequestMethod.POST)
    @ResponseBody
    public String saveSonData(@RequestBody InputAmount inputAmount) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "新增失败");
        inputAmount.setId(UUIDUtil.getUUID());
        inputAmount.setCreateTime(new Date());
        inputAmountService.insert(inputAmount);
        json.put("msg", "新增成功");
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入量--查询总入量、总出量、总平衡量
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInAndOutTotal", method = RequestMethod.POST)
    @ResponseBody
    public String getInAndOutTotal(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "失败");
        //查询入量总数
        String in = inputAmountService.getInTotal(map);
        //查询出量总数
        String out = outputAmountService.getOutTotal(map);
        //计算差值 总评衡量
        double in_int = Double.valueOf(in);
        double out_int = Double.valueOf(out);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("in", in);
        jsonObject.put("out", out);
        jsonObject.put("balance", Math.abs(MathDoubleUtil.sub(in_int, out_int)));//取绝对值

        json.put("data", jsonObject);
        json.put("msg", "成功");
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入量--查询出入量占比
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInAndOutProportion", method = RequestMethod.POST)
    @ResponseBody
    public String getInAndOutProportion(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "失败");
        //查询入量总数
        String in = inputAmountService.getInTotal(map);
        //查询出量总数
        String out = outputAmountService.getOutTotal(map);
        //计算差值 总评衡量
        double in_int = Double.valueOf(in);
        double out_int = Double.valueOf(out);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "总入量");
        jsonObject.put("value", in_int);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "总出量");
        jsonObject1.put("value", out_int);

        JSONArray array = new JSONArray();
        array.add(jsonObject);
        array.add(jsonObject1);
        json.put("series_data", array);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("总入量");
        jsonArray.add("总出量");
        json.put("legend_data", jsonArray);

        json.put("msg", "成功");
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入量--入量分析
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInAnalyze", method = RequestMethod.POST)
    @ResponseBody
    public String getInAnalyze(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        List<Map<String, Object>> list = inputAmountService.getInAnalyze(map);
        if (list != null && list.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> jsonObject = list.get(i);
                jsonArray.add(jsonObject.get("name").toString());
                JSONObject obj = new JSONObject();
                obj.put("value", jsonObject.get("dosage").toString());
                obj.put("name", jsonObject.get("name").toString());
                jsonArray1.add(obj);
            }
            json.put("msg", "成功");
            json.put("legend_data", jsonArray);
            json.put("series_data", jsonArray1);
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入量--出入量分析（最下边接口）
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInAndOutAnalyze", method = RequestMethod.POST)
    @ResponseBody
    public String getInAndOutAnalyze(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        //type  0：按天 1：按小时
        if (map != null && map.containsKey("type") && StringUtils.isNotEmpty(map.get("type").toString()) && map.get("type").toString().equals("0")) {
            //0：按天
            List<Map<String, Object>> dayList = inputAmountService.getInAndOutAnalyzeByDay(map);
            if (dayList != null && dayList.size() > 0) {
                for (int i = 0; i < dayList.size(); i++) {
                    String in = dayList.get(i).get("in_dosage").toString();//入量
                    String out = dayList.get(i).get("out_dosage").toString();//出量
                    double in_int = Double.valueOf(in);
                    double out_int = Double.valueOf(out);
                    dayList.get(i).put("balance", Math.abs(MathDoubleUtil.sub(in_int, out_int)));//平衡量
                }
                //查询入量总数
                String in = inputAmountService.getInTotal(map);
                //查询出量总数
                String out = outputAmountService.getOutTotal(map);
                //计算差值 总评衡量
                double in_int = Double.valueOf(in);
                double out_int = Double.valueOf(out);
                Map<String, Object> dayMap = new HashMap<>();
                dayMap.put("data_time", "总计");
                dayMap.put("in_dosage", in);
                dayMap.put("out_dosage", out);
                dayMap.put("balance", Math.abs(MathDoubleUtil.sub(in_int, out_int)));
                dayList.add(0, dayMap);

                json.put("data", JSONArray.fromObject(dayList));
                json.put("msg", "成功");
            }
        } else {
            //1：按小时
            List<Map<String, Object>> timeList = inputAmountService.getInAndOutAnalyzeByTime(map);
            if (timeList != null && timeList.size() > 0) {
                for (int i = 0; i < timeList.size(); i++) {
                    String in = timeList.get(i).get("in_dosage").toString();//入量
                    String out = timeList.get(i).get("out_dosage").toString();//出量
                    double in_int = Double.valueOf(in);
                    double out_int = Double.valueOf(out);
                    timeList.get(i).put("balance", Math.abs(MathDoubleUtil.sub(in_int, out_int)));//平衡量
                }
                //查询入量总数
                String in = inputAmountService.getInTotal(map);
                //查询出量总数
                String out = outputAmountService.getOutTotal(map);
                //计算差值 总评衡量
                double in_int = Double.valueOf(in);
                double out_int = Double.valueOf(out);
                Map<String, Object> dayMap = new HashMap<>();
                dayMap.put("data_time", "总计");
                dayMap.put("in_dosage", in);
                dayMap.put("out_dosage", out);
                dayMap.put("balance", Math.abs(MathDoubleUtil.sub(in_int, out_int)));
                timeList.add(0, dayMap);

                json.put("data", JSONArray.fromObject(timeList));
                json.put("msg", "成功");
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 统计分析--出入量--日期分析
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getDateAnalyze", method = RequestMethod.POST)
    @ResponseBody
    public String getDateAnalyze(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        //type  0：按天 1：按小时
        if (map != null && map.containsKey("type") && StringUtils.isNotEmpty(map.get("type").toString()) && map.get("type").toString().equals("0")) {
            //0：按天
            List<Map<String, Object>> dayList = inputAmountService.getInAndOutAnalyzeByDay(map);
            if (dayList != null && dayList.size() > 0) {
                JSONArray xAxisArray = new JSONArray();
                JSONArray inArray = new JSONArray();
                JSONArray outArray = new JSONArray();
                JSONArray balanceArray = new JSONArray();
                for (int i = 0; i < dayList.size(); i++) {
                    xAxisArray.add(dayList.get(i).get("data_time").toString());//日期
                    inArray.add(dayList.get(i).get("in_dosage").toString());//入量
                    outArray.add(dayList.get(i).get("out_dosage").toString());//出量
                    balanceArray.add(MathDoubleUtil.sub(Double.valueOf(dayList.get(i).get("in_dosage").toString()), Double.valueOf(dayList.get(i).get("out_dosage").toString())));//平衡量
                }
                json.put("xAxis_data", xAxisArray);//日期
                json.put("series_in_data", inArray);//入量
                json.put("series_out_data", outArray);//出量
                json.put("series_balance_data", balanceArray);//日期
                json.put("msg", "成功");
            }
        } else {
            //1：按小时
            List<Map<String, Object>> timeList = inputAmountService.getInAndOutAnalyzeByTime(map);
            if (timeList != null && timeList.size() > 0) {
                JSONArray xAxisArray = new JSONArray();
                JSONArray inArray = new JSONArray();
                JSONArray outArray = new JSONArray();
                JSONArray balanceArray = new JSONArray();
                for (int i = 0; i < timeList.size(); i++) {
                    xAxisArray.add(timeList.get(i).get("date").toString() + " " + timeList.get(i).get("time").toString());//日期
                    inArray.add(timeList.get(i).get("in_dosage").toString());//入量
                    outArray.add(timeList.get(i).get("out_dosage").toString());//出量
                    balanceArray.add(MathDoubleUtil.sub(Double.valueOf(timeList.get(i).get("in_dosage").toString()), Double.valueOf(timeList.get(i).get("out_dosage").toString())));//平衡量
                }
                json.put("xAxis_data", xAxisArray);//日期
                json.put("series_in_data", inArray);//入量
                json.put("series_out_data", outArray);//出量
                json.put("series_balance_data", balanceArray);//日期
                json.put("msg", "成功");
            }
        }

        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 护理文书-护理单文书-入量-核对签名-根据入量主键id查询出入量详情
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getDataDetailsByIdTest", method = RequestMethod.POST)
    @ResponseBody
    public String getDataDetailsByIdTest(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        Map<String, Object> obj = inputAmountService.getDataDetailsById(map);
        if (obj != null) {
            obj.put("allowance_dosage", "");
            if (StringUtils.isNotEmpty(obj.get("order_no").toString())) {
                map.put("orderNo", obj.get("order_no").toString());
                List<String> list = inputAllowanceService.getAllowanceDosageByOrderNo(map);
                if (list != null && list.size() > 0) {
                    obj.put("allowance_dosage", StringUtils.join(list, '/'));//StringUtils.join(list,'/') list遍历成字符串拼接 /
                }
            }
            json.put("data", JSONArray.fromObject(obj));
            json.put("code", HttpCode.OK_CODE.getCode());
            json.put("msg", "查询成功");
        }
        return json.toString();
    }

    /**
     * 护理文书-护理单文书-出入量合并-核对签名-根据入量主键id查询出入量详情
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getDataDetailsById", method = RequestMethod.POST)
    @ResponseBody
    public String getDataDetailsById(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        Map<String, Object> obj = inOutAmountService.queryInOutAmountById(map);
        if (Objects.nonNull(obj)) {
            obj.put("allowance_dosage", "");
            if (StringUtils.isNotEmpty(obj.get("order_no").toString())) {
                map.put("orderNo", obj.get("order_no").toString());
                List<String> list = inOutAmountService.getAllowanceDosageByOrderNo(map);
                if (list != null && list.size() > 0) {
                    obj.put("allowance_dosage", StringUtils.join(list, '/'));//StringUtils.join(list,'/') list遍历成字符串拼接 /
                }
            }
            json.put("data", JSONArray.fromObject(obj));
            json.put("code", HttpCode.OK_CODE.getCode());
            json.put("msg", "查询成功");
        }
        return json.toString();
    }

    /**
     * 护理文书-护理单文书-入量-核对签名-保存核对签名
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveCheckSignatureTest", method = RequestMethod.POST)
    @ResponseBody
    public String saveCheckSignatureTest(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "失败");
        if (map != null && map.containsKey("id") && map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("id").toString())) {
            InputAmount inputAmount = inputAmountService.queryData(map.get("id").toString());
            if (inputAmount != null) {
                inputAmount.setCheckSignature(map.get("checkSignature").toString());
                inputAmountService.update(inputAmount);
                json.put("code", HttpCode.OK_CODE.getCode());
                json.put("msg", "成功");
            }
        }
        return json.toString();
    }

    /**
     * 护理文书-护理单文书-出入量合一-核对签名-保存核对签名
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveCheckSignature", method = RequestMethod.POST)
    @ResponseBody
    public String saveCheckSignature(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "失败");
        if (map != null && map.containsKey("id") && map.containsKey("checkSignature") && StringUtils.isNotEmpty(map.get("id").toString())) {
            InOutAmount inOutAmount = inOutAmountService.queryData(map.get("id").toString());
            if (Objects.nonNull(inOutAmount)) {
                logger.info("saveCheckSignature >> dataTime >> " + inOutAmount.getDataTime());
                inOutAmount.setCheckSignature(map.get("checkSignature").toString());
                inOutAmountService.update(inOutAmount);
                json.put("code", HttpCode.OK_CODE.getCode());
                json.put("msg", "成功");
            }
        }
        return json.toString();
    }

}
