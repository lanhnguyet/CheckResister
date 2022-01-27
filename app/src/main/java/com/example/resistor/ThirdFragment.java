package com.example.resistor;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.Instant;

public class ThirdFragment extends Fragment {
    ImageView avt;
    TextView tv1,phone,name,logout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String id;
    private static final int RESULT_OK = -1;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    private static final int PICK_IMAGE=1;
    Uri imageUri;
    private Instant Picasso;

    public ThirdFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_third,null);
        tv1=view.findViewById(R.id.textview1);
        name=view.findViewById(R.id.tvname);
        phone=view.findViewById(R.id.tvphone);
        logout=view.findViewById(R.id.logout);

//        firebaseAuth=FirebaseAuth.getInstance();
//        firebaseUser=firebaseAuth.getCurrentUser();
//
//        name.setText(firebaseUser.getEmail());
//        phone.setText(firebaseUser.getPhoneNumber());
//        storageReference = FirebaseStorage.getInstance().getReference();
//
//        StorageReference ProfileRef=storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
            }
        });

//        avt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gallegy=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(gallegy,PICK_IMAGE);
//            }
//        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.thirdFragment,new SupportFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
