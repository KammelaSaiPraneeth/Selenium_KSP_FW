package com.APITest;

import java.util.*;
import java.util.stream.Collectors;

public class streereeams {

    public static void main(String []args)
    {

         List<Integer> nums= Arrays.asList(2,3,4,5,6,7);
         List<Integer> doubled=nums.stream().map(n-> n*2).collect(Collectors.toList());

         List<Map<String,String>> testdata=  new ArrayList<>();
         Map<String,String> tc_1_data= new HashMap<>();
         tc_1_data.put("TestCaseFlag","Y");
        tc_1_data.put("TestCaseName","TestCase1");
        tc_1_data.put("Store","Cincinati");
        tc_1_data.put("Item","Bacoon");

        Map<String,String> tc_2_data= new HashMap<>();
        tc_2_data.put("TestCaseFlag","N");
        tc_2_data.put("TestCaseName","TestCase2");
        tc_2_data.put("Store","Dallas");
        tc_2_data.put("Item","Chicken");
        Map<String,String> tc_3_data= new HashMap<>();
        tc_3_data.put("TestCaseFlag","Y");
        tc_3_data.put("TestCaseName","TestCase2");
        tc_3_data.put("Store","Florida");
        tc_3_data.put("Item","Mutton");
        testdata.add(tc_3_data);
        testdata.add(tc_2_data);
        testdata.add(tc_1_data);
        System.out.println("This is first map data"+testdata);
        List<Map<String,String>> EnabledTests=testdata.stream().filter(Map -> Map.get("TestCaseFlag").equals("Y")).collect(Collectors.toList());
        EnabledTests.forEach(Map -> System.out.println(Map));

    }



}
