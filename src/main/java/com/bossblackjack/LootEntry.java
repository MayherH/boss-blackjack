package com.bossblackjack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LootEntry
{
    private final int itemId;
    private final int minQuantity;
    private final int maxQuantity;
    private final int weight;
}
