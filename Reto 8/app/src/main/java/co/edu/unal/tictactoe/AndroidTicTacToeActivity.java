package co.edu.unal.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.harding.tictactoe.TicTacToeGame;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    // Various text displayed
    private TextView mInfoTextView;
    private TicTacToeGame mGame;
    private int human = 0;
    private int ties = 0;
    private int android = 0;
    private TextView mHumanTextView;
    private TextView mTiesTextView;
    private TextView mAndroidTextView;
    private BoardView mBoardView;
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;
    boolean offline = true;
    String username;

    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.button);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.click);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(offline) {
            // Save the current scores
            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putInt("mHumanWins", human);
            ed.putInt("mComputerWins", android);
            ed.putInt("mTies", ties);
            ed.apply();
        } else {

        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("");

        username = getIntent().getStringExtra("username");
        offline = username.length() == 0;

        levels = new CharSequence[]{
                getResources().getString(R.string.difficulty_easy),
                getResources().getString(R.string.difficulty_harder),
                getResources().getString(R.string.difficulty_expert)};

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (offline) {
            // Restore the scores
            human = mPrefs.getInt("mHumanWins", 0);
            android = mPrefs.getInt("mComputerWins", 0);
            ties = mPrefs.getInt("mTies", 0);
            difficulty = mPrefs.getInt("difficulty", 2);
        } else {

        }

        mGame = new TicTacToeGame();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);
        // Listen for touches on the board
        mBoardView.setOnTouchListener(mTouchListener);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanTextView = (TextView) findViewById(R.id.human);
        mTiesTextView = (TextView) findViewById(R.id.ties);
        mAndroidTextView = (TextView) findViewById(R.id.android);

        mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.valueOf(levels[difficulty].toString()));

        //
        mSoundOn = mPrefs.getBoolean("sound", true);
        String difficultyLevel = mPrefs.getString("difficulty_level", getResources().getString(R.string.difficulty_harder));
        if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
        else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
        else
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);

        mGame = new TicTacToeGame();
        if (savedInstanceState == null) {
            startNewGame();
        }

        Toast toast = Toast.makeText(this, !offline ? username + " vs. user1" : "Offline", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (offline) {
            mGame.setBoardState(savedInstanceState.getCharArray("board"));
//        mGameOver = savedInstanceState.getBoolean("mGameOver");
            mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
            human = savedInstanceState.getInt("mHumanWins");
            android = savedInstanceState.getInt("mComputerWins");
            ties = savedInstanceState.getInt("mTies");
            canH = savedInstanceState.getBoolean("canH");
            canC = savedInstanceState.getBoolean("canC");
        } else {

        }
        update();
    }

    // Set up the game board.
    private void startNewGame() {
        mBoardView.invalidate();
        if (offline) {
            mGame.reset();
            canH = true;
            canC = false;
            // Human goes first
            mInfoTextView.setText(R.string.first_human);
        } else {

        }
        update();
    }

    void update() {
        mHumanTextView.setText(String.valueOf(human));
        mTiesTextView.setText(String.valueOf(ties));
        mAndroidTextView.setText(String.valueOf(android));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    //    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_ABOUT_ID = 1;
    static final int DIALOG_QUIT_ID = 2;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.settings:
//                showDialog(DIALOG_DIFFICULTY_ID);
                startActivityForResult(new Intent(this, Settings.class), 0);
                return true;
            case R.id.reset:
                human = 0;
                ties = 0;
                android = 0;
                update();
                return true;
            case R.id.about:
                showDialog(DIALOG_ABOUT_ID);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }

    boolean mSoundOn = true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CANCELED) {
            // Apply potentially new settings
            mSoundOn = mPrefs.getBoolean("sound", true);
            String difficultyLevel = mPrefs.getString("difficulty_level", getResources().getString(R.string.difficulty_harder));
            if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
            else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
            else
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        }
    }

    int difficulty = 2;
    CharSequence[] levels = null;

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case DIALOG_ABOUT_ID:
                Context context = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.about_dialog, null);
                builder.setView(layout);
                builder.setPositiveButton("OK", null);
                dialog = builder.create();
                break;
            case DIALOG_QUIT_ID:
                // Create the quit confirmation dialog
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (offline) {
                                    SharedPreferences.Editor ed = mPrefs.edit();
                                    ed.putInt("mHumanWins", human);
                                    ed.putInt("mComputerWins", android);
                                    ed.putInt("mTies", ties);
                                    ed.apply();
                                } else {

                                }
                                AndroidTicTacToeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;
        }
        return dialog;
    }

    // Listen for touches on the board
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.v("TicTacToe", "COMPUTER");
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    if (mSoundOn)
                        mComputerMediaPlayer.start();
                }
                winner = mGame.checkForWinner();
                if (winner == 0)
                    mInfoTextView.setText(R.string.turn_human);
                else if (winner == 1) {
                    mInfoTextView.setText(R.string.result_tie);
                    ties++;
                    update();
                } else if (winner == 2) {
                    String defaultMessage = getResources().getString(R.string.result_human_wins);
                    mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
                    human++;
                    update();
                } else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    android++;
                    update();
                }
                canH = true;
                mBoardView.invalidate();
            }
        };

        Handler handlerC = new Handler();

        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int col = (int) event.getX() / mBoardView.getBoardCellWidth();
            int row = (int) event.getY() / mBoardView.getBoardCellHeight();
            final int pos = row * 3 + col;
            if (mGame.checkForWinner() == 0 && canH && setMove(TicTacToeGame.HUMAN_PLAYER, pos)) {
                Log.v("TicTacToe", "HUMAN");
                canH = false;
                if (mSoundOn)
                    mHumanMediaPlayer.start();
                canC = true;
                int winner = mGame.checkForWinner();
                if (winner == 1) {
                    mInfoTextView.setText(R.string.result_tie);
                    ties++;
                    update();
                } else if (winner == 2) {
                    String defaultMessage = getResources().getString(R.string.result_human_wins);
                    mInfoTextView.setText(mPrefs.getString("victory_message", defaultMessage));
                    human++;
                    update();
                } else {
                    handlerC.postDelayed(run, 1000);
                }
            }
            // So we aren't notified of continued events when finger is moved
            return false;
        }

        private boolean setMove(char player, int location) {
            if (mGame.setMove(player, location)) {
                mBoardView.invalidate();   // Redraw the board
                return true;
            }
            return false;
        }
    };

    boolean canH = true, canC = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (offline) {
            outState.putCharArray("board", mGame.getBoardState());
            outState.putInt("mHumanWins", human);
            outState.putInt("mComputerWins", android);
            outState.putInt("mTies", ties);
            outState.putCharSequence("info", mInfoTextView.getText());
            outState.putBoolean("canH", canH);
            outState.putBoolean("canC", canC);
        } else {

        }
    }
}



