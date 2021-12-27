package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.BaseIntegrationTest;
import com.bankSystem.BankSystem.api.dto.UserSaveRequestDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiControllerTest extends BaseIntegrationTest {
    public void setObjectMapperAny() {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Test
    public void 회원가입_성공() throws Exception{
        setObjectMapperAny();

        // given
        String NAME = "tester";
        LocalDate BIRTH_DATE = LocalDate.of(2002, 2, 10);
        String ADDRESS = "경기도 용인시 처인구 ~~~";
        String EMAIL = "tester@test.com";
        String PASSWORD = "20020210";
        String phoneNumber = "01064257873";

        String object = objectMapper.writeValueAsString(new UserSaveRequestDto().builder()
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .address(ADDRESS)
                .email(EMAIL)
                .password(PASSWORD)
                .phoneNumber(phoneNumber));

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

    public void 회원가입_이메일_중복() throws Exception{

    }
}