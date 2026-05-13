package com.TestExecution;

public class poritn {


    public static void main (String[] args)
    {
        double fast= 20.0;
        double slow= 20.0;

        int fast_data= (int) fast;
        int slow_data=(int) slow;
        System.out.println(fast_data);
        System.out.println(slow_data);

        String fastd= String.valueOf(fast_data);
        String slowd=String.valueOf(slow_data);
        if(fastd.length()<3)
        {
            fastd="0"+fastd;
        }


    }
}
