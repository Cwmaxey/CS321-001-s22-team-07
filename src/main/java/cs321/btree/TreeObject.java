package cs321.btree;

/**
 * TreeObject
 */
public class TreeObject
{
    long DNA;
    int freq;
    // Constructor
    public TreeObject(long DNA){
        this.DNA = DNA;
        this.freq = 0;
    }

    // Get the key from the TreeObject.
    public long getKey() {
        return this.DNA;
    }

    // Increase the frequency if a duplicate is found.
    public void increaseFreq() {
        this.freq++;
    }

    public int getFreq(){
        return this.freq;
    }

    // Need the length of the string when converting from Binary to String (AAAC)
    // is length 4 so when calling toBinaryString("AAAC") returns 1 so there are three A's
    public String toString() {
        return "";
    }

}
