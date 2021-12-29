package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.BaseIntegrationTest;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveDto;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveRequestDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.session.SessionConst;
import com.bankSystem.BankSystem.testData.TestUser;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiControllerTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;
    protected UserSaveRequestDto userSaveRequestDto;
    protected UserSaveDto userSaveDto;
    protected MockHttpSession session;
    protected MockHttpServletRequest request;

    @Before
    public void setObjectMapperAny() {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        userSaveRequestDto = UserSaveRequestDto.builder()
                .name(TestUser.NAME)
                .birthDate(TestUser.BIRTH_DATE)
                .address(TestUser.ADDRESS)
                .email(TestUser.EMAIL)
                .password(TestUser.PASSWORD)
                .phoneNumber(TestUser.PHONE_NUMBER)
                .build();

        userSaveDto = UserSaveDto.builder()
                .name(TestUser.NAME)
                .birthDate(TestUser.BIRTH_DATE)
                .address(TestUser.ADDRESS)
                .email(TestUser.EMAIL)
                .password(TestUser.PASSWORD)
                .phoneNumber(TestUser.PHONE_NUMBER)
                .build();

    }

    @Test
    public void 회원가입_성공() throws Exception{
        // given
        String object = objectMapper.writeValueAsString(userSaveRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/user/add")
                .content(object)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(TestUser.NAME))
                .andExpect(jsonPath("$.email").value(TestUser.EMAIL));
    }


    @Test
    public void 회원가입_실패_이메일_중복() throws Exception{
        // given
        userRepository.save(userSaveDto);
        String object = objectMapper.writeValueAsString(userSaveRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/user/add")
                .content(object)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("EMAIL_ALREADY_IN_USE"));
    }

    // 유저 정보 조회
//    @Test
    public void 개인정보_조회() throws Exception {
        // 로그인을 한게 아니라면 오류 처리 -> 이거는 인터셉터에서 처리할 거임 걱정ㄴㄴ
        // 로그인을 하지 않고도 할 수 있는 것: 로그인, 회원 가입

        // given
        userRepository.save(userSaveDto);
        User user = userRepository.findUserByEmail(userSaveDto.getEmail());

        session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, user.getId());
        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/user")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

}