package cs321.create;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods dealing with DNA sequences and its compact representation as long variables.
 */
public class SequenceUtils
{

	public static long DNAStringToLong(String DNAString)  {
		long key = 0;
        for(int i = 0; i < DNAString.length(); i++)
        {
            switch(DNAString.charAt(i))
            {
                case 'a':
                    key = key << 2;                    
                    break;
                case 't':
                    key = key << 2;
                    key = ( key & ~((~(~0<<2)) )) | (( 3 & ((~(~0<<2)))));
                    break;
                case 'c':
                    key = key << 2;
                    key = ( key & ~((~(~0<<2)) )) | (( 1 & ((~(~0<<2)))));
                    break;
                case 'g':
                    key = key << 2;
                    key = (key & ~((~(~0<<2)) )) | (( 2 & ((~(~0<<2)))));
                    break;
            }
        }
        return key;
	}


	public String longToDNAString(long sequence, int seqLength) {
		String temp = Long.toBinaryString(sequence);
		String result = "";
		// loop through the string

		while(temp.length() < (seqLength * 2)) 
		{
			temp = "0" + temp;
		}
		for(int i = 0; i < temp.length() - 1; i+=2) 
		{
			if(temp.substring(i, i+2).equals("00")) 
			{
				result += "A";
			}
			else if(temp.substring(i, i+2).equals("11")) 
			{
				result += "T";
			}
			else if(temp.substring(i, i+2).equals("01")) 
			{
				result += "C";
			}
			else if(temp.substring(i, i+2).equals("10")) 
			{
				result += "G";
			}
		}
		return result;
	}


	public static long getComplement(long sequence, int seqLength) {
		return 100;
	}

}
