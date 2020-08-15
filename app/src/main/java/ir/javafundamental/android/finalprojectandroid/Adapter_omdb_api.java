package ir.javafundamental.android.finalprojectandroid;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter_omdb_api extends RecyclerView.Adapter<Adapter_omdb_api.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Search> Data;
    private Context mContext;
    private ItemClickListener mClickListener;

    public Adapter_omdb_api  (List<Search> ListSearchClass ,Context context)
    {
        this.mInflater = LayoutInflater.from(context);
        Data=ListSearchClass;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Adapter_omdb_api.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.myviewholder_omdb_api, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_omdb_api.ViewHolder holder, int position) {
        Search dto_search = Data.get(position);
        try
        {
            holder.txt_Title.setText(String.valueOf(dto_search.getTitle()));
            holder.txt_year.setText(String.valueOf(dto_search.getYear()));
            holder.txt_imdbID.setText(String.valueOf(dto_search.getImdbID()));
            holder.txt_type.setText(String.valueOf(dto_search.getType()));
            Glide.with(this.mContext).load(dto_search.getPoster()).into(holder.img_poster);
            holder.img_poster.setTag(dto_search.getPoster());

            OmdbRepository database = new OmdbRepository(holder.btnSave.getContext(), "OmdbV3", null, 1);
            if  (database.ExsitsFilm(dto_search.getImdbID())) {
                holder.btnSave.setBackground(ContextCompat.getDrawable(holder.btnSave.getContext(), R.drawable.ic_save));
            }
            else {
                holder.btnSave.setBackground(ContextCompat.getDrawable(holder.btnSave.getContext(), R.drawable.ic_notsave));
            }
        }
         catch (Exception ex){
             holder.img_poster.setBackground(ContextCompat.getDrawable(holder.img_poster.getContext(), R.drawable.ic_no_image_available));
             holder.txt_Title.setText(this.mContext.getString(R.string.error_for_connection));
             holder.txt_year.setText(this.mContext.getString(R.string.error_for_connection));
             holder.txt_imdbID.setText(this.mContext.getString(R.string.error_for_connection));
             holder.txt_type.setText(this.mContext.getString(R.string.error_for_connection));
             holder.img_poster.setTag("");
        }
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        Button myButton;
        TextView txt_Title;
        TextView txt_year;
        TextView txt_imdbID;
        TextView txt_type;
        Button btnMore;
        Button btnSave;
        ImageView img_poster;

        ViewHolder(View itemView) {
            super(itemView);
            txt_Title = itemView.findViewById(R.id.title);
            txt_year = itemView.findViewById(R.id.year);
            txt_imdbID = itemView.findViewById(R.id.imdbID);
            txt_type = itemView.findViewById(R.id.type);
            img_poster = itemView.findViewById(R.id.imgShort);
            btnMore = itemView.findViewById(R.id.btnMore);
            btnSave = itemView.findViewById(R.id.btnSave);
            btnMore.setOnClickListener(this);
            btnSave.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(txt_imdbID.getText().toString(), view.getId(), view);
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(String imdbID, Integer typeButton, View view);
    }

}
