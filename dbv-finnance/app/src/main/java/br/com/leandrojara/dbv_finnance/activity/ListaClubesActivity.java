package br.com.leandrojara.dbv_finnance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.model.Clube;

public class ListaClubesActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listClubes;
    private FloatingActionButton fabAddClube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clubes);
        setTitle(R.string.label_clubes);

        listClubes = findViewById(R.id.listClubes);
        fabAddClube = findViewById(R.id.fabAddClube);
        fabAddClube.setOnClickListener(this);

        FirebaseFirestore.getInstance().collection(new Clube().getCollectionName()).addSnapshotListener(ListaClubesActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots.isEmpty()) {
                    listClubes.setEmptyView(findViewById(R.id.empty_text_view));
                } else {
                    List<Clube> clubes = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        clubes.add(documentSnapshot.toObject(Clube.class));
                    }

                    ArrayAdapter<Clube> adapter = new ArrayAdapter<>(ListaClubesActivity.this, R.layout.simple_row_item, R.id.simple_row_item_view, clubes);
                    listClubes.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAddClube:
                startActivity(new Intent(this, CadastroClubeActivity.class));
                break;
        }
    }
}
