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

public class PhrasesActivity extends AppCompatActivity {

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
        words.add(new Word("one","ek",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","do",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","teen",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","chaar",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","paanch",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","chaeh",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","saath",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","aath",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","naw",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","dus",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter arrayAdapter=new WordAdapter(this,words,R.color.category_phrases);
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
                        mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmAudioResourceId());
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
