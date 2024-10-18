package com.openclassroom.orion.module.subscription.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@EqualsAndHashCode
public class SubscriptionId implements Serializable {
    private Long user;
    private Long theme;
}
