package com.code.research.flight;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FlightApp {
    // Example usage
    public static void main(String[] args) {
        List<Flight> flights = List.of(
                new Flight("WAW", "KRK", "2025-03-10", "08:00", "2025-03-10", "10:00"),
                new Flight("WRO", "RZE", "2025-03-11", "08:00", "2025-03-11", "14:00"),
                new Flight("KRK", "WAW", "2025-03-13", "06:00", "2025-03-13", "08:30"),
                new Flight("KRK", "WRO", "2025-03-10", "10:45", "2025-03-10", "15:00"),
                new Flight("POZ", "KTW", "2025-03-10", "10:00", "2025-03-10", "14:30"),
                new Flight("KTW", "POZ", "2025-03-10", "15:30", "2025-03-10", "23:00")
        );

        boolean exists = FlightUtil.isFlightExist(flights);
        log.info("Is there a valid connection? {}", exists);
    }
}
