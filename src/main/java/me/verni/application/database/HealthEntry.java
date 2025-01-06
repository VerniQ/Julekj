package me.verni.application.database;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "health_entries")
public class HealthEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    private LocalTime time;

    private double weight;

    private int systolicPressure;

    private int diastolicPressure;

    private String mood;

    public HealthEntry() {
    }

    public HealthEntry(LocalDate date, LocalTime time, double weight, int systolicPressure, int diastolicPressure, String mood) {
        this.date = date;
        this.time = time;
        this.weight = weight;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.mood = mood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "HealthEntry{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", weight=" + weight +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", mood='" + mood + '\'' +
                '}';
    }
}