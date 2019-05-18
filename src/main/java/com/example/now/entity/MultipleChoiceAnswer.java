package com.example.now.entity;


/**
 * Multiple choice answer entity class
 *
 * TODO : 待删
 * @author jjc
 * @date 2019/05/17
 */
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
