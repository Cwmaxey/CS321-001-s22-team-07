package cs321.common;

public class ParseArgumentUtils {
    /**
     * Verifies if lowRangeInclusive <= argument <= highRangeInclusive
     */
    public static void verifyRanges(int argument, int lowRangeInclusive, int highRangeInclusive)
            throws ParseArgumentException {

    }

    public static int convertStringToInt(String argument) throws ParseArgumentException {
        char[] DNAArray = argument.toCharArray();
        // need to convert BTreeNode into bytes possibly?
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < DNAArray.length; i++) {
            char ATCG = Character.toUpperCase(DNAArray[i]);
            switch (ATCG) {
                case 'A':
                    result.append("00");
                    break;
                case 'T':
                    result.append("11");
                    break;
                case 'C':
                    result.append("01");
                    break;
                case 'G':
                    result.append("10");
                    break;
            }
        }

        return Integer.parseInt(result.toString(), 2);
    }
}
