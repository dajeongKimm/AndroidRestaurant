package com.example.tje.food;

import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class SimpleReviewAdapter extends RecyclerView.Adapter<SimpleReviewHolder> {

    private static final String LOG_TAG = "simplereviewadapter";
    private static final String URL = Const.SIMPLEREVIEWADAPTER_IP;
    private static final String M_IMAGE_URL = Const.SIMPLEREVIEWADAPTER_M_IP;

    List<ReviewListView> data;
    Intent receiveIntent;

    public void setData(List<ReviewListView> data, Intent receiveIntent) {
        this.data = data;
        this.receiveIntent = receiveIntent;
    }

    @Override
    public SimpleReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_review_item, viewGroup, false);
        return new SimpleReviewHolder(view, receiveIntent);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleReviewHolder simpleReviewHolder, int i) {
        final ReviewListView contact = data.get(i);

        simpleReviewHolder.restaurant_id.setText(contact.getRestaurant_id()+"");
        simpleReviewHolder.nicknameTv.setText(contact.getMember_nickname());
        simpleReviewHolder.like_count.setText(Integer.toString(contact.getSimple_review_like_count()));
        simpleReviewHolder.bad_count.setText(Integer.toString(contact.getSimple_review_notify_count()));
        simpleReviewHolder.review_registdate.setText(contact.getSimple_review_registdate());
        Log.d("=====================", contact.getTotal_score() + "");//
        simpleReviewHolder.contents_text.setText(contact.getSimple_review_contents_text());
        simpleReviewHolder.total_score_rating.setRating(contact.getTotal_score());
        simpleReviewHolder.review_id.setText(contact.getSimple_review_id() + "");


        if(contact.getFile_name() != null) {
            Picasso.get().load(URL + contact.getFile_name()).into(simpleReviewHolder.list_image);
            simpleReviewHolder.list_image.setVisibility(View.VISIBLE);
        }

        if(contact.getMember_photo() != null) {
            Picasso.get().load(M_IMAGE_URL + contact.getMember_photo()).into(simpleReviewHolder.member_photo_btn);
        } else {
            Picasso.get().load(M_IMAGE_URL + "default.png").into(simpleReviewHolder.member_photo_btn);
        }

//        new AsyncTask<String, Integer, Bitmap>() {
//
//            Bitmap bitmap;
//
//            @Override
//            protected Bitmap doInBackground(String... strings) {
//                try {
//
//                    if (contact.getFile_name() != null) {
//                        URL url = new URL(M_IMAGE_URL + contact.getMember_photo());
//                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.connect();
//
//                        bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//                    } else {
//                        URL url = new URL(M_IMAGE_URL + "default.png");
//                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.connect();
//
//                        bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//                    }
//
//
//                } catch (Exception e) {
//                    Log.d(LOG_TAG, e.getMessage());//
//                }
//
//                return bitmap;
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap bitmap) {
//                simpleReviewHolder.member_photo_btn.setImageBitmap(bitmap);
//            }
//        }.execute();


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
