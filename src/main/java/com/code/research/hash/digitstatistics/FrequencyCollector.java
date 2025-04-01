package com.code.research.hash.digitstatistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * {@code FrequencyCollector} is a custom collector implementation that computes a frequency map
 * for elements of type {@code T}, using a numeric type {@code U} (e.g., {@code Integer} or {@code Long})
 * to count occurrences.
 *
 * <p>This collector is useful when you need to compute the frequency distribution of elements in a stream.
 * It is highly generic, allowing you to specify:
 * <ul>
 *   <li>The type of the elements being counted (T).</li>
 *   <li>The numeric type for counting (U), which must extend {@link Number}.</li>
 *   <li>An identity value representing the initial count (typically 1 or 1L).</li>
 *   <li>An adder function to combine counts, such as {@code Integer::sum} or {@code Long::sum}.</li>
 * </ul>
 *
 * <p>The collector accumulates elements into a {@code Map<T, U>} where each key is an element
 * and the corresponding value is the frequency of that element in the stream.
 *
 * @param <T> the type of elements to count.
 * @param <U> the numeric type for counts, which extends {@link Number}.
 */
public class FrequencyCollector<T, U extends Number> implements Collector<T, Map<T, U>, Map<T, U>> {

    /**
     * The identity value to initialize the count for each element. Typically, 1 or 1L.
     */
    private final U identity;

    /**
     * A {@code BinaryOperator} that combines two count values. For example, {@code Integer::sum}
     * or {@code Long::sum}.
     */
    private final BinaryOperator<U> adder;

    /**
     * Constructs a {@code FrequencyCollector} with the specified identity value and adder function.
     *
     * @param identity the initial count value for each element.
     * @param adder    the function used to add two count values together.
     */
    public FrequencyCollector(U identity, BinaryOperator<U> adder) {
        this.identity = identity;
        this.adder = adder;
    }

    /**
     * Returns a new supplier of the result container.
     * The supplier creates an empty {@code HashMap} to accumulate frequency counts.
     *
     * @return a supplier that returns a new {@code HashMap<T, U>}.
     */
    @Override
    public Supplier<Map<T, U>> supplier() {
        return HashMap::new;
    }

    /**
     * Returns an accumulator function that incorporates a new element into the result container.
     * For each element, this function updates the frequency count by using the adder function.
     *
     * @return a {@code BiConsumer} that updates the frequency map for each element.
     */
    @Override
    public BiConsumer<Map<T, U>, T> accumulator() {
        return (map, element) -> map.compute(element, (k, v) -> (v == null) ? identity : adder.apply(v, identity));
    }


    /**
     * Returns a combiner function that merges two frequency maps into one.
     * This is used in parallel stream operations.
     *
     * @return a {@code BinaryOperator} that merges two maps by adding the counts for duplicate keys.
     */
    @Override
    public BinaryOperator<Map<T, U>> combiner() {
        return (map1, map2) -> {
            map2.forEach((k, v) -> map1.merge(k, v, adder));
            return map1;
        };
    }

    /**
     * Returns a finisher function which is the identity function in this collector,
     * because the intermediate result is already the final result.
     *
     * @return a {@code Function} that returns its input map.
     */
    @Override
    public Function<Map<T, U>, Map<T, U>> finisher() {
        return Function.identity();
    }

    /**
     * Returns a set of characteristics for this collector.
     * This collector has the {@code IDENTITY_FINISH} characteristic because the accumulator
     * itself is the final result.
     *
     * @return an unmodifiable {@code Set} containing {@code Characteristics.IDENTITY_FINISH}.
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

    /**
     * A factory method to obtain a {@code FrequencyCollector} instance that produces a frequency map
     * with {@code Integer} counts.
     *
     * @param <T> the type of elements to count.
     * @return a new {@code FrequencyCollector<T, Integer>} with identity value 1 and using {@code Integer::sum}.
     */
    public static <T> FrequencyCollector<T, Integer> toFrequencyMapInteger() {
        return new FrequencyCollector<>(1, Integer::sum);
    }

    /**
     * A factory method to obtain a {@code FrequencyCollector} instance that produces a frequency map
     * with {@code Long} counts.
     *
     * @param <T> the type of elements to count.
     * @return a new {@code FrequencyCollector<T, Long>} with identity value 1L and using {@code Long::sum}.
     */
    public static <T> FrequencyCollector<T, Long> toFrequencyMapLong() {
        return new FrequencyCollector<>(1L, Long::sum);
    }

    /**
     * A factory method to obtain a {@code FrequencyCollector} instance that produces a frequency map
     * with {@code Double} counts.
     *
     * @param <T> the type of elements to count.
     * @return a new {@code FrequencyCollector<T, Double>} with identity value 1L and using {@code Double::sum}.
     */
    public static <T> FrequencyCollector<T, Double> toFrequencyMapDouble() {
        return  new FrequencyCollector<>(1.0, Double::sum);
    }

    public static <T> FrequencyCollector<T, Float> toFrequencyMapFloat() {
        return  new FrequencyCollector<>((float)1, Float::sum);
    }

}
