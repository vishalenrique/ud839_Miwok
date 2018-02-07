package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bhati on 02-Feb-18.
 */

public class WordAdapter extends ArrayAdapter<Word>{

    int color;

     WordAdapter(Context context, List<Word> words,int color){
        super(context,0,words);
        this.color=color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView== null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        final Word word=getItem(position);
        TextView miwokTextView=convertView.findViewById(R.id.miwok_text_view);
        TextView defaultTextView=convertView.findViewById(R.id.default_text_view);
        ImageView imageView=convertView.findViewById(R.id.image_view);
        ImageView audioButton=convertView.findViewById(R.id.play_button);

        miwokTextView.setText(word.getMiwokTranslation());
        defaultTextView.setText(word.getDefaultTranslation());

        if(word.getImageResourceId() == 0){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(word.getImageResourceId());
        }

        View viewContainer=convertView.findViewById(R.id.text_container);
        int colorHex=ContextCompat.getColor(getContext(),color);
        viewContainer.setBackgroundColor(colorHex);

        return convertView;
    }
}
