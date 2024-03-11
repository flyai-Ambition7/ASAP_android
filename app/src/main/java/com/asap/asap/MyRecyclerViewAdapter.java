package com.asap.asap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {
    /*
  어댑터의 동작원리 및 순서
  1.(getItemCount) 데이터 개수를 세어 어댑터가 만들어야 할 총 아이템 개수를 얻는다.
  2.(getItemViewType)[생략가능] 현재 itemview의 viewtype을 판단한다
  3.(onCreateViewHolder)viewtype에 맞는 뷰 홀더를 생성하여 onBindViewHolder에 전달한다.
  4.(onBindViewHolder)뷰홀더와 position을 받아 postion에 맞는 데이터를 뷰홀더의 뷰들에 바인딩한다.
  */
    String TAG = "RecyclerViewAdapter";

    //리사이클러뷰에 넣을 데이터 리스트
    ArrayList<RecyclerViewItem> dataModels;

    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public MyRecyclerViewAdapter(Context context, ArrayList<RecyclerViewItem> dataModels) {
        this.dataModels = dataModels;
        this.context = context;
    }


    public int getItemCount() {
        //데이터 리스트의 크기를 전달해주어야 함
        return dataModels.size();
    }


    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");

        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }


    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //  이미지 설정
        GlideApp.with(context).load(Uri.parse(dataModels.get(position).getImageUrl()))
                .placeholder(R.drawable.result_page_default_image)
                .into(myViewHolder.resultImageView);

        RecyclerViewItem item = dataModels.get(position);


        myViewHolder.resultCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelected(isChecked); // 아이템 내 상태 체크로 변경
                // 선택 유무에 따른 배경색
                if (isChecked){
                    holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
                }else holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        // ▼ 리사이클러 내의 아이템 클릭시 동작하는 부분
        myViewHolder.resultImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(context, R.layout.result_image_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                ImageView dialogImageView = dialogView.findViewById(R.id.resultImageDialogView);
                // 현재 클릭된 애의 url 가져오기
                String selectedImageUrl = dataModels.get(position).getImageUrl();

                // Glide로 동일하게 그려주기
                GlideApp.with(context).load(Uri.parse(selectedImageUrl))
                        .placeholder(R.drawable.result_page_default_image)
                        .into(dialogImageView);
                dlg.setTitle("결과 이미지");
                dlg.setIcon(R.drawable.asap_icon);
                dlg.setView(dialogView);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView resultImageView;
        CheckBox resultCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultImageView = itemView.findViewById(R.id.resultImageView);
            resultCheckBox = itemView.findViewById(R.id.resultCheckBox);
        }
    }


}