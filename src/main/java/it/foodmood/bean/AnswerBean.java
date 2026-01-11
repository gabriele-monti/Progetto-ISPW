package it.foodmood.bean;

import java.util.HashSet;
import java.util.Set;

import it.foodmood.domain.value.StepType;

public class AnswerBean {
    private final StepType stepType;
    private final Set<String> answers;
    private Integer value;

    public AnswerBean(StepType stepType, Set<String> answers){
        this.stepType = stepType;
        this.answers = answers != null ? new HashSet<>(answers) : new HashSet<>();
        this.value = null;
    }

    public AnswerBean(StepType stepType, Integer value){
        this.stepType = stepType;
        this.answers = new HashSet<>();
        this.value = value;
    }

    public StepType getStepType(){
        return stepType;
    }

    public Set<String> getAnswers(){
        return new HashSet<>(answers);
    }

    public Integer getValue(){
        return value;
    }

    public void setValue(Integer value){
        this.value = value;
    }
}
