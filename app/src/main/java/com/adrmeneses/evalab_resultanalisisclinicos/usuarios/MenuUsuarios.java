package com.adrmeneses.evalab_resultanalisisclinicos.usuarios;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adrmeneses.evalab_resultanalisisclinicos.FormularioPrincipal;
import com.adrmeneses.evalab_resultanalisisclinicos.R;
import com.adrmeneses.evalab_resultanalisisclinicos.adaptadores.AdaptadorListaUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.basedatos.DBUsuarios;
import com.adrmeneses.evalab_resultanalisisclinicos.contenedore.UsuarioActivo;
import com.adrmeneses.evalab_resultanalisisclinicos.entidades.Usuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuUsuarios extends AppCompatActivity {
    RecyclerView viewListaUsuarios;
    AdaptadorListaUsuarios adaptadorListaUsuarios;
    DBUsuarios dbUsuarios;
    UsuarioActivo usrActivo;
    private int idUsr;
    private LinearLayout contentOpcion;
    private FloatingActionButton btnAgregar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_usuarios);

        dbUsuarios = new DBUsuarios(this);
        usrActivo = UsuarioActivo.getInstance();
        idUsr = (int)usrActivo.getIdUsuario();
        contentOpcion = findViewById(R.id.contenedorOpcionListaUsuarios);
        btnAgregar = findViewById(R.id.menuUsuariosBotonAgregar);
        viewListaUsuarios = findViewById(R.id.menuUsuariosRecyclerView);
        viewListaUsuarios.setLayoutManager(new LinearLayoutManager(this));

        adaptadorListaUsuarios = new AdaptadorListaUsuarios(dbUsuarios.leerUsuarios());
        viewListaUsuarios.setAdapter(adaptadorListaUsuarios);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lanzarAgregar = new Intent(MenuUsuarios.this, FormularioPrincipal.class);
                startActivity(lanzarAgregar);
            }
        });

    }
}