package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> arrayList = FlightBuilder.createFlights();
        System.out.println(FlightFilter.filterFlights(arrayList,Rule.GROUND_TIME_MORE_THAN_TWO_HOURS));
    }
}