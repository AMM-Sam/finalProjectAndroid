package ir.javafundamental.android.finalprojectandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class OmdbRepository extends SQLiteOpenHelper {

    String TABLE_NAME = "OmdbInformation";
    private Context mycontext;

    public OmdbRepository(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mycontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "ImdbId Text," +
                "Year TEXT," +
                "Poster TEXT," +
                "Genre TEXT," +
                "Runtime TEXT," +
                "Actors TEXT," +
                "imdbRating TEXT," +
                "Released TEXT," +
                "Awards TEXT," +
                "Production TEXT," +
                "Director TEXT," +
                "Writer TEXT," +
                "Language TEXT," +
                "Website TEXT," +
                "Rated TEXT," +
                "Country TEXT," +
                "Metascore TEXT," +
                "imdbVotes TEXT," +
                "Type TEXT," +
                "DVD TEXT," +
                "BoxOffice TEXT" +
                ")";
        db.execSQL(CREATE_TABLE_QUERY);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade
    }

    public void insertOmdbInformation(String ImdbId) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.omdbapi.com/?i=" + ImdbId + "&apikey=79047db4",null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(mycontext, mycontext.getString(R.string.error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                OmdbDetailClass dto_OmdbClassDetail = gson.fromJson(responseString, OmdbDetailClass.class);
                insert( dto_OmdbClassDetail.getTitle(),
                        dto_OmdbClassDetail.getImdbID(),
                        dto_OmdbClassDetail.getYear(),
                        dto_OmdbClassDetail.getPoster(),
                        dto_OmdbClassDetail.getGenre() == null ? "" : dto_OmdbClassDetail.getGenre(),
                        dto_OmdbClassDetail.getRuntime() == null ? "" : dto_OmdbClassDetail.getRuntime(),
                        dto_OmdbClassDetail.getActors() == null ? "" : dto_OmdbClassDetail.getActors(),
                        dto_OmdbClassDetail.getImdbRating() == null ? "" : dto_OmdbClassDetail.getImdbRating(),
                        dto_OmdbClassDetail.getReleased() == null ? "" : dto_OmdbClassDetail.getReleased(),
                        dto_OmdbClassDetail.getAwards() == null ? "" : dto_OmdbClassDetail.getAwards(),
                        dto_OmdbClassDetail.getProduction() == null ? "" : dto_OmdbClassDetail.getProduction(),
                        dto_OmdbClassDetail.getDirector() == null ? "" : dto_OmdbClassDetail.getDirector(),
                        dto_OmdbClassDetail.getWriter() == null ? "" : dto_OmdbClassDetail.getWriter().replace("\'", "\'\'")
                                                                                                        .replace("\"", "\"\"")
                                                                                                        .replace("`", "``")
                                                                                                        .replace("\\", "\\\\"),
                        dto_OmdbClassDetail.getLanguage() == null ? "" : dto_OmdbClassDetail.getLanguage(),
                        dto_OmdbClassDetail.getWebsite() == null ? "" : dto_OmdbClassDetail.getWebsite(),
                        dto_OmdbClassDetail.getRated() == null ? "" : dto_OmdbClassDetail.getRated(),
                        dto_OmdbClassDetail.getCountry() == null ? "" : dto_OmdbClassDetail.getCountry(),
                        dto_OmdbClassDetail.getMetascore() == null ? "" : dto_OmdbClassDetail.getMetascore(),
                        dto_OmdbClassDetail.getImdbVotes() == null ? "" : dto_OmdbClassDetail.getImdbVotes(),
                        dto_OmdbClassDetail.getType() == null ? "" : dto_OmdbClassDetail.getType(),
                        dto_OmdbClassDetail.getDVD() == null ? "" : dto_OmdbClassDetail.getDVD(),
                        dto_OmdbClassDetail.getBoxOffice() == null ? "" : dto_OmdbClassDetail.getBoxOffice());
            }
        });
    }
    private void insert(String Title
                        , String ImdbId
                        , String Year
                        , String Poster
                        , String Genre
                        , String Runtime
                        , String Actors
                        , String ImdbRating
                        , String Released
                        , String Awards
                        , String Production
                        , String Director
                        , String Writer
                        , String Language
                        , String Website
                        , String Rated
                        , String Country
                        , String Metascore
                        , String ImdbVotes
                        , String Type
                        , String DVD
                        , String BoxOffice) {
        String INSERT_OmdbInformation_QUERY = "INSERT INTO " + TABLE_NAME + "(Title,ImdbId,Year,Poster,Genre,Runtime,Actors,ImdbRating,Released,Awards,Production,Director,Writer,Language,Website,Rated,Country,Metascore,ImdbVotes,Type,DVD,BoxOffice)" +
                "VALUES("
                            + "'" + Title + "'" + "," +
                            "'" + ImdbId + "'" + ","+
                            "'" + Year + "'" + ","+
                            "'" + Poster + "'" + ","+
                            "'" + Genre + "'" + ","+
                            "'" + Runtime + "'" + ","+
                            "'" + Actors + "'" + ","+
                            "'" + ImdbRating + "'" + ","+
                            "'" + Released + "'" + ","+
                            "'" + Awards + "'" + ","+
                            "'" + Production + "'" + ","+
                            "'" + Director + "'" + ","+
                            "'" + Writer + "'" + ","+
                            "'" + Language + "'" + ","+
                            "'" + Website + "'" + ","+
                            "'" + Rated + "'" + ","+
                            "'" + Country + "'" + ","+
                            "'" + Metascore + "'" + ","+
                            "'" + ImdbVotes + "'"+ ","+
                            "'" + Type + "'" + ","+
                            "'" + DVD + "'" + ","+
                            "'" + BoxOffice + "'" +
                        ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(INSERT_OmdbInformation_QUERY);
        db.close();
    }
    public void DeleteOmdbInformation( String ImdbId) {
        String Delete_OmdbInformation_QUERY = "Delete From " + TABLE_NAME +" Where ImdbId="+ "'" + ImdbId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Delete_OmdbInformation_QUERY);
        db.close();
    }

    List<OmdbDetailClass> getAllOmdbInformation() {

        String GET_ALL_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster,Genre,Runtime,Actors,ImdbRating,Released,Awards,Production,Director,Writer,Language,Website,Rated,Country,Metascore,ImdbVotes,Type,DVD,BoxOffice FROM " + TABLE_NAME;
        ArrayList<OmdbDetailClass> OmdbDetailClassList = new ArrayList<>();
        OmdbDetailClass omdbDetailClass = new OmdbDetailClass();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            omdbDetailClass = new OmdbDetailClass();

            omdbDetailClass.setTitle(cursor.getString(0));
            omdbDetailClass.setImdbID(cursor.getString(1));
            omdbDetailClass.setYear(cursor.getString(2));
            omdbDetailClass.setPoster(cursor.getString(3));
            omdbDetailClass.setGenre(cursor.getString(4));
            omdbDetailClass.setRuntime(cursor.getString(5));
            omdbDetailClass.setActors(cursor.getString(6));
            omdbDetailClass.setImdbRating(cursor.getString(7));
            omdbDetailClass.setReleased(cursor.getString(8));
            omdbDetailClass.setAwards(cursor.getString(9));
            omdbDetailClass.setProduction(cursor.getString(10));
            omdbDetailClass.setDirector(cursor.getString(11));
            omdbDetailClass.setWriter(cursor.getString(12));
            omdbDetailClass.setLanguage(cursor.getString(13));
            omdbDetailClass.setWebsite(cursor.getString(14));
            omdbDetailClass.setRated(cursor.getString(15));
            omdbDetailClass.setCountry(cursor.getString(16));
            omdbDetailClass.setMetascore(cursor.getString(17));
            omdbDetailClass.setImdbVotes(cursor.getString(18));
            omdbDetailClass.setType(cursor.getString(19));
            omdbDetailClass.setDVD(cursor.getString(20));
            omdbDetailClass.setBoxOffice(cursor.getString(21));

            OmdbDetailClassList.add(omdbDetailClass);
        }
        db.close();
        return OmdbDetailClassList;
    }

    OmdbClass getAllOmdbInformation_Favorite() {

        String GET_ALL_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster FROM " + TABLE_NAME;
        OmdbClass dto_OmdbClass = new OmdbClass();
        List<Search> lst_dto_Search = new ArrayList<Search>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            Search dto_Search = new Search();

            dto_Search.setTitle(cursor.getString(0));
            dto_Search.setImdbID(cursor.getString(1));
            dto_Search.setYear(cursor.getString(2));
            dto_Search.setPoster(cursor.getString(3));

            lst_dto_Search.add(dto_Search);
        }
        dto_OmdbClass.setSearch(lst_dto_Search);
        db.close();
        return dto_OmdbClass;
    }

    boolean ExsitsFilm(String ImdbId)
    {

        String GET_OmdbInformation_QUERY = "SELECT ImdbId FROM " + TABLE_NAME+ " Where ImdbId="+ "'" + ImdbId + "'";
        OmdbDetailClass omdbDetailClass = new OmdbDetailClass();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            if (ImdbId.equals(cursor.getString(0)))
            {
                db.close();
                return  true    ;
            }
        }
        db.close();
        return false;
    }

    OmdbDetailClass GetRowOmdbInformation(String ImdbId)
    {

        String GET_OmdbInformation_QUERY = "SELECT Title,ImdbId,Year,Poster,Genre,Runtime,Actors,ImdbRating,Released,Awards,Production,Director,Writer,Language,Website,Rated,Country,Metascore,ImdbVotes,Type,DVD,BoxOffice FROM " + TABLE_NAME+ " Where ImdbId="+ "'" + ImdbId + "'";
        OmdbDetailClass omdbDetailClass = new OmdbDetailClass();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_OmdbInformation_QUERY, null);

        while (cursor.moveToNext()) {
            if (ImdbId.equals(cursor.getString(1)))
            {
                omdbDetailClass.setTitle(cursor.getString(0));
                omdbDetailClass.setImdbID(cursor.getString(1));
                omdbDetailClass.setYear(cursor.getString(2));
                omdbDetailClass.setPoster(cursor.getString(3));
                omdbDetailClass.setGenre(cursor.getString(4));
                omdbDetailClass.setRuntime(cursor.getString(5));
                omdbDetailClass.setActors(cursor.getString(6));
                omdbDetailClass.setImdbRating(cursor.getString(7));
                omdbDetailClass.setReleased(cursor.getString(8));
                omdbDetailClass.setAwards(cursor.getString(9));
                omdbDetailClass.setProduction(cursor.getString(10));
                omdbDetailClass.setDirector(cursor.getString(11));
                omdbDetailClass.setWriter(cursor.getString(12));
                omdbDetailClass.setLanguage(cursor.getString(13));
                omdbDetailClass.setWebsite(cursor.getString(14));
                omdbDetailClass.setRated(cursor.getString(15));
                omdbDetailClass.setCountry(cursor.getString(16));
                omdbDetailClass.setMetascore(cursor.getString(17));
                omdbDetailClass.setImdbVotes(cursor.getString(18));
                omdbDetailClass.setType(cursor.getString(19));
                omdbDetailClass.setDVD(cursor.getString(20));
                omdbDetailClass.setBoxOffice(cursor.getString(21));
            }
        }
        db.close();
        return   omdbDetailClass;

    }
}
