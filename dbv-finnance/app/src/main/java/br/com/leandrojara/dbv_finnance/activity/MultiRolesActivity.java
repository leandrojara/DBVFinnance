package br.com.leandrojara.dbv_finnance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.model.enums.Role;
import br.com.leandrojara.dbv_finnance.util.Utils;

public class MultiRolesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAdministrador;
    private Button btnDiretor;
    private Button btnTesoureiro;
    private Button btnResponsavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_roles);
        setTitle(R.string.multi_roles_title);

        btnAdministrador = findViewById(R.id.btnAdministrador);
        btnAdministrador.setOnClickListener(this);
        btnAdministrador.setVisibility(Utils.sessionUser.getRoles().contains(Role.ADMINISTRADOR) ? View.VISIBLE : View.GONE);

        btnDiretor = findViewById(R.id.btnDiretor);
        btnDiretor.setOnClickListener(this);
        btnDiretor.setVisibility(Utils.sessionUser.getRoles().contains(Role.DIRETOR) ? View.VISIBLE : View.GONE);

        btnTesoureiro = findViewById(R.id.btnTesoureiro);
        btnTesoureiro.setOnClickListener(this);
        btnTesoureiro.setVisibility(Utils.sessionUser.getRoles().contains(Role.TESOUREIRO) ? View.VISIBLE : View.GONE);

        btnResponsavel = findViewById(R.id.btnResponsavel);
        btnResponsavel.setOnClickListener(this);
        btnResponsavel.setVisibility(Utils.sessionUser.getRoles().contains(Role.RESPONSAVEL) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdministrador:
                startActivity(new Intent(this, AdministradorActivity.class));
                break;
            case R.id.btnDiretor:
                startActivity(new Intent(this, DiretorActivity.class));
                break;
            case R.id.btnTesoureiro:
                startActivity(new Intent(this, TesoureiroActivity.class));
                break;
            case R.id.btnResponsavel:
                startActivity(new Intent(this, ResponsavelActivity.class));
                break;
        }
    }
}
