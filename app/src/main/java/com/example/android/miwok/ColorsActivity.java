package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    AudioManager audioManager;

    /**
     * its used for controlling the audio and accordingly changing the state of the it.
     */
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener =new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange){
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    mediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mediaPlayer.pause();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words=new ArrayList<>();
        words.add(new Word("black","kaala",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("red","laal",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("green","hara",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown","bhoora",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray","dhoosar",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("white","safaed",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("dusty yellow","peela",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow","doosra peela",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter arrayAdapter=new WordAdapter(this,words,R.color.category_colors);
        ListView listView=findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //   Word word= words.get(i);
                Word word= (Word) adapterView.getItemAtPosition(i);

                //In case user click multiple item quickly
                releaseMediaPlayer();

                if (audioManager != null) {
                    if(audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmAudioResourceId());
                        mediaPlayer.start();

                        // when the audio completes playing callback//
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                releaseMediaPlayer();
                            }
                        });
                    }
                }
            }
        });
    }

    private void releaseMediaPlayer() {
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
