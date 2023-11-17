package com.example.springboototlp;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.List;

@Configuration
public class MetricFilterConfig {

//    @Bean
//    public MeterFilter commonTagsMetric() {
//        return MeterFilter.commonTags(
//                List.of(
//                        Tag.of("zone.id", ZoneId.of("Asia/Jakarta").toString())
//                )
//        );
//    }
}
