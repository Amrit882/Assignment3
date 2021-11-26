package com.example.assignment3;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class FragmentData extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.question_fragment,container,false);
        TextView question = view.findViewById(R.id.questionArea);
        Bundle extras = getArguments();
        question.setText(extras.getString("question"));
        switch((int)((Math.random() * 4) + 1))
        {
            case 1:
                question.setBackgroundColor(Color.parseColor("#66FF75"));
                break;
            case 2:
                question.setBackgroundColor(Color.parseColor("#66FFFF"));
                break;
            case 3:
                question.setBackgroundColor(Color.parseColor("#FF66E5"));
                break;
            case 4:
                question.setBackgroundColor(Color.parseColor("#FF8C66"));
                break;
            default:
                question.setBackgroundColor(Color.parseColor("#FF0000"));
        }

        return view;
    }
}

