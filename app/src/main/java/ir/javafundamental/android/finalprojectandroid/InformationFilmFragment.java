package ir.javafundamental.android.finalprojectandroid;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        TextView txtyearDetail = view.findViewById(R.id.yeardetail);
        TextView txtTitleDetail = view.findViewById(R.id.titledetail);
        ImageView poster = view.findViewById(R.id.poster);
        Button btn_Save = view.findViewById(R.id.AddToFavorite);

        txtyearDetail.setText(dto_OmdbClassDetail.getYear().toString());
        txtTitleDetail.setText(dto_OmdbClassDetail.getTitle().toString());
        Glide.with(view.getContext()).load(dto_OmdbClassDetail.getPoster()).into(poster);
        OmdbRepository database = new OmdbRepository(view.getContext(), "OmdbV1", null, 1);
        if  (database.ExsitsFilm(dto_OmdbClassDetail.getImdbID().toString())==true)
        {
            ExsitsFilm=true;
            btn_Save.setText("حذف");
        }
        else
        {
            ExsitsFilm=false;
            btn_Save.setText("ذخیره");
        }

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OmdbRepository database = new OmdbRepository(v.getContext(), "OmdbV1", null, 1);
                if  (ExsitsFilm) {
                    database.DeleteOmdbInformation( dto_OmdbClassDetail.getImdbID().toString());
                    Button btn_Save = v.findViewById(R.id.AddToFavorite);
                    btn_Save.setText("ذخیره");
                    ExsitsFilm=false;
                }
                else {
                    Button btn_Save = v.findViewById(R.id.AddToFavorite);
                    database.insertOmdbInformation(dto_OmdbClassDetail.getTitle().toString(), dto_OmdbClassDetail.getImdbID().toString(), dto_OmdbClassDetail.getYear().toString(), dto_OmdbClassDetail.getPoster().toString());
                    btn_Save.setText("حذف");
                    ExsitsFilm=true;
                }


            }
        });
    }

}