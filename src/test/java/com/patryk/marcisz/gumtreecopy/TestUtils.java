package com.patryk.marcisz.gumtreecopy;

import java.io.InputStream;
import java.util.Scanner;

public class TestUtils {

    public static String inputStreamtoString(InputStream inputStream){
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
