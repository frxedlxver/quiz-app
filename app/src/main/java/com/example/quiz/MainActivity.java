package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nameEntry;
    private Toast invalidNameMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // locate views
        nameEntry = findViewById(R.id.editTextNameEntry);

        // locate button view and set listener
        findViewById(R.id.nameEntryConfirmBtn).setOnClickListener(nameEntryConfirmListener);

        // instantiate the toast object only once to avoid queueing multiple messages
        // if user repeatedly submits a blank name entry
        invalidNameMsg = Toast.makeText(
                getApplicationContext(),
                "Names must be at least one character.",
                Toast.LENGTH_SHORT);
    }

    public View.OnClickListener nameEntryConfirmListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String userName = String.valueOf(nameEntry.getText());


            if(userName.length() == 0) {

                // make screen shake
                findViewById(R.id.editTextNameEntry).startAnimation(
                        AnimationUtils.loadAnimation(getApplicationContext(), R.anim.screen_shake));

                invalidNameMsg.show();

            } else { // start quiz activity
                Intent quizIntent = new Intent(MainActivity.this, QuizActivity.class);

                // pass userName to next activity using intent
                quizIntent.putExtra("userName", userName);

                startActivity(quizIntent);
            }
        }
    };
}