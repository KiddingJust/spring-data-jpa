package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class SpringdatajpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdatajpaApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider(){
        //getCurrentAuditor 라는 메서드를 Override 한 것
        //인터페이스에서 메서드가 한개이면 람다로 가능
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
