package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.dto.UserSaveRequestDto;
import com.bankSystem.BankSystem.dto.UserSaveResponseDto;
import com.bankSystem.BankSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    // 회원 정보 조회(일부 정보)

    // 회원 정보 조회(상세:모든 정보 다)

    // 회원 가입
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public UserSaveResponseDto save(@RequestBody @Validated UserSaveRequestDto userSaveRequestDto) {
        return userService.save(userSaveRequestDto);
    }

    // 회원 정보 수정

    // 탈퇴
}
