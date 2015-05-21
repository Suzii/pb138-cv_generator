/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.json.JSONObject;

/**
 *
 * @author Zuzana
 */
public class CvUtilImpl implements CvUtil {

    @Override
    public boolean attachFile(OutputStream out, File file) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("outputstream is null");
        }

        if (file == null) {
            return false;
        }

        try (FileInputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        out.flush();
        return true;
    }

    @Override
    public JSONObject extractUserData(BufferedReader reader) {

        if (reader == null) {
            throw new IllegalArgumentException("reader is null");
        }
        StringBuilder sb = new StringBuilder();
        try {
            //BufferedReader reader = request.getReader();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new JSONObject(sb.toString());
    }
}
