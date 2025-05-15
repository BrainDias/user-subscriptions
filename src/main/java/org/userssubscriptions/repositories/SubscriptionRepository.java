package org.userssubscriptions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.userssubscriptions.entities.ServiceSubscription;

import java.util.List;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<ServiceSubscription, Long> {

    @Query(value = """
    SELECT s.* FROM subscription s
    LEFT JOIN user_subscription us ON s.id = us.subscription_id
    GROUP BY s.id
    ORDER BY COUNT(us.user_id) DESC
    LIMIT 3
    """, nativeQuery = true)
    List<ServiceSubscription> findTop3ByMostUsersNative();
}
