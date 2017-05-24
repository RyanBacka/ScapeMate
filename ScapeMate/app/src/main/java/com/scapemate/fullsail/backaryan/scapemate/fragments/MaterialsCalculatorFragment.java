package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.objects.Materials;

import java.util.ArrayList;
import java.util.Locale;

public class MaterialsCalculatorFragment extends Fragment implements View.OnClickListener, TextWatcher {


    private static final String TAG = "MaterialsCalcFrag";
    ArrayList<Materials> materialsList = new ArrayList<>();

    public MaterialsCalculatorFragment() {
        // Required empty public constructor
    }

    public static MaterialsCalculatorFragment newInstance() {
        return new MaterialsCalculatorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materials_calculator, container, false);
        view.findViewById(R.id.materialsSave).setOnClickListener(this);
        getActivity().findViewById(R.id.menu).setOnClickListener(this);
        ((EditText) view.findViewById(R.id.widthET)).addTextChangedListener(this);
        ((EditText) view.findViewById(R.id.lengthET)).addTextChangedListener(this);
        ((EditText) view.findViewById(R.id.depthET)).addTextChangedListener(this);
        ((EditText) view.findViewById(R.id.costOfMaterialsET)).addTextChangedListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu) {
            MenuFragment menuFragment = MenuFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, menuFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            EditText widthET = ((EditText)getActivity().findViewById(R.id.widthET));
            EditText lengthET = ((EditText)getActivity().findViewById(R.id.lengthET));
            EditText depthET = ((EditText)getActivity().findViewById(R.id.depthET));
            EditText costET = ((EditText)getActivity().findViewById(R.id.costOfMaterialsET));
            EditText typeOfMaterialET = ((EditText)getActivity().findViewById(R.id.materialsET));
            if(widthET.getText().toString().isEmpty() || lengthET.getText().toString().isEmpty() ||
                    depthET.getText().toString().isEmpty() || costET.getText().toString().isEmpty() ||
                    typeOfMaterialET.getText().toString().isEmpty()){
                Toast.makeText(getActivity(),"One or more fields is empty",Toast.LENGTH_LONG).show();
            } else {
                double width = Double.parseDouble(widthET.getText().toString());
                double length = Double.parseDouble(lengthET.getText().toString());
                double depth = Double.parseDouble(depthET.getText().toString());
                double cost = Double.parseDouble(costET.getText().toString());
                double cubicYards = getCubicYards(width, length, depth);
                Materials materials = new Materials(cubicYards, getCost(cubicYards, cost), typeOfMaterialET.getText().toString());
                materialsList.add(materials);
            }
            DataHelper dataHelper = new DataHelper();
            dataHelper.saveMaterials(materialsList,getActivity());
            NewBIdFragment newBIdFragment = NewBIdFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer,newBIdFragment)
                    .commit();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        EditText widthET = ((EditText) getActivity().findViewById(R.id.widthET));
        EditText lengthET = ((EditText) getActivity().findViewById(R.id.lengthET));
        EditText depthET = ((EditText) getActivity().findViewById(R.id.depthET));
        EditText costOfMaterialET = ((EditText) getActivity().findViewById(R.id.costOfMaterialsET));
        if (!widthET.getText().toString().isEmpty() && !lengthET.getText().toString().isEmpty() &&
                !depthET.getText().toString().isEmpty() && !costOfMaterialET.getText().toString().isEmpty()) {
            setTotalCost(Double.parseDouble(widthET.getText().toString()),
                    Double.parseDouble(lengthET.getText().toString()),
                    Double.parseDouble(depthET.getText().toString()),
                    Double.parseDouble(costOfMaterialET.getText().toString()));
        }

    }

    public void setTotalCost(double width, double length, double depth, double costOfMaterial) {
        double cubicYards = getCubicYards(width,length,depth);
        double cost = getCost(cubicYards,costOfMaterial);
        String costString = String.format(Locale.ENGLISH, "%.2f", cost);
        String totalString = "Total Cost: $" + costString;
        ((TextView) getActivity().findViewById(R.id.totalCostTV)).setText(totalString);
    }

    public double getCubicYards(double width, double length, double depth){
        return ((width*12) * (length*12) * depth)/46656;
    }

    public double getCost(double cubicYards, double costOfMaterials){
        return cubicYards * costOfMaterials;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText widthET = ((EditText)getActivity().findViewById(R.id.widthET));
        EditText lengthET = ((EditText)getActivity().findViewById(R.id.lengthET));
        EditText depthET = ((EditText)getActivity().findViewById(R.id.depthET));
        EditText costET = ((EditText)getActivity().findViewById(R.id.costOfMaterialsET));
        EditText typeOfMaterialET = ((EditText)getActivity().findViewById(R.id.materialsET));
        if(widthET.getText().toString().isEmpty() || lengthET.getText().toString().isEmpty() ||
                depthET.getText().toString().isEmpty() || costET.getText().toString().isEmpty() ||
                typeOfMaterialET.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),"One or more fields is empty",Toast.LENGTH_LONG).show();
        } else {
            double width = Double.parseDouble(widthET.getText().toString());
            double length = Double.parseDouble(lengthET.getText().toString());
            double depth = Double.parseDouble(depthET.getText().toString());
            double cost = Double.parseDouble(costET.getText().toString());
            double cubicYards = getCubicYards(width, length, depth);
            Materials materials = new Materials(cubicYards, getCost(cubicYards, cost), typeOfMaterialET.getText().toString());
            materialsList.add(materials);
        }
        widthET.getText().clear();
        lengthET.getText().clear();
        depthET.getText().clear();
        costET.getText().clear();
        typeOfMaterialET.getText().clear();
        Toast.makeText(getActivity(),"Enter more materials",Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
}
