package com.gridnine.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class FlightFilterTest {
    static List<Flight> flights = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        flights = FlightBuilder.createFlights();
    }

    @Test
    void removesFlightsThatDepartBeforeCurrentDate() {
        List<Flight> filteredWithFirstRule = FlightFilter.filterFlights(flights, Rule.FLIGHTS_BEFORE_CURRENT_DATE);
        List<Flight> expectedFlights = new ArrayList<>(flights);
        expectedFlights.remove(2);

        Assertions.assertEquals(filteredWithFirstRule, expectedFlights);
    }

    @Test
    void removesFlightsThatArrivesBeforeDeparting() {
        List<Flight> filteredWithSecondRule = FlightFilter.filterFlights(flights, Rule.ARRIVAL_BEFORE_DEPARTURE);
        List<Flight> expectedFlights = new ArrayList<>(flights);
        expectedFlights.remove(3);

        Assertions.assertEquals(filteredWithSecondRule, expectedFlights);
    }

    @Test
    void removesFlightsWithGroundTimeMoreThanTwoHours() {
        List<Flight> thirdRuleFilter = FlightFilter.filterFlights(flights, Rule.GROUND_TIME_MORE_THAN_TWO_HOURS);
        List<Flight> expectedFlights = new ArrayList<>(flights);
        expectedFlights.removeAll(flights.subList(4, 8));
        Assertions.assertEquals(thirdRuleFilter, expectedFlights);
    }

    @Test
    void removesFlightsWithEveryRule() {
        List<Flight> complexFilteredList = FlightFilter.filterFlights(flights, Rule.FLIGHTS_BEFORE_CURRENT_DATE, Rule.ARRIVAL_BEFORE_DEPARTURE, Rule.GROUND_TIME_MORE_THAN_TWO_HOURS);
        List<Flight> expectedFlights = new ArrayList<>(flights);
        expectedFlights.removeAll(flights.subList(2, 8));
        Assertions.assertEquals(complexFilteredList, expectedFlights);
    }
}