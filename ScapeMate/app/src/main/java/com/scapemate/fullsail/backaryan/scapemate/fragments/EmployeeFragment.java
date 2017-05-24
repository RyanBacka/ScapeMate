package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.Employee;

import java.util.ArrayList;

public class EmployeeFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "EmployeeFragment";
    DataHelper dataHelper = new DataHelper();

    public EmployeeFragment() {
        // Required empty public constructor
    }

    public static EmployeeFragment newInstance() {
        return new EmployeeFragment();
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
        View view = inflater.inflate(R.layout.fragment_employee, container, false);
        view.findViewById(R.id.employeeSave).setOnClickListener(this);
        getActivity().findViewById(R.id.menu).setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Employee employee = getEmployee();
        if (employee != null) {
            Company company = dataHelper.readCompany(getActivity());
            if (company.getEmployees() == null) {
                ArrayList<Employee> employees = readEmployees(employee);
                dataHelper.saveEmployee(employees, getActivity());
            } else {
                ArrayList<Employee> employees = company.getEmployees();
                employees.add(employee);
                company.setEmployees(employees);
                dataHelper.saveCompany(company,getActivity());
            }
            ((EditText) getActivity().findViewById(R.id.employeeNameET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.hourlyWageET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.averageHoursET)).getText().clear();
            Toast.makeText(getActivity(), "Add another Employee.", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu) {
            MenuFragment menuFragment = MenuFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, menuFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            final Employee employee = getEmployee();
            if (employee != null) {
                Company company = dataHelper.readCompany(getActivity());
                if (company.getEmployees() == null) {
                    ArrayList<Employee> employees = readEmployees(employee);
                    dataHelper.saveEmployee(employees, getActivity());
                }else{
                    ArrayList<Employee> employees = company.getEmployees();
                    employees.add(employee);
                    company.setEmployees(employees);
                    dataHelper.saveCompany(company,getActivity());
                }
                getActivity().finish();
            }
        }

    }

    private Employee getEmployee() {
        String name = ((EditText) getActivity().findViewById(R.id.employeeNameET)).getText().toString();
        String wage = ((EditText) getActivity().findViewById(R.id.hourlyWageET)).getText().toString();
        String hours = ((EditText) getActivity().findViewById(R.id.averageHoursET)).getText().toString();
        if (name.equalsIgnoreCase("") || wage.equalsIgnoreCase("") || hours.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "One or more fields is empty", Toast.LENGTH_LONG).show();
        } else {
            return new Employee(name, Double.parseDouble(wage), Double.parseDouble(hours));
        }
        return null;
    }

    private ArrayList<Employee> readEmployees(Employee employee) {
        ArrayList<Employee> employees;
        employees = dataHelper.readEmployee(getActivity());
        employees.add(employee);
        return employees;
    }

}
