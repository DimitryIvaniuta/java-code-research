package com.code.research.algorithm.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.code.research.algorithm.test.dto.MonthlyRevenue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SalesRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SalesRepository repo;

    @Test
    void getMonthlyRevenue_returnsExpectedList_andUsesCorrectSql() {
        // -- arrange -------------------------------------------------------
        List<MonthlyRevenue> fakeResult = Arrays.asList(
                new MonthlyRevenue(LocalDate.of(2025,6,1), 42L, new BigDecimal("1234.56")),
                new MonthlyRevenue(LocalDate.of(2025,6,1), 99L, new BigDecimal("  789.00"))
        );
        // stub jdbcTemplate.query(sql, rowMapper) → our fakeResult
        when(jdbcTemplate.query(eq(SalesRepository.SQL), any(RowMapper.class)))
                .thenReturn(fakeResult);

        // -- act ----------------------------------------------------------
        List<MonthlyRevenue> actual = repo.getMonthlyRevenue();

        // -- assert -------------------------------------------------------
        // 1) Repository returned exactly the stubbed list
        assertSame(fakeResult, actual);

        // 2) It called JdbcTemplate.query(...) with the exact SQL
        ArgumentCaptor<RowMapper<MonthlyRevenue>> mapperCaptor =
                ArgumentCaptor.forClass(RowMapper.class);

        verify(jdbcTemplate).query(eq(SalesRepository.SQL), mapperCaptor.capture());

        // Optionally, verify the RowMapper maps a ResultSet correctly:
        // (but this goes beyond pure stubbing—would require mocking a ResultSet)
    }

}
