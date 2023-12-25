package com.adrmeneses.evalab_resultanalisisclinicos.usuarios;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.adaptadores.AdaptadorListaUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Usuarios;

public class MenuUsuarios extends AppCompatActivity {
    RecyclerView viewListaUsuarios;
    AdaptadorListaUsuarios adaptadorListaUsuarios;
    DBUsuarios dbUsuarios;
    UsuarioActivo usrActivo;
    private int idUsr;
    private LinearLayout contentOpcion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_usuarios);

        dbUsuarios = new DBUsuarios(this);
        usrActivo = UsuarioActivo.getInstance();
        idUsr = (int)usrActivo.getIdUsuario();
        contentOpcion = findViewById(R.id.contenedorOpcionListaUsuarios);

        viewListaUsuarios = findViewById(R.id.menuUsuariosRecyclerView);
        viewListaUsuarios.setLayoutManager(new LinearLayoutManager(this));

        adaptadorListaUsuarios = new AdaptadorListaUsuarios(dbUsuarios.leerUsuarios());
        viewListaUsuarios.setAdapter(adaptadorListaUsuarios);
/*
        contentOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtId = view.findViewById(R.id.listaUsuarioTextViewId);
                Log.d(TAG, "onClick: el id es: "+txtId.getText());
            }
        });*/
    }
}