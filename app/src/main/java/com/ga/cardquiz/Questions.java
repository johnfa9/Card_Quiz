package com.ga.cardquiz;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    //This class generates a fixed list of questions using the Question object

    public static final Question[] questionList = {
            new Question("radio","What color is the sky ?", "blue",
                    "red", "green", "brown","blue",null,
                    false),
            new Question("radio","How many fingers is on a hand ?", "1",
                    "2", "3", "5","5", null,
                    false),
            new Question("radio", "How many years is a president elected for ?",
                    "1", "2", "4", "10","4",
                    null,
                    false),
            new Question("radio","How many wheels are on a car ?", "1",
                    "2", "4", "10","4", null,
                    false),
            new Question("checkbox",
                    "Which of the following are programming languages ?",
                    "Big Bird", "C++", "Java", "Superman",
                    "choiceB" + "choiceC", null,false)
    };
}