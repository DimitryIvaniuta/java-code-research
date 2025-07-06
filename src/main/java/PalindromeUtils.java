import java.util.Comparator;
import java.util.List;

public class PalindromeUtils {

    /**
     * Filters the input list to keep only palindromic strings (ignoring case),
     * and returns them sorted by length in descending order.
     *
     * @param strings the input List of String
     * @return a List of palindromes sorted from longest to shortest
     */
    public static List<String> getPalindromesBySizeDesc(List<String> strings) {
        return strings.stream()
                .filter(PalindromeUtils::isPalindrome)
                .sorted(Comparator.comparingInt(String::length).reversed())
                .toList();
    }

    /**
     * Checks if a string is a palindrome, ignoring case.
     *
     * @param s the string to test
     * @return true if s reads the same forwards and backwards
     */
    private static boolean isPalindrome(String s) {
        String lower = s.toLowerCase();
        return new StringBuilder(lower).reverse().toString().equals(lower);
    }

    public static void main(String[] args) {
        List<String> words = List.of(
                "Racecar", "Level", "World", "radar",
                "hello",   "Madam", "noon",  "refer",
                "Java",    "civic", "kayak", "step on no pets"
        );

        List<String> palindromes = getPalindromesBySizeDesc(words);
        System.out.println(palindromes);
        // Possible output:
        // [step on no pets, Racecar, Madam, Level, radar, civic, kayak, refer, noon]
    }
}
