package com.openclassroom.orion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscriptions")
@IdClass(SubscriptionId.class)
@Getter
@Setter
@NoArgsConstructor
public class Subscription {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "theme_id", referencedColumnName = "id", nullable = false)
    private Theme theme;

}


