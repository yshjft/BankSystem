package com.bankSystem.BankSystem.api;

import com.bankSystem.BankSystem.dto.UserSaveRequestDto;
import com.bankSystem.BankSystem.dto.UserSaveResponseDto;
import com.bankSystem.BankSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/add")
    public UserSaveResponseDto save(@Validated @RequestBody UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult) {
        return userService.save(userSaveRequestDto);
    }
}
