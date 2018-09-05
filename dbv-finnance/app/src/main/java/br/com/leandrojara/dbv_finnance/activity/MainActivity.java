package br.com.leandrojara.dbv_finnance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

import br.com.leandrojara.dbv_finnance.R;
import br.com.leandrojara.dbv_finnance.model.Clube;
import br.com.leandrojara.dbv_finnance.model.Desbravador;
import br.com.leandrojara.dbv_finnance.model.Usuario;
import br.com.leandrojara.dbv_finnance.model.enums.Role;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Clube clube = new Clube();
        clube.setNome("Clube de Desbravadores Fernando Stahl");

        final Desbravador desbravadorDiretor = new Desbravador();
        desbravadorDiretor.setClube(clube);
        desbravadorDiretor.setNome("Leandro de Souza Jara");

        final Usuario usuarioDiretor = new Usuario();
        usuarioDiretor.setEmail("leandro.souza.jara@hotmail.com");
        usuarioDiretor.setNome("Leandro de Souza Jara");
        usuarioDiretor.setRole(Role.DIRETOR);
        usuarioDiretor.setDesbravadores(Arrays.asList(desbravadorDiretor));

        clube.setDiretor(usuarioDiretor);

        Desbravador desbravadorTesoureiro = new Desbravador();
        desbravadorTesoureiro.setNome("Vanilce Lima de Souza");
        desbravadorTesoureiro.setClube(clube);
        Usuario usuarioTesoureiro = new Usuario();
        usuarioTesoureiro.setEmail("valcont2008@hotmail.com");
        usuarioTesoureiro.setNome("Vanilce Lima de Souza");
        usuarioTesoureiro.setRole(Role.TESOUREIRO);

        clube.setTesoureiro(usuarioTesoureiro);

        clube.add();
    }
}
