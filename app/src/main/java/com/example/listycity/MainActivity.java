package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;

    private Button btnAddCity, btnDeleteCity, btnConfirm;
    private EditText etCity;
    private View addPanel;

    private int selectedPosition = -1; // -1 means “nothing selected”

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1) Grab views
        cityList = findViewById(R.id.city_list);
        btnAddCity = findViewById(R.id.btn_add_city);
        btnDeleteCity = findViewById(R.id.btn_delete_city);
        btnConfirm = findViewById(R.id.btn_confirm);
        etCity = findViewById(R.id.et_city);
        addPanel = findViewById(R.id.add_panel);

        // 2) Initial data
        String[] cities = {"Lahore", "Karachi", "Islamabad", "Rawalpindi", "Multan"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        // 3) Adapter: connects dataList -> content.xml row
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, R.id.content_view, dataList);
        cityList.setAdapter(cityAdapter);

        // 4) Selecting an item
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityList.setItemChecked(position, true); // gives the “highlight” behavior
        });

        // 5) ADD CITY button: show the add panel
        btnAddCity.setOnClickListener(v -> {
            addPanel.setVisibility(View.VISIBLE);
            etCity.requestFocus();
        });

        // 6) CONFIRM button: actually add
        btnConfirm.setOnClickListener(v -> {
            String newCity = etCity.getText().toString().trim();

            if (newCity.isEmpty()) {
                Toast.makeText(this, "Enter a city name", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();

            etCity.setText("");
            addPanel.setVisibility(View.GONE);
        });

        // 7) DELETE CITY button: remove selected
        btnDeleteCity.setOnClickListener(v -> {
            if (selectedPosition == -1) {
                Toast.makeText(this, "Tap a city to select it first", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();

            // Reset selection
            selectedPosition = -1;
            cityList.clearChoices();
        });
    }
}
