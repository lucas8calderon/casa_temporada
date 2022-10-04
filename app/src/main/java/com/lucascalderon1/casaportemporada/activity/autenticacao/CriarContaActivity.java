package com.lucascalderon1.casaportemporada.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lucascalderon1.casaportemporada.R;
import com.lucascalderon1.casaportemporada.activity.MainActivity;
import com.lucascalderon1.casaportemporada.helper.FirebaseHelper;
import com.lucascalderon1.casaportemporada.model.Usuario;

public class CriarContaActivity extends AppCompatActivity {


    private EditText edit_nome, edit_telefone, edit_senha, edit_email;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);


        configCliques();
        iniciaComponentes();
    }

    public void validaDados(View view) {
        String nome = edit_nome.getText().toString();
        String email = edit_email.getText().toString();
        String telefone = edit_telefone.getText().toString();
        String senha = edit_senha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!telefone.isEmpty()) {
                    if (!senha.isEmpty()) {

                        progressBar.setVisibility(View.VISIBLE);


                        Usuario usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmal(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);

                        cadastrarUsuario(usuario);



                    } else {
                        edit_senha.requestFocus();
                        edit_senha.setError("Informe uma senha.");
                    }
                } else {
                    edit_telefone.requestFocus();
                    edit_telefone.setError("Informe um telefone.");
                }

            } else  {
                edit_email.requestFocus();
                edit_email.setError("Informe um e-mail.");

            }

    } else

    {
        edit_nome.requestFocus();
        edit_nome.setError("Informe seu nome.");

    }

}

private void cadastrarUsuario(Usuario usuario) {
    FirebaseHelper.getAuth().createUserWithEmailAndPassword(
            usuario.getEmal(), usuario.getSenha()
    ).addOnCompleteListener(task -> {
        if (task.isSuccessful()) {

            String idUser = task.getResult().getUser().getUid();
            usuario.setId(idUser);
            usuario.salvar();

            finish();
            startActivity(new Intent(this, MainActivity.class));

        } else {
            String error = task.getException().getMessage();
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }

    });

}


    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());

    }

    private void iniciaComponentes() {

        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Crie sua conta");
    }

}