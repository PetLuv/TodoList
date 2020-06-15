package ru.nehodov.todolist;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //define view objects
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignup;
    TextView textviewSingin;
    TextView textviewMessage;
    //ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;

    //
    TextView Text_mypage;
    ImageButton Img_myPage;
    TextView Text_todo;
    ImageButton Img_todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class)); //추가해 줄 ProfileActivity
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSingin= (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        //progressDialog = new ProgressDialog(this);

        //
        Text_mypage = (TextView) findViewById(R.id.Text_mypage);
        Img_myPage = (ImageButton) findViewById(R.id.Img_myPage);
        Text_todo = (TextView) findViewById(R.id.Text_todo);
        Img_todo = (ImageButton) findViewById(R.id.Img_todo);

        //button click event
        buttonSignup.setOnClickListener(this);
        textviewSingin.setOnClickListener(this);
        //
        Text_mypage.setOnClickListener(this);
        Img_myPage.setOnClickListener(this);
        Text_todo.setOnClickListener(this);
        Img_todo.setOnClickListener(this);
    }

    //Firebse creating a new user
    private void registerUser(){
        //사용자가 입력하는 email, password를 가져온다.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //email과 password가 비었는지 아닌지를 체크 한다.
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }


        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        //progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        //progressDialog.show();


        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            //에러발생시
                            textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                            Toast.makeText(MainActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                        }
                        //progressDialog.dismiss();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup) {
            //TODO
            registerUser();

        }

        if(view == textviewSingin) {
            //TODO
            startActivity(new Intent(this, LoginActivity.class)); //추가해 줄 로그인 액티비티
        }
        if(view == Text_todo || view == Img_todo) {
            //TODO
            startActivity(new Intent(this, HostActivity.class)); //추가해 줄 로그인 액티비티
        }
        if(view == Text_mypage || view == Img_myPage ) {
            //TODO
            //startActivity(new Intent(this, LoginActivity.class)); //추가해 줄 로그인 액티비티
            if(firebaseAuth.getCurrentUser() == null){
                Toast.makeText(MainActivity.this, "로그인 해주세요", Toast.LENGTH_LONG).show();
                //그리고 profile 액티비티를 연다.
                startActivity(new Intent(getApplicationContext(), LoginActivity.class)); //추가해 줄 ProfileActivity
            }
            else startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
    }
}
