package truekai.cc.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import truekai.cc.mapper.MsArticleMapper;
import truekai.cc.model.MsArticleDO;
import org.joda.time.DateTime;
import java.util.Map;

@Component
@Slf4j
@EnableScheduling
public class ViewCountHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MsArticleMapper articleMapper;


    @Scheduled(fixedRate = 10000*20,initialDelay = 5000)
    @Async //扔到线程池 执行
    public void scheduled(){
       log.info("=====>>>>> 同步浏览量开始执行  {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        Map<Object, Object> countMap = redisTemplate.opsForHash().entries("blog::view_count");
        for (Map.Entry<Object,Object> entry : countMap.entrySet()){
            Long articleId = Long.parseLong(entry.getKey().toString());
            Integer count = Integer.parseInt(entry.getValue().toString());
            MsArticleDO article = new MsArticleDO();
            article.setId(articleId);
            article.setViewCounts(count);
            articleMapper.updateById(article);
        }
      log.info("=====>>>>> 同步浏览量结束  {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
    }

}
