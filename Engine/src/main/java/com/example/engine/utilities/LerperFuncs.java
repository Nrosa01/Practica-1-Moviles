package com.example.engine.utilities;

public class LerperFuncs {
    private static float Linear(float time)
    {
        return time;
    }

    private static float EaseIn(float time)
    {
        return time * time;
    }

    private static float EaseOut(float time)
    {
        return time * (2 - time);
    }

    private static float EaseInOut(float time)
    {
        return time < 0.5 ? 2 * time * time : -1 + (4 - 2 * time) * time;
    }

    public static float getValue(float time, LerpType lerpType)
    {
        switch (lerpType)
        {
            case EaseIn:
                return EaseIn(time);
            case EaseOut:
                return EaseOut(time);
            case EaseInOut:
                return EaseInOut(time);
            default:
                return Linear(time);
        }
    }
}
