package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;

public class FlightFilter {

    public static List<Flight> filterFlights(List<Flight> flights, Rule... rules){
        List<Flight> result = new ArrayList<>(flights);
        for(Rule rule : rules) {
            result = rule.filterFlightsWithRules(result);
        }
        return result;
    }
}