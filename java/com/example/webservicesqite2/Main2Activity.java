package com.example.webservicesqite2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.webservicesqite2.Models.UserModel;
import com.example.webservicesqite2.MyWebServices.MyWebServiceR;
import com.example.webservicesqite2.database.DBOperations;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.imgUser)
    ImageView mImgUser;
    @BindView(R.id.edtName)
    EditText mEdtName;
    @BindView(R.id.edtPhone)
    EditText mEdtPhone;
    @BindView(R.id.btnAdd)
    Button mBtnAdd;
    @BindView(R.id.btnEdit)
    Button mBtnEdit;
    @BindView(R.id.btnDelete)
    Button mBtnDelete;
    private MyWebServiceR mMyWebServiceR;
    private UserModel mUserModel;
    private DBOperations mDbOperations;
    private String mPicturePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            //   Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            checkPermissionRunTime();
        }

        mDbOperations = new DBOperations(this);
        mDbOperations.openDB();
        mMyWebServiceR = MyWebServiceR.retrofit.create(MyWebServiceR.class);

        if (getIntent().hasExtra("MODEL")) {
            mUserModel = getIntent().getExtras().getParcelable("MODEL");
            mEdtName.setText(mUserModel.getName());
            mEdtPhone.setText(mUserModel.getPhone());
            mPicturePath = mUserModel.getImage();
            mImgUser.setImageURI(Uri.parse(mPicturePath));
        }
    }

    private void checkPermissionRunTime() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                3003);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3003) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDbOperations.closeDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDbOperations.openDB();
    }

    @OnClick({R.id.imgUser, R.id.btnAdd, R.id.btnEdit, R.id.btnDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgUser:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1001);
                break;
            case R.id.btnAdd:
                savedata(1);
                break;
            case R.id.btnEdit:
                savedata(2);
                break;
            case R.id.btnDelete:
                Call<String> call = mMyWebServiceR.deleteUsers(3, mUserModel.getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(Main2Activity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        finishandresult();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("", "=== onFail: " + t.getMessage());
                        t.printStackTrace();
                        Toast.makeText(Main2Activity.this, "Fail: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                break;
        }
    }


    private void savedata(int operation) {
        boolean isError = false;
        String name, phone, image;
        int flag = 1;
        name = mEdtName.getText().toString();
        phone = mEdtPhone.getText().toString();
        image = mPicturePath;

        if (name.isEmpty()) {
            mEdtName.setError("Plz fill name");
            isError = true;
        }

        if (phone.isEmpty()) {
            mEdtPhone.setError("plz fill phone");
            isError = true;
        }

        if (isError) {
            return;
        }

        if (operation == 1) {
            mMyWebServiceR.saveUser(flag, name, phone, image).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("", "=== onResponse: " + response.code());
                    Log.d("", "=== onResponse: " + response.body());
                    Toast.makeText(Main2Activity.this, "Success: " + response.body(), Toast.LENGTH_SHORT).show();
                    finishandresult();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("", "=== onFailure: " + t.getMessage());
                    t.printStackTrace();
                    Toast.makeText(Main2Activity.this, "Fail: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("", "onFailure: >>>>>>>>>>>>>>>>>>> " + t.getMessage());
                }
            });
        }

        if (operation == 2) {
            int id = mUserModel.getId();
            mMyWebServiceR.updateUser(flag, id, name, phone, image).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("", "=== onResponse: " + response.code());
                    Log.d("", "=== onResponse: " + response.body());
                    Toast.makeText(Main2Activity.this, "Success: " + response.body(), Toast.LENGTH_SHORT).show();
                    finishandresult();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("", "=== onFailure: " + t.getMessage());
                    t.printStackTrace();
                    Toast.makeText(Main2Activity.this, "Fail: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("", "onFailure: >>>>>>>>>>>>>>>>>>> " + t.getMessage());
                }
            });
        }


    }

    void finishandresult() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mPicturePath = cursor.getString(columnIndex);
            cursor.close();
            mImgUser.setImageURI(Uri.parse(mPicturePath));

        }
    }
}
