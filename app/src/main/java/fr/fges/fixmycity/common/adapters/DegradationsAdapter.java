package fr.fges.fixmycity.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import fr.fges.fixmycity.R;

/**
 * Created by Guillaume on 06/05/2016.
 */

public class DegradationsAdapter extends RecyclerView.Adapter<DegradationsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<File> mItems;
    private int mSize;

    public DegradationsAdapter(Context context, File[] photos, int size) {
        this.mContext = context;
        this.mSize = size;
        mItems = new ArrayList<>();
        if (photos != null && photos.length > 0) {
            for (File photo : photos) {
                mItems.add(photo);
            }
        }
    }

    @Override
    public DegradationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.degradation_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DegradationsAdapter.ViewHolder holder, int position) {
        if (mItems != null && mItems.size() > 0) {
            if (mItems.get(position).exists()) {
                Glide
                        .with(mContext)
                        .load(mItems.get(position).getPath())
                        .override(mSize, mSize)
                        .into(holder.mImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(File photo) {
        this.mItems.add(photo);
        notifyDataSetChanged();
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

            //TODO - DEBUG ONLY TO REMOVE
            mReference.setText("A018FFR8");
            mDescription.setText("This is a description of a degradation");
        }
    }
}
