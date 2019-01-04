package com.example.tje.food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tje.food.Model.ReviewListView;

import java.util.List;
import java.util.UUID;

public class SimpleReviewAdapter extends RecyclerView.Adapter<SimpleReviewHolder> {

    private static final String LOG_TAG = "simplereviewadapter";

    List<ReviewListView> data;
    Bitmap img;

    public void setData(List<ReviewListView> data) {
        this.data = data;
    }

    @Override
    public SimpleReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_review_item, viewGroup, false);
        return new SimpleReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleReviewHolder simpleReviewHolder, int i) {
        ReviewListView contact = data.get(i);

        simpleReviewHolder.nicknameTv.setText(contact.getMember_nickname());
        simpleReviewHolder.like_count.setText(Integer.toString(contact.getSimple_review_like_count()));
        simpleReviewHolder.bad_count.setText(Integer.toString(contact.getSimple_review_notify_count()));
        simpleReviewHolder.review_registdate.setText(contact.getSimple_review_registdate());
        Log.d("=====================", contact.getTotal_score() + "");//
        simpleReviewHolder.contents_text.setText(contact.getSimple_review_contents_text());
        simpleReviewHolder.total_score_rating.setRating(contact.getTotal_score());
        simpleReviewHolder.review_id.setText(contact.getSimple_review_id() + "");

        /*
        new AsyncTask<String, Integer, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... strings) {
                img = BitmapFactory.decodeFile("http://static.inven.co.kr/image_2011/site_image/lol/dataninfo/icon/skinfull/leblanc_splash_0.jpg?v=20181221a");

                return img;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                simpleReviewHolder.member_photo_btn.setImageBitmap(img);
            }
        }.execute();
        */

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
