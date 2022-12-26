package truekai.cc.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 
 */


@Component("oSSConfig2")
@Data
@ToString
public class OSSConfig2 {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketname;

}
