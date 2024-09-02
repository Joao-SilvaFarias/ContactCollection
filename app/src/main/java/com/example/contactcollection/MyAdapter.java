package com.example.contactcollection;

import static com.example.contactcollection.R.drawable.heart_icon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Contato> contatoList;
    ItemClickListener itemClickListener;

    public MyAdapter(Context context, List<Contato> contatoList, ItemClickListener itemClickListener) {
        this.context = context;
        this.contatoList = contatoList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.contato_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Contato contato = contatoList.get(position);
        holder.tvNome.setText(contato.getNome());
        holder.tvEmail.setText(contato.getEmail());
        holder.tvPerfil.setText(contato.getTvPerfil());
        Picasso.get().load(contato.getFavorito()).placeholder(R.drawable.heart_icon).error(heart_icon).into(holder.imgFavorito);
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(contatoList.get(position));
        });
        holder.itemView.setOnLongClickListener(view -> {
            itemClickListener.onLongItemClick(contatoList.get(position));
            return true;
        });
        FirebaseFirestore bancoDados = FirebaseFirestore.getInstance();
        holder.imgFavorito.setOnClickListener(view -> {
            if (contato.getFavorito() == null){

                Map<String, Object> dados = new HashMap<>();
                dados.put("nome", contato.getNome());
                dados.put("email", contato.getEmail());
                dados.put("telefone", contato.getTelefone());
                dados.put("id", contato.getId());
                dados.put("favorito", "https://firebasestorage.googleapis.com/v0/b/contactcollection.appspot.com/o/heart-solid-24.png?alt=media&token=495613f2-0f67-4ab3-a5d5-2ef1fa16e3db");
                bancoDados.collection("contatos").document(contato.getId()).set(dados);
                Toast.makeText(context, "Contato "+contato.getNome()+" adicionado aos favoritos", Toast.LENGTH_SHORT).show();
            }else {
                holder.imgFavorito.setImageResource(heart_icon);
                Map<String, Object> dados = new HashMap<>();
                dados.put("nome", contato.getNome());
                dados.put("email", contato.getEmail());
                dados.put("telefone", contato.getTelefone());
                dados.put("id", contato.getId());
                Toast.makeText(context, "Contato "+contato.getNome()+" removido dos favoritos", Toast.LENGTH_SHORT).show();
                bancoDados.collection("contatos").document(contato.getId()).set(dados);
            }
        });
    }



    public interface ItemClickListener{
        void onItemClick(Contato contato);
        void onLongItemClick(Contato contato);
    }

    @Override
    public int getItemCount() {
        return contatoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvEmail, tvPerfil;
        ImageView imgFavorito;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPerfil = itemView.findViewById(R.id.tvPerfil);
            imgFavorito = itemView.findViewById(R.id.imgFavorito);
        }
    }
}
