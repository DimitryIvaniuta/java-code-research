package com.code.research.algorithm.test;

import com.code.research.algorithm.test.dto.MonthlyRevenue;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SalesRepository {

    private final JdbcTemplate jdbcTemplate;

    // exactly the same SQL you want to test
    static final String SQL =
            "SELECT\n" +
                    "  DATE_TRUNC('month', sale_date) AS month,\n" +
                    "  product_id,\n" +
                    "  SUM(unit_price * quantity)     AS monthly_revenue\n" +
                    "FROM sales\n" +
                    "GROUP BY DATE_TRUNC('month', sale_date), product_id\n" +
                    "ORDER BY month, product_id";

    public SalesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MonthlyRevenue> getMonthlyRevenue() {
        return jdbcTemplate.query(
                SQL,
                (rs, rowNum) -> new MonthlyRevenue(
                        rs.getTimestamp("month").toLocalDateTime().toLocalDate(),
                        rs.getLong("product_id"),
                        rs.getBigDecimal("monthly_revenue")
                )
        );
    }

}
