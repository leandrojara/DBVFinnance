package br.com.leandrojara.dbv_finnance.repository;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import br.com.leandrojara.dbv_finnance.model.Usuario;

public class UsuarioRepository extends BaseRepository<Usuario> {

    public Usuario findById(String id) {
        final Usuario[] usuario = new Usuario[1];

        db.collection(getCollectionName()).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                usuario[0] = new Usuario(documentSnapshot.getData());
            }
        });

        return usuario[0];
    }
}
