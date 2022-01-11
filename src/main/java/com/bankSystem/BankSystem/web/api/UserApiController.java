package com.bankSystem.BankSystem.web.api;

import com.bankSystem.BankSystem.web.dto.user.UserResponse;
import com.bankSystem.BankSystem.web.dto.user.get.UserGetResponseDto;
import com.bankSystem.BankSystem.web.dto.user.join.UserJoinRequestDto;
import com.bankSystem.BankSystem.web.dto.user.join.UserJoinResponseDto;
import com.bankSystem.BankSystem.web.dto.user.update.UserUpdateRequestDto;
import com.bankSystem.BankSystem.web.dto.user.update.UserUpdateResponseDto;
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
    @ResponseStatus(HttpStatus.OK)
    public UserResponse get() {
        UserGetResponseDto userGetResponseDto = userService.get();

        return UserResponse.builder()
                .status(HttpStatus.OK.value())
                .message("get success")
                .userResponseDto(userGetResponseDto)
                .build();
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse join(@RequestBody @Validated UserJoinRequestDto userJoinRequestDto) {
        UserJoinResponseDto userJoinResponseDto = userService.join(userJoinRequestDto);

        return UserResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("join success")
                .userResponseDto(userJoinResponseDto)
                .build();
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse update(@RequestBody @Validated UserUpdateRequestDto userUpdateRequestDto) {
        UserUpdateResponseDto userUpdateResponseDto = userService.update(userUpdateRequestDto);

        return UserResponse.builder()
                .status(HttpStatus.OK.value())
                .message("update success")
                .userResponseDto(userUpdateResponseDto)
                .build();
    }

    // 탈퇴
}
