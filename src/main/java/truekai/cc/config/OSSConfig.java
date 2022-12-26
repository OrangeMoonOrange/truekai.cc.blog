package truekai.cc.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**

 **/

@Component("oSSConfig")
@Data
@ToString
public class OSSConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketname;

}
