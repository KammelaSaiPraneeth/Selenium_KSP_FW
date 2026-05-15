package JavaDSAProgramms;
/*
import io.cucumber.java.it.Ma;
import io.cucumber.java.sl.In;*/

import javax.sound.midi.Soundbank;
import java.util.*;

public class StringsPrograms {

    //This program is intended to reverse a String
    public void StringReverse(String actualString)
    {
        //This program is intended to reverse a string
        String actString= actualString;
        String reverseString= "";
        for(int i=actString.length()-1;i>=0;i--)
        {
            reverseString+=actString.charAt(i);
        }
        System.out.println("This is the reverse String --> "+reverseString);
    }

    //This program is intended to check if a string is palindrome
    public void palidromeCheckString(String actualString)
    {
        //This program is intended to check if a string is palindrome
        String actString= actualString;
        String reverseString= "";
        for(int i=actString.length()-1;i>=0;i--)
        {
            reverseString+=actString.charAt(i);
        }
        if(actString.equals(reverseString))
        {
            System.out.println("The provided String is a palindrome String");
        }
    }
    public void firstNonRepeatingCharacter(String actualString)
    { //This program is to find the first non repeating character of the string
        String actString= actualString;
        Map<Character, Integer> freq= new LinkedHashMap<>();
        for(int i=0;i<actualString.length();i++)
        {
            if(freq.containsKey(actString.charAt(i)))
            {
                freq.put(actualString.charAt(i),freq.get(actualString.charAt(i))+1);
            }
            else
                freq.put(actualString.charAt(i),1);
        }
        for(char c:freq.keySet())
        {
            if(freq.get(c)==1)
            {
                System.out.println("The First re occuring character is -->" +c);
                break;
            }
        }
    }

    public void occuranceOfEachChar(String actualString)
    { //This program is to find out the occurance of each string
        String actString = actualString;
        Map<Character, Integer> freqCountMap= new HashMap<>();
        for(char c:actString.toCharArray())
        {
            if(freqCountMap.containsKey(c))
            {
                freqCountMap.put(c,freqCountMap.get(c)+1);
            }
            else
                freqCountMap.put(c,1);
        }
        System.out.println("The occurance of each character is" +freqCountMap);
    }

    //Program to remove duplicate values from a string
    public void removeDuplicateCharac(String actualString)
    {
        String act= actualString;
        Set<Character> setData= new LinkedHashSet<>();
        StringBuilder result= new StringBuilder();
        for(char c:act.toCharArray())
        {
            if(!setData.contains(c))
            {
                setData.add(c);
                result.append(c);
            }
        }
        System.out.println("Given  string after removing duplicates is ->"+result);
    }

    //Program to find first repeating character in the string
    public  void firstRepetingChar(String actualString)
    {
        String actString= actualString;
        Map<Character, Integer> resultMap= new LinkedHashMap<>();
        for(char c:actString.toCharArray())
        {
            if(resultMap.containsKey(c))
            {
                resultMap.put(c,resultMap.get(c)+1);
            }
            else resultMap.put(c,1);
        }
        for(char c:resultMap.keySet())
        {
            if(resultMap.get(c)>1)
            {
                System.out.println("The first repeting character is -> "+c);
                break;
            }
        }

    }
    //Programs to find if the string is anagram
    public void anagramCheck(String data1,String data2)
    {
        String firstData=data1;
        String secondData=data2;
        char[] fD= firstData.toCharArray();
        char[] sD=secondData.toCharArray();
        Arrays.sort(fD);
        Arrays.sort(sD);
        if(Arrays.equals(fD,sD))
        {
            System.out.println(" The provided two strings are Anagram");
        }
    }
    //Program to find the most frequent character
    public void freqCharac(String actualData)
    {
        String actData= actualData;
        Map<Character,Integer> dataMap= new LinkedHashMap<>();
        for(char c :actData.toCharArray())
        {
            dataMap.put(c,dataMap.getOrDefault(c,0)+1);
        }
        char bestChar=0;
        int bestint=0;
        for (Map.Entry<Character, Integer> e : dataMap.entrySet())
        {
            if(e.getValue()>bestint)
            {
                bestint=e.getValue();
                bestChar=e.getKey();
            }
        }
        System.out.println("The most freq occuring character is -> "+bestChar);
    }

    //This program is intended to count the total words in the string
    public void wordsInString(String actualString)
    {
        String actString= actualString;
        String[] split = actString.split("//s+");
        int a=split.length;
        System.out.println(" The total words in the string are "+a);
    }

    //This program is intended to reverse the each word of the given string.
    public  void reverseEachWordStrin(String actualString)
    {
        String actData=actualString;
        String[] arrStringWords= actualString.split("");
        StringBuilder builderResult= new StringBuilder();
        for(int i=0;i<arrStringWords.length;i++)
        {
            String w= arrStringWords[i];
            for(int j=w.length();j>=0;j--)
            {
                builderResult.append(w.charAt(j));
            }
            if(i<arrStringWords.length-1)
            {
                builderResult.append(" ");
            }
        }
    }

    //This program is intended to count vowels and consonents in the string.
    public void vowelsAndConsonants(String actData)
    {
        String actualData = actData.toLowerCase().trim();
        char[] charData = actualData.toCharArray();
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        int vowelsCount = 0;
        int consonantsCount = 0;

        for(int i=0; i<charData.length; i++)
        {
            if(charData[i] <'a' || charData[i]>'z')
                continue;
             if(charData[i]=='a' || charData[i]=='e' || charData[i]=='i' || charData[i]=='o' || charData[i]=='u')
             {
                 vowelsCount++;
             }
             else
                 consonantsCount++;
        }
    }

    //This program intends to sort the characters by frequency in a given string
    public void charFreqSort(String actualData)
    {
        String actData=actualData;
        Map<Character, Integer> freqCount= new HashMap<>();
        for( int i=0 ; i<actData.length();i++)
        {
            char c= actualData.toCharArray()[i];
            if(freqCount.containsKey(c))
            {
                freqCount.put(c,freqCount.get(c)+1);
            }
            else
                freqCount.put(c,0);
        }




    }



}
