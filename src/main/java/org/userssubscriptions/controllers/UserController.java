package org.userssubscriptions.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.userssubscriptions.entities.User;
import org.userssubscriptions.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public HttpStatus createUser(@RequestBody final User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable final Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public HttpStatus updateUser(@PathVariable final Long id, @RequestBody final User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable final Long id) {
        return userService.delete(id);
    }
}
