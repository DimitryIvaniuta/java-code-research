package com.code.research.concurrent.orders.service;

import com.code.research.concurrent.orders.domain.Order;
import com.code.research.concurrent.orders.exception.OutOfStockException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * JDBC-backed InventoryService: reserves stock for an order in a single transaction.
 */
public class JdbcInventoryService implements InventoryService {
    private static final Logger logger = Logger.getLogger(JdbcInventoryService.class.getName());
    private final DataSource dataSource;

    public JdbcInventoryService(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource, "dataSource");
    }

    @Override
    public void reserve(Order order) throws OutOfStockException {
        // Count SKUs in the order
        Map<String,Integer> counts = new HashMap<>();
        for (String sku : order.getItems()) {
            counts.merge(sku, 1, Integer::sum);
        }

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Lock & check each SKU
                for (Map.Entry<String,Integer> e : counts.entrySet()) {
                    String sku = e.getKey();
                    int needed = e.getValue();
                    // SELECT ... FOR UPDATE to lock the row
                    try (PreparedStatement ps = conn.prepareStatement(
                            "SELECT quantity FROM inventory WHERE sku = ? FOR UPDATE")) {
                        ps.setString(1, sku);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) {
                                throw new OutOfStockException("SKU not found: " + sku);
                            }
                            int available = rs.getInt("quantity");
                            if (available < needed) {
                                throw new OutOfStockException(
                                        "Insufficient stock for SKU " + sku +
                                                " (needed=" + needed + ", available=" + available + ")");
                            }
                        }
                    }
                    // Deduct stock
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE inventory SET quantity = quantity - ? WHERE sku = ?")) {
                        ps.setInt(1, needed);
                        ps.setString(2, sku);
                        ps.executeUpdate();
                    }
                }
                conn.commit();
                logger.fine(() -> "Reserved inventory for order " + order.getId());
            } catch (OutOfStockException oe) {
                conn.rollback();
                throw oe;
            } catch (SQLException se) {
                conn.rollback();
                throw new RuntimeException("Error reserving inventory", se);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during inventory reservation", e);
        }
    }
}
