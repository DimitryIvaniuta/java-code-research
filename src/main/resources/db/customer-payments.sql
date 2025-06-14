SELECT
    c.id as cid, c.name as cname, COALESCE(sum(p.amount), 0) as total
    FROM CUSTOMER c,
              LEFT JOIN PAIMENT p ON (p.CUSTOMER_ID = c.ID)
       GROUP BY
           c.id,
           c.name
       HAVING
           sum(p.amount) > 100
       ORDER BY
           c.id;

select
    c.id as cid,
    COUNT(p.id) as pcount,
    SUM(p.amount) as pamount_sum,
    AVG(p.amount) as pamount_avg,
    MAX(p.amount) as pamount_max,
    MIN(p.amount) as pamount_min
  FROM Customer c
    JOIN Paiment p ON c.id = p.customer_id
  GROUP BY
      c.id
    HAVING
        COUNT(p.id) < 5 AND MAX(p.amount) > 1000;

SELECT DATE_TRUNC('month', sales.date) as month,
    product_id,
    sum(unit_price * quantity)
FROM sales
    GROUP BY month,
        product_id
    ORDER BY
        month,
        product_id;

SELECT
    TO_CHAR(DATE_TRUNC('month', sale_date), 'YYYY-MM')  AS year_month,
    product_id,
    SUM(unit_price * quantity)                         AS monthly_revenue
FROM sales
GROUP BY 1, product_id
ORDER BY 1, product_id;

