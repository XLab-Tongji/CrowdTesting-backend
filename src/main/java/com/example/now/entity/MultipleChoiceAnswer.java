package com.example.now.entity;
//TODO : 待删
public class MultipleChoiceAnswer {
    public Integer ans;
    public Integer index;

    public MultipleChoiceAnswer(){

    }
    public MultipleChoiceAnswer(Integer ans, Integer index) {
        this.ans = ans;
        this.index = index;
    }

    public Integer getAns() {
        return ans;
    }

    public void setAns(Integer ans) {
        this.ans = ans;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
