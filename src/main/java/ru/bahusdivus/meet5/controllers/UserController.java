package ru.bahusdivus.meet5.controllers;

import org.springframework.web.bind.annotation.*;
import ru.bahusdivus.meet5.constants.FraudulentStatus;
import ru.bahusdivus.meet5.dto.UserCreateRequestDto;
import ru.bahusdivus.meet5.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/visit/{id}")
    public String isFraudulent(@PathVariable("id") int id) {
        return userService.isUserFraudulent(id) ?
                FraudulentStatus.FRAUDULENT.toString() :
                FraudulentStatus.NOT_FRAUDULENT.toString();
    }

    // Put do not return value
    @PostMapping("/")
    public int saveUserGetId(@Valid @RequestBody UserCreateRequestDto user) {
        return userService.createUserGetId(user);
    }
}
