package com.devsyncit.schoolmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_profile extends AppCompatActivity {
    TextInputEditText student_name, student_id, student_class, student_email, student_phone_number;
    ImageButton edit_btn;
    LinearLayout buttons_layout;
    MaterialButton save_btn, cancel_btn;
    CircleImageView student_image;
    DatabaseReference dbRef;
    ProgressBar progressBar;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    private String base64Image = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        student_name = findViewById(R.id.student_name);
        student_id = findViewById(R.id.student_id);
        student_class = findViewById(R.id.student_class);
        student_email = findViewById(R.id.student_email);
        student_phone_number = findViewById(R.id.student_phone_number);
        edit_btn = findViewById(R.id.edit_btn);
        buttons_layout = findViewById(R.id.buttons_layout);
        save_btn = findViewById(R.id.save_btn);
        cancel_btn = findViewById(R.id.cancel_btn);
        student_image = findViewById(R.id.student_image);
        progressBar = findViewById(R.id.progressBar);


        student_image.setEnabled(false);

        //=====GETTING DATA FROM BUNDLE=====================
        Intent intent = getIntent();
        String std_name = intent.getStringExtra("student_name");
        String std_class = intent.getStringExtra("student_class_no");
        String std_id = intent.getStringExtra("student_roll_no");
        String std_email = intent.getStringExtra("student_email");
        String std_phone_number = intent.getStringExtra("student_phone_number");
        //===================================================


        student_name.setText(""+std_name);
        student_class.setText(""+std_class);
        student_id.setText(""+std_id);
        student_email.setText(std_email);
        student_phone_number.setText(std_phone_number);

        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(""+std_id);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        progressBar.setVisibility(View.VISIBLE);
                        new Thread(() -> {

                            try {
                                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                                Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);

                                // 🔁 Read EXIF data
                                @SuppressLint({"NewApi", "LocalSuppress"}) ExifInterface exif = new ExifInterface(getContentResolver().openInputStream(imageUri));
                                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                                Bitmap rotatedBitmap = rotateBitmapIfRequired(originalBitmap, orientation);

                                // 🔁 Resize and compress
                                Bitmap resizedBitmap = resizeBitmap(rotatedBitmap, 800);

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                                byte[] compressedBytes = stream.toByteArray();

                                base64Image = Base64.encodeToString(compressedBytes, Base64.DEFAULT);

                                runOnUiThread(() -> {
                                    student_image.setImageBitmap(resizedBitmap);
                                    progressBar.setVisibility(View.GONE);
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }).start();
                    }
                });

//        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result->{
//            student_image.setImageURI(result);
//        });
//


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String base64Image = snapshot.child("profileImage").getValue(String.class);

                    Bitmap bitmap = decodeBase64ToBitmap(base64Image);
                    student_image.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        student_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            }
        });


        StudentProfileDb studentProfileDb = new StudentProfileDb(Student_profile.this);

//        SQLiteDatabase profileDb = studentProfileDb.getReadableDatabase();
//        studentProfileDb.selectData()



        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student_name.setEnabled(true);
                student_email.setEnabled(true);
                student_phone_number.setEnabled(true);
                student_image.setEnabled(true);

                buttons_layout.setVisibility(View.VISIBLE);

                student_name.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.white));
                student_email.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.white));
                student_phone_number.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.white));
            }
        });


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String std_name = student_name.getText().toString();
                String std_id = student_id.getText().toString();
                String std_email = student_email.getText().toString();
                String std_mobile_number = student_phone_number.getText().toString();

                if (base64Image != null) {
                    dbRef.child("profileImage").setValue(base64Image).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Image Saved in Realtime DB", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
                }


                String url = Url_Ip.ip+"/Apps/student_data_update.php?roll="+std_id+"&&name="+std_name+"&&email="+std_email+"&&mb_number="+std_mobile_number;
                RequestQueue queue = Volley.newRequestQueue(Student_profile.this);

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        student_name.setEnabled(false);
                        student_email.setEnabled(false);
                        student_phone_number.setEnabled(false);

                        buttons_layout.setVisibility(View.GONE);

                        student_name.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.dim_grey));
                        student_email.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.dim_grey));
                        student_phone_number.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.dim_grey));

                        Toast.makeText(Student_profile.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("response", ""+response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Student_profile.this, ""+error, Toast.LENGTH_SHORT).show();
                        Log.d("response", ""+error.toString());
                    }
                });

                queue.add(request);

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                student_name.setEnabled(false);
                student_email.setEnabled(false);
                student_phone_number.setEnabled(false);
                student_image.setEnabled(false);

                buttons_layout.setVisibility(View.GONE);

                student_name.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.dim_grey));
                student_email.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.dim_grey));
                student_phone_number.setBackgroundColor(ContextCompat.getColor(Student_profile.this, R.color.dim_grey));

            }
        });


    }

    private String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private Bitmap decodeBase64ToBitmap(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private Bitmap resizeBitmap(Bitmap original, int maxSize) {
        int width = original.getWidth();
        int height = original.getHeight();

        float ratio = (float) width / height;

        if (ratio > 1) {
            width = maxSize;
            height = (int) (width / ratio);
        } else {
            height = maxSize;
            width = (int) (height * ratio);
        }

        return Bitmap.createScaledBitmap(original, width, height, true);
    }

    private Bitmap rotateBitmapIfRequired(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


}