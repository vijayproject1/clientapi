package com.infotech.clientapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import sk.antons.jmom.util.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        File file = ResourceUtils.getFile("classpath:input.json");
        String inputString = Files.readString(file.toPath());
        File files = ResourceUtils.getFile("classpath:Format.json");
        String spec = Files.readString(files.toPath());


        String path = "/a/b/c/d/address[*]/e/f";

        HashMap<String, Object> map = new HashMap<>();
        JSONObject ak = new JSONObject();
        JSONObject bk = new JSONObject();

        JSONObject a = new JSONObject();
        map.put("ak", new JSONObject());
        a.put("A", map.get("ak"));

        map.put("bk", new JSONObject());

        ((JSONObject) map.get("ak")).put("b", map.get("bk"));


        JSONArray arry = new JSONArray();
        arry.add(new JSONObject());
        arry.add(new JSONObject());

        map.put("ck", arry);

        ((JSONObject) map.get("bk")).put("c", map.get("ck"));


        JSONArray cArr = (JSONArray) map.get("ck");
        for (Object jsonObject :
                cArr) {
            map.put("dk", new JSONObject());
            JSONObject jsonObject1 = (JSONObject) jsonObject;
            jsonObject1.put("d", map.get("dk"));
        }

        map.put("ek", new JSONObject());

        ((JSONObject) map.get("dk")).put("e", map.get("ek"));

        System.out.println(a);


        LinkedHashMap<String, Object> specMap =
                mapper.readValue(spec, LinkedHashMap.class);


        LinkedHashMap<String, Object> value = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : specMap.entrySet()) {
            List<String> elements = PathUtil.elements(entry.getKey());

            HashMap<String, Object> jsonMap = new HashMap<>();
            JSONObject finalObj = new JSONObject();
            TemplateValue templateValue = mapper.readValue(mapper.writeValueAsString(entry.getValue()), TemplateValue.class);
            transform(0, elements, jsonMap, finalObj, entry.getKey(), inputString, templateValue);
            System.out.println("FINAL " + finalObj);

        }

    }


    public static void transform(int index, List<String> elements, HashMap<String, Object> jsonMap, JSONObject finalObj, String path, String inputString, TemplateValue templateValue) {

        for (int i = index; i < elements.size(); i++) {
            if (i == index) {
                jsonMap.put(elements.get(i), new JSONObject());
                finalObj.put(elements.get(i), jsonMap.get(elements.get(i)));
            }
            else if (i == elements.size() - 1) {

                Object obj = jsonMap.get(elements.get(i - 1));
                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;
                    jsonObject.put(elements.get(i), "HAppYEnding");
                } else {
                    JSONArray jsonArray = (JSONArray) obj;
                    for (Object input :
                            jsonArray) {
                        JSONObject jsonObject1 = (JSONObject) input;
                        jsonObject1.put(elements.get(i), "HAppYEnding");
                    }
                }
           }
            else if (elements.get(i).contains("*")) {
                JSONArray jsonArray = new JSONArray();
                JSONArray inputVal = (JSONArray) getInputValues(i, path, inputString, templateValue.getValue(), ".a1.b1.c1[*]");
                inputVal.forEach(ele -> {
                    jsonArray.add(new JSONObject());
                });
                jsonMap.put(elements.get(i), jsonArray);
                Object obj = jsonMap.get(elements.get(i - 1));
                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;
                    jsonObject.put(elements.get(i), jsonMap.get(elements.get(i)));
                } else {
                    System.out.println("NEED TO ARRAY " + obj);
                }

            } else {
                jsonMap.put(elements.get(i), new JSONObject());
                Object obj = jsonMap.get(elements.get(i - 1));
                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;
                    jsonObject.put(elements.get(i), jsonMap.get(elements.get(i)));
                } else {
                    System.out.println(index + elements.get(index));
                    JSONArray jsonArray = (JSONArray) obj;

                    for (Object input :
                            jsonArray) {
                        HashMap<String, Object> childMap = new HashMap<>();
                        JSONObject childObj = new JSONObject();
                        transform(i, elements, childMap, childObj, path, inputString, templateValue);
//                        jsonMap.put(elements.get(i), new JSONObject());
                        JSONObject jsonObject1 = (JSONObject) input;
                        jsonObject1.putAll(childObj);
                    }
                }
            }
        }

    }

    private static Object getValues(TemplateValue templateValue, String jsonString) {
        return JsonPath.read(jsonString, "$" + templateValue.getValue());
    }

    private static Object getInputValues(int index, String path, String inputJson, String inputPath, String tempPath) {

//        String currentPath = "";
//        List<String> elements = PathUtil.elements(path);
//
//        for (int i = 0; i < index;
//             i++) {
//            currentPath = currentPath + "." + elements.get(i);
//        }
//
//        String[] arr = currentPath.split("[*]");
//        int currentArrayPoint = arr.length - 1;
//        currentPath = ".App.item[*]";
        try {
            return JsonPath.read(inputJson, "$" + tempPath);

        } catch (Exception e) {
            return null;
        }
    }


    private static Object getJsonObject(int index, String path, JSONObject output) {

        String currentPath = "";
        List<String> elements = PathUtil.elements(path);

        for (int i = 0; i < index;
             i++) {
            currentPath = currentPath + "." + elements.get(i);
        }
        try {
            return JsonPath.read(output.toString(), "$" + currentPath);

        } catch (Exception e) {
            return null;
        }

    }


}
