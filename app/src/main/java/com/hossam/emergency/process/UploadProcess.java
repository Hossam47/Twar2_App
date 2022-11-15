package com.hossam.emergency.process;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hossam.emergency.R;
import com.hossam.emergency.models.ImageModel;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui_component.UIComponenet;
import com.hossam.emergency.utils.ToastStyle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

import static com.hossam.emergency.algorithem.UniqueIntgerID.generateUniqueId;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;
import static com.hossam.emergency.firebase.FirebaseContract.getMessengerReference;

/**
 * Created by hossam on 11/20/17.
 */

public class UploadProcess extends UIComponenet {

    Context context;
    ToastStyle toastStyle;
    Twar2App twar2App;

    public UploadProcess(Context context) {
        this.context = context;
        toastStyle = new ToastStyle(context);
        twar2App = (Twar2App) context.getApplicationContext();
    }


    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        if (realImage.getWidth() > maxImageSize && realImage.getHeight() > maxImageSize) {
            float ratio = Math.min(
                    maxImageSize / realImage.getWidth(),
                    maxImageSize / realImage.getHeight());
            int width = Math.round(ratio * realImage.getWidth());
            int height = Math.round(ratio * realImage.getHeight());

            Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width / 2,
                    height / 2, filter);
            return newBitmap;
        }
        return realImage;
    }

    public void uploadImage(final Bitmap image, final Activity activity) {

        if (image != null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReference().child("users")
                    .child(getCurrentUserID()).child("profile").child(String.valueOf(generateUniqueId()));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            image.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] bytes = baos.toByteArray();

            UploadTask uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            if (uri != null) {

                                getMainReference().child("image").setValue(uri.toString());

                            }
                        }
                    });



                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(activity, getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void uploadProfileImage(File file) {

        if (file != null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReference().child("users")
                    .child(getCurrentUserID()).child("profile").child(String.valueOf(generateUniqueId()));

            UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            if (uri != null) {

                                getMainReference().child("image").setValue(uri.toString());

                            }
                        }
                    });

//                    if (taskSnapshot.getDownloadUrl() != null) {
//
//                        getMainReference().child("image").setValue(taskSnapshot.getDownloadUrl().toString());
//
//                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    toastStyle.negativeToast(context.getResources().getString(R.string.try_again));

                    Log.d("Image_problem", "onFailure: "+e);
                }
            });
        }
    }

    public void uploadCaseModelImages(File file, final ImageModel model, final String caseId) {

        final String randomID = UUID.randomUUID().toString();

        model.setId(randomID);

        if (file != null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReference().child("cases").child(caseId)
                    .child(String.valueOf(generateUniqueId()));

            UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            if (uri != null) {

                                model.setUrl(uri.toString());

                                getCasesReference().child(caseId)
                                        .child("images").child(model.getId()).setValue(model);

                            }
                        }
                    });

//                    if (taskSnapshot.getDownloadUrl() != null) {
//
//                        model.setUrl(taskSnapshot.getDownloadUrl().toString());
//
//                        getCasesCountryReference().child(twar2App.getUserCountry()).child(caseId)
//                                .child("images").child(model.getId()).setValue(model);
//
//                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(context, context.getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    Log.d("Image_problem", "onFailure: "+e);
                }
            });
        }
    }


    public void uploadMessegeImages(File file, final ImageModel model, final String message_id, final String chat_id) {

        final String randomID = UUID.randomUUID().toString();

        model.setId(randomID);

        if (file != null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReference().child("messages").child(chat_id).child(message_id)
                    .child(String.valueOf(generateUniqueId()));

            UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            if (uri != null) {


                                model.setUrl(uri.toString());

                                getMessengerReference().child(chat_id).child(message_id).child("image").setValue(model.getUrl());


                            }
                        }
                    });

//
//                    if (taskSnapshot.getDownloadUrl() != null) {
//
//                        model.setUrl(taskSnapshot.getDownloadUrl().toString());
//
//                        getMessengerReference().child(chat_id).child(message_id).child("image").setValue(model.getUrl());
//
//                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    toastStyle.negativeToast(context.getResources().getString(R.string.try_again));
                    Log.d("Image_problem", "onFailure: "+e);
                }
            });
        }
    }


//    public Bitmap imageCompression(Uri uri){
//
//        Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
//        //options.height = xxx;//some compression configuration.
//        Tiny.getInstance().source(uri.getPath()).asBitmap().withOptions(options).compress(new BitmapCallback() {
//            @Override
//            public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
//
//
//            }
//        });
//    }

}
