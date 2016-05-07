package fr.fges.fixmycity.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.models.Degradation;

/**
 * Created by Guillaume on 06/05/2016.
 */

public class DegradationsAdapter extends RecyclerView.Adapter<DegradationsAdapter.ViewHolder> {

    private List<Degradation> mDegradationsList;

    public DegradationsAdapter(List<Degradation> degradations) {
        this.mDegradationsList = degradations;
    }

    @Override
    public DegradationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.degradation_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DegradationsAdapter.ViewHolder holder, int position) {
        Degradation degradation = mDegradationsList.get(position);

        holder.mDescription.setText(degradation.getmDescription());
        holder.mReference.setText(degradation.getmReference());
        Context context = holder.mImage.getContext();
        Glide
                .with(context)
                .load(degradation.getmImagePath())
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mDegradationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mReference;
        private TextView mDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.degradation_item_imv);
            mReference = (TextView) itemView.findViewById(R.id.degradation_item_reference);
            mDescription = (TextView) itemView.findViewById(R.id.degradation_item_description);
        }
    }
}
