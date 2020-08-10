package ir.javafundamental.android.finalprojectandroid;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter_omdb_api extends RecyclerView.Adapter<Adapter_omdb_api.ViewHolder> {

    private LayoutInflater mInflater;
    private List<OmdbClass> Data;
    private Context mContext;

    private Adapter_omdb_api  (List<OmdbClass> ListOmdbClass ,Context context)
    {
        mContext=context;
        Data=ListOmdbClass;
    }

    @NonNull
    @Override
    public Adapter_omdb_api.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.myviewholder_omdb_api, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_omdb_api.ViewHolder holder, int position) {
        OmdbClass omdbClass = Data.get(position);
        try {
            holder.txt_Title.setText(String.valueOf(omdbClass.getTitle()));
            holder.txt_year.setText(String.valueOf(omdbClass.getYear()));
            holder.txt_rated.setText(String.valueOf(omdbClass.getRated()));

            Glide.with(this.mContext).load(omdbClass.getPoster()).into(holder.img_poster);
        }
        catch (Exception ex){
            String xxx = ex.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener {
//        Button myButton;
        TextView txt_Title ;
        TextView txt_year ;
        TextView txt_rated ;
        ImageView img_poster;
        ViewHolder(View itemView) {
            super(itemView);
            txt_Title = itemView.findViewById(R.id.title);
            txt_year = itemView.findViewById(R.id.year);
            txt_rated = itemView.findViewById(R.id.rated);
            img_poster = itemView.findViewById(R.id.imgShort);
        }

        //@Override
        //public void onClick(View view) {
        //    if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        //}

    }
}
