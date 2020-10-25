package com.ga.cardquiz;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    //This class generates a fixed list of questions using the Question object

    //    public static List<Question> getBrands() {
//        //return a list of questions
//        List<Question> QuestionList = new ArrayList<>();
//        QuestionList.add(new Question("what color is the sky ?", "blue","red","green","brown"));
//        return QuestionList;
//    }
    public static final Question[] questionList = {
            new Question("what color is the sky ?", "blue", "red", "green", "brown","blue",false),
            new Question("how many fingers is on a hand ?", "1", "2", "3", "5", "5",false),
            new Question("how many years is a president elected for ?", "1", "2", "4", "10","4", false),
    };
}