package br.com.leandrojara.dbv_finnance.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.lang.reflect.ParameterizedType;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;

public abstract class BaseRepository<T extends EntityBase> {

    private final FirebaseFirestore db;

    protected BaseRepository() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getAll(OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {
        try {
            EntityBase instance = getGeneric().newInstance();
            db.collection(instance.getCollectionName()).get()
                    .addOnCompleteListener(onCompleteListener)
                    .addOnFailureListener(onFailureListener);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Class<T> getGeneric() {
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
