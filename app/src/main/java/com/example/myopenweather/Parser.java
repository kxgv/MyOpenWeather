package com.example.myopenweather;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parser extends AppCompatActivity {

    public List<Temp> parserJson(String JSon) throws JSONException {

        Temp temp;
        String time = null;
        String humid = null;
        String calorFred = null;
        String temperature = null;
        String direccion = "";

        List <Temp> llista_dades = new ArrayList<>();

        JSONObject resultJSON = null;

        try {

            resultJSON = new JSONObject(JSon);
            JSONArray resultsJSON = resultJSON.getJSONArray("list");

            if (resultsJSON.length() > 0){
                Integer integer = 0;
                while (integer<resultsJSON.length()){
                    temperature = resultsJSON.getJSONObject(integer).getJSONObject("main").getString("temp");
                    humid = resultsJSON.getJSONObject(integer).getJSONObject("main").getString("humidity");
                    time = resultsJSON.getJSONObject(integer).getString("dt_txt");

                    if (temperature != null){
                        if (Double.parseDouble(temperature) >= 20){
                            calorFred = "hot";
                        } else {
                            calorFred = "cold";
                        }
                    }

                    temp = new Temp(time, temperature, calorFred, humid);
                    Log.d("JSON", "bloc " + String.valueOf(integer) + " " + time + " " + temperature + " " + humid + " " +calorFred);
                    llista_dades.add(temp);
                    time = null;
                    humid = null;
                    temperature = null;
                    integer += 1;

                    Log.d("JSON", "dades " + direccion);

                }

            }
        } catch (JSONException e){
            e.printStackTrace();
            Log.d("JSON", "ParseJSON " + e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            Log.d("JSON", "Exception: " + e.getMessage());
        }

        return llista_dades;
    }

    public List<Temp> parsejaXml(String xml) throws XmlPullParserException, IOException, JSONException {

        String time = null;
        String temperature = null;
        String calorFred = null, humid=null;
        Temp temp;

        List<Temp> llista = new ArrayList<Temp>();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader(xml));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("time")) {
                    time = xpp.getAttributeValue(null, "from");
                }
                if (xpp.getName().equals("temperature")) {
                    temperature = xpp.getAttributeValue(null, "value");
                }
                if (xpp.getName().equals("humidity")) {
                    humid = xpp.getAttributeValue(null, "value");
                }

                if (temperature != null) {
                    if (Double.parseDouble(temperature) >= 20) {
                        calorFred = "hot";
                    } else {
                        calorFred = "cold";
                    }
                }

                if (time != null && temperature != null) {
                    try {
                        time = time.replace   ("T"," ");
                        temp = new Temp(time, temperature, calorFred,humid);
                        Log.d("test", "parsejant "+time+temperature+calorFred+humid);
                        llista.add(temp);
                        time=null;
                        temperature=null;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("test", "parsejaXml: "+e.getMessage());
                    }
                }
            }

            eventType = xpp.next();

        }
        if (llista==null) Log.d("test", "llista nula");
        return llista;
    }
}