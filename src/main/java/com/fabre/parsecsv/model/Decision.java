package com.fabre.parsecsv.model;

import java.util.ArrayList;
import java.util.List;

public class Decision {

    private String id;
    private List<Integer> vars;
    private Integer decision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getVars() {
        return vars;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public void addVar(Integer var) {
        if (vars == null) {
            vars = new ArrayList<>();
        }

        vars.add(var);
    }

    public boolean checkRange(Integer max, Integer min) {
        return !vars.stream().anyMatch(var -> var > max || var < min);
    }
}
