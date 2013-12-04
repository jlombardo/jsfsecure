package edu.wctc.maven.glassfish.jsfsecure.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.text.MessageFormat;

/**
 * Use this class to change the password stored in the databae from one that is
 * visible as plain text (a security threat) to one that is "hashed". Hashing is
 * a one-way encryption system. Hashes can be generated but cannot be reverse
 * engineered, which is why they are called one-way hashes. Use this class to
 * generate a hashed password, based on the original plain text version, using
 * SHA-256, which is a superior hashing algorithm. Then copy the output from the
 * console and use it to replace what you have in your database.
 *
 * @author jlombardo
 */
public class HashedPasswordGenerator {

    public static void generateHash(String password) {
        // This is one way of generating a SHA-256 hash. I uses classes/methods
        // from the Google Guava project. See the Maven pom.xml file which
        // I've modified to include the Guava libraries. See the imports
        // above which show what is being used.
        String hash
                = Hashing.sha256()
                .hashString(password, Charsets.UTF_8).toString();

        String output = MessageFormat.format("{0} hashed to: {1}", password, hash);

        System.out.println(output);
    }

    public static void main(String[] args) {
        // you can generate as many as you need ... modify to suite...
        generateHash("password1");
        generateHash("password2");
        generateHash("password3");
    }
}
