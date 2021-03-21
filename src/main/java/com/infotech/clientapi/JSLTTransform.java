package com.infotech.clientapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.spt.data.jslt.Parser;
import com.schibsted.spt.data.jslt.Expression;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSLTTransform {

    public static void main(String[] args) throws Exception {

        File file = ResourceUtils.getFile("classpath:input.json");
        String inputString = Files.readString(file.toPath());
        File files = ResourceUtils.getFile("classpath:Format.json");
        String spec = Files.readString(files.toPath());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result =
                mapper.readValue(spec, HashMap.class);
        LinkedHashMap value = (LinkedHashMap) result.get("/Order/shipment/address[*]/doorNo");
        TemplateValue templateValue = mapper.readValue(mapper.writeValueAsString(value),TemplateValue.class);
        System.out.println(templateValue.getValue());

    }
}



