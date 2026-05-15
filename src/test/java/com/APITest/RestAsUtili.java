package com.APITest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestAsUtili {




    public String  fillUpDate(String jsonDataRet)
    {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("country", "India");
        valuesMap.put("state", "Telangana");
        valuesMap.put("pinCode", "500001");
        valuesMap.put("kkkaa","poaihsg");

        Pattern pattern = Pattern.compile("\\{\\{(.+?)\\}\\}");
        Matcher matcher = pattern.matcher(jsonDataRet);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1); // Extract the key (e.g., "country")
            String value = valuesMap.getOrDefault(key, ""); // Get value from Map
            // Matcher.quoteReplacement handles special characters in the value
            matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static  void  main(String[] args)
    {
  /*      RestAsUtili restut= new RestAsUtili();
     //   String dataKso=restut.readAndParseFile("src/test/resources","point.json");
        String finalData=restut.fillUpDate(dataKso);
        System.out.println("This is the json data"+finalData);*/
    }
}
