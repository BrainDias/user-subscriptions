package org.userssubscriptions.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.userssubscriptions.entities.ServiceSubscription;
import org.userssubscriptions.services.SubscriptionService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{userId}/subscriptions")
    public HttpStatus addSubscription(final ServiceSubscription subscription, @PathVariable final Long userId) {
        return subscriptionService.addSubscription(subscription, userId);
    }

    @GetMapping("/{id}/subscriptions")
    public Set<String> getSubscriptions(@PathVariable final Long userId) {
        return subscriptionService.getSubscriptions(userId);
    }

    @DeleteMapping("/{userId}/subscriptions/{subId}")
    public HttpStatus deleteSubscription(@PathVariable final Long userId, @PathVariable final Long subId) {
        return subscriptionService.deleteSubscription(userId, subId);
    }

    @GetMapping("/subscriptions/top")
    public Set<String> getTopSubscriptions() {
        return subscriptionService.getTopSubscriptions();
    }
}
