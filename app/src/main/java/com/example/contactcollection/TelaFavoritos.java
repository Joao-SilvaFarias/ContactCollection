package com.example.contactcollection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaFavoritos extends AppCompatActivity {

    List<Contato> contatoListFavoritos;
    RecyclerView recyclerView;
    MyAdapter adapter;
    FirebaseFirestore bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_favoritos);

        contatoListFavoritos = new ArrayList<>();
        recyclerView = findViewById(R.id.rvFavoritos);
        bancoDados = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(TelaFavoritos.this, contatoListFavoritos, new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Contato contato) {
                Intent intent = new Intent(TelaFavoritos.this, DetalhesContato.class);
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());
                intent.putExtra("tvPerfil", contato.getTvPerfil());
                intent.putExtra("telefone", contato.getTelefone());
                intent.putExtra("id", contato.getId());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(Contato contato) {

            }

        });
        recyclerView.setAdapter(adapter);
        buscarDados();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void buscarDados() {
        bancoDados.collection("contatos").whereEqualTo("favorito", "https://firebasestorage.googleapis.com/v0/b/contactcollection.appspot.com/o/heart-solid-24.png?alt=media&token=495613f2-0f67-4ab3-a5d5-2ef1fa16e3db").orderBy("nome").addSnapshotListener((value, error) -> {
            if (value != null){
                contatoListFavoritos.clear();
                for (DocumentSnapshot document : value.getDocuments()){
                    contatoListFavoritos.add(new Contato(document.getString("nome"), document.getString("email"), String.valueOf(document.getString("nome").charAt(0)), document.getString("telefone"), document.getString("id"), document.getString("favorito")));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}