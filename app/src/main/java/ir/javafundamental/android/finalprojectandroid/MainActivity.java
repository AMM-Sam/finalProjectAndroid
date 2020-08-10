package ir.javafundamental.android.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.omdbapi.com/?S=matrix&apikey=fcf181e5",null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.mainRecycleView);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,RecyclerView.VERTICAL, false  );
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter_omdb_api adapter = new Adapter_omdb_api(getApplicationContext(),)

    }
}