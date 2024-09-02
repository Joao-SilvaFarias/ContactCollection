package com.example.contactcollection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends AppCompatActivity {

    private List<Contato> contatoList;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseFirestore bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);

        View view1 = LayoutInflater.from(TelaPrincipal.this).inflate(R.layout.contato_layout, null);
        ImageView imgFavorito = view1.findViewById(R.id.imgFavorito);
        ImageView btnFavoritos = findViewById(R.id.imgFavoritos);
        FloatingActionButton btnAdicionar = findViewById(R.id.btnAdicionar);
        bancoDados = FirebaseFirestore.getInstance();
        contatoList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(getApplicationContext(), contatoList, new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Contato contato) {
                Intent intent = new Intent(TelaPrincipal.this, DetalhesContato.class);
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());
                intent.putExtra("tvPerfil", contato.getTvPerfil());
                intent.putExtra("telefone", contato.getTelefone());
                intent.putExtra("id", contato.getId());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(Contato contato) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaPrincipal.this);
                View view = LayoutInflater.from(TelaPrincipal.this).inflate(R.layout.excluir_layout, null);
                builder.setView(view);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bancoDados.collection("contatos").document(contato.getId()).delete();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bk_alert_alterar);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
                    }
                });
                dialog.show();
            }

        });
        recyclerView.setAdapter(adapter);
        popularLista();
        btnAdicionar.setOnClickListener(view -> {
            startActivity(new Intent(TelaPrincipal.this, TelaAdicionar.class));
        });

        btnFavoritos.setOnClickListener(view -> {
            startActivity(new Intent(TelaPrincipal.this, TelaFavoritos.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void popularLista() {
        bancoDados.collection("contatos").orderBy("nome").addSnapshotListener((querySnapshot, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
                    }

                    if (querySnapshot != null) {
                        contatoList.clear();
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            try {
                                contatoList.add(new Contato(document.getString("nome"), document.getString("email"), String.valueOf(document.getString("nome").charAt(0)), document.getString("telefone"), document.getString("id"), document.getString("favorito")));
                            } catch (Exception e){
                                Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }
}
