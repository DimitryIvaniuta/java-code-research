package com.code.research.algorithm.streams.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class EmployeeWithId {

    private final Long id;
    private final String name;

    public EmployeeWithId(Long id, String name) {
        this.id   = Objects.requireNonNull(id,   "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

}
