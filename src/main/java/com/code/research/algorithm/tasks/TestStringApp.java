package com.code.research.algorithm.tasks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.stream.Streams;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class TestStringApp {
    private static Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public static int sumCounting(String string1){
        String[] intStr = string1.split("[^\\p{L}\\p{N}]+");
        return Stream.of(intStr).map(Integer::parseInt).reduce(0, Integer::sum);
    }

    public static boolean isAnagramCounting(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }

        int s1 = string1.chars().sum();
        int s2 = string2.chars().sum();
        log.info("s1 = " + s1 + ", s2 = " + s2);
        return s1 == s2;
    }
    private static boolean isAnagramSort(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }
        char a1[] = string1.toCharArray();
        char a2[] = string2.toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }
    private static boolean hasPalindromePermutation(String text) {
        long charsWithOddOccurrencesCount = text.chars().boxed()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .values()
                .stream()
                .filter(count -> count % 2 != 0)
                .count();
        return charsWithOddOccurrencesCount % 2 == 1;
    }
    private static boolean isPalindromeStream(String palindrome) {
        String temp = palindrome.replaceAll("\\s+", "").toLowerCase();
        return IntStream
                .range(0, palindrome.length() / 2)
                .noneMatch(i -> temp.charAt(i) != palindrome.charAt(temp.length() - i - 1));
    }

        private static boolean isPalindrome(String palindrome){
        int forward = 0;
        int backward = palindrome.length() - 1;
        while(backward>forward){
            char charForward = palindrome.charAt(forward++);
            char charBack = palindrome.charAt(backward--);
            if(charForward != charBack){
                return false;
            }
        }
        return true;
    }

    private static String doReverse(String input){
        String clean = input.replaceAll("\\s+", "");
        StringBuilder reverse = new StringBuilder();
        char[] chars = clean.toCharArray();
        for(int i = chars.length - 1; i >= 0; i--){
            char reverseChar = chars[i];
            reverse.append(reverseChar);
        }
        return reverse.toString();
    }

    public static boolean isValidLocalDate(String dateStr, DateTimeFormatter dateTimeFormatter) {
        try {
            LocalDate localDate = LocalDate.parse(dateStr, dateTimeFormatter);
            log.info("Parsed local date: {}", localDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidDate(String dateStr, String dateFormatString) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        dateFormat.setLenient(true);
        try {
            log.info("Parsed date: {}", dateFormat.parse(dateStr));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return numericPattern.matcher(strNum).matches();
    }

    public static String reverseUsingCharsMethod(String str) {
        if (str == null) {
            return null;
        }
        return str.chars()
                .mapToObj(c -> (char) c)
                .reduce("", (a, b) -> b + a, (a2, b2) -> b2 + a2);
    }

    public static String reverseUsingIntStreamRangeMethod(String str) {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        return IntStream.range(0, chars.length)
                .mapToObj(i -> chars[chars.length - i - 1])
                .collect(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append
                ).toString();
    }

    public static String reverseStr(String input) {
        if (input == null) {
            return input;
        }

        StringBuilder out = new StringBuilder();
        for (int i = input.length() - 1; i >= 0; i--) {
            if (i == input.length() - 1) {
                Character c = input.charAt(i);
                out.append(c.toString().toUpperCase());
            } else {
                out.appendCodePoint(input.codePointAt(i));
            }
        }
        return out.toString();
    }

    public static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
                .filter(str -> !str.isBlank())
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

    public String textBlocks() {
        return """
                Get busy living
                or
                get busy dying.
                --Stephen King""";
    }

    static String decodeText(String input, Charset charset,
                             CodingErrorAction codingErrorAction) throws IOException {
        CharsetDecoder charsetDecoder = charset.newDecoder();
        charsetDecoder.onMalformedInput(codingErrorAction);
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes(charset)), charsetDecoder)).readLine();
    }

    static String convertToBinary(String input, String encoding)
            throws UnsupportedEncodingException {
        byte[] encoded_input = Charset.forName(encoding)
                .encode(input)
                .array();
        return IntStream.range(0, encoded_input.length)
                .map(i -> encoded_input[i])
                .mapToObj(e -> Integer.toBinaryString(e ^ 255))
                .map(e -> String.format("%1$" + Byte.SIZE + "s", e).replace(" ", "0"))
                .collect(Collectors.joining(" "));
    }

    String composeUsingMessageFormatter(String feelsLike, String temperature, String unit) {
        return MessageFormat.format("Today''s weather is {0}, with a temperature of {1} degrees {2}",
                feelsLike, temperature, unit);
    }

    String decodeText(String input, String encoding) throws IOException {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes()),
                        Charset.forName(encoding)
                )).readLine();
    }

    public static void main(String[] args) {
        List<String> arr = Arrays.asList("s1", "s2");
        arr.stream().collect(Collectors.joining(", "));
        String resArr = String.join(", ", arr);

        String[] myFavouriteLanguages = {"Java", "JavaScript", "Python"};
        Arrays.sort(myFavouriteLanguages);
        String res = Arrays.toString(myFavouriteLanguages);
        log.info(res);

        StringJoiner joiner = new StringJoiner(", ");
        joiner.add("s1");
        joiner.add("s2");

        String[] strings = {"I'm", "running", "out", "of", "pangrams!"};
        String res2 = String.join(", ", strings);

        String res3 = String.format("Str1: %s1 %2f %d", "ate",
                2.5056302, 33);

        String res4 = "My S3".concat("s4").concat("s5");
        StringBuilder sb = new StringBuilder();
        StringBuffer sbf = new StringBuffer();

        String s6 = new String("s8");
        String h5 = s6.intern();

        String str = "Hello, Baeldung!";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            log.info("Char: {}", c);
        }
        for (char c : str.toCharArray()) {
            log.info("Char: {}", c);
        }

        str.chars().forEach(c -> {
            log.info("Char: {}", c);
        });

        CharacterIterator ci = new StringCharacterIterator(str);
        while (CharacterIterator.DONE != ci.current()) {
            log.info("Char: {}", ci.current());
            ci.next();
        }

        String string1 = "using comparison operator";
        String string2 = "using comparison operator";
        String string3 = new String("using comparison operator");

        log.info("Compare string1: {}", string1.compareTo(string2));
        log.info("Compare string2: {}", string1.compareTo(string3));
        log.info("Compare string3: {}", string1.equals(string3));
        log.info("Compare string4: {}", string1 == string3);
        log.info("Compare string5: {}", string1 == string2);
        log.info("Compare string6: {}", StringUtils.equals(string1, string2));
        log.info("Compare string7: {}", StringUtils.equals(string1, string3));
        try {
            log.info("Byte Encoding1: {}", convertToBinary("語", "Big5"));
            log.info("Byte Encoding2: {}", convertToBinary("T", "UTF-32"));
            log.info("Byte Encoding3: {}", convertToBinary("語", "UTF-8"));
            log.info("Byte Encoding4: {}", convertToBinary("語", "UTF-16"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            decodeText(
                    "The façade pattern is a software design pattern.",
                    StandardCharsets.US_ASCII,
                    CodingErrorAction.IGNORE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String resFormat = MessageFormat.format("   stest1 ${0} s2 ${1}   ", "v1", "v2");
        String[] splitStr = "peter,$james,$#@thomas".split("[^\\p{L}\\p{N}]+");
        String splitStrForstLetterUpperCaseValue = Stream.of(splitStr)
                .filter(s -> !s.isBlank())
                .map(String::toUpperCase)
                .collect(Collectors.joining(" - "));
        log.info("Str Split1: {}", Arrays.toString(splitStr));
        log.info("Str Split2: {}", splitStrForstLetterUpperCaseValue);

        String[] resSplit = StringUtils.split(resFormat, " ");
        log.info("Str split ::: {}", Streams.of(resSplit).collect(Collectors.joining(" :: ")));

        log.info("Str remove last: {}", removeLastCharOptional("Test string!"));
        log.info("Str reverse: {}", reverseStr("Test string"));
        log.info("Str reverse stream: {}", reverseUsingIntStreamRangeMethod("Test stream string"));
        log.info("Str reverse reduce: {}", reverseUsingCharsMethod("Test reduce string"));
        String nStr = "123";
        log.info("Int nStr: {}", Integer.parseInt(nStr));
        log.info("Float nStr: {}", Float.parseFloat(nStr));
        log.info("Double nStr: {}", Double.parseDouble(nStr));
        log.info("Long nStr: {}", Long.parseLong(nStr));
        log.info("BigInteger nStr: {}", new BigInteger(nStr));
        log.info("isNumeric1: {}", isNumeric("543"));
        log.info("isNumeric1-: {}", isNumeric("-54543332"));
        log.info("isNumeric2: {}", isNumeric("432.2312"));
        log.info("isNumeric3: {}", isNumeric("123a"));
        log.info("isNumeric4: {}", StringUtils.isNumeric("56142"));
        log.info("is valid Date1: {}", isValidDate("02/28/2019", "MM/dd/yyyy"));
        log.info("is valid Date2: {}", isValidDate("02/30/2019", "MM/dd/yyyy"));
        log.info("is valid Local Date1: {}", isValidLocalDate("20190228", DateTimeFormatter.BASIC_ISO_DATE));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ROOT)
                .withResolverStyle(ResolverStyle.LENIENT);
        log.info("is valid Local Date2: {}", isValidLocalDate("02/33/2019", formatter));
        String output1 = resFormat.substring(0, 1).toUpperCase() + resFormat.substring(1, resFormat.length() -1);
        log.info(" Capitalized output1: {}", output1);
        String output2 = Pattern.compile("^.")
                .matcher(resFormat)
                .replaceFirst(f -> f.group().toUpperCase());
        log.info(" Capitalized output2: {}", output2);
        String output3 = StringUtils.capitalize(resFormat);
        log.info(" Capitalized output3: {}", output3);
        resFormat = resFormat.concat("Concat123");
        String output4 = StringUtils.deleteWhitespace(resFormat);
        log.info(" Whitespace output4: {}:", output4);
        StringJoiner sj = new StringJoiner(",");
        sj.add("a1").add("a2").add("a3");
        log.info("String join: {}", sj.toString());
        String[] arrayStrings = {"a1", "a2", "a3"};
        String arrJoin = Arrays.stream(arrayStrings)
                .collect(Collectors.joining(","));
        log.info("String join2: {}", arrJoin);
        String arrJoin3 = String.join(": ", arrayStrings);
        log.info("String join3: {}", arrJoin3);
//        String intStr = "1 , 2 , 3";
//        Integer intStrSum = Stream.of(intStr.split(","))
//                .map(String::trim)
//                .map(Integer::parseInt)
//                .collect(
//                ...?...
//                );

        byte[] byteArr = new byte[7];
        new Random().nextBytes(byteArr);
        String generatedString = new String(byteArr);
        log.info("generatedString: {}", generatedString);

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        Random random = new Random();
        String generatedStr = random.ints(leftLimit, rightLimit)
                .limit(targetStringLength)
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append
                ).toString();
        log.info("generatedStr a-z: {}", generatedStr);

        int leftLimit2 = 48; // numeral '0'
        int rightLimit2 = 122; // letter 'z'
        int targetStringLength2 = 16;
        Random random2 = new Random();

        String generatedString2 = random.ints(leftLimit2, rightLimit2 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength2)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        log.info("generatedStr2 a-z: {}", generatedString2);

        String someString = "elephant";
        char someChar = 'e';
        int count = 0;

        for(int i = 0; i < someString.length(); i++) {
            if(someString.charAt(i) == someChar) {
                count++;
            }
        }
        log.info("char occurrences count: {}", count);
        int count2 = 0;
        String testE = "elephant test my rest";
        Pattern elementPattern = Pattern.compile("([^e]*)e", Pattern.CASE_INSENSITIVE);
        Matcher matchElement = elementPattern.matcher(testE);
        while(matchElement.find()){
            String el = matchElement.group(1);
            log.info("elFind: {}", el);
            count2 ++;
        }
        log.info("E count2: {}", count2);

        long countE = testE.chars().filter(c -> c == 'e').count();
        log.info("countE: {}", countE);

        String testStringToCount = "elephant test my rest";
        Map<String, Long> mapCount = testStringToCount.chars()
                .mapToObj(c -> (char) c)
                .map(c -> String.valueOf((char) c))
                .filter(el -> !el.trim().isEmpty())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                LinkedHashMap::new,
                                Collectors.counting()
                        )
                );

        log.info("Map testStringToCount: {}", mapCount);
        String palindrome = "werew";
        log.info("palindrome: {}", isPalindrome(palindrome));
        log.info("palindrome stream: {}", isPalindromeStream(palindrome));
        log.info("reverse string: {}", doReverse("test"));
        log.info("palindrome has: {}", hasPalindromePermutation(palindrome));
        log.info("is anagrams: {}", isAnagramSort("tea", "eat"));
        log.info("is anagrams2: {}", isAnagramCounting("tea", "eat"));
        log.info("sumCounting: {}", sumCounting("87 , 4, 39"));
    }
}
