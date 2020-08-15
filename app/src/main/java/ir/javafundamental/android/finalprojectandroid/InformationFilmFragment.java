package ir.javafundamental.android.finalprojectandroid;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class InformationFilmFragment extends Fragment {

    OmdbDetailClass dto_OmdbClassDetail= new OmdbDetailClass() ;
    boolean ExsitsFilm=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dto_OmdbClassDetail = (OmdbDetailClass) this.getArguments().getSerializable("dto_OmdbClassDetail");

        ImageView poster = view.findViewById(R.id.poster);
        Button btn_Save = view.findViewById(R.id.AddToFavorite);
        TextView txtTitleDetail = view.findViewById(R.id.titledetail);
        TextView txtyearDetail = view.findViewById(R.id.yeardetail);
        TextView txtruntimedetail = view.findViewById(R.id.runtimedetail);
        TextView txtgenredetail = view.findViewById(R.id.genredetail);
        TextView txtplotdetail = view.findViewById(R.id.plotdetail);
        TextView txtimdbRatingdetail = view.findViewById(R.id.imdbRatingdetail);
        TextView txtreleaseddetail = view.findViewById(R.id.releaseddetail);
        TextView txtawardsdetail = view.findViewById(R.id.awardsdetail);
        TextView txtproductionsdetail = view.findViewById(R.id.productionsdetail);
        TextView txtdirectordetail = view.findViewById(R.id.directordetail);
        TextView txtwriterdetail = view.findViewById(R.id.writerdetail);
        TextView txtactorsdetail = view.findViewById(R.id.actorsdetail);
        TextView txtlanguagedetail = view.findViewById(R.id.languagedetail);
        TextView txtwebsitedetail = view.findViewById(R.id.websitedetail);
        TextView txtrateddetail = view.findViewById(R.id.rateddetail);
        TextView txtcountrydetail = view.findViewById(R.id.countrydetail);
        TextView txtmetascoredetail = view.findViewById(R.id.metascoredetail);
        TextView txtimdbVotesdetail = view.findViewById(R.id.imdbVotesdetail);
        TextView txttypedetail = view.findViewById(R.id.typedetail);
        TextView txtDVDdetail = view.findViewById(R.id.DVDdetail);
        TextView txtboxOfficedetail = view.findViewById(R.id.boxOfficedetail);

        Glide.with(view.getContext()).load(dto_OmdbClassDetail.getPoster()).into(poster);
        txtTitleDetail.setText(dto_OmdbClassDetail.getTitle().toString());
        txtyearDetail.setText(dto_OmdbClassDetail.getYear().toString());
        txtruntimedetail.setText(dto_OmdbClassDetail.getRuntime() == null ? "" : dto_OmdbClassDetail.getRuntime());
        txtgenredetail.setText(dto_OmdbClassDetail.getGenre() == null ? "" : dto_OmdbClassDetail.getGenre());
        txtplotdetail.setText(dto_OmdbClassDetail.getPlot() == null ? "" : dto_OmdbClassDetail.getPlot());
        txtimdbRatingdetail.setText(dto_OmdbClassDetail.getImdbRating() == null ? "" : dto_OmdbClassDetail.getImdbRating());
        txtreleaseddetail.setText(dto_OmdbClassDetail.getReleased() == null ? "" : dto_OmdbClassDetail.getReleased());
        txtawardsdetail.setText(dto_OmdbClassDetail.getAwards() == null ? "" : dto_OmdbClassDetail.getAwards());
        txtproductionsdetail.setText(dto_OmdbClassDetail.getProduction() == null ? "" : dto_OmdbClassDetail.getProduction());
        txtdirectordetail.setText(dto_OmdbClassDetail.getDirector() == null ? "" : dto_OmdbClassDetail.getDirector());
        txtwriterdetail.setText(dto_OmdbClassDetail.getWriter() == null ? "" : dto_OmdbClassDetail.getWriter());
        txtactorsdetail.setText(dto_OmdbClassDetail.getActors() == null ? "" : dto_OmdbClassDetail.getActors());
        txtlanguagedetail.setText(dto_OmdbClassDetail.getLanguage() == null ? "" : dto_OmdbClassDetail.getLanguage());
        txtwebsitedetail.setText(dto_OmdbClassDetail.getWebsite() == null ? "" : dto_OmdbClassDetail.getWebsite());
        txtrateddetail.setText(dto_OmdbClassDetail.getRated() == null ? "" : dto_OmdbClassDetail.getRated());
        txtcountrydetail.setText(dto_OmdbClassDetail.getCountry() == null ? "" : dto_OmdbClassDetail.getCountry());
        txtmetascoredetail.setText(dto_OmdbClassDetail.getMetascore() == null ? "" : dto_OmdbClassDetail.getMetascore());
        txtimdbVotesdetail.setText(dto_OmdbClassDetail.getImdbVotes() == null ? "" : dto_OmdbClassDetail.getImdbVotes());
        txttypedetail.setText(dto_OmdbClassDetail.getType() == null ? "" : dto_OmdbClassDetail.getType());
        txtDVDdetail.setText(dto_OmdbClassDetail.getDVD() == null ? "" : dto_OmdbClassDetail.getDVD());
        txtboxOfficedetail.setText(dto_OmdbClassDetail.getBoxOffice() == null ? "" : dto_OmdbClassDetail.getBoxOffice());

        OmdbRepository database = new OmdbRepository(view.getContext(), "OmdbV3", null, 1);
        if  (database.ExsitsFilm(dto_OmdbClassDetail.getImdbID().toString()))
        {
            ExsitsFilm=true;
            btn_Save.setBackground(ContextCompat.getDrawable(btn_Save.getContext(), R.drawable.ic_save));
        }
        else
        {
            ExsitsFilm=false;
            btn_Save.setBackground(ContextCompat.getDrawable(btn_Save.getContext(), R.drawable.ic_notsave));
        }

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OmdbRepository database = new OmdbRepository(v.getContext(), "OmdbV3", null, 1);
                if  (ExsitsFilm) {
                    database.DeleteOmdbInformation( dto_OmdbClassDetail.getImdbID());
                    Button btn_Save = v.findViewById(R.id.AddToFavorite);
                    btn_Save.setBackground(ContextCompat.getDrawable(btn_Save.getContext(), R.drawable.ic_notsave));
                    ExsitsFilm=false;
                }
                else {
                    Button btn_Save = v.findViewById(R.id.AddToFavorite);
                    database.insertOmdbInformation(dto_OmdbClassDetail.getImdbID());
                    btn_Save.setBackground(ContextCompat.getDrawable(btn_Save.getContext(), R.drawable.ic_save));
                    ExsitsFilm=true;
                }
            }
        });
    }

}