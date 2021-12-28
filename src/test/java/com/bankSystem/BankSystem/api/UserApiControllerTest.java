package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.BaseIntegrationTest;
import com.bankSystem.BankSystem.api.dto.user.UserDto;
import com.bankSystem.BankSystem.api.dto.user.UserSaveRequestDto;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiControllerTest extends BaseIntegrationTest {
    public static final String NAME = "tester";
    public static final String ADDRESS = "경기도 용인시 처인구 ~~~";
    public static final String EMAIL = "tester@test.com";
    public static final String PASSWORD = "20020210";
    public static final String PHONE_NUMBER = "01064257873";

    UserSaveRequestDto userSaveRequestDto;
    UserDto userDto;

    @Autowired
    UserRepository userRepository;


    @Before
    public void setObjectMapperAny() {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        LocalDate BIRTH_DATE = LocalDate.of(2002, 2, 10);

        userSaveRequestDto = UserSaveRequestDto.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .address(ADDRESS)
                .email(EMAIL)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
                .build();

        userDto = UserDto.builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .address(ADDRESS)
                .email(EMAIL)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
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
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.email").value(EMAIL));
    }


    @Test
    public void 회원가입_실패_이메일_중복() throws Exception{
        // given
        userRepository.save(userDto);
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
}