import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class FunctionalUtils {

    /**
     * @param strings: the input collection of <code>String</code>s.
     * @return a collection of <code>String</code>s that start with a
     * capital letter (i.e., ‘A’ through ‘Z’).
     */
    public static Collection<String> capitalized(Collection<String> strings) {
        return strings.stream()
                .filter(s -> s.substring(0,1).toUpperCase().equals(s.substring(0,1))).collect(toList());
    }

    /**
     * Find and return the longest <code>String</code> in a given collection of <code>String</code>s.
     *
     * @param strings: the given collection of <code>String</code>s.
     * @param from_start: a <code>boolean</code> flag that decides how ties are broken.
    If <code>true</code>, then the element encountered earlier in
     * the iteration is returned, otherwise the element encountered
     * later is returned.
     * @return the longest <code>String</code> in the given collection,
     * where ties are broken based on <code>from_start</code>.
     */
    public static String longest(Collection<String> strings, boolean from_start) {
        return strings.stream()
                .collect(groupingBy(String::length, TreeMap::new, toList()))
                .lastEntry()
                .getValue()
                .stream()
                .reduce(from_start ? (first, second) -> first : (first, second) -> second)
                .orElse(null);
    }

    /**
     * Find and return the least element from a collection of given elements that are comparable.
     *
     * @param items: the given collection of elements
     * @param from_start: a <code>boolean</code> flag that decides how ties are broken.
     * If <code>true</code>, then the element encountered earlier in
     * the iteration is returned, otherwise the element encountered
     * later is returned.
     * @param <T>: the type parameter of the collection (i.e., the items are all of type T).
     * @return the least element in <code>items</code>, where ties are
     * broken based on <code>from_start</code>.
     */
    public static <T extends Comparable<T>> T least(Collection<T> items, boolean from_start) {
        return items.stream()
                .sorted()
                .reduce((first, second) -> first.compareTo(second) == 0 && !from_start ? second : first)
                .orElse(null);
    }

    /**
     * Flattens a map to a stream of <code>String</code>s, where each element in the list
     * is formatted as "key -> value".
     *
     * @param aMap the specified input map.
     * @param <K> the type parameter of keys in <code>aMap</code>.
     * @param <V> the type parameter of values in <code>aMap</code>.
     * @return the flattened list representation of <code>aMap</code>.
     */
    public static <K, V> List<String> flatten(Map<K, V> aMap) {
        return aMap.entrySet().stream()
                .map(s -> s.toString().replaceAll("=", " -> "))
                .collect(Collectors.toList());
    }
}
