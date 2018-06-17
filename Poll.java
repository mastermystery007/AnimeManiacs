package com.doodlz.husain.animemaniacs;

import android.util.Log;

/**
 * Created by husain on 08-02-2018.
 */

public class Poll {

    private String pollQuestion;
    private String animeName;
    private String userName;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int op1votes,op2votes,op3votes,op4votes;


    public Poll(){}


    public Poll(String poll_question, String anime_name, String user_name, String option1, String option2, String option3, String option4,int op1votes,int op2votes,int op3votes,int op4votes) {
        this.pollQuestion = poll_question;
        this.animeName = anime_name;
        this.userName = user_name;
        this.option1  = option1;
        this.option2  = option2;
        this.option3  = option3;
        this.option4  = option4;
        this.op1votes = op1votes;
        this.op2votes = op2votes;
        this.op3votes = op3votes;
        this.op4votes = op4votes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }


    public int getOp1votes() {
        return op1votes;
    }

    public void setOp1votes(int op1votes) {
        this.op1votes = op1votes;
    }

    public int getOp2votes() {
        return op2votes;
    }

    public void setOp2votes(int op2votes) {
        this.op2votes = op2votes;
    }

    public int getOp3votes() {
        return op3votes;
    }

    public void setOp3votes(int op3votes) {
        this.op3votes = op3votes;
    }

    public int getOp4votes() {
        return op4votes;
    }

    public void setOp4votes(int op4votes) {
        this.op4votes = op4votes;
    }

    public void getDetails(){
        Log.d("ViewPollBoiz","data of poll is"+this.getOption1()+this.getOption2()+this.getOption3()+this.getOption4());
    }
}
