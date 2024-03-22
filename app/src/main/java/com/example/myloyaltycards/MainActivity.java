package com.example.myloyaltycards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    private static int example = 0;

    private RecyclerView cardsList;
    private LinearLayoutManager layoutManager;
    private CardAdapter adapter;
    private Context context;
    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards_list);

        initUI();
    }

    private void initUI(){
        context = this;
        cardsList = (RecyclerView) findViewById(R.id.cardRecView);

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);

        cardsList.setLayoutManager(layoutManager);
        cardsList.setHasFixedSize(true);

        adapter = new CardAdapter(context);
        cardsList.setAdapter(adapter);

        setTitle("My cards");

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        addBtn = (FloatingActionButton) findViewById(R.id.addCardBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewCard();
            }
        });

        if(example == 1) {
            ArrayList<Card> app = new Example().getExampleCards();

            for (Card card : app) {
                FidelityCards.getInstance().addCard(card);
            }

            example++;
        }

        if(FidelityCards.getInstance().size() == 0){
            TextView text = (TextView) findViewById(R.id.noCardTxt);
            text.setVisibility(View.VISIBLE);
        }
    }

    private void openNewCard(){
        Intent newIntent = new Intent(new Intent(this,NewCardActivity.class));
        startActivity(newIntent);
    }
}
