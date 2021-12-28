package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.BaseIntegrationTest;
import com.bankSystem.BankSystem.api.dto.auth.LoginRequestDto;
import com.bankSystem.BankSystem.api.dto.user.UserSaveRequestDto;
import com.bankSystem.BankSystem.testData.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApiControllerTest extends BaseIntegrationTest {

    UserSaveRequestDto userSaveRequestDto;

    @Before
    public void setUp() throws Exception{
        userSaveRequestDto = UserSaveRequestDto.builder()
                .name(User.NAME)
                .birthDate(User.BIRTH_DATE)
                .address(User.ADDRESS)
                .email(User.EMAIL)
                .password(User.PASSWORD)
                .phoneNumber(User.PHONE_NUMBER)
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
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email(User.EMAIL)
                .password(User.PASSWORD)
                .build();

        String content = objectMapper.writeValueAsString(loginRequestDto);

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
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("user@test.com")
                .password(User.PASSWORD)
                .build();

        String content = objectMapper.writeValueAsString(loginRequestDto);

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
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email(User.EMAIL)
                .password("111111")
                .build();

        String content = objectMapper.writeValueAsString(loginRequestDto);

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