package com.example.projetodisp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.example.projetodisp.modelo.Solicitacoes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SolicitacoesActivity extends AppCompatActivity {
    private Button btn_catalogo;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = findViewById(R.id.recyclerView);
        btn_catalogo = findViewById(R.id.btn_catalogo);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(null);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 back button pressed
                finish();
                startActivity(new Intent(SolicitacoesActivity.this, MainActivity.class));

            }
        });

        
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        Solicitacoes l = (Solicitacoes) getIntent().getSerializableExtra("l");
        Query query = db.getReference().child("Solicitacoes");

        btn_catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SolicitacoesActivity.this, CatalogoBibliotecario.class));
            }
        });

        FirebaseRecyclerOptions<Solicitacoes> options = new FirebaseRecyclerOptions.Builder<Solicitacoes>().setQuery(query, Solicitacoes.class).build();
        adapter = new FirebaseRecyclerAdapter<Solicitacoes, ViewHolder>(options) {

            @Override
            public void onBindViewHolder(ViewHolder holder, int position, Solicitacoes model) {
                holder.bind(model);
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_item_solicitacoes, parent, false);

                return new ViewHolder(view);
            }


        };

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Solicitacoes l;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nome);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), SolicitacoesAceitarRecusar.class);
                    intent.putExtra("l",l);
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        public void bind(Solicitacoes solicitacoes) {
            l = solicitacoes;
            name.setText(solicitacoes.getNome());

        }
    }

}