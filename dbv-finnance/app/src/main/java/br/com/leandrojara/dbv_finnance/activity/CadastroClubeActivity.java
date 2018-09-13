package br.com.leandrojara.dbv_finnance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.model.Clube;
import br.com.leandrojara.dbv_finnance.model.Usuario;
import br.com.leandrojara.dbv_finnance.model.enums.TipoClube;

public class CadastroClubeActivity extends AppCompatActivity {

    private ArrayAdapter<TipoClube> adapterTipoClube;
    private AutoCompleteTextView tipoClube;

    private ArrayAdapter<Usuario> adapterDiretor;
    private AutoCompleteTextView diretor;

    private ArrayAdapter<Usuario> adapterTesoureiro;
    private AutoCompleteTextView tesoureiro;

    public static final String KEY_CLUBE = "keyClube";
    private Clube clube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_clube);
        setTitle(R.string.title_cadastro_clube);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CLUBE)) {
            clube = (Clube) savedInstanceState.get(KEY_CLUBE);
        } else {
            clube = new Clube();
            clube.setTipo(TipoClube.DESBRAVADORES);
        }

        createTipoClube();
        createDiretor();
    }

    private void createDiretor() {
        diretor = findViewById(R.id.diretor);
        if (clube.getDiretor() != null) {
            diretor.setText(clube.getDiretor().getNome());
        }

        adapterDiretor = new ArrayAdapter<Usuario>(CadastroClubeActivity.this, R.layout.simple_row_item, R.id.simple_row_item_view);
        diretor.setAdapter(adapterDiretor);

        diretor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                adapterDiretor.clear();
                FirebaseFirestore.getInstance().collection(new Usuario().getCollectionName()).addSnapshotListener(CadastroClubeActivity.this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Query query = queryDocumentSnapshots.getQuery();
                            query.whereArrayContains("nome", charSequence.toString().trim());
                            query.limit(6);
                            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot document : documents) {
                                            Usuario usuario = document.toObject(Usuario.class);
                                            usuario.setId(document.getId());
                                            adapterDiretor.add(usuario);
                                        }
                                        adapterDiretor.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        diretor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clube.setDiretor((Usuario) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                clube.setDiretor(null);
            }
        });
    }

    private void createTipoClube() {
        tipoClube = findViewById(R.id.tipoClube);
        if (clube.getTipo() != null) {
            tipoClube.setText(clube.getTipo().getDescricao());
        }

        adapterTipoClube = new ArrayAdapter<TipoClube>(CadastroClubeActivity.this, R.layout.simple_row_item, R.id.simple_row_item_view);
        tipoClube.setAdapter(adapterTipoClube);

        tipoClube.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapterTipoClube.clear();
                for (TipoClube tipo : TipoClube.values()) {
                    if (charSequence.toString().trim().isEmpty()) {
                        adapterTipoClube.add(tipo);
                    } else if (tipo.getDescricao().contains(charSequence.toString().trim())) {
                        adapterTipoClube.add(tipo);
                    }
                }
                adapterTipoClube.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tipoClube.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clube.setTipo((TipoClube) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                clube.setTipo(null);
            }
        });
    }
}
