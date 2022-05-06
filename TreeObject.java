/**
 * TreeObject
 */
public class TreeObject
{
    long DNA;
    int freq;
    // Constructors
    public TreeObject (long DNAInp) {
        DNA = DNAInp;
        freq = 1;
    }

    public TreeObject(long DNA, int freq){
        this.DNA = DNA;
        this.freq = freq;
    }

    public TreeObject() {
        this.DNA = -1L;
        freq = 0;
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
