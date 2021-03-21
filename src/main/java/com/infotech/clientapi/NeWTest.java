package com.infotech.clientapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import sk.antons.jmom.util.PathUtil;

import java.util.*;

public class NeWTest {

    public static void main(String[] args) throws ParseException, JsonProcessingException {
//        JSONObject outputJson = new JSONObject();
//
//        String inputPath = "/Order/address[*]/doorNo";
//        String output = "{}";
//        JsonValue json = JsonParser.parse(output);
//        List<String> elems = PathUtil.elements(inputPath);
//        JsonArrayImpl jsonArray = new JsonArrayImpl();
//        jsonArray.add(JsonParser.parse("{\"value\": \"SaveAs\", \"onclick\": \"SaveAs()\"}"));
//        Jmom jmom = Jmom.instance()
//                .add("Order/", jsonArray, true)
//                .add("Order/[0]/id", JsonFactory.stringLiteral("nove mneo"), true);
//        jmom.apply(json);
//
//
//        JSONObject pathObject = new JSONObject();
//
//        JSONObject time = new JSONObject();
//        time.put("time", "value");
//        JSONObject time2 = new JSONObject();
//        time2.put("time", "value");
//
//        JSONArray jsonArray1 = new JSONArray();
//        jsonArray1.add(time);
//        jsonArray1.add(time2);
//
//
//        JSONObject address = new JSONObject();
//        address.put("address", jsonArray1);
//        pathObject.put("order", address);
//        System.out.println(pathObject);
//
//
//        for (String ele :
//                elems) {
//
//            JSONObject object = new JSONObject();
//
//        }

        List<String> result = new ArrayList<>();



        JSONObject output = new JSONObject("{}");
        List<String> paths = new ArrayList<>();
        paths.add("/Order/shipment/item[*]/address[*]/doorNo");
        paths.add("/Order/shipment/item[0]/address[*]/Name");

        List<JSONObject> pathoutput = new ArrayList<>();
        for (String path :
                paths) {
            JSONObject pathObject34 = new JSONObject();
            List<String> elements = PathUtil.elements(path);
            Map<String, Object> previousOutput = new HashMap();
            for (int i = 0; i <elements.size(); i++) {
                if (i == (elements.size() - 1)) {
                    List<String> multipleValues = Arrays.asList("Value1", "value2");
                    List<Object> valueList = new ArrayList<>();
                    for (String val :
                            multipleValues) {
                        JSONObject valueObj = new JSONObject();
                        valueObj.put(elements.get(i), val);
                        valueList.add(valueObj);
                    }
                    previousOutput.put("k", valueList);
                } else if (elements.get(i).contains("[")) {
                    String index=    StringUtils.substringBetween(elements.get(i), "[", "]");
                    System.out.println(index);
                    if (previousOutput.get("k") instanceof List) {
                        JSONObject valueObj = new JSONObject();
                        valueObj.put(elements.get(i), previousOutput.get("k"));
                        previousOutput.put("k", valueObj);
                    } else {
                        JSONArray arr = new JSONArray();
                        arr.add(previousOutput.get("k"));
                        JSONObject valueObj = new JSONObject();
                        valueObj.put(elements.get(i), arr);
                        previousOutput.put("k", valueObj);
                    }
                } else if (i == 0) {
                    pathObject34.put("elements.get(i)" , "");
                    pathObject34.put(elements.get(i), previousOutput.put("k",new Object()));
                } else {
                    JSONObject valueObj = new JSONObject();
                    valueObj.put(elements.get(i), previousOutput.get("k"));
                    previousOutput.put("k", valueObj);
                }
            }
            output = pathObject34;
            pathoutput.add(pathObject34);
            String outputs=pathObject34.toString().replace("[*]","");
            System.out.println(outputs);

        }


        System.out.println(output);


//
//        outputJson.put("shi", pathObject);
//
//        System.out.println(outputJson.toString());

//        for (String ele :
//                elems) {
//            if (ele.contains("*")) {
//                System.out.println(ele);
//                JSONArray jsonArray = new JSONArray();
//
//            } else if (elems.indexOf(ele) == elems.size()) {
//                System.out.println(ele);
//
//            } else {
//                System.out.println(ele);
//            }
//
//        }

//        StringBuilder path= new StringBuilder();
//
//
//         apply(json,"/Order",JsonFactory.stringLiteral("LolD"));
//        apply(json,"/Order/address[*]",JsonFactory.stringLiteral("LolD"));
//        //apply(json,"/Order/address[*]/doorNo",JsonFactory.stringLiteral("LolD"));
//
//        System.out.println(json.toPrettyString(""));
//
//
////        for (String ele:
////        elems) {
////            path.append("/").append(ele);
////            System.out.println(path.toString());
////
////            apply(json,path.toString(),JsonFactory.stringLiteral("LolD"));
////            System.out.println(json.toPrettyString(""));
////
////        }


//        JsonArrayImpl jsonArray = new JsonArrayImpl();
//        jsonArray.add(JsonParser.parse("{\"doorNo\":1}"));
//        Jmom jmom = Jmom.instance()
//                .add("/address", jsonArray, true)
//                .add(inputPath, JsonFactory.stringLiteral("Value"), true)
//                .add("Order", JsonFactory.stringLiteral(""), false);
//
//        jmom.apply(json);
//        System.out.println(json.toPrettyString(""));
    }

}
