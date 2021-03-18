package com.twocoms.rojgarkendra.registrationscreen.model;

import android.content.Intent;

 public class SliderItemModel {

    Integer img;
    String text;
    String text1;

    public SliderItemModel() {
    }

     public SliderItemModel(Integer img, String text, String text1) {
         this.img = img;
         this.text = text;
         this.text1 = text1;
     }

     public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

     public String getText1() {
         return text1;
     }

     public void setText1(String text1) {
         this.text1 = text1;
     }
 }
