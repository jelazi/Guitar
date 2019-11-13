/**
 * Copyright 2017 Kailash Dabhi (Kingbull Technology)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lubin.guitar.Teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import lubin.guitar.R;
import omrecorder.AudioChunk;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;
import omrecorder.WriteAction;

/**
 * @author Kailash Dabhi
 * @date 18-07-2016. Copyright (c) 2017 Kingbull Technology. All rights reserved.
 */
public class WavRecorderActivity extends AppCompatActivity {
  Recorder recorder;
  ImageView recordButton;
  AudioManager audioManager;
  CountDownTimer countDownTimer;
  TextView countdownText;
  EditText nameFile;
  boolean isRecording;
  SoundPool soundPool;
  int soundId;
  Button playBtn;
  Button saveBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recorder);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(getResources().getString(R.string.posibility_recording));
    countdownText = findViewById(R.id.countdown);
    nameFile = findViewById(R.id.name_file);
    playBtn =  findViewById(R.id.play_btn);
    playBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        playRecord();
      }
    });
    playBtn.setEnabled(false);
    saveBtn = findViewById(R.id.save_btn);
    saveBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          copyRecord();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    saveBtn.setEnabled(false);
    isRecording = false;
    recordButton = findViewById(R.id.recordButton);
    recordButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startRecord();
      }
    });

    audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    int maxStreams = 4;
    int streamType = AudioManager.STREAM_MUSIC;
    int srcQuality = 0;
    soundPool = new SoundPool(maxStreams, streamType, srcQuality);

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
    int id = item.getItemId();
    switch(id)
    {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return true;
  }

  private void setupRecorder() {
    recorder = OmRecorder.wav(
        new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
          @Override
          public void onAudioChunkPulled(AudioChunk audioChunk) {
            animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
          }
        }), file());
  }

  private void playRecord() {
    File file = new File(getFilesDir() +
            File.separator + "Temp" +
            File.separator + "01.wav");
    if (file.exists() && soundId != 0) {
      float vol = audioManager.getStreamVolume(
              AudioManager.STREAM_MUSIC);
      float maxVol = audioManager.getStreamMaxVolume(
              AudioManager.STREAM_MUSIC);
      float leftVolume = vol / maxVol;
      float rightVolume = vol / maxVol;
      int priority = 1;
      int no_loop = 0;


      soundPool.play(soundId, leftVolume, rightVolume, priority, no_loop, 1);
    } else {
      Toast.makeText(this, getResources().getString(R.string.warning_nothing_record), Toast.LENGTH_LONG);
    }
    }



  private void copyRecord() throws IOException {
    File sourceFolder = new File(getFilesDir() +
            File.separator + "Temp");
    final File sourceFile = new File (sourceFolder, "01.wav");
    File destFolder = new File(getFilesDir() +
            File.separator + "Instruments");
    String name = nameFile.getText().toString();
    final File destFile = new File (destFolder, name + ".wav");
    if (!destFile.getParentFile().exists())
      destFile.getParentFile().mkdirs();

    if (!destFile.exists()) {
      destFile.createNewFile();
    } else {
      AlertDialog dialog;
      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(getResources().getString(R.string.warning_file_exists));
      builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  FileChannel source = null;
                  FileChannel destination = null;

                  try {
                    source = new FileInputStream(sourceFile).getChannel();
                    destination = new FileOutputStream(destFile).getChannel();
                    destination.transferFrom(source, 0, source.size());
                  } catch (FileNotFoundException e) {
                    e.printStackTrace();
                  } catch (IOException e) {
                    e.printStackTrace();
                  } finally {
                    if (source != null) {
                      try {
                        source.close();
                      } catch (IOException e) {
                        e.printStackTrace();
                      }
                    }
                    if (destination != null) {
                      try {
                        destination.close();
                      } catch (IOException e) {
                        e.printStackTrace();
                      }
                    }
                  }
                }
      });
                  builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                  });
                  builder.show();
    }
  }

  private void startRecord() {
    countdownText.setText("4");
    setupNoiseRecorder();
    countDownTimer = new CountDownTimer(5000, 500) {
      public void onTick(long millisUntilFinished) {
        if (millisUntilFinished >= 1000) {
          String text = Long.toString((millisUntilFinished / 1000 - 1));
          countdownText.setText(text);
          getSupportActionBar().setTitle(getResources().getString(R.string.warning_prepare_recording));
        } else {
          getSupportActionBar().setTitle(getResources().getString(R.string.warning_recording));

          countdownText.setText(getResources().getString(R.string.warning_recording));
          recorder.startRecording();
        }
      }

      public void onFinish() {
        getSupportActionBar().setTitle(getResources().getString(R.string.posibility_recording));
        try {
          recorder.stopRecording();
          isRecording = false;
          soundId = soundPool.load(getFilesDir() + File.separator + "Temp" + File.separator + "01.wav", 1);
        } catch (IOException e) {
          e.printStackTrace();
        }
        playBtn.setEnabled(true);
        saveBtn.setEnabled(true);
        countdownText.setText(getResources().getString(R.string.recording_finish));
      }
    };
    countDownTimer.start();
  }


  private void setupNoiseRecorder() {
    recorder = OmRecorder.wav(
        new PullTransport.Noise(mic(),
            new PullTransport.OnAudioChunkPulledListener() {
              @Override
              public void onAudioChunkPulled(AudioChunk audioChunk) {
                animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
              }
            },
            new WriteAction.Default(),
            new Recorder.OnSilenceListener() {
              @Override
              public void onSilence(long silenceTime) {
                Log.e("silenceTime", String.valueOf(silenceTime));
                Toast.makeText(WavRecorderActivity.this, "silence of " + silenceTime + " detected",
                    Toast.LENGTH_SHORT).show();
              }
            }, 200
        ), file()
    );
  }

  private void animateVoice(final float maxPeak) {
    recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
  }

  private PullableSource mic() {
    return new PullableSource.Default(
        new AudioRecordConfig.Default(
            MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
            AudioFormat.CHANNEL_IN_MONO, 44100
        )
    );
  }

  @NonNull
  private File file() {
    File folder = new File(getFilesDir() +
            File.separator + "Temp");
    boolean success = true;
    if (!folder.exists()) {
      success = folder.mkdirs();
    }
    if (success) {
      // Do something on success
    } else {
      // Do something else on failure
    }
    return new File(folder, "01.wav");
  }
}
