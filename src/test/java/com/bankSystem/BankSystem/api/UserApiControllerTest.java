package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.BaseIntegrationTest;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveRequestDto;
import com.bankSystem.BankSystem.domain.user.User;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.session.SessionKey;
import com.bankSystem.BankSystem.testData.TestUser;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiControllerTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;
    protected UserSaveRequestDto userSaveRequestDto;
    protected MockHttpSession session;

    @BeforeEach
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
        String object1 = objectMapper.writeValueAsString(userSaveRequestDto);
        String object2 = objectMapper.writeValueAsString(userSaveRequestDto);

        mockMvc.perform(post("/api/user/add")
                .content(object1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/user/add")
                .content(object2)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("EMAIL_ALREADY_IN_USE"));
    }

    @Test
    public void 개인정보_조회() throws Exception {
        // given
        String object = objectMapper.writeValueAsString(userSaveRequestDto);
        mockMvc.perform(post("/api/user/add")
                .content(object)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        User user = userRepository.findUserByEmail(userSaveRequestDto.getEmail());

        session = new MockHttpSession();
        session.setAttribute(SessionKey.LOGIN_MEMBER, user.getId());

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/user")
                .session(session)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

}