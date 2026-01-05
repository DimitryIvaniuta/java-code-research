package com.code.research.service.reserveorder;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * R2DBC entity mapped to table: orders
 * <p>
 * Minimal fields for reservation use-case:
 * - status: NEW / RESERVED / PAID / CANCELLED (string for flexibility)
 * - reservedUntil: reservation TTL timestamp
 * - version: optimistic locking (optional but recommended)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("orders")
public class OrderEntity {

    @Id
    @Column("id")
    private String id;

    @Column("status")
    private String status;

    /**
     * When the reservation expires (TTL). If null -> not reserved.
     */
    @Column("reserved_until")
    private Instant reservedUntil;

    /**
     * Who reserved it (userId). Optional, but useful for conflict responses.
     */
    @Column("reserved_by")
    private String reservedBy;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    /**
     * Optimistic locking. Spring Data R2DBC will include version in UPDATE WHERE clause.
     */
    @Version
    @Column("version")
    private Long version;
}
