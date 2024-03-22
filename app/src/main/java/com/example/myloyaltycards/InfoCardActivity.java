package com.example.myloyaltycards;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;

import java.io.IOException;

public class InfoCardActivity extends AppCompatActivity {
    private ImageView logoImg;
    private ImageView codeImg;
    private TextView codeTxt;
    private TextView companyTxt;
    private ConstraintLayout cardLayout;
    private Card card;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_info);

        if (savedInstanceState == null) {

            int numberType = -1;

            Bundle bundle = this.getIntent().getExtras();
            if(bundle != null) {
                int position = (bundle.getInt("position", -1));
                card = FidelityCards.getInstance().getCards().get(position);
            }
            else{
                 card = null;
            }
        }

        if(card != null)
            initUI();
        else
            NavUtils.navigateUpFromSameTask(this);
    }

    private void initUI(){
        companyTxt = (TextView) findViewById(R.id.companyText);
        companyTxt.setText(card.getCompanyName());

        codeTxt = (TextView) findViewById(R.id.codeTxt);
        codeTxt.setText(card.getClientCode());

        logoImg = (ImageView) findViewById(R.id.logoImage);
        if(card.getLogo() != null) {
            logoImg.setImageBitmap(card.getLogo());
        }

        cardLayout = (ConstraintLayout) findViewById(R.id.card_info_layout);
        cardLayout.setBackgroundColor(card.getBackgroundColor());

        codeImg = (ImageView) findViewById(R.id.clientCodeImage);

        codeImg.measure(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        int width = codeImg.getMeasuredWidth();
        int height = codeImg.getMeasuredHeight();

        codeImg.setImageBitmap(card.generateCode(width, height));

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(card.getCompanyName() + " info");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
