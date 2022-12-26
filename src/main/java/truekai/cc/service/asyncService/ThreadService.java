package truekai.cc.service.asyncService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import truekai.cc.mapper.MsArticleMapper;
import truekai.cc.vo.MsArticleVo;

/**
 * 作者：熊凯凯
 * 日期：2022-12-25 12:22
 */
@Component
@Slf4j
public class ThreadService {
    @Autowired
    private MsArticleMapper articleMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;



    //异步更新文章的阅读数,
    //线程池的异常机制。需不需事务回滚
    /**
     * 方案1：update article set view_count=100 where view_count=99 and id=11 ；利用数据库实现高并发一致性 [可以参考减库存实现]
     * 方案2：使用redis 自增，在redis 自增之后发送到消息队列进行消费，然后同步数据库，比定时任务好
     * 先暂时采用方案1
     */
    @Async
    @Transactional
    public void updateArticleViewCount(Long id, MsArticleVo vo) {
        log.info("更新ID:{},前阅读数量:{}",id,vo.getViewCounts());
        Integer a=articleMapper.updateArticleViewCountById(id,vo.getViewCounts());
        log.info("异步更新阅读数量成功！");
    }
    //方案2：通过Redis来同步
    @Async
    @Transactional
    public void updateArticleViewCount2(Long id, MsArticleVo vo) {
        log.info("更新ID:{},前阅读数量:{}",id,vo.getViewCounts());
        redisTemplate.opsForHash().increment("blog::view_count",String.valueOf(vo.getId()),1);
        log.info("异步更新阅读数量成功！");
    }
}
