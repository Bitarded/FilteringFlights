package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum that represents set of rules
 */
public enum Rule {
    FLIGHTS_BEFORE_CURRENT_DATE {
        @Override
        List<Flight> filterFlightsWithRules(List<Flight> flights) {
            return flights.stream()
                    .filter(flight -> flight.getSegments()
                            .stream()
                            .noneMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())))
                    .collect(Collectors.toList());

        }
    },
    ARRIVAL_BEFORE_DEPARTURE {
        @Override
        List<Flight> filterFlightsWithRules(List<Flight> flights) {
            return flights.stream()
                    .filter(flight -> flight.getSegments()
                            .stream()
                            .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                    .collect(Collectors.toList());
        }
    },
    GROUND_TIME_MORE_THAN_TWO_HOURS {
        @Override
        List<Flight> filterFlightsWithRules(List<Flight> flights) {
            List<Flight> flightsResult = new ArrayList<>(flights);

            List<Flight> collect = flightsResult.stream()
                    .filter(flight -> flight.getSegments().size() > 1)
                    .collect(Collectors.toList());

            for (Flight flight : collect) {
                List<Segment> segments = flight.getSegments();
                LocalDateTime tempDate = null;
                long groundTime=0;
                for (Segment segment : segments) {
                    if (tempDate == null) {
                        tempDate = segment.getArrivalDate();
                    } else {
                        LocalDateTime departureDate = segment.getDepartureDate();
                        long hours = ChronoUnit.HOURS.between( tempDate,departureDate);
                        groundTime+=hours;
                        if (groundTime>=2) {
                            flightsResult.remove(flight);
                            break;
                        } else {
                            tempDate = segment.getArrivalDate();
                        }
                    }
                }
            }

            return flightsResult;
        }
    };

    abstract List<Flight> filterFlightsWithRules(List<Flight> flights);
}