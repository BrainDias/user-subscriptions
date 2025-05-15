package org.userssubscriptions.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.userssubscriptions.entities.ServiceSubscription;
import org.userssubscriptions.entities.User;
import org.userssubscriptions.repositories.SubscriptionRepository;
import org.userssubscriptions.repositories.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public HttpStatus addSubscription(ServiceSubscription subscription, Long userId) {
        log.info("Adding subscription '{}' to user {}", subscription.getName(), userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });
        user.getSubscriptions().add(subscription);
        subscription.getUsers().add(user);
        userRepository.save(user);
        log.info("Subscription '{}' added to user {}", subscription.getName(), userId);
        return HttpStatus.ACCEPTED;
    }

    public Set<String> getSubscriptions(Long userId) {
        log.info("Getting subscriptions for user {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User {} not found", userId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });
        return user.getSubscriptions().stream().map(ServiceSubscription::getName).collect(Collectors.toSet());
    }

    public HttpStatus deleteSubscription(Long userId, Long subId) {
        log.info("Deleting subscription {} from user {}", subId, userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User {} not found", userId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });
        user.getSubscriptions().removeIf(sub -> sub.getId().equals(subId));
        return HttpStatus.ACCEPTED;
    }

    public Set<String> getTopSubscriptions() {
        return subscriptionRepository.findTop3ByMostUsersNative().stream().map(ServiceSubscription::getName).collect(Collectors.toSet());
    }
}
