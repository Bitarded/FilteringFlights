package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Factory class to get sample list of flights.
 */
public class FlightBuilder {
    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                //#1 A normal flight with two hour duration
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //#2 A normal multi segment flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //#3 A flight departing in the past
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //#4 A flight that departs before it arrives
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //#5 A flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //#6 Another flight with more than two hours ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)),
                //#7 Flight with more than two hours of ground time in small portions
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //#8 Long Flight with more than two hours of ground time
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(5),
                        threeDaysFromNow.plusHours(7), threeDaysFromNow.plusHours(10)),
                //#9 Long Flight
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(5),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(10),
                        threeDaysFromNow.plusHours(10), threeDaysFromNow.plusHours(20))
                        );
    }

    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
