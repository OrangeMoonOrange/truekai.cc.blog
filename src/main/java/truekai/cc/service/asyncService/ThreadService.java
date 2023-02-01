package truekai.cc.service.asyncService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import truekai.cc.mapper.MsArticleMapper;
import truekai.cc.mapper.MsSysUserMapper;
import truekai.cc.model.MsSysUserDO;
import truekai.cc.service.MailService;
import truekai.cc.vo.MsArticleVo;

import java.util.ArrayList;
import java.util.List;

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


    @Autowired
    private MailService mailService;

    @Autowired
    private MsSysUserMapper userMapper;

    @Value("${is.push.new.msg}")
    private Integer isPushNewMsg;



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

    /**
     * 异步发送简单文本邮件
     * @param to
     * @param subject
     * @param content
     */
    @Async
    public void sendMailForNewArticle(List<String> to, String subject, String content) {
        for (String s : to) {
            mailService.sendSimpleMail(s,subject,content);
        }
        log.info("邮件发送成功");
    }

    @Async
    public void sendHtmlMailForNewArticle(Long articAuthorId,Long articleDOKey,String title) {
        if (isPushNewMsg == 1) {
            List<String> toList = new ArrayList<>();
            List<MsSysUserDO> msSysUserDOS = userMapper.selectList(new LambdaQueryWrapper<MsSysUserDO>()
                    .eq(MsSysUserDO::getStatus,"1"));
            for (MsSysUserDO msSysUserDO : msSysUserDOS) {
                //邮件地址存在且不是作者的发送通知邮件
                if (StringUtils.isNotBlank(msSysUserDO.getEmail()) && (!articAuthorId.equals(msSysUserDO.getId()))) {
                    toList.add(msSysUserDO.getEmail());
                }
            }
            //组装发送的内筒
            String subject = "发表新的博客啦！主题是：【" + title + "】";
            String contenet=String.format("欢迎查看新博客：<a href=\"http://truekai.cc/#/view/%s\">%s</a>",articleDOKey,title);
            try {
                for (String s : toList) {
                    mailService.sendHtmlMail(s, subject, contenet, null);
                    log.info("用户邮件发送成功，{}",s);
                }
            }catch (Exception e){
                log.error("发送通知邮件失败");
            }
        }
        log.info("邮件发送成功");
    }
}
