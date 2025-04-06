package edu.fisa.ce;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CICDController {

    private final MeterRegistry meterRegistry;

    @Autowired
    public CICDController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // 기존 요청
    // http://localhost:8080/fisa1
    @GetMapping("/fisa1")
    public String reqRes() {
        log.info("**요청응답**");
        return "요청 응답 성공";
    }

    // 커스텀 메트릭용 요청
    // http://localhost:8080/countUp
    @GetMapping("/countUp")
    public String count() {
        log.info("**카운터 증가 요청 들어옴**");
        meterRegistry.counter("custom.counter", "endpoint", "/countUp").increment();
        return "count 증가됨!";
    }
}
