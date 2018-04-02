package com.example.webservicesqite2.Adpters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.webservicesqite2.Main2Activity;
import com.example.webservicesqite2.Models.UserModel;
import com.example.webservicesqite2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shriji on 31/3/18.
 */

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.MyViewHolder> {

    private Activity mContext;
    private List<UserModel> mList;

    public UserAdpter(Activity context, List<UserModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.useradpterlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final UserModel userModel = mList.get(position);
        holder.mTxtAid.setText(String.valueOf(userModel.getId()));
        holder.mTxtAname.setText(userModel.getName());
        holder.mTxtAphone.setText(userModel.getPhone());
        holder.mImgAuser.setImageURI(Uri.parse(userModel.getImage()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Main2Activity.class);
                intent.putExtra("MODEL",userModel);
                mContext.startActivityForResult(intent,1234);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgAuser)
        ImageView mImgAuser;
        @BindView(R.id.txtAid)
        TextView mTxtAid;
        @BindView(R.id.txtAname)
        TextView mTxtAname;
        @BindView(R.id.txtAphone)
        TextView mTxtAphone;
        View mView;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }
}
