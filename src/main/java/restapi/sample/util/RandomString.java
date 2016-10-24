package restapi.sample.util;

/**
 * Created by SDS on 2015-11-20.
 */
public class RandomString {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; //0 -55

//    public static void main (String[] args) {
//        System.out.println(random(20));
//        System.out.println(random(20));
//        System.out.println(random(20));
//        System.out.println(random(20));
//        System.out.println(random(20));
//    }

    public static String random(int length) {
        StringBuilder sb = new StringBuilder();

        for ( int i = 0 ; i < length ; i++ ) {
            int randIndex = (int) Math.floor(Math.random() * 56);
            sb.append(ALPHABET.charAt(randIndex));
        }

        return sb.toString();
    }
}
