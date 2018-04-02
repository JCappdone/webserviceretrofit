package com.example.webservicesqite2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.webservicesqite2.Adpters.UserAdpter;
import com.example.webservicesqite2.Models.UserModel;
import com.example.webservicesqite2.MyWebServices.MyWebServiceR;
import com.example.webservicesqite2.database.DBOperations;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<UserModel> mList;
    private UserAdpter mUserAdpter;
    private MyWebServiceR mMyWebServiceR;
    private DBOperations mDbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mList = new ArrayList<>();
        mUserAdpter = new UserAdpter(this, mList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mUserAdpter);

        mDbOperations = new DBOperations(this);
        mDbOperations.openDB();

        mMyWebServiceR = MyWebServiceR.retrofit.create(MyWebServiceR.class);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenu:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivityForResult(intent, 1234);
                return true;
            case R.id.undoAllMenu:
                undoAllData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234 && resultCode == RESULT_OK) {
            getAllUserInList();

        }
    }

    private void getAllUserInList() {
        Call<List<UserModel>> call = mMyWebServiceR.getUsers(4);
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                Log.d("", "=== onResponse: " + response.code());
                Log.d("", "=== onResponse: " + response.message());
                mList.clear();
                mList.addAll(response.body());
                mUserAdpter.notifyDataSetChanged();
                Log.d("", "-------------------------------------------" + mList.size());
                for (UserModel userModel : mList) {
                    mDbOperations.insertUser(userModel);
                }
            }
            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Log.d("", "=== onFail: " + t.getMessage());
                t.printStackTrace();
            }
        });


    }

    private void undoAllData() {
        if (isNetworkConnected()) {
            Call<String> call = mMyWebServiceR.undoAllUsers(5);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    getAllUserInList();
                    Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        } else {
            mList.clear();
            mList.addAll(mDbOperations.getAllUser());
            mUserAdpter.notifyDataSetChanged();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
