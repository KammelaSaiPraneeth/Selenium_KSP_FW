package JavaDSAProgramms;

import java.util.*;

public class ArraysDSA {

    //Program to find the min and max of an array
    public void minAndMaxArray(int[] arrData)
    {
        int[] arrr=arrData;
        if (arrr == null || arrr.length == 0) {
            throw new IllegalArgumentException("Array must be non-null and non-empty.");
        }
        int min=0;
        int max=0;

        for( int i=0;i<arrr.length;i++)
        {
            int v= arrr[i];
            if(v>max)
                max=v;
            else if(v<min)
                min=v;
        }
        System.out.println(" The max is" +max+ " and the min is" +min );
    }

    //Program to remove duplicates from an array
    public int[] removeDup(int[] a)
    {
        int[] b= a;
        Set<Integer> ss= new LinkedHashSet<>();
        for( int i=0;i<b.length;i++)
        {
            ss.add(b[i]);
        }
        int[] array2 = new int[ss.size()];
        int dd=0;
        for(int j:ss)
            array2[dd++]=j;
        return array2;
    }

    //Program to move all the Zeros to end in Array
    public void allZerosToEnd(int[] givenArray)
    {
        int write=0;
        for(int i=0;i<givenArray.length;i++)
        {
          if(givenArray[i]!=0)
          {
              givenArray[write++]=givenArray[i];
          }
          while(write<givenArray.length)
          {
              givenArray[write++] = 0;
          }
        }
    }

}
