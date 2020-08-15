package ir.javafundamental.android.finalprojectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements   Adapter_omdb_api.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //***********************************************************
        //***********************************************************
        TextView txt_search = findViewById(R.id.txt_Search);
        txt_search.setText("matrix");
        //***********************************************************
        search("S=matrix");
        //***********************************************************
        Toolbar t_main = findViewById(R.id.toolbar);
        setSupportActionBar(t_main);

        //***********************************************************
        Button btn_Search = findViewById(R.id.btn_Search);
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt_search = findViewById(R.id.txt_Search);
                if (txt_search.getText().toString().trim().length() == 0) {
                    Toast.makeText(MainActivity.this, getString(R.string.plx_enter_movie_title), Toast.LENGTH_SHORT).show();
                    return;
                }
                search("S="+txt_search.getText().toString().trim());
            }
        });
        //***********************************************************
        TextView txt_myFavoritelist = findViewById(R.id.txt_myfavoritelist);
        txt_myFavoritelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView txt_search = findViewById(R.id.txt_Search);
        search("S=" + txt_search.getText().toString());
    }

    @Override
    public void onItemClick(String imdbID, Integer typeButton, View view) {
        if (typeButton == R.id.btnSave){
            OmdbRepository database = new OmdbRepository(this, "OmdbV3", null, 1);
            if  (database.ExsitsFilm(imdbID)) {
                database.DeleteOmdbInformation(imdbID);
                view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_notsave));
            }
            else {
                database.insertOmdbInformation(imdbID);
                view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_save));
            }
        }
        else {
            searchDetail(imdbID);
        }
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
        client.get("http://www.omdbapi.com/?i=" + imdbID + "&apikey=79047db4",null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                OmdbDetailClass dto_OmdbClassDetail = gson.fromJson(responseString, OmdbDetailClass.class);
                if (dto_OmdbClassDetail.getResponse().toUpperCase().equals("false".toUpperCase())){
                    Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
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
        client.get("http://www.omdbapi.com/?" + str_urlParam + "&apikey=79047db4",null, new TextHttpResponseHandler() {
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
                    Toast.makeText(MainActivity.this, getString(R.string.plx_enter_movie_title), Toast.LENGTH_SHORT).show();
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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewJson.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerViewJson.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.hambergermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hambergermenu_icon: {
                DrawerLayout nv = findViewById(R.id.dr_activity_main);
                if(!nv.isDrawerOpen(Gravity.RIGHT))
                    nv.openDrawer(Gravity.RIGHT);
                else
                    nv.closeDrawer(Gravity.LEFT);
                break;
            }
        }
        return true;
    }

}