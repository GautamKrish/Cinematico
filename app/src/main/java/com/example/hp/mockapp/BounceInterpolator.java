package com.example.hp.mockapp;

import android.view.animation.Interpolator;

/**
 * Created by HP on 08-10-2017.
 */

public class BounceInterpolator implements Interpolator {

    private double mAmplitude = 1;
    private double mFrequency = 10;

    public BounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}

