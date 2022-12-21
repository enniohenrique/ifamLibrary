package com.example.projetodisp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.projetodisp.modelo.Usuarios;

public class MeusLivrosDetalhes extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_livros_detalhes);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        Usuarios l = (Usuarios) getIntent().getSerializableExtra("l");


        TextView nome = findViewById(R.id.nome);
        TextView data = findViewById(R.id.autor);
        TextView horario = findViewById(R.id.paginas);
        TextView editora = findViewById(R.id.editora);

        nome.setText(l.getLivros());
        data.setText("Autor: " + l.getAutorDoLivro());
        horario.setText("Páginas: " + l.getPaginas());
        editora.setText("Editora: " + l.getEditora());
    }
}