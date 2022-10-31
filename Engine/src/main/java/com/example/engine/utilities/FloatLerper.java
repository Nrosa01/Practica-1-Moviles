package com.example.engine.utilities;

public class FloatLerper {
    private float startValue;
    private float endValue;
    private float duration;
    private float currentTime;
    private LerpType lerpType;
    private boolean reversed = false;
    private float currentValue;
    private boolean isPaused = false;

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
        this.reversed = false;
    }

    public void update(double deltaTime) {
        if (isPaused)
            return;

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

    public void restart() {
        currentTime = 0;
        isPaused = false;
    }

    public float getStartValue() {
        return this.startValue;
    }

    public float getEndValue() {
        return this.endValue;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public boolean isFinished() {
        return duration - currentTime < 0.01f;
    }

    public boolean getPaused()
    {
        return  isPaused;
    }

    public void reverse() {
        this.reversed = !this.reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public float getValue() {
        return this.currentValue;
    }

    public float getProgress() {
        return currentTime / duration;
    }
}
