package br.com.leandrojara.dbv_finnance.components;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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


    public SearchDialog(@NonNull final Context context, String title, final String collectionName, final String searchField) {
        super(context);

        setContentView(R.layout.search_dialog);
        setTitle(title);

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
                                T obj = document.toObject(getClazz());
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

        listViewSearchDialog = findViewById(R.id.listViewSearchDialog);
        listViewSearchDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: fazer uma forma de quem estiver usando esse objeto, interceptar este evento
            }
        });
    }

    private void setId(T obj, String id) {
        Method[] methods = getClazz().getMethods();
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

    private Class<T> getClazz() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
