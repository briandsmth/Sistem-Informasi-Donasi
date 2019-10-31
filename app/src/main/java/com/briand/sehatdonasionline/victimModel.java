package com.briand.sehatdonasionline;

import com.google.firebase.database.Exclude;

public class victimModel {
    private String nama, goals, imageurl, story, key;
    private int position;

    public victimModel(){

    }

    public victimModel (int position){
        this.position = position;
    }

    public victimModel(String nama, String goals, String imageurl, String story){
        if (nama.trim().equals("")){
            nama = "no name";
        }

        this.nama = nama;
        this.goals = goals;
        this.story = story;
        this.imageurl = imageurl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }


    @Exclude
    public String getKey() {

        return key;
    }
    @Exclude
    public void setKey(String key) {

        this.key = key;
    }

    public int getPosition() {

        return position;
    }

    public void setPosition(int position) {

        this.position = position;
    }
}
