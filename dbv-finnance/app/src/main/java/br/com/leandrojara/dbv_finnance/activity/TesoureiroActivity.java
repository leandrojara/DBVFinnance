package br.com.leandrojara.dbv_finnance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.leandrojara.dbv_finnance.R;

public class TesoureiroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesoureiro);
        setTitle(R.string.label_tesoureiro);
    }
}
