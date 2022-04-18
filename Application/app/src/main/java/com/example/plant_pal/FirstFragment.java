package com.example.plant_pal;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.plant_pal.databinding.FragmentFirstBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    TextView moistureThreshold;
    TextView sunlightThreshold;
    TextView moistureLevel;
    TextView sunlightLevel;
    TextView plantNameLabel;

    private final static int INTERVAL = 5000;
    final Handler handler = new Handler();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        } catch(Exception e) {}

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moistureThreshold = view.findViewById(R.id.moisture_threshold);
        sunlightThreshold = view.findViewById(R.id.sunlight_threshold);
        moistureLevel = view.findViewById(R.id.moisture);
        sunlightLevel = view.findViewById(R.id.sunlight);
        plantNameLabel = view.findViewById(R.id.plantName);

        getCurrentPlantData();
        getWaterSun();

        try {
            if(Integer.parseInt(moistureLevel.getText().toString()) <
                    Integer.parseInt(moistureThreshold.getText().toString())) {
                moistureLevel.setTextColor(Color.parseColor("#F57F71"));
            } else {
                moistureLevel.setTextColor(Color.parseColor("#000000"));
            }
            if(Integer.parseInt(sunlightLevel.getText().toString()) <
                    Integer.parseInt(sunlightThreshold.getText().toString())) {
                sunlightLevel.setTextColor(Color.parseColor("#F57F71"));
            } else {
                sunlightLevel.setTextColor(Color.parseColor("#000000"));
            }
        } catch(Exception e) {}

        handler.postDelayed(new Runnable() {
            public void run() {
                getCurrentPlantData();
                getWaterSun();
                try {
                    if (Integer.parseInt(moistureLevel.getText().toString()) <
                            Integer.parseInt(moistureThreshold.getText().toString())) {
                        moistureLevel.setTextColor(Color.parseColor("#F57F71"));
                    } else {
                        moistureLevel.setTextColor(Color.parseColor("#000000"));
                    }
                    if (Integer.parseInt(sunlightLevel.getText().toString()) <
                            Integer.parseInt(sunlightThreshold.getText().toString())) {
                        sunlightLevel.setTextColor(Color.parseColor("#F57F71"));
                    } else {
                        sunlightLevel.setTextColor(Color.parseColor("#000000"));
                    }
                } catch(Exception e) {}
                handler.postDelayed(this,INTERVAL);
            }
        }, INTERVAL);

        binding.plantSpecies.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateLog();
                getCurrentPlantData();
                getWaterSun();
                Toast.makeText(requireActivity(),"Update request sent",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCurrentPlantData() {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        String url = "http://" + config.IP_ADDRESS + "/php/getCurrentPlant.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String plantName = response.getString("SelectedPlant");
                            double moistureThresholdVal = response.getDouble("MoistureThreshold");
                            double sunlightThresholdVal = response.getDouble("SunlightThreshold");

                            plantNameLabel.setText(plantName);
                            moistureThreshold.setText(String.valueOf(moistureThresholdVal * 100));
                            sunlightThreshold.setText(String.valueOf(sunlightThresholdVal * 100));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireActivity(), "Failed to get current plant data..", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void updateLog() {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        String url = "http://" + config.IP_ADDRESS + "/php/updateLog.php";

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
    }

    public void getWaterSun() {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        String url = "http://" + config.IP_ADDRESS + "/php/getLog.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            double currentSunlight = response.getDouble("CurrentSunlight") * 100;
                            double currentWater = response.getDouble("CurrentWater") * 100;
                            moistureLevel.setText(String.valueOf((int)currentWater));
                            sunlightLevel.setText(String.valueOf((int)currentSunlight));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(jsonObjectRequest);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}