package com.jyxd.web.hl7.socket;

import com.jyxd.web.data.dictionary.MonitorDictionary;
import com.jyxd.web.service.dictionary.MonitorDictionaryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序 值越小越先执行
 */
@Component
@Order(value = 1)
public class ApplicationRunnerImpl  implements ApplicationRunner {

    private static Logger logger= LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    @Autowired
    private MonitorDictionaryService monitorDictionaryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startMonitor();
    }

    /**
     * 查询所有已经分配给床位的监护仪，启动这些监护仪socket连接，监听信息
     */
    public void startMonitor(){
        List<MonitorDictionary> list=monitorDictionaryService.getBedMonitorList();
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size() ; i++) {
                MonitorDictionary monitorDictionary=list.get(i);
                 if(StringUtils.isNotEmpty(monitorDictionary.getMonitorIp()) && StringUtils.isNotEmpty(monitorDictionary.getMonitorPort())){
                        new SocketThread(monitorDictionary.getMonitorIp(),Integer.valueOf(monitorDictionary.getMonitorPort())).start();//启动客户端
                 }
            }
        }
    }

}
