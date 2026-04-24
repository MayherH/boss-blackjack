package com.bossblackjack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SupportedBoss
{
    VORKATH("Vorkath"),
    ZULRAH("Zulrah"),
    VARDORVIS("Vardorvis"),
    YAMA("Yama"),
    WHISPERER("The Whisperer"),
    SARACHNIS("Sarachnis"),
    MUSPAH("Phantom Muspah"),
    KRIL("K'ril Tsutsaroth"),
    KREE("Kree'arra"),
    ZILY("Commander Zilyana"),
    GRAARDOR("General Graardor"),
    GGS("Grotesque Guardians"),
    MOLE("Giant Mole"),
    DUKE("Duke Sucellus"),
    LEVI("The Leviathan"),
    CORP("Corporeal Beast"),
    CERBERUS("Cerberus"),
    ARAXXOR("Araxxor"),
    AMOXLIATL("Amoxliatl"),
    HYDRA("Alchemical Hydra");

    private final String npcName;

    @Override
    public String toString()
    {
        return npcName;
    }
}
