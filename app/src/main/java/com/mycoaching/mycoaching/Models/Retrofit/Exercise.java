package com.mycoaching.mycoaching.Models.Retrofit;

/**
 * Created by kevin on 08/07/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exercise {

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("exercise")
    @Expose
    private String exercise;

    @SerializedName("intensity")
    @Expose
    private Integer intensity;

    @SerializedName("repetitions")
    @Expose
    private Integer repetitions;

    @SerializedName("series")
    @Expose
    private Integer series;

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