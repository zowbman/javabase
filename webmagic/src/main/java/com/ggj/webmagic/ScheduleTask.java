package com.ggj.webmagic;

import com.ggj.webmagic.elasticsearch.ElasticSearch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author:gaoguangjin
 * @date 2016/8/19 11:01
 */
@Service
@Slf4j
public class ScheduleTask {
    @Autowired
    private WebmagicService webmagicService;
    @Autowired
    private ElasticSearch elasticSearch;
    //一个小时更新一次，执行top
//    @Scheduled(initialDelay = 0, fixedRate = 1000*60*60*6)
//    @Scheduled(cron="0 */60 * * * ?")
    public void scheduleUpdateTieBa() {
        try {
            webmagicService.addTieBaTop();
        } catch (Exception e) {
           log.error("贴吧同步TOP失败！"+e.getLocalizedMessage());
        }
    }

    //1分钟更新一次，执行top
    @Scheduled(initialDelay = 0, fixedDelay = 1000*10)
    public void scheduleTieBaImage() {
        try {
        webmagicService.addTieBaImage();
        } catch (Exception e) {
            log.error("贴吧同步Image失败！"+e.getLocalizedMessage());
        }
    }

    /**
     * 将贴吧信息放到elasticsearch
     */
    @Scheduled(initialDelay = 0, fixedDelay = 1000*60*1)
    public void scheduleSearchIndex() {
        try {
            elasticSearch.addTieBaContentIndex();
        } catch (Exception e) {
            log.error("贴吧同步Image失败！"+e.getLocalizedMessage());
        }
    }

}
