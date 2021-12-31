package com.bankSystem.BankSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled //테스트 클래스 또는 테스트 메소드를 실행하지 않습니다
@AutoConfigureMockMvc // @SpringBootTest 어노테이션을 사용하는 경우, MockMvc를 이용한 테스트를 진행하기 위해 필요한 어노테이션입니다
@Transactional
public class BaseIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
}
