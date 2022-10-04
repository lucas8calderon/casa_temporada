package com.lucascalderon1.casaportemporada.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.lucascalderon1.casaportemporada.R;
import com.lucascalderon1.casaportemporada.adapter.AdapterAnuncios;
import com.lucascalderon1.casaportemporada.helper.FirebaseHelper;
import com.lucascalderon1.casaportemporada.model.Anuncio;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeusAnunciosActivity extends AppCompatActivity implements AdapterAnuncios.OnClick {

    private ProgressBar progressBar;
    private TextView text_info;
    private SwipeableRecyclerView rv_anuncios;
    private AdapterAnuncios adapterAnuncios;
    private List<Anuncio> anuncioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        iniciaComponentes();
        configRv();

        configCliques();
    }

    private void configCliques() {
        findViewById(R.id.ib_add).setOnClickListener(view ->
                startActivity(new Intent(this, FormAnuncioActivity.class)));


        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaAnuncios();
    }

    private void configRv() {
        rv_anuncios.setLayoutManager(new LinearLayoutManager(this));
        rv_anuncios.setHasFixedSize(true);
        adapterAnuncios = new AdapterAnuncios(anuncioList, this);
        rv_anuncios.setAdapter(adapterAnuncios);

        rv_anuncios.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                showDialogDelete(position);


            }
        });

    }

    private void showDialogDelete(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deletar anúncio");
        builder.setMessage("Clique em sim para confirmar ou não para cancelar.");
        builder.setNegativeButton("Não", ((dialog, which) -> {
            dialog.dismiss();
            adapterAnuncios.notifyDataSetChanged();
            Toast.makeText(this, "Você cancelou a função de deletar.", Toast.LENGTH_SHORT).show();
        }));

        builder.setPositiveButton("Sim", ((dialog, which) -> {

            anuncioList.get(pos).deletar();
            adapterAnuncios.notifyItemRemoved(pos);



        }));
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void recuperaAnuncios() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("anuncios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    anuncioList.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Anuncio anuncio = snap.getValue(Anuncio.class);
                        anuncioList.add(anuncio);

                    }

                    text_info.setText("");


                } else {
                    text_info.setText("Nenhum anúncio cadastrado.");

                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(anuncioList);
                adapterAnuncios.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void iniciaComponentes() {

        TextView text_titulo = findViewById(R.id.text_titulo_anuncio);
        text_titulo.setText("Meus Anúncios");

        progressBar = findViewById(R.id.progressBar);
        text_info = findViewById(R.id.text_info);
        rv_anuncios = findViewById(R.id.rv_anuncios);
    }

    @Override
    public void OnClickListener(Anuncio anuncio) {
        Intent intent = new Intent(this, FormAnuncioActivity.class);
        intent.putExtra("anuncio", anuncio);
        startActivity(intent);
    }
}