package com.bossblackjack;

import java.util.Arrays;
import java.util.List;

public class LootTables {

    //region Vardorvis table
    public static final List<LootEntry> VARDORVIS_TABLE = Arrays.asList(
            // Uniques (1/136)
            new LootEntry(28276, 1, 1, 3),   // Chromium ingot
            new LootEntry(28285, 1, 1, 3),   // Ultor vestige
            new LootEntry(28319, 1, 1, 1),   // Executioner's axe head
            new LootEntry(26243, 1, 1, 1),   // Virtus piece

            // Awakener's orb (1/80)
            new LootEntry(28334, 1, 1, 12),  // Awakener's orb

            // Blood quartz (1/204)
            new LootEntry(28268, 1, 1, 5),   // Blood quartz

            // 1/12.5 drops
            new LootEntry(453, 195, 195, 80),   // Coal
            new LootEntry(449, 67, 67, 80),     // Adamantite ore
            new LootEntry(19580, 54, 54, 80),   // Rune javelin tips
            new LootEntry(19582, 54, 54, 80),   // Dragon javelin tips
            new LootEntry(4699, 300, 300, 80),   // Lava rune
            new LootEntry(565, 300, 300, 80),   // Blood rune

            // Supply drops (1/5.3)
            new LootEntry(139,1,1,195),   // Prayer Potion(3)


            // 1/20 drops
            new LootEntry(1619, 37, 37, 50),    // Uncut ruby
            new LootEntry(1617, 37, 37, 50),    // Uncut diamond

            // 1/50 drops
            new LootEntry(451, 27, 27, 20),     // Runite ore
            new LootEntry(11232, 150, 150, 20),  // Dragon dart tip
            new LootEntry(566, 600, 600, 20),   // Soul rune

            // 1/100 drops
            new LootEntry(7936, 180, 180, 10),  // Pure essence
            new LootEntry(440, 57, 57, 10),     // Iron ore
            new LootEntry(442, 57, 57, 10),     // Silver ore
            new LootEntry(447, 57, 57, 10),     // Mithril ore
            new LootEntry(1607, 25, 25, 10),    // Sapphire
            new LootEntry(1605, 25, 25, 10),    // Emerald
            new LootEntry(1603, 25, 25, 10),    // Ruby
            new LootEntry(383, 300, 300, 10),   // Raw shark
            new LootEntry(825, 63, 63, 10),     // Bronze javelin
            new LootEntry(828, 63, 63, 10),   // Mithril javelin
            new LootEntry(829, 63, 63, 10),   // Adamant javelin
            new LootEntry(9245, 90, 90, 10),    // Onyx bolts (e)
            new LootEntry(556, 180, 180, 10),   // Mind rune
            new LootEntry(558, 180, 180, 10)    // Fire rune
    );
    //endregion

    //region Amoxliatl table
    public static final List<LootEntry> AMOXLIATL_TABLE = Arrays.asList(
            new LootEntry(29889, 1, 1, 5), // Glacial Temotli
            new LootEntry(30105, 1, 1, 4), // Tooth half of key

            new LootEntry(1432, 1, 1, 34), // Rune mace
            new LootEntry(1275, 1, 1, 34), // Rune pickaxe
            new LootEntry(1127, 1, 1, 17), // Rune platebody
            new LootEntry(1079, 1, 1, 17), // Rune platelegs

            new LootEntry(555, 200, 400, 86), // water rune
            new LootEntry(562, 30, 60, 69), // chaos rune
            new LootEntry(560, 20, 40, 69), // death rune
            new LootEntry(565, 15, 30, 69), // blood rune
            new LootEntry(566, 30, 45, 69), // soul rune
            new LootEntry(561, 30, 60, 69), // nature rune

            new LootEntry(453, 20, 30, 86), // Coal
            new LootEntry(444, 20, 30, 86), // Gold ore
            new LootEntry(449, 5, 10, 69), // Adamantite ore
            new LootEntry(139, 1, 1, 34), // Pray pot (3)
            new LootEntry(451, 1, 1, 34), // Runite ore
            new LootEntry(29381, 60, 100, 34), // Blessed Bone Shards
            new LootEntry(571, 10, 20, 34), // Water orb
            new LootEntry(1444, 1, 1, 34), // Water Tally
            new LootEntry(30088, 1, 1, 17), // Huasca seed
            new LootEntry(1623, 1, 1, 30) // Uncut Sapphire
    );
    //endregion

    //region Phosani's table
    public static final List<LootEntry> PHOSANI_TABLE = Arrays.asList(

            new LootEntry(24419, 1, 1, 14), // Inq Helm
            new LootEntry(24420, 1, 1, 14), // Inq Body
            new LootEntry(24421, 1, 1, 14), // Inq Legs
            new LootEntry(24417, 1, 1, 9), // Inq Mace
            new LootEntry(24422, 1, 1, 20), // NM Staff
            new LootEntry(24511, 1, 1, 6), // Harm orb
            new LootEntry(24514, 1, 1, 6), // Volly orb
            new LootEntry(24517, 1, 1, 6), // Eldritch orb

            new LootEntry(564, 247, 420, 400),
            new LootEntry(561, 165, 305, 400),
            new LootEntry(560, 165, 305, 400),
            new LootEntry(565, 343, 765, 400),
            new LootEntry(566, 110, 228, 400),
            new LootEntry(2, 137, 382, 400),
            new LootEntry(892, 412, 957, 300),

            new LootEntry(447, 165, 305, 500),
            new LootEntry(453, 220, 458, 400),
            new LootEntry(444, 165, 305, 400),
            new LootEntry(449, 40, 95, 400),
            new LootEntry(1513, 40, 95, 400),
            new LootEntry(215, 13, 26, 400),
            new LootEntry(219, 13, 26, 400),
            new LootEntry(5300, 5, 10, 300),
            new LootEntry(1621, 33, 75, 300),
            new LootEntry(1619, 27, 60, 300),
            new LootEntry(451, 11, 26, 200),

            new LootEntry(365, 16, 29, 600),
            new LootEntry(385, 13, 26, 600),
            new LootEntry(139, 8, 15, 500),
            new LootEntry(10927, 6, 12, 500),
            new LootEntry(6687, 8, 15, 500),
            new LootEntry(189, 8, 15, 500),

            new LootEntry(995, 41417, 74500, 211)
    );
    //endregion


}
