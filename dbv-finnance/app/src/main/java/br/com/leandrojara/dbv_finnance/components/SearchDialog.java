package br.com.leandrojara.dbv_finnance.components;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import br.com.leandrojara.dbv_finnance.R;

public class SearchDialog<T> extends Dialog {

    private ListView listViewSearchDialog;
    private EditText editTextSearchDialog;
    private final Class<T> clazz;

    public SearchDialog(@NonNull final Context context, String title, final String collectionName, final String searchField, final Class<T> clazz) {
        super(context);
        this.clazz = clazz;

        setContentView(R.layout.search_dialog);
        setTitle(title);

        listViewSearchDialog = findViewById(R.id.listViewSearchDialog);
        listViewSearchDialog.setEmptyView(findViewById(R.id.empty_text_view));

        editTextSearchDialog = findViewById(R.id.editTextSearchDialog);
        editTextSearchDialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Query query = FirebaseFirestore.getInstance().collection(collectionName).limit(6);
                if (!charSequence.toString().trim().isEmpty()) {
                    String[] split = charSequence.toString().trim().replaceAll("  ", " ").split(" ");
                    for (String queryStr : split) {
                        query.whereArrayContains(searchField, queryStr);
                    }
                }

                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<T> list = new ArrayList<>();
                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot document : documents) {
                                T obj = document.toObject(clazz);
                                setId(obj, document.getId());
                                list.add(obj);
                            }
                        }

                        ArrayAdapter<T> adapter = new ArrayAdapter<>(context, R.layout.simple_row_item, R.id.simple_row_item_view);
                        adapter.addAll(list);
                        listViewSearchDialog.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setId(T obj, String id) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("setId")) {
                try {
                    method.invoke(obj, id);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            listViewSearchDialog.setOnItemClickListener(onItemClickListener);
        }
    }
}
