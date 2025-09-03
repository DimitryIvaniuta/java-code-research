package com.code.research.function;

 import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.IntStream;

public class FunctionalInterfacesExamples {

    // --- Tiny domain model for realism ---
    public record User(String id, String email, String name, int age, boolean vip) {}
    public record Order(long id, String userId, BigDecimal amount, String status,
                        Instant createdAt, int items) {}

    // --- FUNCTION: T -> R (transformations, mapping, DTO<->entity) ---
    // Map an incoming DTO-like Map to a strongly-typed User.
    static final Function<Map<String, Object>, User> MAP_TO_USER = dto -> new User(
            Objects.toString(dto.get("id")),
            Objects.toString(dto.get("email")).trim().toLowerCase(),
            Objects.toString(dto.get("name")).trim(),
            ((Number) dto.getOrDefault("age", 0)).intValue(),
            Boolean.TRUE.equals(dto.get("vip"))
    );

    // Business normalization pipeline: trim -> lowercase -> validate.
    static final Function<String, String> NORMALIZE_EMAIL =
            ((Function<String, String>) String::trim)
                    .andThen(String::toLowerCase)
                    .andThen(email -> {
                        if (!email.contains("@")) throw new IllegalArgumentException("Invalid email: " + email);
                        return email;
                    });

    // Memoized function: expensive profile fetch, cached by userId (computeIfAbsent is called at most once per key).
    static <T, R> Function<T, R> memoize(Function<T, R> fn) {
        var cache = new ConcurrentHashMap<T, R>();
        return key -> cache.computeIfAbsent(key, fn);
    }

    // --- PREDICATE: T -> boolean (validation, filtering, rules) ---
    static final Predicate<Order> IS_HIGH_VALUE =
            o -> o.amount().compareTo(new BigDecimal("1000")) >= 0;

    static final Predicate<Order> IS_SETTLED_STATUS =
            o -> Set.of("PAID", "CAPTURED", "SETTLED").contains(o.status());

    // Combine rules: "VIP OR high-value" AND "settled"
    static final Predicate<Order> ELIGIBLE_FOR_FAST_PAYOUT =
            ((Predicate<Order>) (o -> o.items() > 0))        // ← use getItems()
                    .and(IS_SETTLED_STATUS)
                    .and(o -> o.amount().compareTo(BigDecimal.ZERO) > 0)
                    .and(o -> o.userId() != null && !o.userId().isBlank());

    // --- CONSUMER: side-effects (logging, audit, messaging) ---
    static final Consumer<Order> LOG_ORDER =
            o -> System.out.printf("order=%d status=%s amount=%s%n", o.id(), o.status(), o.amount());

    // Example "publisher": you’d call your Kafka/HTTP client here.
    static final Consumer<Order> PUBLISH_ORDER_EVENT =
            o -> {/* producer.send("order-events", serialize(o)); */};

    // Chain side-effects (both run, order matters)
    static final Consumer<Order> AUDIT_AND_PUBLISH =
            LOG_ORDER.andThen(PUBLISH_ORDER_EVENT);

    // --- SUPPLIER: () -> T (lazy init, IDs, clocks, configuration) ---
    static final Supplier<String> NEW_ID = () -> UUID.randomUUID().toString();

    // Time control (testable): inject Clock; Supplier hides the dependency from callsites.
    static Supplier<Instant> clockNow(Clock clock) { return () -> Instant.now(clock); }

    // Safe retry wrapper for Suppliers (simplified)
    static <T> Supplier<T> retry(Supplier<T> s, int maxAttempts) {
        return () -> {
            RuntimeException last = null;
            for (int i = 0; i < maxAttempts; i++) {
                try { return s.get(); } catch (RuntimeException e) { last = e; }
            }
            throw last;
        };
    }

    // --- COMPARATOR: multi-key, null handling, direction ---
    // Priority: settled first, then amount DESC, then createdAt ASC (nulls last).
    static final Comparator<Order> ORDER_BUSINESS_SORT = Comparator
            .comparing((Order o) -> IS_SETTLED_STATUS.test(o)).reversed()         // settled = true comes first
            .thenComparing(Order::amount, Comparator.nullsLast(Comparator.reverseOrder()))
            .thenComparing(Order::createdAt, Comparator.nullsLast(Comparator.naturalOrder()));

    // --- PRIMITIVE SPECIALIZATIONS (avoid boxing in hot paths) ---
    // IntPredicate: classify HTTP status (real-life filter for retry logic)
    static final IntPredicate IS_2XX = code -> code / 100 == 2;
    static final IntPredicate IS_RETRIABLE_STATUS = code -> code == 429 || code / 100 == 5;

    // IntUnaryOperator: exponential backoff for retry (capped)
    static IntUnaryOperator backoff(int baseMs, int maxMs) {
        return attempt -> Math.min(maxMs, baseMs * (1 << Math.max(0, attempt - 1)));
    }

    // IntFunction: generate shard key / partition name for DB or Kafka
    static final IntFunction<String> PARTITION = p -> "orders-partition-" + p;

    // ToIntFunction<Order>: cheap extractor for aggregation (items count)
    static final ToIntFunction<Order> ITEMS = Order::items;

    // ObjIntConsumer: apply a percentage discount to a mutable accumulator
    static final ObjIntConsumer<Map<String, BigDecimal>> APPLY_PERCENT_DISCOUNT =
            (totalsByUser, percent) -> totalsByUser.replaceAll(
                    (u, total) -> total.subtract(total.multiply(BigDecimal.valueOf(percent).movePointLeft(2)))
            );

    // LongPredicate: file size guard (bytes > threshold)
    static LongPredicate largerThanMB(long mb) {
        long threshold = mb * 1024 * 1024L;
        return bytes -> bytes > threshold;
    }

    // DoubleUnaryOperator: numeric normalization (e.g., clamp a score to [0,1])
    static final DoubleUnaryOperator CLAMP_01 = x -> x < 0 ? 0 : (x > 1 ? 1 : x);

    // --- Tiny demo to cement the ideas ---
    public static void main(String[] args) {

        // FUNCTION
        var dto = Map.<String, Object>of("id","U1","email"," Alice@Example.com ","name"," Alice ","age",31,"vip",true);
        User user = MAP_TO_USER.apply(dto);
        System.out.println("user: " + user);

        // PREDICATE + CONSUMER + COMPOSITION
        var o1 = new Order(1001, user.id(), new BigDecimal("1599.00"), "PAID", Instant.now(), 3);
        var o2 = new Order(1002, user.id(), new BigDecimal("49.99"), "CREATED", Instant.now(), 1);
        List.of(o1, o2).stream()
                .filter(ELIGIBLE_FOR_FAST_PAYOUT)
                .forEach(AUDIT_AND_PUBLISH);

        // SUPPLIER (IDs, time, retry)
        String id1 = NEW_ID.get();
        Instant now = clockNow(Clock.systemUTC()).get();
        String id2 = retry(NEW_ID, 3).get();
        System.out.println("ids: " + id1 + ", " + id2 + " now=" + now);

        // COMPARATOR (multi-key sort)
        var list = new ArrayList<>(List.of(
                o2,
                new Order(1003, user.id(), new BigDecimal("1599.00"), "CAPTURED", now.minusSeconds(60), 2),
                o1
        ));
        list.sort(ORDER_BUSINESS_SORT);
        System.out.println("sorted: " + list);

        // PRIMITIVE SPECIALIZATIONS (no boxing in hot path)
        int[] statuses = {200, 201, 502, 429, 504, 418};
        int retriable = (int) IntStream.of(statuses).filter(IS_RETRIABLE_STATUS).count();
        System.out.println("retriable statuses: " + retriable);

        IntUnaryOperator delay = backoff(100, 5_000);
        System.out.println("backoff ms for attempts 1..6: " +
                IntStream.rangeClosed(1, 6).map(delay).collect(StringBuilder::new,
                        (sb, ms) -> sb.append(ms).append(" "), StringBuilder::append));

        String p3 = PARTITION.apply(3);
        System.out.println("partition: " + p3);

        int totalItems = list.stream().mapToInt(ITEMS).sum(); // primitive stream, no boxing
        System.out.println("total items: " + totalItems);

        var totals = new HashMap<String, BigDecimal>();
        totals.put(user.id(), new BigDecimal("100.00"));
        APPLY_PERCENT_DISCOUNT.accept(totals, 10); // 10% discount
        System.out.println("totals after discount: " + totals);

        boolean big = largerThanMB(100).test(150L * 1024 * 1024);
        System.out.println("file >100MB? " + big);

        double clamped = CLAMP_01.applyAsDouble(1.25);
        System.out.println("clamped: " + clamped);
    }
}
