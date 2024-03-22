package com.example.myloyaltycards;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NavUtils;

import java.io.File;
import java.io.IOException;

import petrov.kristiyan.colorpicker.ColorPicker;

public class NewCardActivity extends AppCompatActivity {
    private ImageButton companyLogo;
    private Bitmap imageUri;
    private AppCompatEditText companyTxt;
    private AppCompatEditText codeTxt;
    private SwitchCompat codeQR;
    private boolean useQR;
    private Button colorBtn;
    private int backgroundColor;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_card);

        initUI();
    }

    private void initUI(){
        companyLogo = (ImageButton) findViewById(R.id.pickLogoImage);
        companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(NewCardActivity.this);
            }
        });

        companyTxt = (AppCompatEditText) findViewById(R.id.companyNameTxt);
        codeTxt = (AppCompatEditText) findViewById(R.id.clientCodeTxt);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Add a new card");

        imageUri = null;

        backgroundColor = Color.parseColor("#f84c44");

        colorBtn = (Button) findViewById(R.id.bckgroundColorBtn);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPicker(NewCardActivity.this)
                        .setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                            @Override
                            public void setOnFastChooseColorListener(int position, int color) {
                                backgroundColor = color;
                                colorBtn.setBackgroundColor(color);
                            }

                            @Override
                            public void onCancel() {

                            }
                        })
                        .setDefaultColorButton(backgroundColor)
                        .setColumns(5)
                        .setRoundColorButton(true).show();
            }
        });

        colorBtn.setBackgroundColor(backgroundColor);

        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    FidelityCards.getInstance().addCard(new Card(companyTxt.getText().toString(), imageUri, codeTxt.getText().toString(), useQR, backgroundColor));
                    NavUtils.navigateUpFromSameTask(NewCardActivity.this);
                }
                else{
                    Toast.makeText(NewCardActivity.this, "Insert at least a company name and a client code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        codeQR = (SwitchCompat) findViewById(R.id.QRCodeSwitch);
        codeQR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                useQR = isChecked;
            }
        });
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

    private boolean checkFields(){
        if(companyTxt.getText().toString().trim().length() == 0 && codeTxt.getText().toString().trim().length() == 0){
            return false;
        }

        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==  0 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            companyLogo.setImageURI(selectedImageUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imageUri = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURIForGallery(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, projection, null,
                null, null);
        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        assert false;
        cursor.close();
        return uri.getPath();
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {


                 if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                     startActivityForResult(Intent.createChooser(pickPhoto, "select image"), 0);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
