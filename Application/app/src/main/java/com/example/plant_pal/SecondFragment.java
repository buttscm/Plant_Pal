package com.example.plant_pal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.plant_pal.databinding.FragmentSecondBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ListView listView;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> plantList;

    private int id;
    private double moistureThreshold;
    private double sunlightThreshold;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.plant_list_view);
        plantList = new ArrayList<>();

        getPlantList();
        buildList();

        EditText plant_filter = view.findViewById(R.id.plant_filter);

        plant_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {}

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                updateCurrentPlant(listView.getAdapter().getItem(i).toString());
                Toast.makeText(requireActivity(),"Plant species updated",Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        getPlantList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getPlantList() {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        String url = "http://" + config.IP_ADDRESS + "/php/getPlantList.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0; i < response.length(); i++) {
                            try {
                                    JSONObject responseObj = response.getJSONObject(i);
                                    String plantName = responseObj.getString("Plant Name");
                                    if(!(plantList.contains(plantName))) { plantList.add(plantName); }
                                    buildList();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(requireActivity(), "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }

    public void updateCurrentPlant(String plantName) {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        String url = "http://" + config.IP_ADDRESS + "/php/getPlant.php?plantName=" + plantName;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = response.getInt("ID");
                            String moistureThresholdStr = response.getString("Water Level");
                            String sunlightThresholdStr = response.getString("Sunlight Level");

                            if (moistureThresholdStr.indexOf('D') != -1) {
                                moistureThreshold = .15;
                            } else if (moistureThresholdStr.indexOf('M') != 1) {
                                moistureThreshold = .30;
                            } else if (moistureThresholdStr.contains("We")) {
                                moistureThreshold = .50;
                            } else {
                                Toast.makeText(requireActivity(), "Moisture Threshold not found. Please enter manually in settings", Toast.LENGTH_LONG).show();
                            }

                            if (sunlightThresholdStr.indexOf('F') != -1) {
                                sunlightThreshold = .20;
                            } else if (sunlightThresholdStr.indexOf('S') != 1) {
                                sunlightThreshold = .40;
                            } else if (sunlightThresholdStr.indexOf('N') != 1) {
                                sunlightThreshold = .60;
                            } else {
                                Toast.makeText(requireActivity(), "Sunlight Threshold not found. Please enter manually in settings", Toast.LENGTH_LONG).show();
                            }

                            updateCurrentPlant_fragment(id,plantName,moistureThreshold,sunlightThreshold);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(requireActivity(), "Fail to get current plant data..", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void updateCurrentPlant_fragment(int id, String plantName, double moistureThreshold, double sunlightThreshold)
    {
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        String url = "http://" + config.IP_ADDRESS + "/php/updatePlant.php?id=" + id + "&plantName=" + plantName +
                "&moistureThreshold=" + moistureThreshold + "&sunlightThreshold=" + sunlightThreshold;

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

    public void buildList() {

        adapter = new ArrayAdapter<String>(requireActivity(),
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                plantList);

        listView.setAdapter(adapter);
    }

}