package com.gridnine.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gridnine.testing.FlightBuilder.createFlight;


class FlightFilterTest {
    static List<Flight> flights = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        flights = createTestFlights();

    }

    static List<Flight> createTestFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                //#1 A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //#2 A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //#3 A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //#4 A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //#5 A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //#6 Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4), threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)),
                //#7 Flight with more than two hours of ground time in small portions
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2), threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4), threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7), threeDaysFromNow.plusHours(8)),
                //#8 Long Flight with more than two hours of ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(8), threeDaysFromNow.plusHours(10)),
                //#9 Long Flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(10), threeDaysFromNow.plusHours(10), threeDaysFromNow.plusHours(20)));
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