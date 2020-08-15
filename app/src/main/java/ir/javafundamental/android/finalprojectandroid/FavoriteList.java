package ir.javafundamental.android.finalprojectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FavoriteList extends AppCompatActivity implements   Adapter_omdb_api.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        search();
    }

    @Override
    protected void onResume() {
        super.onResume();
        search();
    }

    public void search(){
        OmdbRepository database = new OmdbRepository(this, "OmdbV3", null, 1);
        OmdbClass dto_OmdbClass = database.getAllOmdbInformation_Favorite();
        if (dto_OmdbClass.getSearch().size() == 0){
            Toast.makeText(FavoriteList.this, getString(R.string.no_items), Toast.LENGTH_SHORT).show();
        }
        fillAdapter(dto_OmdbClass);
    }

    public void fillAdapter(OmdbClass dto_OmdbClass){
        RecyclerView recyclerViewJson = findViewById(R.id.mainRecycleView);
        recyclerViewJson.setHasFixedSize(true);
        //recyclerViewJson.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerViewJson.setLayoutManager(layoutManager);
        recyclerViewJson.setLayoutManager(new LinearLayoutManager(FavoriteList.this));
        Adapter_omdb_api adapterJson = new Adapter_omdb_api(dto_OmdbClass.getSearch(), FavoriteList.this);
        adapterJson.setClickListener(this);
        recyclerViewJson.setAdapter(adapterJson);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewJson.getContext(), new LinearLayoutManager(this).getOrientation());
        recyclerViewJson.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onItemClick(String imdbID, Integer typeButton, View view) {
        if (typeButton == R.id.btnSave){
            OmdbRepository database = new OmdbRepository(this, "OmdbV3", null, 1);
            if  (database.ExsitsFilm(imdbID)) {
                database.DeleteOmdbInformation(imdbID);
                view.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_notsave));
                search();
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

    public void searchDetail(String imdbID){
        OmdbRepository database = new OmdbRepository(this, "OmdbV3", null, 1);
        OmdbDetailClass dto_omdbDetailClass = database.GetRowOmdbInformation(imdbID);
//        if (dto_omdbDetailClass.getResponse().toUpperCase().equals("false".toUpperCase())){
//            Toast.makeText(FavoriteList.this, "مشکل", Toast.LENGTH_SHORT).show();
//        }
        fillAdapterDetail(dto_omdbDetailClass);
    }

    public void fillAdapterDetail(OmdbDetailClass dto_OmdbClassDetail){
        Intent intent = new Intent(FavoriteList.this, ShowInformationFilm.class);
        intent.putExtra("dto_OmdbClassDetail", dto_OmdbClassDetail);
        startActivity(intent);
    }
}