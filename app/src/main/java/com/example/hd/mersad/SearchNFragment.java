package com.example.hd.mersad;



import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchNFragment extends seachFatherFragment {
    Button naghdiSearchButton;
    EditText min_deadline;
    EditText max_deadline;
    EditText max_progress;
    EditText min_progress;
    EditText inst_name;
    EditText proj_name;


    public static SearchNFragment newSlide(){
        SearchNFragment fragment = new SearchNFragment();
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
        final View view = inflater.inflate(R.layout.searchnaghdi, container, false);
        inst_name = (EditText) view.findViewById(R.id.inst_name);
        proj_name = (EditText) view.findViewById(R.id.proj_name);
        min_deadline = (EditText) view.findViewById(R.id.min_deadline);
        max_deadline = (EditText) view.findViewById(R.id.max_deadline);
        min_progress = (EditText) view.findViewById(R.id.min_prog);
        max_progress = (EditText) view.findViewById(R.id.max_prog);
        naghdiSearchButton = (Button) view.findViewById(R.id.search_naghdi_button) ;


        naghdiSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inst_name.setError(null);
                int min_prog = 0;
                int max_prog = 100;
                try{min_prog = Integer.parseInt(min_progress.getText().toString());}catch (Exception e){};
                try{max_prog = Integer.parseInt(max_progress.getText().toString());}catch (Exception e){};

                SearchNaghdiQuery searchNaghdiQuery = new SearchNaghdiQuery(inst_name.getText().toString(),proj_name.getText().toString(),
                        min_prog ,max_prog,
                        min_deadline.getText().toString(),max_deadline.getText().toString());
//                String json = "  {\n" +
//                        "    \"inst_name\": \""+inst_name.getText().toString()+"\",\n" +
//                        "    \"proj_name\": \""+proj_name.getText().toString()+"\",\n" +
//                        "    \"min_progress\": "+min_progress.getText().toString()+",\n" +
//                        "    \"max_progress\": "+max_progress.getText().toString()+",\n" +
//                        "    \"min_deadline\": \""+min_deadline.getText().toString()+"\",\n" +
//                        "    \"max_deadline\": \""+max_deadline.getText().toString()+"\"\n" +
//                        "      }";
//                final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);
                MyApp.myApi.searchNPost(searchNaghdiQuery).enqueue(new Callback<SearchRecord[]>() {
                    @Override
                    public void onResponse(Call<SearchRecord[]> call, Response<SearchRecord[]> response) {
                        SearchRecord[] records = response.body();
                        if (records==null){return;}
                        TableLayout tl = (TableLayout)view.findViewById(R.id.naghdi_search_table);
                        for (int i = 0; i < records.length; i++) {
                            addRecord(view,records[i],tl);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchRecord[]> call, Throwable t) {
                        inst_name.setError(getString(R.string.error_connection));
                        inst_name.requestFocus();
                    }
                });
            }
        });



        return view;
    }


}
