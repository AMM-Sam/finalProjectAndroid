package ir.javafundamental.android.finalprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements   Adapter_omdb_api.ItemClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search("S=matrix");

        Button btn_Search = findViewById(R.id.btn_Search);
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt_search = findViewById(R.id.txt_Search);
                if (txt_search.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, "لطفا  عنوان  فیلم را وارد کنید", Toast.LENGTH_SHORT).show();
                    return;
                }
                search("S="+txt_search.getText().toString().trim());
            }
        });

    }

    @Override
    public void onItemClick(String imdbID) {
        searchDetail(imdbID);
    }
    public void fillAdapterDetail(OmdbDetailClass dto_OmdbClassDetail){
        Intent intent = new Intent(MainActivity.this, ShowInformationFilm.class);
        intent.putExtra("dto_OmdbClassDetail", dto_OmdbClassDetail);
        startActivity(intent);
    }
    public void searchDetail(String imdbID){
        //http://www.omdbapi.com/?i=tt0382026&apikey=fcf181e5
        //"http://www.omdbapi.com/?S=matrix&apikey=fcf181e5"
        //"http://www.omdbapi.com/?" + str_urlParam + "&apikey=fcf181e5"
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.omdbapi.com/?i=" + imdbID + "&apikey=fcf181e5",null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "مشکل", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                OmdbDetailClass dto_OmdbClassDetail = gson.fromJson(responseString, OmdbDetailClass.class);
                if (dto_OmdbClassDetail.getResponse().toUpperCase().equals("false".toUpperCase())){
                    Toast.makeText(MainActivity.this, "مشکل", Toast.LENGTH_SHORT).show();
                }
                fillAdapterDetail(dto_OmdbClassDetail);
            }
        });
    }
    ///-------
    public void search(String str_urlParam){
        //http://www.omdbapi.com/?i=tt0382026&apikey=fcf181e5
        //"http://www.omdbapi.com/?S=matrix&apikey=fcf181e5"
        //"http://www.omdbapi.com/?" + str_urlParam + "&apikey=fcf181e5"
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.omdbapi.com/?" + str_urlParam + "&apikey=fcf181e5",null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                OmdbClass dto_OmdbClass = new OmdbClass();
                fillAdapter(dto_OmdbClass);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                //Type listType = new TypeToken<ArrayList<OmdbClass>>(){}.getType();
                //ArrayList lst_OmdbClass = gson.fromJson(responseString, listType);
                OmdbClass dto_OmdbClass = gson.fromJson(responseString, OmdbClass.class);
                if (dto_OmdbClass.getResponse().toUpperCase().equals("false".toUpperCase())){
                    Toast.makeText(MainActivity.this, "لطفا  عنوان  فیلم را وارد کنید", Toast.LENGTH_SHORT).show();
                    dto_OmdbClass.setSearch(new ArrayList<Search>());
                }
                fillAdapter(dto_OmdbClass);
            }
        });
    }

    public void fillAdapter(OmdbClass dto_OmdbClass){
        RecyclerView recyclerViewJson = findViewById(R.id.mainRecycleView);
        recyclerViewJson.setHasFixedSize(true);
        //recyclerViewJson.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerViewJson.setLayoutManager(layoutManager);
        recyclerViewJson.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        Adapter_omdb_api adapterJson = new Adapter_omdb_api(dto_OmdbClass.getSearch(), getApplicationContext());
        adapterJson.setClickListener(this);
        recyclerViewJson.setAdapter(adapterJson);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.favoritemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_Favorite: {
                Intent intent = new Intent(MainActivity.this, FavoriteList.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }

    // allows clicks events to be caught


}