package com.bossblackjack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SimulatedDrop
{
    private final int itemId;
    private final int quantity;
}