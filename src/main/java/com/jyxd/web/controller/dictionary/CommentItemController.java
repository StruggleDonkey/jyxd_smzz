package com.jyxd.web.controller.dictionary;

import com.jyxd.web.data.dictionary.CommenItemDictionary;
import com.jyxd.web.service.dictionary.CommentItemService;
import com.jyxd.web.service.dictionary.CommonDictionaryService;
import com.jyxd.web.util.HttpCode;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/commentItem")
public class CommentItemController {

    private static Logger logger= LoggerFactory.getLogger(CommentItemController.class);

    @Autowired
    private CommentItemService commentItemService;

    @Autowired
    private CommonDictionaryService commonDictionaryService;

    /**
     * 新增一条通用字典表数据
     * @param commenItemDictionary
     * @return
     */
    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(@RequestBody(required=false) CommenItemDictionary commenItemDictionary){
        JSONObject json=new JSONObject();
        json.put("data",new ArrayList<>());
        json.put("code", HttpCode.FAILURE_CODE.getCode());
        json.put("msg","添加失败");
        commenItemDictionary.setId(UUIDUtil.getUUID());
        commentItemService.insert(commenItemDictionary);
        json.put("code",HttpCode.OK_CODE.getCode());
        json.put("msg","添加成功");
        return json.toString();
    }

    /**
     * 更新一条通用字典表数据状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","更新失败");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                commenItemDictionary.setStatus((int)map.get("status"));
                commentItemService.update(commenItemDictionary);
                json.put("msg","更新成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 编辑一条通用字典表数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public String edit(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","编辑失败");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id") && map.containsKey("status") && map.containsKey("commonItemCode") && map.containsKey("commonItemName")
                && map.containsKey("type") && map.containsKey("description")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                commenItemDictionary.setStatus((int)map.get("status"));
                commenItemDictionary.setType(map.get("type").toString());
                commenItemDictionary.setCommonItemName(map.get("commonItemName").toString());
                commenItemDictionary.setCommonItemCode(map.get("commonItemCode").toString());
                commenItemDictionary.setDescription(map.get("description").toString());
                commentItemService.update(commenItemDictionary);
                json.put("msg","编辑成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 删除一条通用字典表数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","删除失败");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        if(map.containsKey("id")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                commenItemDictionary.setStatus(-1);
                commentItemService.update(commenItemDictionary);
                json.put("msg","删除成功");
                json.put("code",HttpCode.OK_CODE.getCode());
            }
        }
        return json.toString();
    }

    /**
     * 根据主键id查询通用字典表对象
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryData",method= RequestMethod.POST)
    @ResponseBody
    public String queryData(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map.containsKey("id")){
            CommenItemDictionary commenItemDictionary=commentItemService.queryData(map.get("id").toString());
            if(commenItemDictionary!=null){
                json.put("data",JSONObject.fromObject(commenItemDictionary));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询通用字典表列表（可以不分页）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryList",method= RequestMethod.POST)
    @ResponseBody
    public String queryList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        List<CommenItemDictionary> list =commentItemService.queryList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据条件分页查询通用字典表列表（可以不分页）--多表查询
     * @param map
     * @return
     */
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public String getList(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        if(map!=null && map.containsKey("start")){
            int totalCount =commentItemService.queryNum(map);
            map.put("start",((int)map.get("start")-1)*(int)map.get("size"));
            json.put("totalCount",totalCount);
        }
        List<Map<String,Object>> list =commentItemService.getList(map);
        if(list!=null && list.size()>0){
            json.put("data",JSONArray.fromObject(list));
            json.put("msg","查询成功");
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 根据名称获取code数组（适用于护理文书--护理单--获取出入量类型、护理文书--护理单模块内容）
     * @param map
     * @return
     */
    @RequestMapping(value = "/getCodeListByName",method= RequestMethod.POST)
    @ResponseBody
    public String getCodeListByName(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("msg","暂无数据");
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        String type=commonDictionaryService.queryTypeByName(map);
        if(StringUtils.isNotEmpty(type)){
            map.put("type",type);
            List<Map<String,Object>> list =commentItemService.getCodeListByType(map);
            if(list!=null && list.size()>0){
                json.put("data",JSONArray.fromObject(list));
                json.put("msg","查询成功");
            }
        }
        json.put("code",HttpCode.OK_CODE.getCode());
        return json.toString();
    }

    /**
     * 从his系统获取并更新通用字典数据 如用药分类字典、补液类型字典、给药途径字典、护理级别、病情、婚姻状况
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateCommentItemByHis",method= RequestMethod.POST)
    @ResponseBody
    public String updateDepartmentByHis(@RequestBody(required=false) Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",HttpCode.FAILURE_CODE.getCode());
        json.put("data",new ArrayList<>());
        json.put("msg","失败");

        map.put("type","drug_type");//用药分类
        //查询本地数据库字典数据 用药分类
        List<CommenItemDictionary> list=commentItemService.queryDataListByType(map);
        //从his数据库视图中查询 用药分类
        List<Map<String,Object>> hisList=commentItemService.getDrugTypeByHis(map);
        if(hisList!=null && hisList.size()>0){
            if(list!=null && list.size()>0){
                //如果数据库有数据则需要和his中获取的数据做比较再更新
                ArrayList arrayList= new ArrayList();
                for (int i = 0; i <list.size(); i++) {
                    arrayList.add(list.get(i).getCommonItemCode());
                }
                ArrayList arrayHisList= new ArrayList();
                for (int i = 0; i < hisList.size(); i++) {
                    arrayHisList.add(hisList.get(i).get("drug_type_code").toString());
                }

                for (int i = 0; i <list.size(); i++) {
                    if(!arrayHisList.contains(list.get(i).getCommonItemCode())){
                        //如果his系统中数据 里 没有本地数据 则删除本地数据
                        list.get(i).setStatus(-1);
                        commentItemService.update(list.get(i));
                    }
                }

                for (int i = 0; i <hisList.size(); i++) {
                    if(!arrayList.contains(hisList.get(i).get("drug_type_code").toString())){
                        //如果本地数据不包含 his系统数据 则新增数据
                        CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                        commenItemDictionary.setStatus(1);
                        commenItemDictionary.setId(UUIDUtil.getUUID());
                        commenItemDictionary.setCommonItemCode(hisList.get(i).get("drug_type_code").toString());//用药分类编码
                        commenItemDictionary.setCommonItemName(hisList.get(i).get("drug_type_name").toString());//用药分类名称
                        commenItemDictionary.setType("drug_type");//用药分类
                        commentItemService.insert(commenItemDictionary);
                    }
                }

            }else{
                //直接将his获取的数据添加到本地数据库
                for (int i = 0; i < hisList.size(); i++) {
                    CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                    commenItemDictionary.setStatus(1);
                    commenItemDictionary.setId(UUIDUtil.getUUID());
                    commenItemDictionary.setCommonItemCode(hisList.get(i).get("drug_type_code").toString());//用药分类编码
                    commenItemDictionary.setCommonItemName(hisList.get(i).get("drug_type_name").toString());//用药分类名称
                    commenItemDictionary.setType("drug_type");//用药分类
                    commentItemService.insert(commenItemDictionary);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }

        map.put("type","order_attr");//补液类型
        //查询本地数据库字典数据 补液类型
        List<CommenItemDictionary> list1=commentItemService.queryDataListByType(map);
        //从his数据库视图中查询 补液类型
        List<Map<String,Object>> hisList1=commentItemService.getDrugTypeByHis(map);
        if(hisList1!=null && hisList1.size()>0){
            if(list1!=null && list1.size()>0){
                //如果数据库有数据则需要和his中获取的数据做比较再更新
                ArrayList arrayList= new ArrayList();
                for (int i = 0; i <list1.size(); i++) {
                    arrayList.add(list1.get(i).getCommonItemCode());
                }

                ArrayList arrayHisList= new ArrayList();
                for (int i = 0; i < hisList1.size(); i++) {
                    arrayHisList.add(hisList1.get(i).get("order_attr_code").toString());
                }

                for (int i = 0; i <list1.size(); i++) {
                    if(!arrayHisList.contains(list1.get(i).getCommonItemCode())){
                        //如果his系统中数据 里 没有本地数据 则删除本地数据
                        list1.get(i).setStatus(-1);
                        commentItemService.update(list1.get(i));
                    }
                }

                for (int i = 0; i <hisList1.size(); i++) {
                    if(!arrayList.contains(hisList1.get(i).get("order_attr_code").toString())){
                        //如果本地数据不包含 his系统数据 则新增数据
                        CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                        commenItemDictionary.setStatus(1);
                        commenItemDictionary.setId(UUIDUtil.getUUID());
                        commenItemDictionary.setCommonItemCode(hisList1.get(i).get("order_attr_code").toString());//补液类型编码
                        commenItemDictionary.setCommonItemName(hisList1.get(i).get("order_attr_name").toString());//补液类型名称
                        commenItemDictionary.setType("order_attr");//用药分类
                        commentItemService.insert(commenItemDictionary);
                    }
                }

            }else{
                //直接将his获取的数据添加到本地数据库
                for (int i = 0; i < hisList1.size(); i++) {
                    CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                    commenItemDictionary.setStatus(1);
                    commenItemDictionary.setId(UUIDUtil.getUUID());
                    commenItemDictionary.setCommonItemCode(hisList1.get(i).get("order_attr_code").toString());//补液类型编码
                    commenItemDictionary.setCommonItemName(hisList1.get(i).get("order_attr_name").toString());//补液类型名称
                    commenItemDictionary.setType("order_attr");//补液类型
                    commentItemService.insert(commenItemDictionary);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }

        map.put("type","use_mode");//给药途径
        //查询本地数据库字典数据 给药途径
        List<CommenItemDictionary> list2=commentItemService.queryDataListByType(map);
        //从his数据库视图中查询 给药途径
        List<Map<String,Object>> hisList2=commentItemService.getDrugTypeByHis(map);
        if(hisList2!=null && hisList2.size()>0){
            if(list2!=null && list2.size()>0){
                //如果数据库有数据则需要和his中获取的数据做比较再更新
                ArrayList arrayList= new ArrayList();
                for (int i = 0; i <list2.size(); i++) {
                    arrayList.add(list2.get(i).getCommonItemCode());
                }

                ArrayList arrayHisList= new ArrayList();
                for (int i = 0; i < hisList2.size(); i++) {
                    arrayHisList.add(hisList2.get(i).get("use_mode_code").toString());
                }

                for (int i = 0; i <list2.size(); i++) {
                    if(!arrayHisList.contains(list2.get(i).getCommonItemCode())){
                        //如果his系统中数据 里 没有本地数据 则删除本地数据
                        list2.get(i).setStatus(-1);
                        commentItemService.update(list2.get(i));
                    }
                }

                for (int i = 0; i <hisList2.size(); i++) {
                    if(!arrayList.contains(hisList2.get(i).get("use_mode_code").toString())){
                        //如果本地数据不包含 his系统数据 则新增数据
                        CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                        commenItemDictionary.setStatus(1);
                        commenItemDictionary.setId(UUIDUtil.getUUID());
                        commenItemDictionary.setCommonItemCode(hisList2.get(i).get("use_mode_code").toString());//给药途径编码
                        commenItemDictionary.setCommonItemName(hisList2.get(i).get("use_mode_name").toString());//给药途径名称
                        commenItemDictionary.setType("use_mode");//给药途径
                        commentItemService.insert(commenItemDictionary);
                    }
                }

            }else{
                //直接将his获取的数据添加到本地数据库
                for (int i = 0; i < hisList2.size(); i++) {
                    CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                    commenItemDictionary.setStatus(1);
                    commenItemDictionary.setId(UUIDUtil.getUUID());
                    commenItemDictionary.setCommonItemCode(hisList2.get(i).get("use_mode_code").toString());//给药途径编码
                    commenItemDictionary.setCommonItemName(hisList2.get(i).get("use_mode_name").toString());//给药途径名称
                    commenItemDictionary.setType("use_mode");//给药途径
                    commentItemService.insert(commenItemDictionary);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }

        map.put("type","nursing_level");// 护理级别
        //查询本地数据库字典数据  护理级别
        List<CommenItemDictionary> list3=commentItemService.queryDataListByType(map);
        //从his数据库视图中查询  护理级别
        List<Map<String,Object>> hisList3=commentItemService.getDrugTypeByHis(map);
        if(hisList3!=null && hisList3.size()>0){
            if(list3!=null && list3.size()>0){
                //如果数据库有数据则需要和his中获取的数据做比较再更新
                ArrayList arrayList= new ArrayList();
                for (int i = 0; i <list3.size(); i++) {
                    arrayList.add(list3.get(i).getCommonItemCode());
                }

                ArrayList arrayHisList= new ArrayList();
                for (int i = 0; i < hisList3.size(); i++) {
                    arrayHisList.add(hisList3.get(i).get("nursing_level_code").toString());
                }

                for (int i = 0; i <list3.size(); i++) {
                    if(!arrayHisList.contains(list3.get(i).getCommonItemCode())){
                        //如果his系统中数据 里 没有本地数据 则删除本地数据
                        list3.get(i).setStatus(-1);
                        commentItemService.update(list3.get(i));
                    }
                }

                for (int i = 0; i <hisList3.size(); i++) {
                    if(!arrayList.contains(hisList3.get(i).get("nursing_level_code").toString())){
                        //如果本地数据不包含 his系统数据 则新增数据
                        CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                        commenItemDictionary.setStatus(1);
                        commenItemDictionary.setId(UUIDUtil.getUUID());
                        commenItemDictionary.setCommonItemCode(hisList3.get(i).get("nursing_level_code").toString());// 护理级别编码
                        commenItemDictionary.setCommonItemName(hisList3.get(i).get("nursing_level_name").toString());// 护理级别名称
                        commenItemDictionary.setType("nursing_level");// 护理级别
                        commentItemService.insert(commenItemDictionary);
                    }
                }

            }else{
                //直接将his获取的数据添加到本地数据库
                for (int i = 0; i < hisList3.size(); i++) {
                    CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                    commenItemDictionary.setStatus(1);
                    commenItemDictionary.setId(UUIDUtil.getUUID());
                    commenItemDictionary.setCommonItemCode(hisList3.get(i).get("nursing_level_code").toString());// 护理级别编码
                    commenItemDictionary.setCommonItemName(hisList3.get(i).get("nursing_level_name").toString());// 护理级别名称
                    commenItemDictionary.setType("nursing_level");// 护理级别
                    commentItemService.insert(commenItemDictionary);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }

        map.put("type","illness_state");// 病情
        //查询本地数据库字典数据  病情
        List<CommenItemDictionary> list4=commentItemService.queryDataListByType(map);
        //从his数据库视图中查询  病情
        List<Map<String,Object>> hisList4=commentItemService.getDrugTypeByHis(map);
        if(hisList4!=null && hisList4.size()>0){
            if(list4!=null && list4.size()>0){
                //如果数据库有数据则需要和his中获取的数据做比较再更新
                ArrayList arrayList= new ArrayList();
                for (int i = 0; i <list4.size(); i++) {
                    arrayList.add(list4.get(i).getCommonItemCode());
                }

                ArrayList arrayHisList= new ArrayList();
                for (int i = 0; i < hisList4.size(); i++) {
                    arrayHisList.add(hisList4.get(i).get("illness_state_code").toString());
                }

                for (int i = 0; i <list4.size(); i++) {
                    if(!arrayHisList.contains(list4.get(i).getCommonItemCode())){
                        //如果his系统中数据 里 没有本地数据 则删除本地数据
                        list4.get(i).setStatus(-1);
                        commentItemService.update(list4.get(i));
                    }
                }

                for (int i = 0; i <hisList4.size(); i++) {
                    if(!arrayList.contains(hisList4.get(i).get("illness_state_code").toString())){
                        //如果本地数据不包含 his系统数据 则新增数据
                        CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                        commenItemDictionary.setStatus(1);
                        commenItemDictionary.setId(UUIDUtil.getUUID());
                        commenItemDictionary.setCommonItemCode(hisList4.get(i).get("illness_state_code").toString());// 病情编码
                        commenItemDictionary.setCommonItemName(hisList4.get(i).get("illness_state_name").toString());// 病情名称
                        commenItemDictionary.setType("illness_state");// 病情
                        commentItemService.insert(commenItemDictionary);
                    }
                }

            }else{
                //直接将his获取的数据添加到本地数据库
                for (int i = 0; i < hisList4.size(); i++) {
                    CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                    commenItemDictionary.setStatus(1);
                    commenItemDictionary.setId(UUIDUtil.getUUID());
                    commenItemDictionary.setCommonItemCode(hisList4.get(i).get("illness_state_code").toString());// 病情编码
                    commenItemDictionary.setCommonItemName(hisList4.get(i).get("illness_state_name").toString());// 病情名称
                    commenItemDictionary.setType("illness_state");// 病情
                    commentItemService.insert(commenItemDictionary);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }

        map.put("type","marital_state");// 婚姻状况
        //查询本地数据库字典数据  婚姻状况
        List<CommenItemDictionary> list5=commentItemService.queryDataListByType(map);
        //从his数据库视图中查询  婚姻状况
        List<Map<String,Object>> hisList5=commentItemService.getDrugTypeByHis(map);
        if(hisList5!=null && hisList5.size()>0){
            if(list5!=null && list5.size()>0){
                //如果数据库有数据则需要和his中获取的数据做比较再更新
                ArrayList arrayList= new ArrayList();
                for (int i = 0; i <list5.size(); i++) {
                    arrayList.add(list5.get(i).getCommonItemCode());
                }

                ArrayList arrayHisList= new ArrayList();
                for (int i = 0; i < hisList5.size(); i++) {
                    arrayHisList.add(hisList5.get(i).get("marital_state_code").toString());
                }

                for (int i = 0; i <list5.size(); i++) {
                    if(!arrayHisList.contains(list5.get(i).getCommonItemCode())){
                        //如果his系统中数据 里 没有本地数据 则删除本地数据
                        list5.get(i).setStatus(-1);
                        commentItemService.update(list5.get(i));
                    }
                }

                for (int i = 0; i <hisList5.size(); i++) {
                    if(!arrayList.contains(hisList5.get(i).get("marital_state_code").toString())){
                        //如果本地数据不包含 his系统数据 则新增数据
                        CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                        commenItemDictionary.setStatus(1);
                        commenItemDictionary.setId(UUIDUtil.getUUID());
                        commenItemDictionary.setCommonItemCode(hisList5.get(i).get("marital_state_code").toString());// 婚姻状况编码
                        commenItemDictionary.setCommonItemName(hisList5.get(i).get("marital_state_name").toString());// 婚姻状况名称
                        commenItemDictionary.setType("marital_state");// 婚姻状况
                        commentItemService.insert(commenItemDictionary);
                    }
                }

            }else{
                //直接将his获取的数据添加到本地数据库
                for (int i = 0; i < hisList5.size(); i++) {
                    CommenItemDictionary commenItemDictionary=new CommenItemDictionary();
                    commenItemDictionary.setStatus(1);
                    commenItemDictionary.setId(UUIDUtil.getUUID());
                    commenItemDictionary.setCommonItemCode(hisList5.get(i).get("marital_state_code").toString());// 婚姻状况编码
                    commenItemDictionary.setCommonItemName(hisList5.get(i).get("marital_state_name").toString());// 婚姻状况名称
                    commenItemDictionary.setType("marital_state");// 婚姻状况
                    commentItemService.insert(commenItemDictionary);
                }
            }
            json.put("code",HttpCode.OK_CODE.getCode());
            json.put("msg","成功");
        }

        return json.toString();
    }

}
