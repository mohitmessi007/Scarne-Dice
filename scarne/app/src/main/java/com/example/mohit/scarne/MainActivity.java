package com.example.mohit.scarne;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private int uS,uTS,cS,cTS;

    private ImageView diceImage;
    private Random random;
    private boolean userTurn=true;

    private Button rollButton,holdButton,resetButton;
    private TextView status, scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uS = 0;
        uTS = 0;
        cS = 0;
        cTS = 0;
        random = new Random();
        scoreTextView = (TextView) findViewById(R.id.score);
        status = (TextView) findViewById(R.id.textview1);
        rollButton = (Button) findViewById(R.id.roll);
        holdButton = (Button) findViewById(R.id.hold);
        resetButton = (Button) findViewById(R.id.reset);
        diceImage = (ImageView) findViewById(R.id.dice);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setText("");
                int num = rollDice();
                holdButton.setEnabled(true);
                if (num == 1) {
                    uTS = 0;
                    userTurn = false;
                    displayScore("You rolled a 1! So Computer's Turn\n");
                    computerTurn();
                } else {
                    uTS += num;
                    displayScore("User's Turn to play\n");
                }


            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setText("");
                uS += uTS;
                userTurn = false;
                displayScore("User Holds! Computer's Turn to play\n");
                uTS = 0;
                computerTurn();

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
    private int rollDice()
    {
        int number = random.nextInt(6)+1;
        setDice(number);
        Log.d("Roll Dice Value:",Integer.toString(number));
        return number;
    }

    private void computerTurn()
    {
        if(userTurn)return;
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        final Handler handler = new Handler();
        cTS=0;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(cTS<=20)
                {
                    int num=rollDice();
                    if(num!=1) //Reroll dice
                    {
                        cTS+=num;
                        displayScore("Computer's turn to play\n");
                        handler.postDelayed(this,1000);
                    }
                    else
                    {
                        cTS=0;
                        displayScore("Computer rolled a one! User's Turn to play\n");
                        userTurn=true;
                        rollButton.setEnabled(true);
                        holdButton.setEnabled(false);
                    }
                }
                else
                {
                    cS+=cTS;
                    userTurn=true;
                    displayScore("Computer holds,User's turn to play\n");
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(false);
                }
            }
        };
        handler.postDelayed(r,1000);
    }

    private void displayScore(String turnLabel) {

        if(userTurn == true)
        {

            scoreTextView.setText("User Score : "+uS+" Computer Score : " +cS+ " Your Turn Score : "+uTS);
            status.setText(turnLabel);

        }
        else
        {
            scoreTextView.setText("User Score : " +uS+ " Computer Score : "+cS+" Computer Turn Score : "+cTS);
            status.setText(turnLabel);
        }

        if (uS>=100)
        {
            System.out.print("hola");
            turnLabel = "Game over!! You win with score :" +uS;
            status.setText(turnLabel);
            scoreTextView.setText("");
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            diceImage.setImageResource(R.drawable.happy);
        }
        else if(cS>= 100)
        {
            turnLabel = "Game Over!! Computer Wins with score:" +cS;
            status.setText(turnLabel);
            scoreTextView.setText("");
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            diceImage.setImageResource(R.drawable.sad);
        }
    }
    public void setDice(int roll)
    {
        int diceImage1;
        switch(roll)
        {
            case 1:diceImage1 = R.drawable.dice1;break;
            case 2:diceImage1 = R.drawable.dice2;break;
            case 3:diceImage1 = R.drawable.dice3;break;
            case 4:diceImage1 = R.drawable.dice4;break;
            case 5:diceImage1 = R.drawable.dice5;break;
            case 6:diceImage1 = R.drawable.dice6;break;
            default:diceImage1 = R.drawable.dice1;
        }
        diceImage.setImageResource(diceImage1);
    }
}
