package com.example.now.entity;
import java.util.List;
public class QuestionDetail {
    private Question question;
    private List<Option> options;
    private List<Resource> resources;
    private List<OptionSelected> selecteds;
    private List<OpenAnswer> answers;
    private List<Integer> selectedCounts;
    public Question getQuestion() {
        return question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public List<OptionSelected> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<OptionSelected> selecteds) {
        this.selecteds = selecteds;
    }

    public List<OpenAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<OpenAnswer> answers) {
        this.answers = answers;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Integer> getSelectedCounts() {
        return selectedCounts;
    }

    public void setSelectedCounts(List<Integer> selectedCounts) {
        this.selectedCounts = selectedCounts;
    }

    public QuestionDetail() {
    }

    public QuestionDetail(Question question, List<Option> options) {
        this.question = question;
        this.options = options;
    }
}
