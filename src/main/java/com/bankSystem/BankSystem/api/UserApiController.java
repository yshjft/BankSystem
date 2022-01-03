package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.api.dto.user.get.UserGetResponseDto;
import com.bankSystem.BankSystem.api.dto.user.join.UserJoinRequestDto;
import com.bankSystem.BankSystem.api.dto.user.join.UserJoinResponse;
import com.bankSystem.BankSystem.api.dto.user.join.UserJoinResponseDto;
import com.bankSystem.BankSystem.api.dto.user.update.UserUpdateRequestDto;
import com.bankSystem.BankSystem.api.dto.user.update.UserUpdateResponseDto;
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

    @GetMapping
    public UserGetResponseDto get(HttpServletRequest request) {
        return userService.get(request);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public UserJoinResponse join(@RequestBody @Validated UserJoinRequestDto userJoinRequestDto) {
        UserJoinResponseDto userJoinResponseDto = userService.join(userJoinRequestDto);

        return UserJoinResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("join success")
                .userJoinResponseDto(userJoinResponseDto)
                .build();
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserUpdateResponseDto update(@RequestBody @Validated UserUpdateRequestDto userUpdateRequestDto, HttpServletRequest request) {
        return userService.update(userUpdateRequestDto, request);
    }

    // 탈퇴
}
