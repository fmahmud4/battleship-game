package edu.gmu.cs477.project1_fmahmud4;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import edu.gmu.cs477.project1_fmahmud4.Fragments.GameFragment;

public class NPCBoardAdapter extends RecyclerView.Adapter<NPCBoardAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Bitmap> arrBms;
    private ArrayList<Bitmap> arrBms2;
    private Bitmap bmHit, bmMiss;
    private int scorePlayer, scoreNPC;
    private boolean repeatShot = false;
    final private static int BOARDSIZE = 64;
    private ArrayList<Integer> boardList;
    Random rand;



    public NPCBoardAdapter(Context context, ArrayList<Bitmap> arrBms, ArrayList<Bitmap> arrBms2) {
        this.context = context;
        this.arrBms = arrBms;
        this.arrBms2 = arrBms2;
        bmHit = BitmapFactory.decodeResource(context.getResources(), R.drawable.x);
        bmMiss = BitmapFactory.decodeResource(context.getResources(), R.drawable.o);
        scorePlayer = 17;
        scoreNPC = 17;
        boardList = new ArrayList<>();
        for (int i = 0 ; i < BOARDSIZE; i++) {
            boardList.add(i);
        }
        rand = new Random();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.img_cell_board.setImageBitmap(arrBms.get(position));
        holder.img_cell_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkScore()) {
                    if (arrBms.get(position) == null) {
                        arrBms.set(position, bmMiss);

                        repeatShot = false;
                    } else if (arrBms.get(position) == bmMiss || arrBms.get(position) == bmHit) {
                        repeatShot = true;
                    } else {
                        arrBms.set(position, bmHit);
                        repeatShot = false;
                        scorePlayer--;
                        checkScore();
                    }
                    notifyItemChanged(position);
                    if(!repeatShot) {
                        int idx = rand.nextInt(boardList.size());
                        int pos = boardList.get(idx);
                        if (arrBms2.get(pos) == null ) {
                            arrBms2.set(pos, bmMiss);
                        } else {
                            arrBms2.set(pos, bmHit);
                            scoreNPC--;
                            checkScore();
                        }

                        boardList.remove(idx);

                        PlayerBoardAdapter PlayerBoardAdapter = new PlayerBoardAdapter(view.getContext(), arrBms2);
                        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(view.getContext(), 8);
                        GameFragment.rv_board1.setLayoutManager(layoutManager1);
                        GameFragment.rv_board1.setAdapter(PlayerBoardAdapter);
                    }

                }

            }
        });

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

    private boolean checkScore() {
        if (scorePlayer == 0) {
            GameFragment.txt_turn.setText("All ships destroyed");
            //GameFragment.rl_win.setAnimation(anim_win);
            GameFragment.rl_win.setVisibility(View.VISIBLE);
            GameFragment.gameOver = true;
            return true;
        } else if (scoreNPC == 0) {
            GameFragment.txt_turn.setText("All ships destroyed");
            GameFragment.txt_win.setText("CPU win :(");
            GameFragment.rl_win.setVisibility(View.VISIBLE);
            GameFragment.gameOver = true;
        }
        return false;
    }
}
