-- 1) stage already loaded for :day
-- add hash
update stg_snapshot
set row_hash = md5(coalesce(col1,'') || '|' || coalesce(col2,'') || '|' || coalesce(col3,''))
where day = :day;

-- 2) inserts
insert into current_state(business_key, row_hash, col1, col2, col3, last_seen_day)
select s.business_key, s.row_hash, s.col1, s.col2, s.col3, :day
from stg_snapshot s
         left join current_state c on c.business_key = s.business_key
where s.day = :day and c.business_key is null;

-- 3) updates (hash changed)
update current_state c
set row_hash = s.row_hash,
    col1 = s.col1, col2 = s.col2, col3 = s.col3,
    last_seen_day = :day
    from stg_snapshot s
where s.day = :day
  and c.business_key = s.business_key
  and c.row_hash <> s.row_hash;

-- 4) deletes (missing today) -> mark inactive (preferred) or hard delete
update current_state c
set is_active = false
where c.is_active = true
  and c.business_key not in (select business_key from stg_snapshot where day = :day);