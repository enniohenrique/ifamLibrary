package com.example.projetodisp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetodisp.modelo.Solicitacoes;
import com.example.projetodisp.modelo.Usuarios;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SolicitacoesAceitarRecusar extends AppCompatActivity {

    private Button btn_mais;
    private Button btn_menos;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes_aceitar_recusar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        btn_mais = findViewById(R.id.btn_mais);
        btn_menos = findViewById(R.id.btn_menos);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        Solicitacoes l = (Solicitacoes) getIntent().getSerializableExtra("l");

        TextView nome = findViewById(R.id.nome);
        TextView data = findViewById(R.id.autor);
        TextView horario = findViewById(R.id.paginas);
        TextView editora = findViewById(R.id.editora);

        nome.setText(l.getNomeDoLivro());
        data.setText("Autor: " + l.getAutor());
        horario.setText("Páginas: " + l.getPaginas());
        editora.setText("Editora: " + l.getEditora());

        btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recusar();
                finish();
                startActivity(new Intent(SolicitacoesAceitarRecusar.this, SolicitacoesActivity.class));

            }
        });


        btn_mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceitar();
                finish();
                startActivity(new Intent(SolicitacoesAceitarRecusar.this, SolicitacoesActivity.class));

            }
        });

    }

    public void aceitar() {
        Solicitacoes l = (Solicitacoes) getIntent().getSerializableExtra("l");

        DatabaseReference usuarios = referencia.child("Usuarios").child(l.getId());
        DatabaseReference soli = referencia.child("Solicitacoes");

        Usuarios s = new Usuarios();
        s.setLivros(l.getNomeDoLivro());
        s.setAutorDoLivro(l.getAutor());
        s.setEditora(l.getEditora());
        s.setPaginas(l.getPaginas());

        usuarios.child("Meus livros").push().setValue(s);

        Toast.makeText(SolicitacoesAceitarRecusar.this, "Solicitação Aceita!", Toast.LENGTH_SHORT).show();

        soli.child(l.getId()).removeValue();

    }
    public void recusar() {

        Solicitacoes l = (Solicitacoes) getIntent().getSerializableExtra("l");
        DatabaseReference soli = referencia.child("Solicitacoes");
        Toast.makeText(SolicitacoesAceitarRecusar.this, "Solicitação Recusada!", Toast.LENGTH_SHORT).show();
        soli.child(l.getId()).removeValue();

    }

}