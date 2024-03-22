package com.example.myloyaltycards;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;


public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context contetx;

    public CardAdapter(Context context){
        this.contetx = context;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        private View view = null;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDeleteConfirmation(getLayoutPosition());
                    return false;
                }
            });

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    openInfoCard(getLayoutPosition());
                }
            });
        }

        public void setCompanyText(String text){
            TextView v = (TextView) view.findViewById(R.id.companyTxt);
            v.setText(text);
        }

        public void setCompanyImage(Bitmap bitmap){
            ImageView v = (ImageView) view.findViewById(R.id.companyImage);

            if(bitmap != null) {
                v.setImageBitmap(bitmap);
            }
        }

        public void setBackColor(int color){
            LinearLayout v = (LinearLayout) view.findViewById(R.id.card_list_view);
            this.view.setBackgroundColor(color);
        }
    }

    private void openInfoCard(int position){
        Intent newIntent = new Intent(new Intent(contetx,InfoCardActivity.class));

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        newIntent.putExtras(bundle);

        contetx.startActivity(newIntent);
    }

    private void showDeleteConfirmation(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(contetx);
        builder.setMessage(R.string.deleteMessage);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                FidelityCards.getInstance().removeCard(position);
                notifyItemRemoved(position);
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_element, viewGroup, false);

        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CardViewHolder v = (CardViewHolder) viewHolder;

        Card app = FidelityCards.getInstance().getCards().get(i);

        v.setBackColor(app.getBackgroundColor());
        v.setCompanyImage(app.getLogo());
        v.setCompanyText(app.getCompanyName());
    }

    @Override
    public int getItemCount() {
        return FidelityCards.getInstance().size();
    }
}
