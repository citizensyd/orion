package com.openclassroom.orion.module.subscription.repository;

import com.openclassroom.orion.module.subscription.model.Subscription;
import com.openclassroom.orion.module.subscription.model.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    Optional<Object> findByUserIdAndThemeId(Long userId, Long themeId);

    Optional<Subscription> findById(SubscriptionId subscriptionId);

    List<Subscription> findByUserId(Long userId);
}
