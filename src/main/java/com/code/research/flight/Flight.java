package com.code.research.flight;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Flight {
    private String origin;
    private String destination;
    private String departureDate; // format "yyyy-MM-dd"
    private String departureTime; // format "HH:mm"
    private String arrivalDate;   // format "yyyy-MM-dd"
    private String arrivalTime;   // format "HH:mm"

    public Flight(String origin, String destination, String departureDate, String departureTime, String arrivalDate, String arrivalTime) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }

    // equals and hashCode can be generated if needed for list comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(origin, flight.origin) &&
                Objects.equals(destination, flight.destination) &&
                Objects.equals(departureDate, flight.departureDate) &&
                Objects.equals(departureTime, flight.departureTime) &&
                Objects.equals(arrivalDate, flight.arrivalDate) &&
                Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, departureDate, departureTime, arrivalDate, arrivalTime);
    }
}

