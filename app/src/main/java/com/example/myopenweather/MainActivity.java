package com.example.myopenweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView selectCity, cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    private ProgressBar loader;
    private Typeface weatherFont;
    private String city = "Barcelona, SPN"; //CIUDAD POR DEFECTO
    final static String OPEN_WEATHER_MAP_API = "275d21f98ce6515e48e663fce25dd1f6";
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //TODOS LOS TEXTVIEW MUESTRAN EL PRONOSTICO DEL TIEMPO
        super.onCreate(savedInstanceState);             //UTILIZO ICONOS PARA MOSTRAR LOS DIFERENTES PRONOTISCOS(SOLEADO, NUBLADO, ETC) -- SE ENCUENTRAN EN LA CARPETA ASSESTS/FONTS/
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));

        loader = (ProgressBar) findViewById(R.id.loader);
        selectCity = (TextView) findViewById(R.id.selectCity);
        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        //weatherFont = Typeface.createFromAsset(getAssets(), "/fonts/weathericons-regular-webfont.ttf"); //Iconos del tiempo
        weatherFont = ResourcesCompat.getFont(this, R.font.weathericonsfont);
        //Typeface typeface = ResourcesCompat.getFont(context, R.font.myfont);

        weatherIcon.setTypeface(weatherFont);
        LoadCity(city);

        selectCity.setOnClickListener(new View.OnClickListener() { //da la opcion de cambiar la ciudad sin acceder al menu de opciones
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Change City");
                final EditText input = new EditText(MainActivity.this);
                input.setText(city);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Change",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                city = input.getText().toString();
                                LoadCity(city);
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    public void LoadCity(String city) { //void para cargar la ciudad
        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather downloadWeather = new DownloadWeather();
            downloadWeather.execute(city);
        } else {
            Toast.makeText(getApplicationContext(), "Sin conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public class DownloadWeather extends AsyncTask< String, Void, String > { //Downloader para los TextViews
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String...args) {
            String xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                    "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();

                    cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + "°");
                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    updatedField.setText(df.format(new Date(json.getLong("dt") * 1000)));
                    weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));

                    loader.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.weather_menu){ //da la opcion de cambiar la ciudad desde el menu de opciones

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Change City");
                    final EditText input = new EditText(MainActivity.this);
                    input.setText(city);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("Change",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    city = input.getText().toString();
                                    LoadCity(city);
                                }
                            });
                    alertDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();

            return true;
        }

        if (id == R.id.info_menu){ //aqui habria que crear un intent con el fragment para mostrar el RecyclerViewer o en su defecto mostrar los resultados en la actividad principal

            //TODO -- Crear un intent con RecyclerViewerClass
            Intent intent = new Intent(this, RecyclerVieweresList.class);
            intent.putExtra("city", city);
            startActivity(intent);
            //Log.d("Intent", String.valueOf(intent));
            return true;
        }

        if (id == R.id.map_box_menu){

            Intent intent = new Intent(this, Map.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
