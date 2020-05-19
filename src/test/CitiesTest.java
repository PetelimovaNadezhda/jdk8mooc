import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CitiesTest {

    @DataProvider(name = "data-provider")
    public Object[][] dataProviderMethod() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("Addis Ababa", "Ethiopia", 345));
        cities.add(new City("Antananarivo", "Madagascar", 234));
        cities.add(new City("Antsiranana", "Madagascar", 123));
        cities.add(new City("Moscow", "Russia", 2344));
        cities.add(new City("Saint Petersburg", "Russia", 686));
        cities.add(new City("Cairo", "Egypt", 947));

        Map<String, Integer> groupOne = new HashMap<>();
        groupOne.put("Madagascar", 357);
        groupOne.put("Ethiopia", 345);

        Map<String, Integer> groupTwo = new HashMap<>();
        groupTwo.put("Russia", 3030);
        groupTwo.put("Egypt", 947);

        return new Object[][]{
                new Object[]{cities, groupOne, groupTwo}
        };
    }

    @Test(dataProvider = "data-provider")
    void testCitiesGrouping(List<City> cities, Map<String, Integer> groupOne, Map<String, Integer> groupTwo) {
        Map<Boolean, Map<String, Integer>> booleanMapMap = Cities.citiesGrouping(cities);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(booleanMapMap.get(true), groupOne);
        softAssert.assertEquals(booleanMapMap.get(false), groupTwo);
        softAssert.assertAll();
    }
}
