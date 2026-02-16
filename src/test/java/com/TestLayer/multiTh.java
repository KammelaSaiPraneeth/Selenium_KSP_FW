package com.TestLayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class multiTh {

    public int db(JSONObject a)
    {

        System.out.println("This is the thread name:" +Thread.currentThread().getName());
        JSONObject tempobj=a;
        String dbQuery= "Select * from ADC where NAME={1} AND place='{2}' AND streetname='{3}' AND streetname='{4}' AND landmark='{5}' ";
        dbQuery
                .replace("'{1}'",tempobj.getString("Name"))
                .replace("'{2}'", tempobj.getString("place"))
                .replace("'{3}'", tempobj.getString("pincode"))
                .replace("'{4}'",tempobj.getString("streetname"))
                .replace("'{5}'",tempobj.getString("landmark"));
        System.out.println("This is the query generated"+dbQuery);
        int count = 1;
        return count;
    }

    public static void main(String[] args) {

        multiTh mt= new multiTh();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        JSONObject obj = new JSONObject();
        obj.put("Name", "Aarav Mehta");
        obj.put("place", "Pune");
        obj.put("pincode", "411038");
        obj.put("streetname", "Prabhat Road");
        obj.put("landmark", "Near Deccan Gymkhana");

        JSONObject obj1 = new JSONObject();
        obj1.put("Name", "Prerna Khandelwal");
        obj1.put("place", "Ajmer");
        obj1.put("pincode", "305001");
        obj1.put("streetname", "Dargah Bazaar Road");
        obj1.put("landmark", "Near Ana Sagar Lake");

        JSONObject obj2 = new JSONObject();
        obj2.put("Name", "Rajat Kapoor");
        obj2.put("place", "Gwalior");
        obj2.put("pincode", "474001");
        obj2.put("streetname", "Lashkar Road");
        obj2.put("landmark", "Near Jai Vilas Palace");

        JSONArray as = new JSONArray();
        as.put(obj);
        as.put(obj1);
        as.put(obj2);
        System.out.println();

        List<CompletableFuture<Integer>> l1 = new ArrayList<>();
        for(int i=0; i<as.length();i++)
        {
            JSONObject poi= as.getJSONObject(i);
            CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> mt.db(poi), executorService);
            l1.add(integerCompletableFuture);
        }
        //l1 - t1,t2,t3 -->
        //t1-[1,1,1,1,1,0] - future
        //t2 -[1,1,1,1,1,0] - future
        //t3 - [1,1,1,0,1] - future
       // [1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,0]

        List<Integer> results = l1.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println("All counts: " + results);
        long count = results.stream().map(i -> i == 0).count();
        if(count>0)
        {
            System.out.println(" Test fail");
            System.out.println(" report class");
        }

    }
}
