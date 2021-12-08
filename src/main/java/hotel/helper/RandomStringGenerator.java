package hotel.helper;

public class RandomStringGenerator
{
    private RandomStringGenerator() {}
// method to generate random strings
    public static String getRandomString(int size)
    {
        // Choose a random character from the given string
        String alphaNumericStr = "abcdefghijklmnopqurestuvwxyz"
                                + "0123456789"
                                + "ABCDEFGHIJKLMNOPQURESTUVWXYZ";
        StringBuffer sbfr = new StringBuffer(size);
        // loop for generating choosing a
        // random character from the alphaNumericStr variable
        for (int j = 0; j < size; j++)
        {
            // Generating a random number that lies in the range of
            // 0 to alphaNumericStr variable length
            double randNo = Math.random();
            // getting the index using the randNo
            int idx = (int)(alphaNumericStr.length() * randNo);
            // add Character one by one in end of sb
            sbfr.append(alphaNumericStr.charAt(idx));
        }
        // returning the randomly generated string
        return sbfr.toString();
    }
} 