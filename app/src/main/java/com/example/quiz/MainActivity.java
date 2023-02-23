package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int QUIZ_ACTIVITY_REQ_CODE = 0;

    private String userName;
    private int score;

    private EditText nameEntry;
    private Button nameEntryConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEntry = findViewById(R.id.editTextNameEntry);

        nameEntryConfirmBtn = findViewById(R.id.nameEntryConfirmBtn);
        nameEntryConfirmBtn.setOnClickListener(nameEntryConfirmListener);
    }

    public View.OnClickListener nameEntryConfirmListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String userName = String.valueOf(nameEntry.getText());

            if(userName.length() == 0) {
                Toast.makeText(
                        getApplicationContext(),
                    "Names must be at least one character.",
                        Toast.LENGTH_LONG)
                        .show();
            } else {
                Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);
                quizIntent.putExtra("userName", userName);
                startActivity(quizIntent);
            }
        }
    };
}