package me.skizzme.cc.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Statistics {

    private static final HashMap<String, Supplier<Double>> stats = new HashMap<>();
    private static final HashMap<String, Double> minuteDeltas = new HashMap<>();

    private static final HashMap<String, Double> prevMinuteValues = new HashMap<>();
    private static long prevMinute;

    public static void register(String id, Supplier<Double> totalStat) {
        stats.put(id, totalStat);
    }

    public static void updateMinutes() {
        for (Map.Entry<String, Supplier<Double>> entry : stats.entrySet()) {
            String id = entry.getKey();
            Double value = entry.getValue().get();

            minuteDeltas.put(id, value - prevMinuteValues.getOrDefault(id, 0.0));
            prevMinuteValues.put(id, value);
        }
        prevMinute = System.currentTimeMillis();
    }

    public static Double getMinuteStat(String id) {
        if (System.currentTimeMillis() - prevMinute > 60*1000) {
            System.out.println("update");
            updateMinutes();
        }

        System.out.println(id + ", " + minuteDeltas);

        return minuteDeltas.get(id);
    }

}
