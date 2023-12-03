package com.faizanahmed.janabhazir;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.ViewHolder> {

    private List<Vendor> vendorList;



    public VendorAdapter(List<Vendor> vendorList) {
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vendor vendor = vendorList.get(position);
        holder.vendorName.setText(vendor.getName());
        holder.ratingBarTimeliness.setRating(vendor.getAverageTimelinessScore());
        holder.ratingBarQuality.setRating(vendor.getAverageQualityOfServiceScore());
        holder.ratingBarExpertise.setRating(vendor.getAverageExpertiseScore());
        // Load the image into the ImageView. You can use a library like Glide or Picasso.
        // Glide.with(holder.itemView.getContext()).load(vendor.getImageUrl()).into(holder.vendorImage);
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vendorImage;
        TextView vendorName;
        RatingBar ratingBarTimeliness;
        RatingBar ratingBarQuality;
        RatingBar ratingBarExpertise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vendorImage = itemView.findViewById(R.id.image_vendor);
            vendorName = itemView.findViewById(R.id.text_vendor_name);
            ratingBarTimeliness = itemView.findViewById(R.id.ratingBarTimeliness);
            ratingBarQuality = itemView.findViewById(R.id.ratingBarQuality);
            ratingBarExpertise = itemView.findViewById(R.id.ratingBarExpertise);
        }
    }
}
