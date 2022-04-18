package com.example.plant_pal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.plant_pal.databinding.ActivitySettingsBinding;
import com.example.plant_pal.databinding.FragmentFirstBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class Settings extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Plant Pal");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#8EFF8F"));
        actionBar.setBackgroundDrawable(colorDrawable);

        Intent intent = getIntent();
        String moisture = intent.getStringExtra("moist");
        String sunlight = intent.getStringExtra("sun");

        EditText manual_moisture = findViewById(R.id.manual_moisture);
        EditText manual_sunlight = findViewById(R.id.manual_sunlight);
        Switch auto_watering = findViewById(R.id.auto_watering);
        RadioGroup preset_moisture = findViewById(R.id.preset_moisture);
        RadioGroup preset_sunlight = findViewById(R.id.preset_sunlight);

        manual_moisture.setText(moisture);
        manual_sunlight.setText(sunlight);

        preset_moisture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if(i != -1) {
                    RadioButton label = (RadioButton)findViewById(i);

                    if(label.getText().toString().equals("Low")) {
                        manual_moisture.setText("15");
                    }
                    if(label.getText().toString().equals("Medium")) {
                        manual_moisture.setText("30");
                    }
                    if(label.getText().toString().equals("High")) {
                        manual_moisture.setText("50");
                    }
                }
            }
        });

        preset_sunlight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if(i != -1) {
                    RadioButton label = (RadioButton)findViewById(i);
                    if(label.getText().toString().equals("Low")) {
                        manual_sunlight.setText("20");
                    }
                    if(label.getText().toString().equals("Partial")) {
                        manual_sunlight.setText("40");
                    }
                    if(label.getText().toString().equals("Full")) {
                        manual_sunlight.setText("60");
                    }
                }
            }
        });

        binding.saveChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    double moist = Integer.parseInt(manual_moisture.getText().toString()) /100.0;
                    double sun = Integer.parseInt(manual_sunlight.getText().toString()) /100.0;
                    int auto;
                    if(auto_watering.isChecked()) { auto = 1; } else { auto = 0; }
                    updateMoistureSunlight(moist, sun, auto);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateMoistureSunlight(double moisture, double sunlight, int auto) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://" + config.IP_ADDRESS + "/php/updateMoistureSunlight.php?moistureThreshold=" + moisture +
                "&sunlightThreshold=" + sunlight + "&autoWatering=" + auto;

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            queue.add(jsonObjectRequest);
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {}
    }

}
