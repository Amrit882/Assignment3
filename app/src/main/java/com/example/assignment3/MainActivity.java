package com.example.assignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity<quizData> extends AppCompatActivity {

    static final private int TOTAL_QUESTIONS = 10;

    Button trueB, falseB;
    ProgressBar progressBar;
    TextView question;

    private String rightAnswer;
    private int rightAnswers;
    private int quizCount = 1;
    private int progressCounter = 0;

    Question questions;
    StorageManager storageManager;

    ArrayList<ArrayList<String>> allQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questions = new Question();
        trueB = findViewById(R.id.trueButton);
        falseB = findViewById(R.id.falseButton);
        progressBar = findViewById(R.id.progressBar);
        question = (TextView) findViewById(R.id.questionArea);

        storageManager = ((myApp) getApplication()).getStorageManager();

        for (int i = 0; i < TOTAL_QUESTIONS; i++) {

            ArrayList<String> oneQ = new ArrayList<>();

            oneQ.add(Question.quizData[i][0]);
            oneQ.add(Question.quizData[i][1]);

            questions.quizArray.add(oneQ);
        }

        allQuestions = questions.quizArray;
        showNextQuestion();
    }

    public void showNextQuestion() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        FragmentData firstFragment = new FragmentData();

        Random random = new Random();

        int randomNum = random.nextInt(allQuestions.size());
        ArrayList<String> quiz = allQuestions.get(randomNum);

        Bundle bundle = new Bundle();
        bundle.putString("question", quiz.get(0));
        firstFragment.setArguments(bundle);

        rightAnswer = quiz.get(1);

        quiz.remove(0);

        Collections.shuffle(quiz);


        allQuestions.remove(randomNum);

        transaction.replace(R.id.questionFragment, firstFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    public void checkAnswer(View view) {
        progressCounter++;
        progressBar.setProgress(progressCounter);

        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        if (btnText.equals(rightAnswer)) {
            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
            rightAnswers++;

        }
        else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
        }
        if (quizCount == TOTAL_QUESTIONS) {
            showResult();
        }
        else {
            quizCount++;
            showNextQuestion();
        }
    }

    public void showResult(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage("Your Score is : " + rightAnswers + " out of 10");

        builder.setPositiveButton("Save", (dialog, which) -> {

            String history = storageManager.getHistory(MainActivity.this);
            String total = "";
            String correct = "";
            int totalQuestions = 0;
            int correctAnswers = 0;
            if(!history.isEmpty()) {
                for (int i = 0; i < history.length(); i++) {
                    if (history.toCharArray()[i] == '+') {
                        correct = history.substring(0, i);
                        total = history.substring(i + 1);
                    }
                }

                Log.d("total: ", total);
                Log.d("Correct: ", correct);
                totalQuestions = Integer.parseInt(total);
                correctAnswers = Integer.parseInt(correct);
                correctAnswers += rightAnswers;
                totalQuestions += TOTAL_QUESTIONS;

            }
            else{
                totalQuestions = TOTAL_QUESTIONS;
                correctAnswers = rightAnswers;
            }
            storageManager.saveHistory(MainActivity.this, totalQuestions, correctAnswers);
            reset();
        });

        builder.setNegativeButton("Ignore", (dialog, which) -> {reset();});
        builder.show();
    }

    public void reset()
    {
        progressCounter = 0;
        rightAnswers = 0;
        quizCount = 1;
        for (int i = 0; i < TOTAL_QUESTIONS; i++) {

            ArrayList<String> oneQ = new ArrayList<>();

            oneQ.add(Question.quizData[i][0]);
            oneQ.add(Question.quizData[i][1]);

            questions.quizArray.add(oneQ);
        }
        allQuestions = questions.quizArray;
        showNextQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.getAverage: {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                String history = storageManager.getHistory(MainActivity.this);
                String total = "";
                String correct = "";
                int totalQuestions;
                int correctAnswers;

                if(!history.isEmpty()) {
                    for (int i = 0; i < history.toCharArray().length; i++) {
                        if (history.toCharArray()[i] == '+') {
                            correct = history.substring(0, i);
                            total = history.substring(i + 1);
                        }
                    }

                    totalQuestions = Integer.parseInt(total);
                    correctAnswers = Integer.parseInt(correct);
                }
                else
                {
                    totalQuestions = 0;
                    correctAnswers = 0;
                }
                builder.setTitle("History");
                if(totalQuestions > 0) {
                    int allAttempts = totalQuestions/TOTAL_QUESTIONS;
                    builder.setMessage("Your average correct answers are " + (correctAnswers/allAttempts) + " in " + allAttempts);
                    builder.setPositiveButton("Save", (dialog, which) -> {
                        storageManager.saveHistory(MainActivity.this, totalQuestions, correctAnswers);
                    });
                }
                else
                    builder.setMessage("You need to attempt the quiz atleast once.");


                builder.setNegativeButton("Ok", (dialog, which) -> {});
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            }
            case R.id.reset: {
                storageManager.resetHistory(MainActivity.this);
                break;
            }
        }
        return true;
    }

}

