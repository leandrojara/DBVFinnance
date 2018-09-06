package br.com.leandrojara.dbv_finnance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.leandrojara.dbv_finnance.R;

public class CadastroUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText fieldName;
    private EditText fieldEmail;
    private EditText fieldSenha;
    private EditText fieldRepetirSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        setTitle(R.string.title_cadastro_usuario);

        fieldName = findViewById(R.id.field_name);
        fieldEmail = findViewById(R.id.field_email);
        fieldSenha = findViewById(R.id.field_password);
        fieldRepetirSenha = findViewById(R.id.field_retype_password);
        btnCadastrar = findViewById(R.id.button_cadastrar);
        btnCadastrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        
    }
}
