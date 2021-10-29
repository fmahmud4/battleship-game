package edu.gmu.cs477.project1_fmahmud4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import edu.gmu.cs477.project1_fmahmud4.Fragments.GameFragment;

public class PlayerBoardAdapter extends RecyclerView.Adapter<PlayerBoardAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Bitmap> arrBms;
    private Bitmap bmHit, bmMiss;

    public PlayerBoardAdapter(Context context, ArrayList<Bitmap> arrBms) {
        this.context = context;
        this.arrBms = arrBms;
        bmHit = BitmapFactory.decodeResource(context.getResources(), R.drawable.x);
        bmMiss = BitmapFactory.decodeResource(context.getResources(), R.drawable.o);
    }

    @NonNull
    @Override
    public PlayerBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerBoardAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerBoardAdapter.ViewHolder holder, int position) {
        holder.img_cell_board.setImageBitmap(arrBms.get(position));
    }

    @Override
    public int getItemCount() {
        return arrBms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_cell_board;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cell_board = itemView.findViewById(R.id.img_cell_board);
        }
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
    }
}

