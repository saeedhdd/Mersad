package com.example.hd.mersad;

import java.util.Date;

/**
 * Created by hd on 2018/8/1 AD.
 */
public class SearchNaghdiQuery {

    String inst_name;
    String proj_name;
    int min_progress;
    int max_progress;
    String min_deadline;
    String max_deadline;

    public SearchNaghdiQuery(String inst_name, String proj_name, int min_progress, int max_progress, String min_deadline, String max_deadline) {
        this.inst_name = inst_name;
        this.proj_name = proj_name;
        this.min_progress = min_progress;
        this.max_progress = max_progress;
        this.min_deadline = min_deadline;
        this.max_deadline = max_deadline;
    }
}