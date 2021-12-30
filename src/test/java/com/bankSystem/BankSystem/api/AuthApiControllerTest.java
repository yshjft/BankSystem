package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.BaseIntegrationTest;
import com.bankSystem.BankSystem.api.dto.auth.AuthLoginRequestDto;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveRequestDto;
import com.bankSystem.BankSystem.testData.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthApiControllerTest extends BaseIntegrationTest {
    UserSaveRequestDto userSaveRequestDto;

    @BeforeEach
    public void setUp() throws Exception{
        userSaveRequestDto = UserSaveRequestDto.builder()
                .name(TestUser.NAME)
                .birthDate(TestUser.BIRTH_DATE)
                .address(TestUser.ADDRESS)
                .email(TestUser.EMAIL)
                .password(TestUser.PASSWORD)
                .phoneNumber(TestUser.PHONE_NUMBER)
                .build();

        String object = objectMapper.writeValueAsString(userSaveRequestDto);

        mockMvc.perform(post("/api/user/add")
                .content(object)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void 로그인_성공() throws Exception {
        // given
        AuthLoginRequestDto authLoginRequestDto = AuthLoginRequestDto.builder()
                .email(TestUser.EMAIL)
                .password(TestUser.PASSWORD)
                .build();

        String content = objectMapper.writeValueAsString(authLoginRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value("LOGIN_SUCCESS"));

        // 성공
        // code: LOGIN_SUCCESS
    }

    @Test
    public void 로그인_실패_이메일() throws Exception {
        // given
        AuthLoginRequestDto authLoginRequestDto = AuthLoginRequestDto.builder()
                .email("user@test.com")
                .password(TestUser.PASSWORD)
                .build();

        String content = objectMapper.writeValueAsString(authLoginRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());


        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("LOGIN_FAIL_EMAIL"));

        // code: LOGIN_FAIL_EMAIL
        // message: "wrong email"
    }

    @Test
    public void 로그인_실패_패스워드() throws Exception {
        // given
        AuthLoginRequestDto authLoginRequestDto = AuthLoginRequestDto.builder()
                .email(TestUser.EMAIL)
                .password("111111")
                .build();

        String content = objectMapper.writeValueAsString(authLoginRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/auth/login")
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("LOGIN_FAIL_PASSWORD"));

        // code: LOGIN_FAIL_PASSWORD
        // message: "wrong password"
    }


}