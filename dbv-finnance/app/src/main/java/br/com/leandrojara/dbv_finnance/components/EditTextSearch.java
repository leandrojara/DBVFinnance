package br.com.leandrojara.dbv_finnance.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class EditTextSearch<T> extends android.support.v7.widget.AppCompatEditText implements AdapterView.OnItemClickListener, View.OnTouchListener {

    private SearchDialog<T> searchDialog;
    private String title;
    private String collectionName;
    private String searchField;
    private Class<T> clazz;
    private T value;

    public EditTextSearch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditTextSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextSearch(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if (value != null) {
            setText(getValue().toString());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setValue((T) parent.getSelectedItem());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            searchDialog = new SearchDialog<>(getContext(), title != null ? title : "Pesquisa", collectionName, searchField, clazz);
            searchDialog.show();

            if (!getText().toString().trim().isEmpty()) {
                searchDialog.search(getText().toString().trim());
            }

            searchDialog.addOnItemClickListener(this);
            return true;
        }
        return false;
    }
}