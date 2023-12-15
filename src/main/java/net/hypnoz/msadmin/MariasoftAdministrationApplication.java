package net.hypnoz.msadmin;

import net.hypnoz.msadmin.utils.MariasoftProperies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ MariasoftProperies.class })
public class MariasoftAdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MariasoftAdministrationApplication.class, args);
    }

}
