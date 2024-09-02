package com.example.contactcollection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TelaAdicionar extends AppCompatActivity {

    private FirebaseFirestore bancoDados = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_adicionar);

        final Button btnAdicionarContato = (Button) findViewById(R.id.btnAdicionarContato);
        final TextInputEditText tiNome = (TextInputEditText) findViewById(R.id.tiNomeAdicionar);
        final TextInputEditText tiEmail = (TextInputEditText) findViewById(R.id.tiEmailAdicionar);
        final TextInputEditText tiNumero = (TextInputEditText) findViewById(R.id.tiNumeroAdicionar);

        btnAdicionarContato.setOnClickListener(view -> {
            if (!tiNome.getText().toString().isEmpty() && !tiEmail.getText().toString().isEmpty() && !tiNumero.getText().toString().isEmpty()){
                Map<String, Object> dados = new HashMap<>();
                String id = String.valueOf(UUID.randomUUID());
                dados.put("id", id);
                dados.put("nome", tiNome.getText().toString().trim());
                dados.put("email", tiEmail.getText().toString().trim());
                dados.put("telefone", tiNumero.getText().toString().trim());
                bancoDados.collection("contatos").document(id).set(dados);
                Toast.makeText(this, "Contato "+tiNome.getText().toString()+" adicionado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TelaAdicionar.this, TelaPrincipal.class));
                finish();
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}