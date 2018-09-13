package br.com.leandrojara.dbv_finnance.model.base;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.leandrojara.dbv_finnance.model.interfaces.Persistable;

public abstract class EntityBase implements Persistable {

    private String id;
    private final FirebaseFirestore db;

    protected EntityBase() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    public EntityBase(Map<String, Object> map) {
        this();
        Field[] fields = getClass().getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                try {
                    field.set(this, map.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void add() {
        add(null, null, db.batch(), true);
    }

    public void add(OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        add(onSuccessListener, onFailureListener, db.batch(), true);
    }

    private void add(WriteBatch batch, boolean commit) {
        add(null, null, batch, commit);
    }

    private void add(OnSuccessListener onSuccessListener, OnFailureListener onFailureListener, WriteBatch batch, boolean commit) {
        final Map<String, Object> map = new HashMap<>();

        //pega um id disponivel
        if (id == null) {
            DocumentReference reference = db.collection(getCollectionName()).document();
            setId(reference.getId());
        }

        //pega os fields da classe
        Field[] fields = getClass().getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            try {
                //se o field possuir algum valor
                if (field.get(this) != null) {
                    //se for do tipo EntityBase
                    if (field.get(this) instanceof EntityBase) {
                        //insere o dado da dependencia
                        if (((EntityBase) field.get(this)).getReference() == null) {
                            ((EntityBase) field.get(this)).add(batch, false);
                        }
                        //aponta para a referencia
                        map.put(field.getName(), ((EntityBase) field.get(this)).getReference());
                    } else if (field.get(this) instanceof List) {
                        //se for do tipo List
                        List<Object> list = (List<Object>) field.get(this);
                        List<Object> persistentList = new ArrayList<>();
                        for (Object object : list) {
                            //se for uma lista de EntityBase
                            if (object instanceof EntityBase) {
                                //se não possuir refencia no banco
                                if (((EntityBase) object).getReference() == null) {
                                    //insere o dado da dependencia
                                    ((EntityBase) object).add(batch, false);
                                }
                                //aponta para a referencia
                                persistentList.add(((EntityBase) object).getReference());
                            } else if (object instanceof Enum) {
                                //se for uma lista de Enums
                                persistentList.add(((Enum) object).name());
                            } else {
                                //se não for uma lista de EntityBase nem de Enum
                                persistentList.add(object);
                            }
                        }
                        //seta a lista de referencias
                        map.put(field.getName(), persistentList);
                    } else if (field.getType().isEnum()) { //se for um enum
                        map.put(field.getName(), ((Enum) field.get(this)).name());
                    } else { //se for de tipo primitivo: Double, Integer, Date...
                        map.put(field.getName(), field.get(this));
                    }
                }
            } catch (IllegalAccessException e) {
                Log.w("IllegalAccessException", e);
            }
        }

        batch.set(getReference(), map);

        if (commit) {
            Task task = batch.commit();
            if (onSuccessListener != null) {
                task.addOnSuccessListener(onSuccessListener);
            }
            if (onFailureListener != null) {
                task.addOnFailureListener(onFailureListener);
            }
        }
    }

    public void remove(OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        db.collection(getCollectionName()).document(id).delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public DocumentReference getReference() {
        if (id != null) {
            return db.collection(getCollectionName()).document(id);
        } else {
            return null;
        }
    }
}
