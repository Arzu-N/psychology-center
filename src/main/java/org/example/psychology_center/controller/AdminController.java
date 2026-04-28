package org.example.psychology_center.controller;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.dto.request.UserRequestDto;
import org.example.psychology_center.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserRequestDto request) {

        authService.register(request);
    }


}