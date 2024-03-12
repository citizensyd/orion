package com.openclassroom.orion.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class SubscriptionId implements Serializable {
    private Long user;
    private Long theme;
}
