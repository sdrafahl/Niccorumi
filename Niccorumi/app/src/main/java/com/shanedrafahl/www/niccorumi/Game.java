package com.shanedrafahl.www.niccorumi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Display;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;

public class Game extends FragmentActivity {
    protected Card card_Fragment;
    protected Card card_2;
    protected ArrayList<Card> list;
    protected CardGame game;
    protected FragmentTransaction fragmentTransaction;
    protected float dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TextView vie = (TextView) findViewById(R.id.score);
        //vie.setText(game.getScore());

        setContentView(R.layout.content_game);
        Intent intent = getIntent();
        String message = intent.getAction();
        message.trim();
        int num = Integer.parseInt(message);
        int max=0;
        FragmentManager fragmentManager = getFragmentManager();
        this.fragmentTransaction = fragmentManager.beginTransaction();
        Random rl = new Random(new Random(6789).nextInt(1234567890)+987);
        int maxL=0;
        switch (num){
            case 1: maxL= rl.nextInt(5)+15;
                break;
            case 2: maxL= rl.nextInt(5)+10;
                break;
            case 3: maxL = rl.nextInt(5)+5;
                break;
        }
        this.dp = getResources().getDisplayMetrics().density;
        game = new CardGame(maxL);//starts the logical backend of the game
        this.list = game.getList();
        Card exa = game.getTopCard();
        exa.isTopCard=true;
        exa.height=(int) dp*130;
        exa.width=(int) dp*300;
        fragmentTransaction.add(R.id.topCard,game.getTopCard());
        for (int cou = 0; cou < list.size(); cou++) {
            Card c = list.get(cou);
            c.height=(int) dp*130;
            c.width=(int) dp*(750/(game.getRound()+3));
            fragmentTransaction.add(R.id.trueGameContainer, c);
        }
        fragmentTransaction.commit();



    }
    public void getTopCard(View v){
        Card c = game.getTopCard();
        if(c.selected){
            View view = c.getView();
            c.selected=false;
            view.setY(view.getY()-30f);
        }else{
            View view = c.getView();
            c.selected=true;
            view.setY(view.getY() + 30f);

        }
    }
    public void getFaceUp(View v){
        if(game.getMaxPasses()<game.getPasses()){
            return;
        }
        Card faceUp = game.getTopCard();
        int counter=0;
        Card selected=null;
        for(int x=0;x<list.size();x++){
            if(list.get(x).selected){
                counter++;
                selected=list.get(x);
            }
        }
        if(counter>1){
            return;
        }
        if(counter==0){
            return;
        }
        if(counter==1){
            FragmentManager fz = getFragmentManager();
            FragmentTransaction ft = fz.beginTransaction();
            ft.remove(selected);
            Card newC = new Card();
            newC.value=faceUp.value;
            newC.suite=faceUp.suite;
            newC.height=(int)dp*130;
            newC.width=(int)dp*(750/(game.getRound()+3));
            list.add(newC);
            list.remove(selected);
            ft.add(R.id.trueGameContainer,newC);//this crashes
            int val = game.getRan(13)+1;
            int sui = game.getRan(4)+1;
            String suit=null;
            switch (sui){
                case 1: suit="clubs";
                    break;
                case 2: suit="hearts";
                    break;
                case 3: suit="diamonds";
                    break;
                case 4: suit="spades";
                    break;
            }
            Card newTop = new Card();

            newTop.suite=suit;
            newTop.value=val;
            newTop.isTopCard=true;

            newTop.height= (int)(130 * dp);//
            newTop.width=(int) dp * 300;
            game.setTopCard(newTop);
            ft.remove(faceUp);
            ft.add(R.id.topCard, newTop);
            ft.commit();
            game.upDatePasses();//here
        }
    }
    public void goOut(View v) {
        game.resetPasses();
        PriorityQueue<Node> prQ = new PriorityQueue<Node>();
        Collections.sort(list);
        ArrayList<ArrayList<Card>> sorted = new ArrayList<ArrayList<Card>>();
        Node wild = getWildCard();
        if(wild!=null && wild.score>0){
            prQ.add(wild);
        }
        /////////////////////////////////////////
        for(int x=0;x<list.size();x++){
            Card temp = list.get(x);
            int tempValue = temp.value;
            int counter=0;
            ArrayList<Card> set = new ArrayList<Card>();
            for(int g=0;g<list.size();g++){
                //set.clear();
                if(list.get(g).getValue()==tempValue && list.get(g).valid && list.get(g).validSelection){
                    set.add(list.get(g));
                    list.get(g).validSelection=false;
                }
            }
            if(set.size()>2){
                prQ.add(new Node(getCount(set),set));
            }else{
                for(int cv=0;cv<set.size();cv++){
                    set.get(cv).validSelection=true;
                }
            }
        }
        ///////////////////////////////////////Code Above Finds groups of cards with the same value
        for(int x=0;x<list.size();x++){
            Card sample = list.get(x);
            String suit = sample.suite;
            int search1= sample.getValue()-1;
            int search2 = sample.getValue()+1;
            ArrayList<Card> set = new ArrayList<Card>();
            set.add(sample);
            for(int y=0;y<list.size();y++){
                 if(list.get(y).suite.equals(suit) && list.get(y).value==search1 && list.get(y).validSelection){
                     list.get(y).validSelection=false;
                     search1=search1-1;
                     set.add(list.get(y));
                 }
                if(list.get(y).suite.equals(suit) && list.get(y).value==search2 && list.get(y).validSelection){
                    list.get(y).validSelection=false;
                    search2=search2+1;
                    set.add(list.get(y));
                }
            }
            Node newNode = new Node(getCount(set),set);
            if(set.size()>2){
                prQ.add(newNode);
            }else{
                for(int cv=0;cv<set.size();cv++){
                    set.get(cv).validSelection=true;
                }
            }
        }
        ////////////////////////////////////////////////////Code above finds series
        int count=0;
        while(prQ.peek()!=null){
            Node n = prQ.poll();
            if(checkValidity(n)){
                count = count+n.score;
            }
        }
        game.setScore(count + game.getScore());
        if(game.getRound()==10){
            Intent intent = new Intent(Game.this, scoreScreen.class);
            int example = game.getScore();
            String se = Integer.toString(example);
            intent.setAction(se);
            Game.this.startActivity(intent);
        }else{
            game.upDateRound();
            FragmentManager fz = getFragmentManager();
            FragmentTransaction ft = fz.beginTransaction();
            for(int w=0;w<list.size();w++){
                ft.remove(list.get(w));
            }
            game.newSet();
            this.list = game.getList();
            ft.replace(R.id.topCard, game.getTopCard());
            for (int cou = 0; cou < list.size(); cou++) {
                Card c = list.get(cou);
                c.width=900/(game.getRound()+3);
                c.width=(int) dp*c.width;
                c.height=(int) dp*130;
                ft.add(R.id.trueGameContainer, c);
            }
            ft.commit();
        }
        TextView vie = (TextView) findViewById(R.id.score);
        vie.setText(game.getScore()+" ");
    }


    public boolean checkValidity(Node n){
        ArrayList<Card> rt = n.list;
        boolean pass1=true;
        if(rt.size()<3){
            for(int vb=0;vb<rt.size();vb++){
                if(list.get(vb).getValue()!=game.getRound()+3){
                    return false;
                }
            }
        }
        boolean pass=true;
        for(int x=0;x<rt.size();x++){
            if(!rt.get(x).valid){
                pass=false;
                if(rt.get(x).value==game.getRound()+3){
                    if(n.list.size()>3){
                        n.score=n.score-(game.getRound()+3);
                        pass=true;
                    }
                }
            }else{
                rt.get(x).valid=false;
            }
        }
        return pass;
    }

    public Node getWildCard(){
        ArrayList<Card> li = new ArrayList<Card>();
        for(int x=0;x<list.size();x++){
            if(list.get(x).getValue()==game.getRound()+3){
                li.add(list.get(x));
            }
        }
        int num = li.size()*(game.getRound()+3);
        Node s = new Node(num,li);
        return s;
    }
    public int getCount(ArrayList<Card> samp){
        int count=0;
        for(int x=0;x<samp.size();x++){
            count=count+samp.get(x).getValue();
        }
        return count;
    }
    public void getRandom(View v){
        if(game.getPasses()>game.getMaxPasses()){
            return;
        }
        game.upDatePasses();
        boolean pass=false;
        for(int x=0;x<list.size();x++){
            if(list.get(x).selected){
                pass=true;
                FragmentManager fz = getFragmentManager();
                FragmentTransaction ft = fz.beginTransaction();

                ft.remove(game.getTopCard());
                Card newTop = new Card();
                int numa = game.getRan(13)+1;
                int su = game.getRan(4)+1;
                String suitel=null;
                switch (su){
                    case 1:suitel="hearts";
                        break;
                    case 2:suitel="spades";
                        break;
                    case 3: suitel="clubs";
                        break;
                    case 4: suitel="diamonds";
                        break;
                }
                newTop.suite=suitel;
                newTop.value=numa;
                newTop.width=game.getTopCard().width;
                newTop.height=game.getTopCard().height;
                newTop.isTopCard=true;
                game.setTopCard(newTop);
                ft.add(R.id.topCard, newTop);

                ft.remove(list.get(x));
                list.remove(list.get(x));
                Card newC = new Card();
                int num = game.getRan(13)+1;
                int suiteNum = game.getRan(4)+1;
                String suite=null;
                switch (suiteNum){
                    case 1:suite="hearts";
                        break;
                    case 2:suite="spades";
                        break;
                    case 3: suite="clubs";
                        break;
                    case 4: suite="diamonds";
                        break;
                }
                newC.setValue(num);
                newC.suite=suite;
                newC.width=(int) dp*(750/(game.getRound()+3));
                newC.height=(int) dp*130;
                View daf = newC.getView();
                newC.height=(int) dp*130;
                newC.width=(int) dp*(750/(game.getRound()+3));
                list.add(newC);
                ft.add(R.id.trueGameContainer,newC);
                ft.commit();

            }
        }
        if(!pass){
            return;
        }
    }

    public void onClick(final View v){
        Card top = game.getTopCard();
        View vie = top.getView();
        if(vie.equals(v)){
            if(top.selected){
                top.selected=false;
                vie.setY(vie.getY()-30f);
                return;
            }else{
                top.selected=true;
                vie.setY(vie.getY()+30f);
                return;
            }

        }
        for(int w=0;w<list.size();w++){
            if(list.get(w).getView().equals(v)){
                boolean se = list.get(w).selected;
                if(list.get(w).getValue()==game.getRound()+3){
                    PopupMenu newCardMenu = new PopupMenu(Game.this,v);
                    final MenuInflater inf = newCardMenu.getMenuInflater();
                    newCardMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.oneHearts:
                                    cardTrans("hearts",v,1);
                                    break;
                                case R.id.twoHearts:
                                    cardTrans("hearts",v,2);
                                    break;
                                case R.id.threeHearts:
                                    cardTrans("hearts",v,3);
                                    break;
                                case R.id.fourHearts:
                                    cardTrans("hearts",v,4);
                                    break;
                                case R.id.fiveHearts:
                                    cardTrans("hearts",v,5);
                                    break;
                                case R.id.sixHearts:
                                    cardTrans("hearts",v,6);
                                    break;
                                case R.id.sevenHearts:
                                    cardTrans("hearts",v,7);
                                    break;
                                case R.id.eightHearts:
                                    cardTrans("hearts",v,8);
                                    break;
                                case R.id.nineHearts:
                                    cardTrans("hearts",v,9);
                                    break;
                                case R.id.tenHearts:
                                    cardTrans("hearts",v,10);
                                    break;
                                case R.id.jackHearts:
                                    cardTrans("hearts",v,11);
                                    break;
                                case R.id.queenHearts:
                                    cardTrans("hearts",v,12);
                                    break;
                                case R.id.kingHearts:
                                    cardTrans("hearts",v,13);
                                    break;
                                case R.id.oneClubs:
                                    cardTrans("clubs",v,1);
                                    break;
                                case R.id.twoClubs:
                                    cardTrans("clubs",v,2);
                                    break;
                                case R.id.threeClubs:
                                    cardTrans("clubs",v,3);
                                    break;
                                case R.id.fourClubs:
                                    cardTrans("clubs",v,4);
                                    break;
                                case R.id.fiveClubs:
                                    cardTrans("clubs",v,5);
                                    break;
                                case R.id.sixClubs:
                                    cardTrans("clubs",v,6);
                                    break;
                                case R.id.sevenClubs:
                                    cardTrans("clubs",v,7);
                                    break;
                                case R.id.eightClubs:
                                    cardTrans("clubs",v,8);
                                    break;
                                case R.id.nineClubs:
                                    cardTrans("clubs",v,9);
                                    break;
                                case R.id.tenClubs:
                                    cardTrans("clubs",v,10);
                                    break;
                                case R.id.jackClubs:
                                    cardTrans("clubs",v,11);
                                    break;
                                case R.id.queenClubs:
                                    cardTrans("clubs",v,12);
                                    break;
                                case R.id.kingClubs:
                                    cardTrans("clubs",v,13);
                                    break;
                                case R.id.oneSpades:
                                    cardTrans("spades",v,1);
                                    break;
                                case R.id.twoSpades:
                                    cardTrans("spades",v,2);
                                    break;
                                case R.id.threeSpades:
                                    cardTrans("spades",v,3);
                                    break;
                                case R.id.fourSpades:
                                    cardTrans("spades",v,4);
                                    break;
                                case R.id.fiveSpades:
                                    cardTrans("spades",v,5);
                                    break;
                                case R.id.sixSpades:
                                    cardTrans("spades",v,6);
                                    break;
                                case R.id.sevenSpades:
                                    cardTrans("spades",v,7);
                                    break;
                                case R.id.eightSpades:
                                    cardTrans("spades",v,8);
                                    break;
                                case R.id.nineSpades:
                                    cardTrans("spades",v,9);
                                    break;
                                case R.id.tenSpades:
                                    cardTrans("spades",v,10);
                                    break;
                                case R.id.jackSpades:
                                    cardTrans("spades",v,11);
                                    break;
                                case R.id.queenSpades:
                                    cardTrans("spades",v,12);
                                    break;
                                case R.id.kingSpades:
                                    cardTrans("spades",v,13);
                                    break;
                                case R.id.oneDiamonds:
                                    cardTrans("diamonds",v,1);
                                    break;
                                case R.id.twoDiamonds:
                                    cardTrans("diamonds",v,2);
                                    break;
                                case R.id.threeDiamonds:
                                    cardTrans("diamonds",v,3);
                                    break;
                                case R.id.fourDiamonds:
                                    cardTrans("diamonds",v,4);
                                    break;
                                case R.id.fiveDiamonds:
                                    cardTrans("diamonds",v,5);
                                    break;
                                case R.id.sixDiamonds:
                                    cardTrans("diamonds",v,6);
                                    break;
                                case R.id.sevenDiamonds:
                                    cardTrans("diamonds",v,7);
                                    break;
                                case R.id.eightDiamonds:
                                    cardTrans("diamonds",v,8);
                                    break;
                                case R.id.nineDiamonds:
                                    cardTrans("diamonds",v,9);
                                    break;
                                case R.id.tenDiamonds:
                                    cardTrans("diamonds",v,10);
                                    break;
                                case R.id.jackDiamonds:
                                    cardTrans("diamonds",v,11);
                                    break;
                                case R.id.queenDiamonds:
                                    cardTrans("diamonds",v,12);
                                    break;
                                case R.id.kingDiamonds:
                                    cardTrans("diamonds",v,13);
                                    break;
                            }
                            return true;
                        }
                    });
                    inf.inflate(R.menu.newcard, newCardMenu.getMenu());
                    newCardMenu.show();
                }
                if(se){
                    v.setY(v.getY()+30f);
                    list.get(w).selected=false;
                    return;
                }else{
                    v.setY(v.getY()-30f);
                    list.get(w).selected=true;
                    return;
                }
            }
        }

    }
    private void cardTrans(String su,View vk,int value){
        FragmentManager fz = getFragmentManager();
        FragmentTransaction ft = fz.beginTransaction();
        ft.remove(game.getCard(vk));
        list.remove(game.getCard(vk));
        Card n = new Card();
        n.setValue(value);
        n.suite=su;
        n.height=(int) dp*130;

        n.width=(int) dp*(750/(game.getRound()+3));
        list.add(n);
        ft.add(R.id.trueGameContainer,n);
        ft.commit();
    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.content_game);
    }
    class Node implements Comparable<Node>{
        public ArrayList<Card> list;
        public int score;
        public Node(int num, ArrayList<Card> combos){
            score=num;
            list = new ArrayList<Card>();
        }

        @Override
        public int compareTo(Node another) {
            return score - another.score;
        }
    }

}

