package edu.gmu.cs477.project1_fmahmud4.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import edu.gmu.cs477.project1_fmahmud4.NPCBoardAdapter;
import edu.gmu.cs477.project1_fmahmud4.PlayerBoardAdapter;
import edu.gmu.cs477.project1_fmahmud4.R;


public class GameFragment extends Fragment {
    final private static int ROWS = 8;
    final private static int COLS = 8;

    final private static int BOARDSIZE = 64;
    final private static int HORIZONTAL = 0;
    final private static int VERTICAL = 1;

    public static RecyclerView rv_board1, rv_board2;
    public static RelativeLayout rl_win;
    private PlayerBoardAdapter PlayerBoardAdapter;
    private NPCBoardAdapter NPCBoardAdapter;
    public static TextView txt_turn, txt_win;
    private Button btn_again, btn_quit;
    public static boolean gameOver;
    public static String TAG = GameFragment.class.getName();
    private static Bitmap bmHit, bmMiss, bmShip1, bmShip2, bmShip3, bmShip4, bmShip5;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        bmHit = BitmapFactory.decodeResource(getResources(), R.drawable.x);
        bmMiss = BitmapFactory.decodeResource(getResources(), R.drawable.o);
        bmShip1 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ship1);
        bmShip2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ship2);
        bmShip3 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ship3);
        bmShip4 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ship4);
        bmShip5 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ship5);
        btn_quit = view.findViewById(R.id.btn_quit);
        btn_again = view.findViewById(R.id.btn_again);
        rv_board1 = view.findViewById(R.id.rv_board1);
        rv_board2 = view.findViewById(R.id.rv_board2);
        rl_win = view.findViewById(R.id.rl_win);
        txt_turn = view.findViewById(R.id.txt_turn);
        txt_win = view.findViewById(R.id.txt_win);

        ArrayList<Bitmap> arrBms1 = new ArrayList<>();
        ArrayList<Bitmap> arrBms2 = new ArrayList<>();
        createBoard(arrBms1, arrBms2);

        
        PlayerBoardAdapter = new PlayerBoardAdapter(getContext(), arrBms1);
        NPCBoardAdapter = new NPCBoardAdapter(getContext(), arrBms2, arrBms1);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 8);
        RecyclerView.LayoutManager layoutManager2= new GridLayoutManager(getContext(), 8);


        rv_board1.setLayoutManager(layoutManager1);
        rv_board2.setLayoutManager(layoutManager2);
        rv_board1.setAdapter(PlayerBoardAdapter);
        rv_board2.setAdapter(NPCBoardAdapter);

        rv_board2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int pos = rand.nextInt(64);
                if (arrBms1.get(pos) == null) {
                    arrBms1.set(pos, bmMiss);
                    GameFragment.txt_turn.setText("CPU missed");
                } else if (arrBms1.get(pos) == bmMiss || arrBms1.get(pos) == bmHit) {
                    GameFragment.txt_turn.setText("You already shot here!");
                } else {
                    arrBms1.set(pos, bmHit);
                    GameFragment.txt_turn.setText("CPU got a hit!");
                }
                PlayerBoardAdapter = new PlayerBoardAdapter(getContext(), arrBms1);
                RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 8);
                rv_board1.setLayoutManager(layoutManager1);
                rv_board1.setAdapter(PlayerBoardAdapter);
            }
        });

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                getFragmentManager().popBackStack();
            }
        });

        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                System.exit(0);
            }
        });
        return view;
    }

    private void reset() {
        ArrayList<Bitmap> arrBms1 = new ArrayList<>();
        ArrayList<Bitmap> arrBms2 = new ArrayList<>();
        createBoard(arrBms1, arrBms2);
        txt_turn.setText("Choose a square");
        NPCBoardAdapter.setArrBms(arrBms1);
        NPCBoardAdapter.notifyDataSetChanged();
        PlayerBoardAdapter.setArrBms(arrBms2);
        PlayerBoardAdapter.notifyDataSetChanged();
    }

    public void createBoard(ArrayList<Bitmap> arrBms1, ArrayList<Bitmap> arrBms2) {


        //creates nonNull bitmap for opponent vector
        int w = 30, h = 20;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmWater = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap


        for (int i = 0; i < BOARDSIZE; i++) {
            arrBms1.add(null);
        }

        for (int i = 0; i < BOARDSIZE; i++) {
            arrBms2.add(null);
        }


        int[][] board1 = new int[8][8];
        ArrayList<Integer> coordinatesForPlayer1Ships = new ArrayList<>();
        getShipPlacement(board1, 5, 1, coordinatesForPlayer1Ships);
        getShipPlacement(board1, 4, 2, coordinatesForPlayer1Ships);
        getShipPlacement(board1, 3, 3, coordinatesForPlayer1Ships);
        getShipPlacement(board1, 3, 4, coordinatesForPlayer1Ships);
        getShipPlacement(board1, 2, 5, coordinatesForPlayer1Ships);

        int[][] board2 = new int[8][8];
        ArrayList<Integer> coordinatesForPlayer2Ships = new ArrayList<>();
        getShipPlacement(board2, 5, 1, coordinatesForPlayer2Ships);
        getShipPlacement(board2, 4, 2, coordinatesForPlayer2Ships);
        getShipPlacement(board2, 3, 3, coordinatesForPlayer2Ships);
        getShipPlacement(board2, 3, 4, coordinatesForPlayer2Ships);
        getShipPlacement(board2, 2, 5, coordinatesForPlayer2Ships);

        for (int i = 0; i < coordinatesForPlayer1Ships.size(); i++) {
            Bitmap bmCurrShip = bmShip1;

            if (i < 5) {
                bmCurrShip = bmShip1;
            } else if (i >= 5 && i < 9) {
                bmCurrShip = bmShip2;
            } else if (i >= 9 && i < 12) {
                bmCurrShip = bmShip3;
            } else if (i >= 12 && i < 15) {
                bmCurrShip = bmShip4;
            } else if (i >= 15) {
                bmCurrShip = bmShip5;
            }



            arrBms1.set(coordinatesForPlayer1Ships.get(i), bmCurrShip);
            arrBms2.set(coordinatesForPlayer2Ships.get(i), bmWater);
        }
    }

    //THIS IS THE PROFESSORS CODE
    public void getShipPlacement(int[][] board, int shipSize, int shipNum, ArrayList<Integer> coordinates) {
        Random rand = new Random();
        int r, c;
        Boolean placed = false;


        while (!placed) {
            int direction = rand.nextInt(2);

            if (direction == HORIZONTAL) {
                r = rand.nextInt(ROWS); // ROWS, COLS = 8
                c = rand.nextInt(COLS - shipSize);
            } else {
                r = rand.nextInt(ROWS - shipSize);
                c = rand.nextInt(COLS);
            }
            placed = true;
            // check to make sure all locations for the ship are free
            if (direction == HORIZONTAL) {
                for (int i = 0; i < shipSize; i++)
                    if (board[r][c + i] != 0) placed = false;
            } else {
                for (int i = 0; i < shipSize; i++)
                    if (board[r + i][c] != 0) placed = false;
            }
            // if all is ok, place the ship here.
            if (placed) {
                if (direction == HORIZONTAL) {
                    for (int i = 0; i < shipSize; i++) {
                        board[r][c + i] = shipNum;
                        int index = r + ((c + i) * COLS);
                        coordinates.add(index);
                    }

                } else {
                    for (int i = 0; i < shipSize; i++) {
                        board[r + i][c] = shipNum;
                        int index = (r + i) + (c * COLS);
                        coordinates.add(index);
                    }
                }
            }
        }
    }
}