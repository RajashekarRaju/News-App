package com.rkrjstudio.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    // Location Seperator to divide vies between time and date in layout seperately
    private static final String LOCATION_SEPARATOR = "T";

    NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    // ViewHolder class to hold different views to display in layout
    private static class ViewHolder {
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView primaryLocationView;
        private TextView locationOffsetView;
        private TextView descriptionTextView;
        private ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Initializing Viewholder with custom object
        ViewHolder holder;

        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.list_item, parent, false);
            holder = new ViewHolder();
            // Find the TextView with view ID title
            holder.titleTextView = convertView.findViewById(R.id.title);

            // Find the TextView with view ID author
            holder.authorTextView = convertView.findViewById(R.id.author);

            // Find the TextView with view ID time
            holder.primaryLocationView = convertView.findViewById(R.id.time);

            // Find the TextView with view ID date
            holder.locationOffsetView = convertView.findViewById(R.id.date);

            // Find the TextView with view ID description
            holder.descriptionTextView = convertView.findViewById(R.id.description);

            // Find the ImageView with view ID imageView
            holder.imageView = convertView.findViewById(R.id.imageView);

            // defining custom object to store all id's into holder
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // get positions of all items in holder from News class
        News news = getItem(position);

        assert news != null;
        // getting TextViews into holder from id's
        holder.titleTextView.setText(news.getTitle());
        holder.authorTextView.setText(news.getAuthor());
        holder.descriptionTextView.setText(news.getDescription());

        // display image into layout using Glide library
        RequestOptions options = new RequestOptions()
                .error(R.drawable.image_not_found)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(getContext())
                .load(news.getImage())
                .apply(options)
                .into(holder.imageView);

        // Formatting string to display time and date views separately in layout
        String originalLocation = news.getTime();
        // initializing two location objects to store respective strings locations
        String timeFirstLocation;
        String dateSecondLocation;

        // using LOCATION_SEPARATOR variable to seperate views from dataset
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            dateSecondLocation = parts[0] + LOCATION_SEPARATOR;
            timeFirstLocation = parts[1];
        } else {
            dateSecondLocation = getContext().getString(R.string.tseperate);
            timeFirstLocation = originalLocation;
        }

        // Trimming unnecessary string variables from url before fetching into layout
        // using replaceALL() method function
        timeFirstLocation = timeFirstLocation.replaceAll(".$", " ");
        holder.primaryLocationView.setText(timeFirstLocation);

        // Trimming unnecessary string variables from url before fetching into layout
        // using replaceALL() method function
        dateSecondLocation = dateSecondLocation.replaceAll(".$", " ");
        holder.locationOffsetView.setText(dateSecondLocation);

        return convertView;
    }

}