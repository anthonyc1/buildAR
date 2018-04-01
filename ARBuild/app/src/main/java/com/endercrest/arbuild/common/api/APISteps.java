package com.endercrest.arbuild.common.api;

import java.util.List;

public class APISteps {

    List<APIStep> steps;

    public APISteps(List<APIStep> steps) {
        this.steps = steps;
    }

    public List<APIStep> getSteps() {
        return steps;
    }

    public APIStep getStep(int num) {
        return steps.get(num);
    }
}
