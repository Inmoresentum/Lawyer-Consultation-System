package com.cse471.project.views.dashboard;

import lombok.*;

/**
 * Simple DTO class for the inbox list to demonstrate complex object data
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticService {

    private Status status;

    private String city;

    private int input;

    private int output;

    private String theme;

    enum Status {
        EXCELLENT, OK, FAILING
    }

    public AnalyticService(Status status, String city, int input, int output) {
        this.status = status;
        this.city = city;
        this.input = input;
        this.output = output;
    }

}
