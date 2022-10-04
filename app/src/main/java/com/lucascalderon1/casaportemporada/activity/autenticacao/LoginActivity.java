package com.lucascalderon1.casaportemporada.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lucascalderon1.casaportemporada.R;
import com.lucascalderon1.casaportemporada.activity.MainActivity;
import com.lucascalderon1.casaportemporada.helper.FirebaseHelper;

import kotlin.jvm.internal.PropertyReference0Impl;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_email, edit_senha;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configCliques();
        iniciaComponentes();
    }

    private void configCliques() {
        findViewById(R.id.text_criar_conta).setOnClickListener(view ->
                startActivity(new Intent(this, CriarContaActivity.class)));

        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());

        findViewById(R.id.text_recuperar_conta).setOnClickListener(view ->
                startActivity(new Intent(this, RecuperarContaActivity.class)));



    }

    public void validaDados(View view) {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                progressBar.setVisibility(View.VISIBLE);
                logar(email, senha);

            } else {
                edit_senha.requestFocus();
                edit_senha.setError("Informe sua senha.");
            }

        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu e-mail.");

        }


    }

    private void logar(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                finish();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Bem vindo ao App!", Toast.LENGTH_SHORT).show();

            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

            }

        });
    }


    private void iniciaComponentes() {

        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progressBar);



    }


}