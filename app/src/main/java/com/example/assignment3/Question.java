package com.example.assignment3;


import java.util.ArrayList;

public class Question {


    ArrayList<ArrayList<String>> quizArray = new ArrayList<>(1);

    public static String[][] quizData = {
            {"Toronto is capital city of Ontario.", "True"},
            {"Montreal is capital city of Quebec.", "True"},
            {"Reykjavik is the capital city of Iceland.", "True"},
            {"Manchester is the capital city of England.", "False"},
            {"Mumbai is the capital city of India.", "False"},
            {"Madrid is the capital city of Spain.", "True"},
            {"Vancouver is the capital city of British Columbia.", "False"},
            {"Sydney is the capital city of Australia", "False"},
            {"Rome is the capital city of Italy.", "True"},
            {"Marseille is the capital city of France.", "False"}
    };

    public Question()
    {
        quizArray = new ArrayList<>(1);
    }
}