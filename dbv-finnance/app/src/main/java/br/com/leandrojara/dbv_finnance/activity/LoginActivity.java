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

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            nextActivity();
        }
    }

    private void createAccount() {
        startActivity(new Intent(this, CadastroUsuarioActivity.class));
    }

    private void nextActivity() {

    }

    private void trataAuthException(Exception ex) {
        try {
            throw ex;
        } catch (FirebaseAuthUserCollisionException e) {
            Toast.makeText(LoginActivity.this, "O usuário já foi cadastrado anteriormente no sistema.",
                    Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthWeakPasswordException e) {
            Toast.makeText(LoginActivity.this, "Insira uma senha de no mínimo 8 caracteres contendo letras e números.",
                    Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthInvalidCredentialsException e) {
            Toast.makeText(LoginActivity.this, "Email e/ou senha inválidos.",
                    Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthEmailException e) {
            Toast.makeText(LoginActivity.this, "O email digitado é inválido.",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Autenticação falhou.",
                    Toast.LENGTH_SHORT).show();
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
                            Log.d(TAG, "signInWithEmail:success");
                            nextActivity();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            trataAuthException(task.getException());
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Obrigatório.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Obrigatório.");
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
        }
    }
}
