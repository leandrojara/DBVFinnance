package br.com.leandrojara.dbv_finnance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.model.enums.Role;
import br.com.leandrojara.dbv_finnance.repository.UsuarioRepository;
import br.com.leandrojara.dbv_finnance.util.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.labEsqueciSenha).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Utils.sessionUser = new UsuarioRepository().findById(currentUser.getUid());
            nextActivity();
        }
    }

    private void createAccount() {
        startActivity(new Intent(this, CadastroUsuarioActivity.class));
    }

    private void nextActivity() {
        if (Utils.sessionUser.getRoles().size() > 1) {
            startActivity(new Intent(this, MultiRolesActivity.class));
        } else if (Utils.sessionUser.getRoles().contains(Role.ADMINISTRADOR)) {
            startActivity(new Intent(this, AdministradorActivity.class));
        } else if (Utils.sessionUser.getRoles().contains(Role.DIRETOR)) {
            startActivity(new Intent(this, DiretorActivity.class));
        } else if (Utils.sessionUser.getRoles().contains(Role.TESOUREIRO)) {
            startActivity(new Intent(this, TesoureiroActivity.class));
        } else if (Utils.sessionUser.getRoles().contains(Role.RESPONSAVEL)) {
            startActivity(new Intent(this, ResponsavelActivity.class));
        }
    }

    private void esqueciSenha(final String email) {
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.obrigatorio));
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        if (valid) {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, getString(R.string.enviado_email_para) + " " + email, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.erro_enviar_email), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                Log.d(TAG, "signInWithEmail:success");
                                Utils.sessionUser = new UsuarioRepository().findById(mAuth.getCurrentUser().getUid());
                                nextActivity();
                            } else {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, getString(R.string.email_nao_verificado), Toast.LENGTH_LONG).show();
                                            mAuth.signOut();
                                        } else {
                                            Toast.makeText(LoginActivity.this, getString(R.string.erro_enviar_email), Toast.LENGTH_LONG).show();
                                            mAuth.signOut();
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Utils.trataAuthException(LoginActivity.this, task.getException());
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.obrigatorio));
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(getString(R.string.obrigatorio));
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount();
        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.labEsqueciSenha) {
            esqueciSenha(mEmailField.getText().toString());
        }
    }
}
