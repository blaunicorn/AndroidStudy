package com.example.chapter08.entity;

import java.util.ArrayList;
import java.util.List;

public class Planet {
    public int image;
    public  String name;
    public  String desc;

    public Planet(int image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
    }


    public  static  List<Planet> getDefaultList(String[] nameArray,int[] iconArray,String[] descArray) {
        List<Planet> planetList = new ArrayList<>();
        for (int i =0;i<iconArray.length;i++) {
            planetList.add(new Planet(iconArray[i],nameArray[i],descArray[i] ));
        }
        return planetList;
    }

}
