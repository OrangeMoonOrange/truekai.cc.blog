package truekai.cc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import truekai.cc.service.MailService;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private MailService mailService;

    @Test
    void contextLoads() throws Exception {
        String contenet=String.format("你好：呆呆\r\n欢迎查看<a href=\"http://truekai.cc/#/view/%s\">%s</a>",496392703984337152l,"dddddd");
        System.out.println(contenet);
        //mailService.sendHtmlMail("1006974144@qq.com","测试注意",contenet,null);
    }

}
