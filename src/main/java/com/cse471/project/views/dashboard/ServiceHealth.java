package com.cse471.project.views.dashboard;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Simple DTO class for the inbox list to demonstrate complex object data
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ServiceHealth {

    private Status status;

    private String city;

    private int input;

    private int output;

    private String theme;

    enum Status {
        EXCELLENT, OK, FAILING
    }

    public ServiceHealth() {

    }

    public ServiceHealth(Status status, String city, int input, int output) {
        this.status = status;
        this.city = city;
        this.input = input;
        this.output = output;
    }

}
