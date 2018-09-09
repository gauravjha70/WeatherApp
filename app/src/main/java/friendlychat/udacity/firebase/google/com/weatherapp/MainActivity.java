package friendlychat.udacity.firebase.google.com.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    ProgressBar loader;
    String city = "Vellore,India";

    public static TextView place,country,temperature,windSpeed,windDirection,pressure,humidity,weather,weathericon;

    //API Key (Open Weather Api)
    String OPEN_WEATHER_MAP_API = "cbfdb21fa1793c10b14b6b6d00fbef03";

    Double latitude=0.0,longitude=0.0;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        place = findViewById(R.id.place);
        country = findViewById(R.id.country);
        temperature = findViewById(R.id.temp);
        windSpeed = findViewById(R.id.wind_speed);
        windDirection = findViewById(R.id.wind_dir);
        humidity = findViewById(R.id.humidity_value);
        pressure = findViewById(R.id.pressure_value);
        weather = findViewById(R.id.weather);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this,"Cannot access location",Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        latitude = (location.getLatitude());
                        longitude = (location.getLongitude());
                        DownloadWeather downloadWeather = new DownloadWeather();
                        downloadWeather.execute("https://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude)
                                +"&appid="+OPEN_WEATHER_MAP_API);

                    }
                });




    }
}
