package com.feather.netapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.feather.comment.net.HttpClient;
import com.feather.comment.net.NetResponse;
import com.feather.comment.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }
}
