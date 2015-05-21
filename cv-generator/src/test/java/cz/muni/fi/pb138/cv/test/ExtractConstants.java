/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.test;

import cz.muni.fi.pb138.cv.service.Config;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jozef Živčic
 */
public class ExtractConstants {
    
    public static String getStringConstants(String language) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(Config.DBUTIL + "\\texts.xml"))) {
            for (String s = br.readLine(); s != null; s=br.readLine()) {
                sb.append(s);
                sb.append(System.lineSeparator());
            }
        }
        String temp = sb.toString();
        String array[] = temp.split("<language id=");
        for (int i = 0 ; i < array.length; i++) {
            if (array[i].contains("\"" + language + "\""))
                temp = array[i];
        }
        return temp;
    }
    
    public static String getConstant(String constants, String element) {
        String closeElement = "</" + element.substring(1);
        int first = constants.indexOf(element);
        int last = constants.indexOf(closeElement);
        String output = constants.substring(first + element.length(),last);
        return output;
    }
}
