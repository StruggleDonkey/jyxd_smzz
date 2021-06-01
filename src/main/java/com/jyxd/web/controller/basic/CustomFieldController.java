package com.jyxd.web.controller.basic;

import com.jyxd.web.data.basic.AbnormalVitalSigns;
import com.jyxd.web.data.basic.CustomField;
import com.jyxd.web.data.user.User;
import com.jyxd.web.service.basic.AbnormalVitalSignsService;
import com.jyxd.web.service.basic.CustomFieldService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/customField")
public class CustomFieldController {

    private static Logger logger = LoggerFactory.getLogger(CustomFieldController.class);

    @Autowired
    private CustomFieldService customFieldService;

    /**
     * 增加一条自定义字段名称表记录
     *
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody CustomField customField) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "添加失败");
        customField.setId(UUIDUtil.getUUID());
        customField.setCreateTime(new Date());
        customFieldService.insert(customField);
        json.put("code", HttpCode.OK_CODE.getCode());
        json.put("msg", "添加成功");
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-自定义字段功能（不仅限于出入量）- 编辑启用 禁用
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
            CustomField customField = customFieldService.queryData(map.get("id").toString());
            if (customField != null) {
                customField.setStatus((int) map.get("status"));
                customFieldService.update(customField);
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
     * 编辑自定义字段名称表记录单
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
        if (map != null && map.containsKey("id") && map.containsKey("status") && map.containsKey("filedName")) {
            CustomField customField = customFieldService.queryData(map.get("id").toString());
            if (customField != null) {
                customField.setStatus((int) map.get("status"));
                customField.setFiledName((String) map.get("filedName"));
                customFieldService.update(customField);
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
     * 删除自定义字段名称表记录
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
            CustomField customField = customFieldService.queryData(map.get("id").toString());
            if (customField != null) {
                customField.setStatus(-1);
                customFieldService.update(customField);
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
     * 根据主键id查询自定义字段名称表记录
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
            CustomField customField = customFieldService.queryData(map.get("id").toString());
            if (customField != null) {
                json.put("msg", "查询成功");
                json.put("data", JSONObject.fromObject(customField));
            }
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询自定义字段名称表记录列表（也可以不分页）
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
            int totalCount = customFieldService.queryNum(map);
            map.put("start", ((int) map.get("start") - 1) * (int) map.get("size"));
            json.put("totalCount", totalCount);
        }
        List<CustomField> list = customFieldService.queryList(map);
        if (list != null && list.size() > 0) {
            json.put("msg", "查询成功");
            json.put("data", JSONArray.fromObject(list));
        }
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询自定义字段（不仅限于出入量）
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getCustomFieldList", method = RequestMethod.POST)
    @ResponseBody
    public String getCustomFieldList(@RequestBody(required = false) Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "暂无数据");
        if (map != null) {
            List<CustomField> list = customFieldService.queryList(map);
            if (list != null) {
                json.put("list", JSONArray.fromObject(list));
                json.put("msg", "查询成功");
                json.put("code", HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-自定义字段功能（不仅限于出入量）-保存
     *
     * @param customField
     * @return
     */
    @RequestMapping(value = "/saveDate", method = RequestMethod.POST)
    @ResponseBody
    public String saveDate(@RequestBody CustomField customField, HttpSession session) {
        JSONObject json = new JSONObject();
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("data", new ArrayList<>());
        json.put("msg", "失败");
        customField.setId(UUIDUtil.getUUID());
        customField.setCreateTime(new Date());
        User user = (User) session.getAttribute("user");
        if (user != null) {
            customField.setOperatorCode(user.getLoginName());
        }
        customFieldService.insert(customField);
        json.put("msg", "成功");
        json.put("code", HttpCode.OK_CODE.getCode());
        return json.toString();
    }

}
