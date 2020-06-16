package util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hqf
 * @description:
 * @Data: Create in 19:31 2020/6/12
 * @Modified By:
 */
public class TimeUtil {
    public static Map<String, Long> startTime = new HashMap<>();
    public static Map<String, Long> endTime = new HashMap<>();

    public static void setStartTime(String method, Long start) {
        startTime.put(method, start);
    }

    public static void setEndTime(String method, Long end) {
        endTime.put(method, end);
    }

    public static String costTimeMethod(String method) {
        long start = startTime.get(method);
        long end = endTime.get(method);
        double cost = (start-end) / 1000000.0;
        return "---[" + (cost) + " ms]";
    }
}
