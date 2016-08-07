package com.shanedrafahl.www.niccorumi;

import android.net.Uri;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.Date;

/**
 * Created by shanedrafahl on 7/20/16.
 */
public class CardGame {
    private Card topCard;
    private int passes;
    public boolean goOut;
    private int maxPasses;
    private int score;
    private int diff;
    private int round;
    private Hand_Of_Card hand_of_card;
    private Random random;


    public CardGame(int maxPasses) {
        this.maxPasses=maxPasses;
        passes=0;
        goOut=false;
        topCard = null;
        score = 0;
        round = 0;
        hand_of_card = new Hand_Of_Card();
        Date d = new Date();
        random = new Random(d.getTime() + new Random(1984).nextInt(99));
        gameStart();
    }

    private void gameStart() {
        String suite = null;
        topCard = new Card();
        int num1 = random.nextInt(4) + 1;

        switch (num1) {
            case 1:
                topCard.suite = "spades";
                break;
            case 2:
                topCard.suite = "hearts";
                break;
            case 3:
                topCard.suite = "clubs";
                break;
            case 4:
                topCard.suite = "diamonds";
                break;
        }

        topCard.value = random.nextInt(13) + 1;
        for (int x7 = 0; x7 < this.round + 3; x7++) {
            int num = random.nextInt(4) + 1;
            switch (num) {
                case 1:
                    suite = "spades";
                    break;
                case 2:
                    suite = "hearts";
                    break;
                case 3:
                    suite = "clubs";
                    break;
                case 4:
                    suite = "diamonds";
                    break;
            }
            hand_of_card.addCard(random.nextInt(13) + 1, suite);// diamonds,hearts,spades,clubs
        }


    }


    public Card getCard(View v){
        ArrayList<Card> li = hand_of_card.getList();
        for(int x=0;x<li.size();x++){
            if(li.get(x).getView().equals(v)){
                return li.get(x);
            }
        }
        return null;
    }
    public Card getTopCard() {
        return this.topCard;
    }
    public void setMaxPasses(int n){
        this.maxPasses=n;
    }
    public void resetPasses(){
        passes=0;
    }
    public int getPasses(){
        return this.passes;
    }
    public void upDatePasses(){
        this.passes++;
    }
    public int getMaxPasses(){
        return this.maxPasses;
    }
    public int getDifficulty() {
        return this.diff;
    }

    public int getRound() {
        return round;
    }

    public ArrayList<Card> getList() {
        return hand_of_card.getList();
    }

    public int getRan(int num){
        return random.nextInt(num);
    }
    public void setScore(int num){
        this.score=num;
    }
    public void upDateRound(){
        this.round=this.round+1;
    }
    public void setTopCard(Card c){
        this.topCard=c;
    }
    public void newSet(){
        hand_of_card.resetCards();
        for(int x=0;x<this.round+3;x++){
            int randomNum = random.nextInt(4);
            String suite=null;
            switch (randomNum){
                case 0: suite="hearts";
                    break;
                case 1:suite="clubs";
                    break;
                case 2: suite="spades";
                    break;
                case 3: suite="diamonds";
                    break;
            }
            hand_of_card.addCard(random.nextInt(13)+1,suite);
            int exp = random.nextInt(4);
            String suo = null;
            switch ((exp)){
                case 0: suo="hearts";
                    break;
                case 1:suo="clubs";
                    break;
                case 2: suo="spades";
                    break;
                case 3: suo="diamonds";
                    break;
            }
            topCard= new Card();
            topCard.height=130;
            topCard.width=300;
            topCard.isTopCard=true;
            topCard.suite=suo;
            topCard.setValue(random.nextInt(13)+1);
        }
    }

    public int getScore(){
        return this.score;
    }


    class Hand_Of_Card {

        ArrayList<Card> list;


        public Hand_Of_Card() {
            list = new ArrayList<Card>();

        }

        public ArrayList<Card> getList() {
            return list;
        }



        public void resetCards(){
            list.clear();
        }
        public void addCard(int value, String suite) {
            Card card = new Card();
            card.suite = suite;
            card.setValue(value);
            list.add(card);
        }

    }


}
