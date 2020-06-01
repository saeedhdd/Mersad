package com.example.hd.mersad;



        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;


public class dashBoardFrag extends Fragment {


    int layOutId;
    public static dashBoardFrag newSlide(int id){
        dashBoardFrag fragment = new dashBoardFrag();
        fragment.layOutId = id;

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
        View view = inflater.inflate(layOutId, container, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                transit(v);
//            }
//        });
        return view;
    }
}
