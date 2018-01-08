package com.cet325.bg69xx;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MasterArtworksListCustomLayoutHandler extends ArrayAdapter<String>{

    private final Activity context;
    private final List<Integer> id;
    private final List<String> title;
    private final List<Bitmap> image;
    private final List<String> year;
    private final List<String> artist;
    private final List<Integer> rank;

    public MasterArtworksListCustomLayoutHandler(Activity context,List<Integer> id, List<String> artist, List<String> title, List<Bitmap> image, List<String> year, List<Integer> rank) {

        super(context, R.layout.master_artwork_list_element, title);
        this.id = id;
        this.context = context;
        this.artist = artist;
        this.title = title;
        this.image = image;
        this.year = year;
        this.rank = rank;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.master_artwork_list_element, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        txtTitle.setText(title.get(position));

        TextView txtArtist = (TextView) rowView.findViewById(R.id.txtArtist);
        txtArtist.setText(artist.get(position));

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgArtwork);
        imageView.setImageBitmap(image.get(position));

        TextView txtYear = (TextView) rowView.findViewById(R.id.txtYear);
        txtYear.setText(year.get(position));

        RatingBar rtngStar = (RatingBar) rowView.findViewById(R.id.ratingBarMaster);
        rtngStar.setRating(Float.parseFloat(String.valueOf(rank.get(position))));

        setOnClick(rtngStar,id.get(position), position);

        return rowView;
    }

    private void setOnClick(final RatingBar rtngStar, final int artoworkId, final int position) {
        rtngStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateRating(rating, artoworkId, position);
                ratingBar.setRating(rating);
            }
        });
    }

    private void updateRating(float rating, int artoworkId, int position) {
        MySqlLiteHelper db = new MySqlLiteHelper(super.getContext());
        db.updateArtworkRating(rating,artoworkId);
        db.close();
        Toast.makeText(super.getContext(),"Rating has been updated for " + title.get(position),Toast.LENGTH_SHORT).show();
    }

}
