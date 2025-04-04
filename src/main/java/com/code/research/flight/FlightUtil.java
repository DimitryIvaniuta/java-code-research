package com.code.research.flight;


import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
public class FlightUtil {

    private FlightUtil() {
        //
    }

    /**
     * Checks if there exists at least one pair of flights where the destination of one flight
     * matches the origin of another and the connection time between the first flight’s arrival
     * and the second flight’s departure is between the specified thresholds.
     *
     * @param flights List of Flight objects.
     * @return true if such a connecting flight pair exists, otherwise false.
     */

    /**
     * Checks if there exists at least one pair of flights where the destination of one flight
     * matches the origin of another and the connection time (between arrival of the first flight
     * and departure of the second flight) is between 30 minutes and the provided connectionMinutes.
     *
     * @param flights           List of Flight objects.
     * @param connectionMinutes Maximum allowed connection time in minutes.
     * @return true if such a connecting flight pair exists, otherwise false.
     */
    public static boolean isFlightExist(List<Flight> flights, int connectionMinutes) {
        // Define minimum connection threshold: at least 30 minutes.
        Duration minConnection = Duration.ofMinutes(30);
        // Maximum connection duration is now provided as a parameter.
        Duration maxConnection = Duration.ofMinutes(connectionMinutes);

        for (Flight f1 : flights) {
            // Parse f1's arrival as LocalDateTime.
            LocalDateTime arrivalTime = LocalDateTime.of(
                    LocalDate.parse(f1.getArrivalDate()),
                    LocalTime.parse(f1.getArrivalTime())
            );
            for (Flight f2 : flights) {
                if (f1.equals(f2)) {
                    continue; // Skip same flight.
                }
                // Check if f1's destination matches f2's origin.
                if (f1.getDestination().equalsIgnoreCase(f2.getOrigin())) {
                    // Parse f2's departure as LocalDateTime.
                    LocalDateTime departureTime = LocalDateTime.of(
                            LocalDate.parse(f2.getDepartureDate()),
                            LocalTime.parse(f2.getDepartureTime())
                    );
                    // Calculate the connection duration.
                    Duration connectionDuration = Duration.between(arrivalTime, departureTime);
                    // Check that departure is after arrival and within allowed thresholds.
                    if (!connectionDuration.isNegative() &&
                            !connectionDuration.isZero() &&
                            connectionDuration.compareTo(minConnection) >= 0 &&
                            connectionDuration.compareTo(maxConnection) <= 0) {
                        log.info("Valid connection found:");
                        log.info("Flight 1: {}", f1);
                        log.info("Flight 2: {}", f2);
                        log.info("Connection Time: {}", connectionDuration.toMinutes() + " minutes");
                        return true;
                    }
                }
            }
        }
        return false;
    }

}