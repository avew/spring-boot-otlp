package com.example.springboototlp;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j

public class SpringBootOtlpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOtlpApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @RestController
    class HelloController {

        private final RestTemplate restTemplate;
        private final SleepService sleepService;

        HelloController(RestTemplate restTemplate, SleepService sleepService) {
            this.restTemplate = restTemplate;
            this.sleepService = sleepService;
        }

        @GetMapping(value = "/hello")
        public String hello() {
            log.info("SAY WHAT");
            ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("https://httpbin.org/post", "Hello,Cloud", String.class);
            return responseEntity.getBody();
        }

        @GetMapping(value = "/sleep")
        public Long sleep(@RequestParam Long ms) {
            return sleepService.doSleep(ms);
        }


    }

    @Service
    class SleepService {
        @Timed(value = "do.sleep.method.timed")
//		@NewSpan(value = "do-sleep-method-span")
//        @Observed(name = "do.sleep.method.timed", contextualName = "do-sleep-method-span", lowCardinalityKeyValues = {"low", "low"})
        public Long doSleep(Long ms) {
            try {
                TimeUnit.MILLISECONDS.sleep(ms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return ms;
        }
    }
}
