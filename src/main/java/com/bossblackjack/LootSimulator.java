package com.bossblackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LootSimulator {
    private static final Random random = new Random();

    private static final List<LootEntry> VARDORVIS_TABLE = Arrays.asList(
            // Uniques (1/136)
            new LootEntry(28278, 1, 1, 7),   // Chromium ingot
            new LootEntry(28320, 1, 1, 7),   // Ultor vestige
            new LootEntry(28322, 1, 1, 7),   // Executioner's axe head
            new LootEntry(-1, 1, 1, 1),      // Virtus piece (secondary roll 1/3)

            // Awakener's orb (1/80)
            new LootEntry(26908, 1, 1, 12),  // Awakener's orb

            // Blood quartz (1/204)
            new LootEntry(28325, 1, 1, 5),   // Blood quartz

            // Supply drops (1/12.5)
            new LootEntry(453, 195, 195, 80),   // Coal
            new LootEntry(449, 67, 67, 80),     // Adamantite ore
            new LootEntry(19580, 54, 54, 80),   // Rune javelin tips
            new LootEntry(19582, 54, 54, 80),   // Dragon javelin tips
            new LootEntry(554, 300, 300, 80),   // Fire rune
            new LootEntry(565, 300, 300, 80),   // Blood rune
            new LootEntry(560, 600, 600, 80),   // Soul rune

            // 1/20 drops
            new LootEntry(1619, 37, 37, 50),    // Uncut ruby
            new LootEntry(1621, 37, 37, 50),    // Uncut diamond

            // 1/50 drops
            new LootEntry(447, 27, 27, 20),     // Runite ore
            new LootEntry(9187, 150, 150, 20),  // Dragon dart tip

            // 1/100 drops
            new LootEntry(7936, 180, 180, 10),  // Pure essence
            new LootEntry(440, 57, 57, 10),     // Iron ore
            new LootEntry(444, 57, 57, 10),     // Silver ore
            new LootEntry(441, 57, 57, 10),     // Mithril ore
            new LootEntry(1623, 25, 25, 10),    // Sapphire
            new LootEntry(1625, 25, 25, 10),    // Emerald
            new LootEntry(1627, 25, 25, 10),    // Ruby
            new LootEntry(383, 300, 300, 10),   // Raw shark
            new LootEntry(563, 63, 63, 10),     // Bronze javelin
            new LootEntry(11230, 63, 63, 10),   // Mithril javelin
            new LootEntry(11231, 63, 63, 10),   // Adamant javelin
            new LootEntry(9243, 90, 90, 10),    // Onyx bolts (e)
            new LootEntry(556, 180, 180, 10),   // Mind rune
            new LootEntry(554, 180, 180, 10)    // Fire rune
    );

    private static List<LootEntry> getTable(SupportedBoss boss) {
        switch (boss) {
            case VARDORVIS:
                return VARDORVIS_TABLE;
            default:
                return VARDORVIS_TABLE;
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

    public List<SimulatedDrop> simulate(SupportedBoss boss, int targetValue, int thresholdPercent) {
        List<LootEntry> table = getTable(boss);
        List<SimulatedDrop> drops = new ArrayList<>();
        long total = 0;
        long threshold = (long)(targetValue * (thresholdPercent / 100.0));

        do
        {
            LootEntry entry = rollTable(table);
            int itemId = entry.getItemId();

            int quantity = entry.getMinQuantity() == entry.getMaxQuantity()
                    ? entry.getMinQuantity()
                    : entry.getMinQuantity() + random.nextInt(entry.getMaxQuantity() - entry.getMinQuantity() + 1);

            drops.add(new SimulatedDrop(itemId, quantity));
            total += quantity; // placeholder until we hook up ItemManager for prices

        } while (total < threshold);

        return drops;
    }
}
