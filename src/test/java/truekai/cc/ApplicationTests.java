package truekai.cc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import truekai.cc.utils.CustomerId;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private CustomerId customerId;

    @Test
    void contextLoads() {
        System.out.println(customerId.getDataCenterId());
    }

}
