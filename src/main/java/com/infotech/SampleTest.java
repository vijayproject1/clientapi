package com.infotech;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class SampleTest {

    public static void main(String[] args) {


        String message;
        JSONObject main=  new JSONObject();

        JSONObject json = new JSONObject();
        json.put("name", "student");

        JSONArray array = new JSONArray();
        json.put("course", array);

        JSONObject item = new JSONObject();
        item.put("information", "test");
        item.put("id", 3);
        item.put("name", "course1");
        array.add(item);

        main.put("APp",json);
        message = json.toString();
        System.out.println(main);
    }
}
