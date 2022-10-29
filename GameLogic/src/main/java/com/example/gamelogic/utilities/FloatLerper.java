package com.example.gamelogic.utilities;

public class FloatLerper {
    float startValue;
    float endValue;
    float duration;
    float currentTime;
    LerpType lerpType;
    boolean reversed = false;
    float currentValue;

    public FloatLerper(float startValue, float endValue, float duration) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.duration = duration;
        this.currentTime = 0;
        this.currentValue = startValue;
        this.lerpType = LerpType.Linear;
    }

    public FloatLerper(float startValue, float endValue, float duration, LerpType lerpType) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.duration = duration;
        this.currentTime = 0;
        this.currentValue = startValue;
        this.lerpType = lerpType;
    }

    public void update(float deltaTime) {
        if (!reversed)
            currentTime += deltaTime;
        else
            currentTime -= deltaTime;

        this.currentValue = calculateCurrentValue();
    }

    private float calculateCurrentValue() {
        float time = currentTime / duration;
        if (time > 1) {
            time = 1;
            currentTime = duration;
        } else if (time < 0) {
            time = 0;
            currentTime = 0;
        }

        return startValue + (endValue - startValue) * LerperFuncs.getValue(time, lerpType);
    }

    public void reverse()
    {
        this.reversed = !this.reversed;
    }

    public float getValue() {
        return this.currentValue;
    }
}
