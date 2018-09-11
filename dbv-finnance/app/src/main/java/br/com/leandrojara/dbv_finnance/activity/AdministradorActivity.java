package br.com.leandrojara.dbv_finnance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.leandrojara.dbv_finnance.R;

public class AdministradorActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGerenciarClube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        setTitle(R.string.label_administrador);

        btnGerenciarClube = findViewById(R.id.btnGerenciarClube);
        btnGerenciarClube.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGerenciarClube:
                startActivity(new Intent(this, ListaClubesActivity.class));
                break;
        }
    }
}
