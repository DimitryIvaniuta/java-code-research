create table dim_customer (
                              customer_sk bigserial primary key,
                              customer_id text not null,
                              name text,
                              city text,
                              row_hash text not null,
                              valid_from date not null,
                              valid_to date not null,
                              is_current boolean not null,
                              unique (customer_id, valid_from)
);

-- close old current rows where change detected
update dim_customer d
set valid_to = :day - interval '1 day',
    is_current = false
from stg_snapshot s
where s.day = :day
  and d.customer_id = s.business_key
  and d.is_current = true
  and d.row_hash <> s.row_hash;

-- insert new current rows for new keys or changed keys
insert into dim_customer(customer_id, name, city, row_hash, valid_from, valid_to, is_current)
select s.business_key, s.col1 as name, s.col2 as city, s.row_hash,
       :day::date, '9999-12-31'::date, true
from stg_snapshot s
         left join dim_customer d
                   on d.customer_id = s.business_key and d.is_current = true
where s.day = :day
  and (d.customer_id is null or d.row_hash <> s.row_hash);