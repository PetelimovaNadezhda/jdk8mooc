import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cities {

    public static Map<Boolean, Map<String, List<String>>> citiesGrouping(List<City> cities) {
        return cities.stream()
                .collect(Collectors.partitioningBy(city -> city.getName().startsWith("A"), Collectors.groupingBy(City::getCountry, Collectors.mapping(City::getName, Collectors.toList()))));
    }
}
