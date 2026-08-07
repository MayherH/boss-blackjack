package com.bossblackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class LootSimulator {

    private static final Random random = new Random();

    private static List<LootEntry> getTable(SupportedBoss boss) {
        switch (boss) {
            case VARDORVIS:
                return LootTables.VARDORVIS_TABLE;
            case AMOXLIATL:
                return LootTables.AMOXLIATL_TABLE;
            default:
                return LootTables.VARDORVIS_TABLE;
        }
    }

    private LootEntry rollTable(List<LootEntry> table) {
        int totalWeight = 0;
        for (LootEntry entry : table) {
            totalWeight += entry.getWeight();
        }

        int roll = LootSimulator.random.nextInt(totalWeight);
        int cumulative = 0;
        for (LootEntry entry : table) {
            cumulative += entry.getWeight();
            if (roll < cumulative) {
                return entry;
            }
        }
        return table.get(table.size() - 1);
    }

    public List<SimulatedDrop> simulate(SupportedBoss boss)
    {
        List<SimulatedDrop> drops = new ArrayList<>();

        int rolls = 1;

        if (boss == SupportedBoss.AMOXLIATL) {
            drops.add(new SimulatedDrop(29895,(int) (Math.random() * (20 - 2 + 1)) + 2));
            rolls = 2;
        }

        for (int i = 0; i < rolls; i++)
        {
            LootEntry entry = rollTable(getTable(boss));

            int quantity = entry.getMinQuantity() == entry.getMaxQuantity() ? entry.getMinQuantity() : entry.getMinQuantity() + random.nextInt(entry.getMaxQuantity() - entry.getMinQuantity() + 1);

            drops.add(new SimulatedDrop(
                    entry.getItemId(),
                    quantity
            ));
        }

        return drops;
    }
}
