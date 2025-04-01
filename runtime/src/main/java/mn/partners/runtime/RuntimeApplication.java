package mn.partners.runtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RuntimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuntimeApplication.class, args);
    }
}