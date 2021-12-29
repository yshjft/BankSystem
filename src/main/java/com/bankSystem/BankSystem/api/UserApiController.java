package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.api.dto.user.get.UserGetResponseDto;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveRequestDto;
import com.bankSystem.BankSystem.api.dto.user.save.UserSaveResponseDto;
import com.bankSystem.BankSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @GetMapping("/")
    public UserGetResponseDto get(HttpServletRequest request) {
        return userService.get(request);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSaveResponseDto save(@RequestBody @Validated UserSaveRequestDto userSaveRequestDto) {
        return userService.join(userSaveRequestDto);
    }

    // 회원 정보 수정

    // 탈퇴
}
