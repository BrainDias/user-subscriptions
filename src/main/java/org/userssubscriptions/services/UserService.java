package org.userssubscriptions.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.userssubscriptions.entities.User;
import org.userssubscriptions.repositories.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public HttpStatus create(User user) {
        log.info("Creating user: {}", user);
        //Так как id типа Long и стоит @GeneratedValue, ORM сама подставит id по автоматически выбранной стратегии, если у переданного в
        //контроллер объекта это поле null
        userRepository.save(user);
        return HttpStatus.CREATED;
    }

    public User getUser(Long id) {
        log.info("Fetching user with id {}", id);
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public HttpStatus update(Long id, User user) {
        log.info("Updating user with id {}", id);
        userRepository.findById(id).orElseThrow(() -> {
            log.warn("User {} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });
        user.setId(id);
        userRepository.save(user);
        return HttpStatus.ACCEPTED;
    }

    public HttpStatus delete(Long id) {
        log.info("Deleting user with id {}", id);
        userRepository.deleteById(id);
        return HttpStatus.ACCEPTED;
    }
}
