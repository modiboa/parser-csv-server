package com.fabre.parsecsv.model;

import java.util.ArrayList;
import java.util.List;

public class DecisionSetup {

    private int max = -2147483647;
    private int min = 2147483647;

    private List<Decision> decisions;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public List<Decision> getDecisions() {
        return decisions;
    }

    public void addDecision(Decision decision) {
        if (decisions == null) {
            decisions = new ArrayList<>();
        }
        decisions.add(decision);
    }

}
