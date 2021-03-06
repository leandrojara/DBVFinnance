package br.com.leandrojara.dbv_finnance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.components.EditTextSearch;
import br.com.leandrojara.dbv_finnance.model.Clube;
import br.com.leandrojara.dbv_finnance.model.Usuario;
import br.com.leandrojara.dbv_finnance.model.enums.TipoClube;

public class CadastroClubeActivity extends AppCompatActivity {

    private ArrayAdapter<TipoClube> adapterTipoClube;
    private AutoCompleteTextView tipoClube;

    private EditTextSearch<Usuario> diretor;

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
        }

        createTipoClube();
        createDiretor();
    }

    private void createDiretor() {
        diretor = findViewById(R.id.diretor);
        diretor.setValue(clube.getDiretor());
        diretor.setTitle(getString(R.string.label_diretor));
        diretor.setCollectionName(new Usuario().getCollectionName());
        diretor.setSearchField("nomeSplit");
        diretor.setClazz(Usuario.class);
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
                if (charSequence.toString().trim().isEmpty()) {
                    clube.setTipo(null);
                }

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

        tipoClube.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clube.setTipo((TipoClube) adapterView.getSelectedItem());
            }
        });

        tipoClube.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (clube.getTipo() != null) {
                        tipoClube.setText(clube.getTipo().getDescricao());
                    }
                }
            }
        });
    }
}
