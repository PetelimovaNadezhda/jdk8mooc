import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Lesson1 {

    public static void main(String[] args) {
        List<String> listNames = Arrays.asList("Tom", "John", "Mary", "Peter", "David", "Alice");
        System.out.println(listNames + System.lineSeparator());

        //Exercise 1
        stringConsistsFirstLetter(listNames);
        System.out.print(System.lineSeparator());

        //Exercise 2
        removeWordsWithOddLength(listNames);
        System.out.print(System.lineSeparator());

        //Exercise 3
        replaceAllWordUpperCase(listNames);
        System.out.print(System.lineSeparator());

        //Exercise 4
        Map<Object, Object> vehicles = new HashMap<>();
        vehicles.put("BMW", 5);
        vehicles.put("Mercedes", 3);
        vehicles.put("Audi", 4);
        vehicles.put("Ford", 10);

        convertMapIntoString(vehicles);
        System.out.print(System.lineSeparator());

        //Exercise 5
        threadPrintList(Arrays.asList(1, 4, 6, 6, 8));
    }

    public static void stringConsistsFirstLetter(List<String> listOfString) {
        System.out.println("stringConsistsFirstLetter : " +
                listOfString.stream()
                        .map(string -> string.charAt(0))
                        //Argument 1: Creates starting result (new StringBuilder)
                        //Argument 2: Adds an element (String) to result (StringBuilder).
                        //Argument 3: If run the stream in parallel, multiple StringBuilders will be created. This is for combining these together.
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                        .toString());
    }

    public static void removeWordsWithOddLength(List<String> listOfString) {
        System.out.println("removeWordsWithOddLength: ");
        listOfString.stream()
                .filter(string -> string.length() % 2 == 1)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public static void replaceAllWordUpperCase(List<String> listOfString) {
        System.out.println("replaceAllWordUpperCase: ");
        listOfString.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public static void convertMapIntoString(Map<Object, Object> map) {
        System.out.println("replaceAllWordUpperCase: " +
                map.entrySet().stream()
                        .map(set -> set.getKey().toString() + ": " + set.getValue().toString() + "; ")
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                        .toString());
    }

    public static void threadPrintList(List<Object> list) {
        System.out.println("threadPrintList: ");
        new Thread(() -> list.forEach(System.out::println)).start();
    }
}
