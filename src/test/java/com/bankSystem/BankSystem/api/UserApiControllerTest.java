package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.BaseIntegrationTest;
import com.bankSystem.BankSystem.api.dto.user.UserDto;
import com.bankSystem.BankSystem.api.dto.user.UserSaveRequestDto;
import com.bankSystem.BankSystem.domain.user.UserRepository;
import com.bankSystem.BankSystem.testData.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiControllerTest extends BaseIntegrationTest {

    UserSaveRequestDto userSaveRequestDto;
    UserDto userDto;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setObjectMapperAny() {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        userSaveRequestDto = UserSaveRequestDto.builder()
                .name(User.NAME)
                .birthDate(User.BIRTH_DATE)
                .address(User.ADDRESS)
                .email(User.EMAIL)
                .password(User.PASSWORD)
                .phoneNumber(User.PHONE_NUMBER)
                .build();

        userDto = UserDto.builder()
                .name(User.NAME)
                .birthDate(User.BIRTH_DATE)
                .address(User.ADDRESS)
                .email(User.EMAIL)
                .password(User.PASSWORD)
                .phoneNumber(User.PHONE_NUMBER)
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
                .andExpect(jsonPath("$.name").value(User.NAME))
                .andExpect(jsonPath("$.email").value(User.EMAIL));
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