package friendlychat.udacity.firebase.google.com.weatherapp;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class DownloadWeather extends AsyncTask<String,Void,String> {

    String result="";
    URL url;
    HttpURLConnection httpURLConnection;

    @Override
    protected String doInBackground(String... urls) {
        try {
            url = new URL(urls[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            Log.e("message",String.valueOf(url));

            int data = reader.read();

            while(data!=-1)
            {
                char chardata = (char)data;
                result += chardata;

                data = reader.read();
            }
            return result;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        try{
            JSONObject json = new JSONObject(result);
            if(json!=null)
            {
                 JSONObject main = new JSONObject(json.getString("main"));
                 int temperature = (int)(main.getDouble("temp")-273.15);
                 String place = json.getString("name");
                 String country = json.getJSONObject("sys").getString("country");
                 String humidity = main.getString("humidity");
                 String pressure = main.getString("pressure");
                 String windSpeed = json.getJSONObject("wind").getString("speed");
                 String windDir = json.getJSONObject("wind").getString("deg");
                 String weather = json.getJSONArray("weather").getJSONObject(0).getString("description");
                 /*int actualId = json.getJSONArray("weather").getJSONObject(0).getInt("id");
                 String icon = "&#xf01e;";

                 int id = actualId / 100;
                    switch(id) {
                        case 2 : icon = "&#xf01e;";
                            break;
                        case 3 : icon = "&#xf01c;";
                            break;
                        case 7 : icon = "&#xf014;";
                            break;
                        case 8 : icon = "&#xf013;";
                            break;
                        case 6 : icon = "&#xf01b;";
                            break;
                        case 5 : icon = "&#xf019;";
                            break;
                    }*/


                 //Setting the values to the holder
                 MainActivity.place.setText(place);
                 MainActivity.country.setText(country);
                 MainActivity.temperature.setText(String.valueOf(temperature));
                 MainActivity.pressure.setText(pressure+" mb");
                 MainActivity.humidity.setText(humidity+" %");
                 MainActivity.windDirection.setText(windDir+" N");
                 MainActivity.windSpeed.setText(windSpeed+" km/h");
                 MainActivity.weather.setText(weather.toUpperCase());

            }

        }
        catch (Exception e){
        }
        return;
    }
}
