import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

public class CitiesTest {
    @Test
    void testCitiesGrouping() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("Addis Ababa", "Ethiopia"));
        cities.add(new City("Antananarivo", "Madagascar"));
        cities.add(new City("Antsiranana", "Madagascar"));
        cities.add(new City("Moscow", "Russia"));
        cities.add(new City("Saint Petersburg", "Russia"));
        cities.add(new City("Cairo", "Egypt"));

        Map<String, List<String>> groupOne = new HashMap<>();
        groupOne.put("Madagascar", Arrays.asList("Antananarivo", "Antsiranana"));
        groupOne.put("Ethiopia", Arrays.asList("Addis Ababa"));

        Map<String, List<String>> groupTwo = new HashMap<>();
        groupTwo.put("Russia", Arrays.asList("Moscow", "Saint Petersburg"));
        groupTwo.put("Egypt", Arrays.asList("Cairo"));

        Map<Boolean, Map<String, List<String>>> booleanMapMap = Cities.citiesGrouping(cities);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(booleanMapMap.get(true), groupOne);
        softAssert.assertEquals(booleanMapMap.get(false), groupTwo);
        softAssert.assertTrue(booleanMapMap.get(true).values().stream().flatMap(Collection::stream).allMatch(city -> city.startsWith("A")));
        softAssert.assertTrue(booleanMapMap.get(false).values().stream().flatMap(Collection::stream).noneMatch(city -> city.startsWith("A")));
        softAssert.assertAll();
    }
}
