package com.faizanahmed.i200546;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.os.Handler;


public class MainActivity9 extends AppCompatActivity {

    private NetworkChangeReceiver networkChangeReceiver;

    private byte[] convertFileToByteArray(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int PICK_IMAGE_REQUEST = 102;
    //List<ChatMessage> messages = new ArrayList<>();
    List<ChatMessage> chatMessages = new ArrayList<>();
    private SQLDbHelper dbHelper;
    ChatMessageAdapter adapter ;
    private static final int CAPTURE_IMAGE_REQUEST = 103;
    private static final int PICK_MEDIA_REQUEST = 104;
    Uri imageUri;
    private MediaRecorder mediaRecorder;
    private String audioFilePath;
    private static final int RECORD_AUDIO_REQUEST = 105;
    boolean isRecording = false;


    RecyclerView recyclerView;
    private final Handler handler = new Handler();
    private ScreenshotContentObserver contentObserver;

    private int getLastMessageId() {
        List<ChatMessage> messages = dbHelper.getAllMessages();
        if (messages.isEmpty()) {
            return 0;
        }
        Log.d("MainActivity9", "Last message ID: " + messages.get(messages.size() - 1).getId());
        return messages.get(messages.size() - 1).getId();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        recyclerView=findViewById(R.id.Chats_LinearLayout);

        dbHelper = new SQLDbHelper(this);
        adapter = new ChatMessageAdapter(chatMessages,this,dbHelper);


        RecyclerView recyclerView = findViewById(R.id.Chats_LinearLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chatMessages = dbHelper.getAllMessages();
        //adapter = new ChatMessageAdapter(chatMessages);
        recyclerView.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("ChatPrefs", MODE_PRIVATE);
        boolean isDataAdded = prefs.getBoolean("data_added", false);

        if (!isDataAdded) {
            dbHelper.addMessage(new ChatMessage(0,1,2, "Hi there!", "Timestamp", true, 0, null));
            dbHelper.addMessage(new ChatMessage(1,2,1, "Hello!",  "Timestamp", false, 0, null));

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("data_added", true);
            editor.apply();
        }
        updateMessagesView();



        // recyclerView.setAdapter(adapter);
        TextView sendButton = findViewById(R.id.sendButton);
        EditText replyBox = findViewById(R.id.replybox);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = replyBox.getText().toString().trim();

                if (!messageText.isEmpty()) {
                    // Adding new message to the list and refreshing the RecyclerView
//                    messages.add(new ChatMessage(messageText, "You", "05:00", true,"",0));
//                    adapter.notifyItemInserted(messages.size() - 1);
//                    recyclerView.scrollToPosition(messages.size() - 1);
                    byte[] sampleByteArray = new byte[0];


                    ChatMessage newMessage = new ChatMessage((getLastMessageId() + 1),1,2, messageText,  "05:00", true, 0, sampleByteArray);
                    dbHelper.addMessage(newMessage);
                    MessageQueueManager.getInstance().enqueueMessage(newMessage);
                    if (NetworkChangeReceiver.isNetworkConnected) {
                        Intent serviceIntent = new Intent(MainActivity9.this, MessageSendingService.class);
                        startService(serviceIntent);
                    }

                    updateMessagesView();
                    // Clearing the EditText
                    replyBox.setText("");
                }
            }
        });

        ImageView imageButton = findViewById(R.id.ImageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity9.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    requestStoragePermission();
                }
            }
        });

        ImageView pictureButton = findViewById(R.id.PictureBtn);
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity9.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity9.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 98);
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                    }
                }
            }
        });



        ImageView VoiceCallBtn = findViewById(R.id.VoiceCallBtn);
        VoiceCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent intent = new Intent(MainActivity9.this, MainActivity18.class);
                //  startActivity(intent);
            }
        });

        ImageView VideoCallBtn = findViewById(R.id.VideoCallBtn);
        VideoCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(MainActivity9.this, MainActivity17.class);
                // startActivity(intent);
            }
        });

        ImageView audioButton = findViewById(R.id.AudioBtn);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity9.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(MainActivity9.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity9.this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RECORD_AUDIO_REQUEST);
                } else {
                    // Use a flag to check if we are currently recording
                    if (isRecording) {
                        // Stop recording
                        stopAudioRecording();
                        isRecording = false;
                    } else {
                        // Start recording
                        startAudioRecording();
                        isRecording = true;
                    }
                }
            }
        });



        contentObserver = new ScreenshotContentObserver(handler, this);
        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver);

        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }

    private void startAudioRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        // Generate a unique file name using the current timestamp
        String fileName = "audio_" + System.currentTimeMillis() + ".3gp";
        File audioFile = new File(getExternalFilesDir(null), fileName);

        // Update the MediaRecorder's output file path
        mediaRecorder.setOutputFile(audioFile.getAbsolutePath());

        // Store the path for later use
        audioFilePath = audioFile.getAbsolutePath();

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    private void stopAudioRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;

            File audioFile = new File(audioFilePath);
            byte[] audioData = convertFileToByteArray(audioFile);
            ChatMessage newMessage = new ChatMessage((getLastMessageId() + 1),1,2, "Audio recorded",  "Timestamp", true,3, audioData ); // 3 for audio
            dbHelper.addMessage(newMessage);
            MessageQueueManager.getInstance().enqueueMessage(newMessage);
            if (NetworkChangeReceiver.isNetworkConnected) {
                Intent serviceIntent = new Intent(MainActivity9.this, MessageSendingService.class);
                startService(serviceIntent);
            }
            updateMessagesView();
        }
    }



    private void updateMessagesView() {
        List<ChatMessage> chatMessages = dbHelper.getAllMessages();
        adapter.updateMessages(chatMessages);
        recyclerView.scrollToPosition(chatMessages.size() - 1); // Scroll to the last message
    }
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(MainActivity9.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        startActivityForResult(intent, PICK_MEDIA_REQUEST);

    }
    private void openCamera() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(this, "com.example.mmmsa.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        // currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Gallery", "Received activity result with requestCode: " + requestCode + " and resultCode: " + resultCode);

        if (requestCode == PICK_MEDIA_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedMediaUri = data.getData();
            String mimeType = getMimeType(selectedMediaUri);
            File mediaFile = new File(getPath(selectedMediaUri));
            byte[] mediaData = convertFileToByteArray(mediaFile);

            if (mimeType != null && mimeType.startsWith("image")) {
                ChatMessage newMessage = new ChatMessage((getLastMessageId() + 1),1,2, "picture sent",  "05:00", true,1, mediaData ); // 1 for image
                dbHelper.addMessage(newMessage);
                MessageQueueManager.getInstance().enqueueMessage(newMessage);
                if (NetworkChangeReceiver.isNetworkConnected) {
                    Intent serviceIntent = new Intent(MainActivity9.this, MessageSendingService.class);
                    startService(serviceIntent);
                }
            } else if (mimeType != null && mimeType.startsWith("video")) {
                //byte[] sampleByteArray = new byte[] { (byte) 0x3F, (byte) 0x7A, (byte) 0x55, (byte) 0xC6 };

                ChatMessage newMessage = new ChatMessage((getLastMessageId() + 1),1,2, "video sent",  "05:00", true, 2, mediaData); // 2 for video
                dbHelper.addMessage(newMessage);
                MessageQueueManager.getInstance().enqueueMessage(newMessage);
                if (NetworkChangeReceiver.isNetworkConnected) {
                    Intent serviceIntent = new Intent(MainActivity9.this, MessageSendingService.class);
                    startService(serviceIntent);
                }
            }
            updateMessagesView();
        } else if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageData = stream.toByteArray();

                // Store byte array in SQLite
                ChatMessage newMessage = new ChatMessage((getLastMessageId() + 1),1,2, "Image captured",  "05:00", true, 1, imageData);
                MessageQueueManager.getInstance().enqueueMessage(newMessage);
                dbHelper.addMessage(newMessage);
                if (NetworkChangeReceiver.isNetworkConnected) {
                    Intent serviceIntent = new Intent(MainActivity9.this, MessageSendingService.class);
                    startService(serviceIntent);
                }
                updateMessagesView();
            }
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }

        if (contentObserver != null) {
            getContentResolver().unregisterContentObserver(contentObserver);
        }
    }


}