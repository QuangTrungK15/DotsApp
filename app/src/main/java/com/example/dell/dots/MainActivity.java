package com.example.dell.dots;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;


public class MainActivity extends AppCompatActivity {


    LinearLayout layout;

    TextView txtTime,txtScore,txtLevel;
    ImageView imageDot,imageRedDot,life1,life2,life3;
    Random r = new Random();
    CountDownTimer countDownTimer;
    private int time;
    private int score;
    private int level=0;
    private int life=3;
    private int timeLevel=3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialization();
        DrawDots();
    }

    private void DrawDots() {

        InforBegin();


        imageDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartCountDown(false);
                HandingScore();
                HandingLevel();
                HandingTouch();



            }
        });


        imageRedDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer loseplayer2= MediaPlayer.create(MainActivity.this,R.raw.laugh);
                loseplayer2.start();


                if(life==1)
                {
                    life1.setImageResource(R.drawable.brokenheart);
                }
                else if(life==2)
                {
                    life2.setImageResource(R.drawable.brokenheart);
                }
                else if(life==3)
                {
                    life3.setImageResource(R.drawable.brokenheart);
                }
                if(life==0)
                    GameOver();
                life--;
            }
        });




    }


    private void Initialization()
    {
        imageRedDot = findViewById(R.id.imageRedDot);

        layout = findViewById(R.id.layoutID);


        txtTime = findViewById(R.id.txtTime);
        imageDot = findViewById(R.id.imageDot);
        txtScore = findViewById(R.id.txtScore);
        txtLevel = findViewById(R.id.txtLevel);


        imageDot.setX(100);
        imageDot.setY(100);

        imageDot.getLayoutParams().height = 100;
        imageDot.getLayoutParams().width = 100;


        imageRedDot.setX(1000);
        imageRedDot.setY(1000);
        imageRedDot.getLayoutParams().height = 100;
        imageRedDot.getLayoutParams().width = 100;


        life1 = findViewById(R.id.life1);
        life2 = findViewById(R.id.life2);
        life3 = findViewById(R.id.life3);



    }



    private void StartCountDown(boolean flag) {
        if (flag == true) {
            time = timeLevel;
            countDownTimer = new CountDownTimer(time * 1000, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txtTime.setText("" + ((millisUntilFinished / 1)-1));

                }

                @Override
                public void onFinish() {
                    CheckLife();
                    life--;
                    RandomBlue();
                    RandomRed(level);
                    StartCountDown(false);
                }

            };
            countDownTimer.start();

        }
        else if(flag==false)
        {
            countDownTimer.cancel();
            time = timeLevel;
            countDownTimer = new CountDownTimer(time * 1000, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txtTime.setText("" + ((millisUntilFinished / 1)-1));
                }

                @Override
                public void onFinish() {
                    CheckLife();
                    life--;
                    RandomBlue();
                    RandomRed(level);
                    StartCountDown(false);

                }

            };
            countDownTimer.start();
        }
    }



    private void HandingLevel()
    {
        if(score%10==0) {
            level++;
            txtLevel.setText(String.valueOf(level));
            imageDot.getLayoutParams().height = imageDot.getLayoutParams().height-10;
            imageDot.getLayoutParams().width = imageDot.getLayoutParams().width-10;
        }


        if(level>=5)
        {
            RandomRed(level);
            timeLevel=2;
        }






    }

    private void HandingScore()
    {
        score++;
        txtScore.setText(String.valueOf(score));
    }


    private  void HandingTouch()
    {
        RandomBlue();
    }


    private void GameOver()
    {
        countDownTimer.cancel();

        Toast.makeText(MainActivity.this,"Game Over :(",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Game over , Do you play again?\nHigh Score : "+score);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                countDownTimer.cancel();
                time=3;
                score=0;
                level=0;
                life=3;
                life1.setImageResource(R.drawable.heart);
                life2.setImageResource(R.drawable.heart);
                life3.setImageResource(R.drawable.heart);
                StartCountDown(true);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(MainActivity.this,MainGame.class);
                startActivity(i);
                MainActivity.this.finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void RandomBlue()
    {
        int x = r.nextInt(600+1);   //((600-0) + 1)+0
        int y = r.nextInt((800 - 300)+1) + 300;

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade);
        imageDot.startAnimation(animation1);


        imageDot.setX(x);
        imageDot.setY(y);



        //Toast.makeText(MainActivity.this,""+imageDot.getX()+"-----"+imageDot.getY(),Toast.LENGTH_SHORT).show();
        imageDot.requestLayout();
    }

    private void RandomRed(int level) {
        if (level >= 5) {
            int x = r.nextInt(600 + 1);   //((600-0) + 1)+0
            int y = r.nextInt((800 - 300) + 1) + 300;

            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade);
            imageRedDot.startAnimation(animation1);


            imageRedDot.setX(x);
            imageRedDot.setY(y);


            //Toast.makeText(MainActivity.this,""+imageDot.getX()+"-----"+imageDot.getY(),Toast.LENGTH_SHORT).show();
            imageRedDot.requestLayout();
        }
    }

    private void CheckLife()
    {
        if(life==1)
        {
            life1.setImageResource(R.drawable.brokenheart);
        }
        else if(life==2)
        {
            life2.setImageResource(R.drawable.brokenheart);
        }
        else if(life==3)
        {
            life3.setImageResource(R.drawable.brokenheart);
        }
        if(life==0) {
            GameOver();
        }
    }



    private void InforBegin()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you ready?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                StartCountDown(true);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(MainActivity.this,MainGame.class);
                startActivity(i);
                MainActivity.this.finish();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }






}
