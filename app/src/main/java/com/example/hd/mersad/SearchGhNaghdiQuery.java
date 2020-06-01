package com.example.hd.mersad;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by hd on 2018/8/1 AD.
 */
public class SearchGhNaghdiQuery {

    boolean imposeMySchedule;
    String skills;
    String username;
    String tags;

    public SearchGhNaghdiQuery(boolean imposeMySchedule, String skills, String tags, String username) {
        this.skills = skills;
        this.tags = tags;
        this.imposeMySchedule = imposeMySchedule;
        this.username = username;
    }
}
