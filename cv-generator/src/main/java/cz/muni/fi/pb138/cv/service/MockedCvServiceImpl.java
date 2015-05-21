/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONObject;
import org.w3c.dom.Document;

/**
 *
 * @author Zuzana
 */
public class MockedCvServiceImpl implements CvService {

    @Override
    public JSONObject loadCvJSON(String login) {
        //JSONParser parser = new JSONParser();
        if(login == null){
            return null;
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(Config.SAMPLE_JSON_PATH));
            JSONObject jsonObject = new JSONObject(String.join("", lines));
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Document loadCvXML(String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveCv(String login, JSONObject cv) {
        /*try {
            FileWriter file = new FileWriter(SAMPLE_JSON_PATH);
            file.write(cv.toJSONString());
            file.flush();
            file.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return true;
    }

    @Override
    public boolean saveCv(String login, Document cv) {
        return true;
    }

    @Override
    public File generatePdf(String login, String lang) {
        return new File(Config.SAMPLE_PDF_PATH);
    }

    @Override
    public String checkValidity(Document cv) {
        return null;
    }

    @Override
    public String checkValidity(JSONObject cv) {
        return null;
    }

    @Override
    public void cleanAfterGeneratingPDF() {
    }
   
}
