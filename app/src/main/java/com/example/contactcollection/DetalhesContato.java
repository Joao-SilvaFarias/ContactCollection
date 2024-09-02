package com.example.contactcollection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class DetalhesContato extends AppCompatActivity {

    private FirebaseFirestore bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_contato);

        bancoDados = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String nome = intent.getStringExtra("nome");
        String email = intent.getStringExtra("email");
        String tvPerfil = intent.getStringExtra("tvPerfil");
        String telefone = intent.getStringExtra("telefone");
        TextView tvNomeDetalhes = findViewById(R.id.tvNomeDetalhes);
        TextView tvEmailDetalhes = findViewById(R.id.tvEmailDetalhes);
        TextView tvTelefoneDetalhes = findViewById(R.id.tvTelefonelDetalhes);
        TextView tvPerfilDetalhes = findViewById(R.id.tvPerfilDetalhes);
        tvNomeDetalhes.setText(nome);
        tvEmailDetalhes.setText(email);
        tvTelefoneDetalhes.setText(telefone);
        tvPerfilDetalhes.setText(tvPerfil);

        tvNomeDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(DetalhesContato.this).inflate(R.layout.alterar_layout, null);
                TextInputEditText tiAlterar = view1.findViewById(R.id.tiAlterar);
                tiAlterar.setText(tvNomeDetalhes.getText());
                AlertDialog.Builder alert = new MaterialAlertDialogBuilder(DetalhesContato.this);
                alert.setView(view1);
                alert.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!tiAlterar.getText().toString().isEmpty()){
                            bancoDados.collection("contatos").document(id).update("nome", tiAlterar.getText().toString().trim(), "favorito", "https://firebasestorage.googleapis.com/v0/b/contactcollection.appspot.com/o/heart_solid_icon.png?alt=media&token=16229efd-c496-4177-8fc5-d315568ccec1");
                            tvNomeDetalhes.setText(tiAlterar.getText().toString());
                            tvPerfilDetalhes.setText(String.valueOf(tiAlterar.getText().toString().charAt(0)));
                        } else{
                            Toast.makeText(DetalhesContato.this, "Digite algo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alert.create();
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

        tvEmailDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(DetalhesContato.this).inflate(R.layout.alterar_email_layout, null);
                TextInputEditText tiAlterar = view1.findViewById(R.id.tiAlterar);
                tiAlterar.setText(tvEmailDetalhes.getText());
                AlertDialog.Builder alert = new MaterialAlertDialogBuilder(DetalhesContato.this);
                alert.setView(view1);
                alert.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!tiAlterar.getText().toString().isEmpty()){
                            bancoDados.collection("contatos").document(id).update("email", tiAlterar.getText().toString().trim(), "favorito", "https://firebasestorage.googleapis.com/v0/b/contactcollection.appspot.com/o/heart_solid_icon.png?alt=media&token=16229efd-c496-4177-8fc5-d315568ccec1");
                            tvEmailDetalhes.setText(tiAlterar.getText().toString());
                        } else{
                            Toast.makeText(DetalhesContato.this, "Digite algo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alert.create();
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

        tvTelefoneDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(DetalhesContato.this).inflate(R.layout.altera_telefone_layout, null);
                TextInputEditText tiAlterar = view1.findViewById(R.id.tiAlterar);
                tiAlterar.setText(tvTelefoneDetalhes.getText());
                AlertDialog.Builder alert = new MaterialAlertDialogBuilder(DetalhesContato.this);
                alert.setView(view1);
                alert.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!tiAlterar.getText().toString().isEmpty()){
                            bancoDados.collection("contatos").document(id).update("telefone", tiAlterar.getText().toString().trim(), "favorito", "https://firebasestorage.googleapis.com/v0/b/contactcollection.appspot.com/o/heart_solid_icon.png?alt=media&token=16229efd-c496-4177-8fc5-d315568ccec1");
                            tvTelefoneDetalhes.setText(tiAlterar.getText().toString());
                        } else{
                            Toast.makeText(DetalhesContato.this, "Digite algo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alert.create();
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}