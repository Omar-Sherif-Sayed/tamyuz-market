package sa.tamyuz.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TamyuzMarketApplication {

    private TamyuzMarketApplication() {
    }

    static void main(String[] args) {
        SpringApplication.run(TamyuzMarketApplication.class, args);
    }

}
