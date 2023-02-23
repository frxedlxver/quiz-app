package com.example.quiz;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private Quiz quiz;

    private int score;
    private int progress;
    private int questionTotal;


    private TextView questionView;
    private TextView scoreView;
    private TextView progressView;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quiz = new Quiz();
        quiz.initialize(this.getApplicationContext());

        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);
        answerButton1.setOnClickListener(answerButtonListener);
        answerButton2.setOnClickListener(answerButtonListener);
        answerButton3.setOnClickListener(answerButtonListener);
        answerButton4.setOnClickListener(answerButtonListener);

        questionView = findViewById(R.id.questionView);
        scoreView = findViewById(R.id.scoreView);
        progressView = findViewById(R.id.progressView);

        score = 0;
        progress = 0;
        questionTotal = quiz.getTotalQuestionCount();

        displayNextQuestion();
        updateStatsDisplay();
    }

    private void displayNextQuestion() {
        String authorName = quiz.getNextAnswer();
        ArrayList<String> answers = quiz.getChoicesFor(authorName);

        questionView.setText(authorName);
        answerButton1.setText(answers.get(0));
        answerButton2.setText(answers.get(1));
        answerButton3.setText(answers.get(2));
        answerButton4.setText(answers.get(3));
    }

    private void updateStatsDisplay() {
        scoreView.setText(String.format(Locale.CANADA,"SCORE: %d", score));
        progressView.setText(String.format(Locale.CANADA, "%d / %d", progress, questionTotal));
    }

    private void playFlashingColorAnimation(int flashColor) {
        int mainColor = getColor(R.color.white);


        // from https://stackoverflow.com/questions/2614545/animate-change-of-view-background-color-on-android
        ValueAnimator colorAnimation1 = ValueAnimator.ofObject(new ArgbEvaluator(), mainColor, flashColor);
        colorAnimation1.setDuration(250); // milliseconds
        colorAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                findViewById(R.id.questionDisplay).setBackgroundColor((int) animator.getAnimatedValue());
            }

        });

        ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), flashColor, mainColor);
        colorAnimation2.setDuration(250); // milliseconds
        colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                findViewById(R.id.questionDisplay).setBackgroundColor((int) animator.getAnimatedValue());
            }

        });

        colorAnimation1.start();
        colorAnimation2.start();
    }

    private void playIncorrectAnimation() {
        playFlashingColorAnimation(getColor(R.color.red));
    }

    private void playCorrectAnimation() {
        playFlashingColorAnimation(getColor(R.color.green));
    }

    View.OnClickListener answerButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String answer = ((Button) view).getText().toString();
            String question = questionView.getText().toString();

            if (quiz.correctAnswer(question, answer)) {
                playCorrectAnimation();
                score += 10;
            } else {
                playIncorrectAnimation();
            }

            progress += 1;

            updateStatsDisplay();

            if (progress < questionTotal ) {
                displayNextQuestion();
            } else {
                // package score and username in new intent
                Intent resultsIntent = new Intent(QuizActivity.this, ResultsActivity.class);
                resultsIntent.putExtra("score", score);
                resultsIntent.putExtra("userName", getIntent().getStringExtra("userName"));

                // start new results activity and kill this one
                startActivity(resultsIntent);
                finish();
            }

        }
    };
}
