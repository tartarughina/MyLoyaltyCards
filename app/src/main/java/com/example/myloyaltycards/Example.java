package com.example.myloyaltycards;

import android.graphics.Color;

import java.util.ArrayList;

public class Example {
    ArrayList<Card> cards;

    public Example(){
        cards = new ArrayList<Card>();

        cards.add(new Card("Esselunga", null, "Tessera fidaty", true, Color.GREEN));
        cards.add(new Card("Coop", null, "Tessera sociocoop", false, Color.YELLOW));
        cards.add(new Card("Conad", null, "Tessera sapori d'intorni", true, Color.CYAN));
        cards.add(new Card("Metro", null, "Tessera metro big chunk", false, Color.LTGRAY));
    }

    public ArrayList<Card> getExampleCards(){
        return cards;
    }
}
