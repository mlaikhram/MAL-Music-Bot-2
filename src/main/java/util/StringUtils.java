package util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {

    public static String joinWithAnd(List<String> items, String emptyString) {
        if (items.isEmpty()) {
            return emptyString;
        }
        else if (items.size() == 1) {
            return items.iterator().next();
        }
        else if (items.size() == 2) {
            return String.join(" and ", items);
        }
        else {
            List<String> properItems = IntStream.range(0, items.size())
                    .mapToObj(i -> i == items.size() - 1 ? String.format("and %s", items.get(i)) : items.get(i))
                    .collect(Collectors.toList());
            return String.join(", ", properItems);
        }
    }
}
