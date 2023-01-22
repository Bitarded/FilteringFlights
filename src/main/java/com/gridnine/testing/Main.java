package com.gridnine.testing;

import java.util.List;

import static com.gridnine.testing.FlightFilter.filterFlights;

public class Main {
    public static void main(String[] args) {
        List<Flight> flightList = FlightBuilder.createFlights();
        System.out.println(filterFlights(flightList,Rule.FLIGHTS_BEFORE_CURRENT_DATE));
        System.out.println(filterFlights(flightList,Rule.ARRIVAL_BEFORE_DEPARTURE));
        System.out.println(filterFlights(flightList,Rule.GROUND_TIME_MORE_THAN_TWO_HOURS));
    }
}