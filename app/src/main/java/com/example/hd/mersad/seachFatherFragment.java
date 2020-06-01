package com.example.hd.mersad;

import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by hd on 2018/8/1 AD.
 */

public class seachFatherFragment extends Fragment {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addRecord(View view, SearchRecord searchRecord, TableLayout tl) {
        TextView[] viewsInRow = new TextView[4];
        TableRow row = new TableRow(getActivity());
        for (int i = 0; i < 4 ; i++) {
            TextView tv = new TextView(getActivity());
            tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/IRANSANS_LIGHT.TTF"));
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            viewsInRow[i] = tv;
            TextView tx = (TextView) view.findViewById(R.id.day_record_search_naghdi);
            tv.setLayoutParams(tx.getLayoutParams());
            row.addView(tv);

        }
        viewsInRow[3].setText(searchRecord.proj_name);
        viewsInRow[2].setText(searchRecord.inst_name);
        viewsInRow[1].setText(searchRecord.tags);
        viewsInRow[0].setText(searchRecord.time_intervals);

        tl.addView(row);
    }
}
