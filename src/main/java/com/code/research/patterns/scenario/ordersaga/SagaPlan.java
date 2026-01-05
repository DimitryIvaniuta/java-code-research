package com.code.research.patterns.scenario.ordersaga;

import java.util.List;

/**
 * SagaPlan = ordered steps.
 * Each step usually maps to a command/event handled by a microservice.
 */
public record SagaPlan(List<SagaStep> steps) {
}