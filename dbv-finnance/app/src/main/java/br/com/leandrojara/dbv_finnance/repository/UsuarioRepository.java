package br.com.leandrojara.dbv_finnance.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import br.com.leandrojara.dbv_finnance.model.Usuario;

public class UsuarioRepository extends BaseRepository<Usuario> {

    public Usuario findById(String id) {
        final Usuario[] usuario = new Usuario[1];

        db.collection(getCollectionName()).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        usuario[0] = new Usuario(snapshot.getData());
                    }
                }
            }
        });

        return usuario[0];
    }
}
