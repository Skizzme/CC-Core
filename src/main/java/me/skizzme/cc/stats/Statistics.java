package me.skizzme.cc.stats;

import net.impactdev.impactor.api.utility.collections.mappings.Tuple;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.function.Supplier;

public class Statistics {

    private static final HashMap<String, ArrayDeque<Tuple<Long, Double>>> statValues = new HashMap<>();

    public static void updateStat(String id, Double value) {
        statValues.putIfAbsent(id, new ArrayDeque<>());
        ArrayDeque<Tuple<Long, Double>> stats = statValues.get(id);
        stats.addLast(new Tuple<>(System.currentTimeMillis(), value));

        while (!stats.isEmpty() && System.currentTimeMillis() - stats.getFirst().getFirst() >= 1000*60*10) {
            stats.removeFirst();
        }
    }

    public static Double getStat(String id) {
        return getStat(id, 60*1000);
    }

    public static Double getStat(String id, long timeMs) {
        Double sum = 0.0;
        for (Tuple<Long, Double> stat : statValues.getOrDefault(id, new ArrayDeque<>()).reversed()) {
            if (System.currentTimeMillis() - stat.getFirst() >= timeMs) break;
            sum += stat.getSecond();
        }
        return sum;
    }

}
