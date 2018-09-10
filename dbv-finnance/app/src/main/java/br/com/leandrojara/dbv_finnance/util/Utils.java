package br.com.leandrojara.dbv_finnance.util;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.lang.reflect.Field;
import java.util.Date;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.activity.LoginActivity;
import br.com.leandrojara.dbv_finnance.model.Usuario;

public class Utils {

    private static Class[] primitiveTypes = {Double.class, Integer.class, Date.class, String.class, Long.class};
    public static Usuario sessionUser;

    public static boolean isPrimitive(Field field) {
        Class clazz = field.getDeclaringClass();
        if (clazz.isPrimitive() || clazz.isEnum()) {
            return true;
        } else {
            for (Class clazz2 : primitiveTypes) {
                if (clazz.equals(clazz2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getterName(String fieldName) {
        return "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1, fieldName.length());
    }

    public static void trataAuthException(Context context, Exception ex) {
        try {
            throw ex;
        } catch (FirebaseAuthUserCollisionException e) {
            Toast.makeText(context, context.getString(R.string.usuario_ja_cadastrado),
                    Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthWeakPasswordException e) {
            Toast.makeText(context, context.getString(R.string.senha_invalida),
                    Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthInvalidCredentialsException e) {
            Toast.makeText(context, context.getString(R.string.email_senha_invalidos),
                    Toast.LENGTH_SHORT).show();
        } catch (FirebaseAuthEmailException e) {
            Toast.makeText(context, context.getString(R.string.email_invalido),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.falha_autencicacao),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
