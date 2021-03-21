package com.infotech.clientapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import sk.antons.jmom.util.PathUtil;


import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class TransformTest {


    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        File file = ResourceUtils.getFile("classpath:input.json");
        String inputString = Files.readString(file.toPath());
        File files = ResourceUtils.getFile("classpath:Format.json");
        String spec = Files.readString(files.toPath());

        LinkedHashMap<String, Object> specMap =
                mapper.readValue(spec, LinkedHashMap.class);

        JSONObject output = new JSONObject(new HashMap<>());

        List<JSONObject> pathoutput = new ArrayList<>();
        for (Map.Entry<String, Object> entry : specMap.entrySet()) {

            TemplateValue templateValue = mapper.readValue(mapper.writeValueAsString(entry.getValue()), TemplateValue.class);

            String path = entry.getKey();
            JSONObject pathObject34 = new JSONObject();
            List<String> elements = PathUtil.elements(path);
            Map<String, Object> previousOutput = new HashMap();
            for (int i = elements.size() - 1; i >= 0; i--) {
                if (i == (elements.size() - 1)) {
                    List<String> multipleValues = null;
                    Object objectValue = getValues(templateValue, inputString);
                    List<Object> valueList = new ArrayList<>();

                    if (objectValue instanceof List) {
                        multipleValues = (List<String>) objectValue;
                    } else if (objectValue instanceof String) {
                        if (elements.get(i - 1).contains("[")) {
                            multipleValues = Arrays.asList((String) objectValue);
                        } else {
                            JSONObject valueObj = new JSONObject();
                            valueObj.put(elements.get(i), (String) objectValue);
                            previousOutput.put("k", valueObj);
                            continue;
                        }
                    }
                    Object obj = getJsonObject(i, path, output, elements.size());
                    JSONArray array = null;

                    if (obj instanceof JSONArray) {
                        array = (JSONArray) obj;
                    } else if (obj instanceof LinkedHashMap) {
                        System.out.println("LinkedHashMap " + obj);
                        LinkedHashMap map = (LinkedHashMap) obj;
                    }

                    if (1 == multipleValues.size() && null != array && multipleValues.size() < array.size()) {
                        multipleValues = Collections.nCopies(array.size(), multipleValues.get(0));
                    }

                    for (int j = 0; j < multipleValues.size(); j++) {
                        if (null != array && !array.isEmpty() && array.size() > j) {
                            LinkedHashMap map = (LinkedHashMap) array.get(j);
                            if (null == map) {
                                JSONObject valueObj = new JSONObject();
                                valueObj.put(elements.get(i), multipleValues.get(j));
                                valueList.add(valueObj);
                            } else {
                                map.put(elements.get(i), multipleValues.get(j));
                                valueList.add(map);
                            }
                        } else {
                            JSONObject valueObj = new JSONObject();
                            valueObj.put(elements.get(i), multipleValues.get(j));
                            valueList.add(valueObj);
                        }
                    }
                    previousOutput.put("k", valueList);
                } else if (elements.get(i).contains("[")) {
                    String index = StringUtils.substringBetween(elements.get(i), "[", "]");
                    Object obj = getJsonObject(i, path, output, elements.size());
                    if (previousOutput.get("k") instanceof List) {
                        JSONObject valueObj = null == obj ? new JSONObject() : new JSONObject((Map<String, ?>) obj);
                        valueObj.put(elements.get(i), previousOutput.get("k"));
                        previousOutput.put("k", valueObj);
                    } else {
                        JSONArray arr = null == obj ? new JSONArray() : (JSONArray) obj;
                        arr.add(previousOutput.get("k"));
                        JSONObject valueObj = new JSONObject();
                        valueObj.put(elements.get(i), arr);
                        previousOutput.put("k", valueObj);
                    }
                } else if (i == 0) {
                    pathObject34.put(elements.get(i), previousOutput.get("k"));
                } else {
                    JSONObject valueObj = new JSONObject();
                    Object obj = getJsonObject(i, path, output, elements.size());
                    if (null == obj) {
                        valueObj.put(elements.get(i), previousOutput.get("k"));
                    } else {
                        LinkedHashMap map = (LinkedHashMap) obj;
                        LinkedHashMap childMap = (LinkedHashMap) map.get(elements.get(i));
                        if (null != childMap) {
                            JSONObject jsonObject = (JSONObject) previousOutput.get("k");
                            childMap.putAll(jsonObject);
                            valueObj.putAll(map);
                        } else {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(elements.get(i), previousOutput.get("k"));
                            map.putAll(jsonObject);
                            valueObj.putAll(map);
                        }
                    }
                    previousOutput.put("k", valueObj);
                }
            }
            pathoutput.add(pathObject34);
            String outputs = pathObject34.toString().replace("[*]", "");
            output = new JSONObject(new ObjectMapper().readValue(outputs, JSONObject.class));
            System.out.println("Sub " + outputs);

        }


        System.out.println("final " + output.toString().replace("[*]", ""));


    }

    private static Object getValues(TemplateValue templateValue, String jsonString) {
        return JsonPath.read(jsonString, "$" + templateValue.getValue());
    }

    private static Object getJsonObject(int index, String path, JSONObject output, int size) {

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

    private static String getString(String toString) {
        return toString.replace("[*]", "");
    }


}
