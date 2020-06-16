package com.example.prince.splitwise;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    List<AuthUI.IdpConfig> providers;
    private static final int MY_REQUEST_CODE=100;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseReference= FirebaseDatabase.getInstance().getReference("USER");
        providers= Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE
        );

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==MY_REQUEST_CODE ){

            IdpResponse response=IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK )
            {
                DatabaseReference d=databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                d.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MyUser myUser=dataSnapshot.getValue(MyUser.class);
                        if (myUser == null)
                            sendDataToFirebase(FirebaseAuth.getInstance().getCurrentUser());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
            }
            else {
                Toast.makeText(this,response.getError().getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendDataToFirebase(final FirebaseUser user) {
        final MyUser u=new MyUser();
        u.setName1(user.getDisplayName());
        u.setEmail1(user.getEmail());
        u.setDob1("");
        u.setPhonenumber1(user.getPhoneNumber());
        databaseReference.child(user.getUid()).setValue(u);
        Toast.makeText(getApplicationContext(),"Hurray,Registered Succssfully",Toast.LENGTH_SHORT).show();
    }
    private String imgExt(Uri img) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(img));
    }
}
