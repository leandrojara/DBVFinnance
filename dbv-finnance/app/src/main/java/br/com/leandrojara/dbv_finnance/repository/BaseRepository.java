package br.com.leandrojara.dbv_finnance.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.lang.reflect.ParameterizedType;

import br.com.leandrojara.dbv_finnance.model.base.EntityBase;

public abstract class BaseRepository<T extends EntityBase> {

    protected final FirebaseFirestore db;

    protected BaseRepository() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    protected String getCollectionName() {
        try {
            return ((EntityBase) ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance()).getCollectionName();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
