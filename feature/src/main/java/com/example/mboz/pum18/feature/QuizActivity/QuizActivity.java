package com.example.mboz.pum18.feature.QuizActivity;

import android.os.Bundle;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.mboz.pum18.feature.*;
import org.apache.commons.lang3.ArrayUtils;

public class QuizActivity extends AppCompatActivity {
    Button mTrueButton;
    Button mFalseButton;
    Button mNextButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    Resources stringResources;
    int mCurrentIndex = 0;
    int mScore = 0;
    int mMaxScore;

    Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true),
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stringResources = getResources();

        mTrueButton = findViewById(R.id.yes_button);
        mFalseButton = findViewById(R.id.no_button);
        mNextButton = findViewById(R.id.next_button);
        mQuestionTextView = findViewById(R.id.question_text);
        mScoreTextView = findViewById(R.id.score_text);

        mMaxScore=mQuestionBank.length;
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        mScoreTextView.setText(stringResources.getString(R.string.score, mScore, mMaxScore));

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnsweredQuestion(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnsweredQuestion(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
                if(mQuestionBank.length > 0) {
                    mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
                }
            }
        });
    }

    void updateQuestion(){
        mCurrentIndex++;
        if(mCurrentIndex >= mQuestionBank.length){
            mCurrentIndex = 0;
        }
    }

    void checkAnswer(boolean userPressedTrue){
        if(mQuestionBank.length > 0){
            mScore = mScore + (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue? 1 : 0);
            mScoreTextView.setText(stringResources.getString(R.string.score, mScore, mMaxScore));
            mQuestionBank = ArrayUtils.remove(mQuestionBank, mCurrentIndex);
            mCurrentIndex = 0;
        }
    }

    void handleAnsweredQuestion(boolean answer){
        checkAnswer(answer);
        if(mQuestionBank.length > 0){
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        } else {
            mQuestionTextView.setText(R.string.end_text);
        }
    }
}
