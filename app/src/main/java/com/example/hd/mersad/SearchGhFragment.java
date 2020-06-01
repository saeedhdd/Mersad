package com.example.hd.mersad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hd on 2018/8/1 AD.
 */

public class SearchGhFragment extends seachFatherFragment {

        Button naghdiSearchButton;
//        Spinner dayTime;
//        Spinner weekday;
        Spinner tagss;
        EditText skills;
        Switch imposeSchedule;


        public static SearchGhFragment newSlide(){
            SearchGhFragment fragment = new SearchGhFragment();
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.searchnonaghdi, container, false);
//            dayTime = (Spinner) view.findViewById(R.id.daytime);
            tagss = (Spinner) view.findViewById(R.id.skill_select);
//            weekday = (Spinner) view.findViewById(R.id.weekday);
            skills = (EditText) view.findViewById(R.id.tags);
            imposeSchedule = (Switch) view.findViewById(R.id.impose_schedule);
            naghdiSearchButton = (Button) view.findViewById(R.id.search_gh_naghdi_button) ;

            Log.i("ruhi","sekal");
            MyApp.myApi.getSkills().enqueue(new Callback<String[]>() {

                @Override
                public void onResponse(Call<String[]> call, Response<String[]> response) {
                    skills.setError(null);
                    tagss.setEnabled(true);
                    String[] entries = response.body();
                    if(entries==null){return;}
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,entries);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tagss.setAdapter(dataAdapter);

                }

                @Override
                public void onFailure(Call<String[]> call, Throwable t) {
                    tagss.setEnabled(false);
                    skills.setError(getString(R.string.error_connection));
                    skills.requestFocus();
                }
            });




            naghdiSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skills.setError(null);

                    String selectedTag = tagss.getSelectedItem()==null?"":tagss.getSelectedItem().toString();

                    SearchGhNaghdiQuery searchGhNaghdiQuery = new SearchGhNaghdiQuery(
                            imposeSchedule.isChecked(),
                            skills.getText().toString(),
                            selectedTag,
                            User.user.username
                    );

                    MyApp.myApi.searchGhPost(searchGhNaghdiQuery).enqueue(new Callback<SearchRecord[]>() {
                        @Override
                        public void onResponse(Call<SearchRecord[]> call, Response<SearchRecord[]> response) {
                            SearchRecord[] records = response.body();
                            if (records==null) return;
                            TableLayout tl = (TableLayout)view.findViewById(R.id.gh_naghdi_search_table);
                            for (int i = 0; i < records.length; i++) {
                                addRecord(view,records[i],tl);
                            }
                        }

                        @Override
                        public void onFailure(Call<SearchRecord[]> call, Throwable t) {
                            skills.setError(getString(R.string.error_connection));
                            skills.requestFocus();
                        }
                    });
                }
            });



            return view;
        }




}
