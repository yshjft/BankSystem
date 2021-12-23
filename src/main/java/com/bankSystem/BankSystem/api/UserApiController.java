package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.dto.UserSaveRequestDto;
import com.bankSystem.BankSystem.dto.UserSaveResponseDto;
import com.bankSystem.BankSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    // 회원 정보 조회(일부 정보)

    // 회원 정보 조회(상세:모든 정보 다)

    // 회원 가입
    @PostMapping("/add")
    public UserSaveResponseDto save(@Validated @RequestBody UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("에러 발생");
            log.info("errors={}", bindingResult.getAllErrors());
            log.info("슈슉슉 ㅅㅂ={}", userSaveRequestDto.getBirthDate());
            //예외처리 해야함
            throw new IllegalStateException();
        }

        return userService.save(userSaveRequestDto);
    }

    // 회원 정보 수정

    // 탈퇴
}
