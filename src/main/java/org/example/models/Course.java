package org.example.models;


import javax.persistence.*;
import java.util.Iterator;

@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private int duration;

    public Course() {

    }

    public Course(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id= " + id +
                ", Название = '" + title + '\'' +
                ", Продолжительность = " + duration + " месяцев(-а)" +
                '}';
    }


    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateDuration(int newDuration) {
        this.duration = newDuration;
    }


    //region  Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    //endregion
}
