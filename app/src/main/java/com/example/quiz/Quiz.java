package com.example.quiz;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Quiz {

    private static final String INFILE_NAME = "quizContent.txt";
    private static final int TITLE_IDX = 0;
    private static final int AUTHOR_IDX = 1;
    private static final String TXT_FILE_DELIMITER = "\\$";

    private final ArrayList<String> questions;
    private final ArrayList<String> answersMasterList;
    private final ArrayList<String> answers;
    private final HashMap<String, String> answerKey;

    public Quiz() {
        this.answersMasterList = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.answerKey = new HashMap<>();
    }


    // build lists and answerKey from file if answerKey is empty
    // else, rebuild answers as copy of answersMasterList
    public void initialize(Context c) {
        try {

            BufferedReader quizContentReader = new BufferedReader(
                    new InputStreamReader(
                    c.getAssets().open(INFILE_NAME)));

            String line = quizContentReader.readLine();

            while(line != null) {
                String[] lineArr = line.split(TXT_FILE_DELIMITER);

                questions.add(lineArr[TITLE_IDX]);
                answersMasterList.add(lineArr[AUTHOR_IDX]);
                answers.add(lineArr[AUTHOR_IDX]);
                answerKey.put(lineArr[AUTHOR_IDX], lineArr[TITLE_IDX]);

                line = quizContentReader.readLine();
            }

            quizContentReader.close();

            Collections.shuffle(this.answers);
            Collections.shuffle(this.questions);

        } catch (Exception e) {
            Log.e("QuizApp", "Exception", e);
        }
    }

    public String getNextAnswer() {
        int lastIdx = answers.size() - 1;
        String nextAuthor = answers.get(lastIdx);
        answers.remove(lastIdx);
        return nextAuthor;
    }

    public ArrayList<String> getChoicesFor(String author) {
        int choiceCount = 4;
        ArrayList<String> choices = new ArrayList<>(choiceCount);


        choices.add(answerKey.get(author));

        int i = 1;
        String temp;
        while (i < choiceCount) {
            temp = getRandomTitle();
            if(!choices.contains(temp)) {
                choices.add(temp);
                i++;
            }
        }

        // randomize order
        Collections.shuffle(choices);

        return choices;
    }

    public int getTotalQuestionCount() {
        return answersMasterList.size();
    }

    public boolean correctAnswer(String author, String title) {
        return Objects.requireNonNull(answerKey.get(author)).equals(title);
    }

    private String getRandomTitle() {
        Random r = new Random();
        return questions.get(r.ints(0, questions.size()).findFirst().getAsInt());
    }
}
