package com.adsprovider.combineadinventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adsprovider.combineadinventory.ArticleActivity;
import com.adsprovider.combineadinventory.R;
import com.adsprovider.combineadinventory.ad.AdmobAds;
import com.adsprovider.combineadinventory.model.News;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<News> newsArrayList;
    private final Context context;
    private static final int VIEW_TYPE_CONTENT = 0;
    private static final int VIEW_TYPE_AD = 1;

    public NewsAdapter(Context context, List<News> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_AD) {
            View adView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_view_holder, parent, false);
            return new AdViewHolder(adView);
        } else {
            View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            return new NewsViewHolder(contentView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_AD) {
            AdViewHolder adHolder = (AdViewHolder) viewHolder;
            AdmobAds admobAds = new AdmobAds();
            admobAds.loadBannerAd(NewsAdapter.class.getName(),context,adHolder.adContainer);
        } else if (viewHolder instanceof NewsViewHolder ) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
            News news = newsArrayList.get(position);
            newsViewHolder.titleTextView.setText(news.getNewsTitle());
            newsViewHolder.authorTextView.setText("by "+news.getAuthorName());
            Glide.with(context).load(news.newsImageURL).into(newsViewHolder.newsImageView);
            newsViewHolder.itemView.setOnClickListener(v->{
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("title", news.getNewsTitle());
                intent.putExtra("author", news.getAuthorName());
                intent.putExtra("description", news.getDescription());
                intent.putExtra("imageUrl", news.newsImageURL);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Insert an ad after every 3 content items
        if ((position+1) % 5 == 0) { // Ad at every 4th position (0-based index)
            return VIEW_TYPE_AD;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }


    @Override
    public int getItemCount() {
        // Add additional items for ads
//        int adCount = newsArrayList.size() / 3; // 1 ad for every 3 items
//        return newsArrayList.size() + adCount;
        return 50;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView authorTextView;
        public ShapeableImageView newsImageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            newsImageView = itemView.findViewById(R.id.newsImageView);
        }
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {
        FrameLayout adContainer;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            adContainer = itemView.findViewById(R.id.adHolder);
        }
    }

}
