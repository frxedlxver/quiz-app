package com.example.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {


    private TextView userNameView;
    private TextView scoreView;
    private Button retryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        int score = getIntent().getIntExtra("score", 0);
        String userName = getIntent().getStringExtra("userName");

        // locate views
        retryButton = findViewById(R.id.retryBtn);
        userNameView = findViewById(R.id.resultsUserNameView);
        scoreView = findViewById(R.id.resultsScoreView);

        // set view values
        scoreView.setText(String.format(Locale.CANADA, "%d", score));
        userNameView.setText(userName);
        retryButton.setOnClickListener(retryListener);
    }

    View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
