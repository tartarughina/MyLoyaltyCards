package com.example.myloyaltycards;


import android.util.Log;

import java.util.ArrayList;

public class FidelityCards {
    private final String userName;
    private ArrayList<Card> cards;

    private static FidelityCards instance = null;

    public static FidelityCards getInstance(){
        /*
         * The constructor is called only if the static instance is null, so only the first time
         * that the getInstance() method is invoked.
         * All the other times the same instance object is returned.
         */
        if(instance == null)
            instance = new FidelityCards();
        return instance;
    }

    public FidelityCards(String userName, ArrayList<Card> cards) {
        this.userName = userName;
        this.cards = cards;
    }

    public FidelityCards(String userName) {
        this.userName = userName;
    }

    public FidelityCards() {
        this.userName = "";
        this.cards = new ArrayList<Card>();
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int size(){
        return cards.size();
    }

    public Card removeCard(int index) {
        if (index >= 0 && index < this.size()) {
            return cards.remove(index);
        }

        return null;
    }

    public void addCard(Card card){
        if(card != null){
            cards.add(card);
        }
    }

    @Override
    public String toString() {
        return "FidelityCards{" +
                "userName='" + userName + '\'' +
                ", cards=" + cards +
                '}';
    }
}
