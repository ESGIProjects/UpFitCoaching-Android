package com.mycoaching.mycoaching.Models.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kevin on 08/07/2018.
 * Version 1.0
 */

public class Exercise {

    private Integer duration, intensity, repetitions, series;

    private String exercise;

    public Exercise(Integer duration, String exercise, Integer intensity, Integer repetitions, Integer series) {
        this.duration = duration;
        this.exercise = exercise;
        this.intensity = intensity;
        this.repetitions = repetitions;
        this.series = series;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

}