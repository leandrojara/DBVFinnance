package br.com.leandrojara.dbv_finnance.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.model.Usuario;
import br.com.leandrojara.dbv_finnance.model.enums.Role;

public class CadastroUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText fieldName;
    private EditText fieldEmail;
    private EditText fieldSenha;
    private EditText fieldRepetirSenha;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        setTitle(R.string.title_cadastro_usuario);

        fieldName = findViewById(R.id.field_name);
        fieldEmail = findViewById(R.id.field_email);
        fieldSenha = findViewById(R.id.field_password);
        fieldRepetirSenha = findViewById(R.id.field_retype_password);

        findViewById(R.id.button_cadastrar).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.field_retype_password) {
            createAccount();
        }
    }

    private void createAccount() {
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(fieldEmail.getText().toString(), fieldSenha.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Usuario usuario = new Usuario();
                usuario.setNome(fieldName.getText().toString());
                usuario.setEmail(fieldEmail.getText().toString());
                usuario.setRoles(Arrays.asList(Role.RESPONSAVEL));
                usuario.setId(authResult.getUser().getUid());
                usuario.add(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(CadastroUsuarioActivity.this, getString(R.string.cadastro_realizado), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, null);
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String name = fieldName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            fieldName.setError(getString(R.string.obrigatorio));
            valid = false;
        } else {
            fieldName.setError(null);
        }

        String email = fieldEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            fieldEmail.setError(getString(R.string.obrigatorio));
            valid = false;
        } else {
            fieldEmail.setError(null);
        }

        String senha = fieldSenha.getText().toString();
        if (TextUtils.isEmpty(senha)) {
            fieldSenha.setError(getString(R.string.obrigatorio));
            valid = false;
        } else {
            fieldSenha.setError(null);
        }

        String senhaRepetida = fieldRepetirSenha.getText().toString();
        if (TextUtils.isEmpty(senhaRepetida)) {
            fieldRepetirSenha.setError(getString(R.string.obrigatorio));
            valid = false;
        } else {
            fieldRepetirSenha.setError(null);
        }

        if (valid) {
            if (!senha.equals(senhaRepetida)) {
                valid = false;
                fieldSenha.setError(getString(R.string.senha_nao_confere));
                fieldRepetirSenha.setError(getString(R.string.senha_nao_confere));
            } else {
                fieldSenha.setError(null);
                fieldRepetirSenha.setError(null);
            }
        }

        return valid;
    }
}
