//package com.twocoms.rojgarkendra.documentsscreen.controler;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.twocoms.rojgarkendra.R;
//
//public class FrgPostOnBoarding extends Fragment {
//
//    FloatingActionButton uploadDocBtn;
//    RecyclerView recyclerView;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.frgpostonboardinglayout, container, false);
//        initialization(rootView);
//        onClick();
//        return rootView;
//    }
//
//    void initialization(View view){
//        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
//        uploadDocBtn = (FloatingActionButton)view.findViewById(R.id.add_doc);
//    }
//
//    void onClick(){
//        uploadDocBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), UploadDocumentActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}
