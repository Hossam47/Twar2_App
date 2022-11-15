package com.hossam.emergency.process;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.hossam.emergency.models.ImageModel;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.utils.ToastStyle;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class CompressionImages {
    Context context;
    UploadProcess uploadProcess;
    ToastStyle toastStyle;

    public CompressionImages(Context context) {
        this.context = context;
        uploadProcess = new UploadProcess(context);
        toastStyle = new ToastStyle(context);

    }

    public void uploadingMultiImages(final ArrayList<Uri> path, final String case_id) {

        for (int i = 0; i < path.size(); i++) {

            File file = new File(getRealPathFromURI(path.get(i)));

            final ImageModel imageModel = new ImageModel("", "", i, GetTime.getUTCTimetamp(), false);

            Luban.with(context)
                    .load(file)
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            toastStyle.positiveToast("Start uploading images");
                        }

                        @Override
                        public void onSuccess(File file) {
                            uploadProcess.uploadCaseModelImages(file, imageModel, case_id);

                        }

                        @Override
                        public void onError(Throwable e) {
                            toastStyle.negativeToast("Faild in uploading "+e);
                            Log.d("onErrorinUpload", "onErrorinUpload : "+e);
                        }
                    }).launch();


        }
    }


    public void uploadingSingleImages(final ArrayList<Uri> path, final String message_id, final String chat_id) {

        for (int i = 0; i < path.size(); i++) {

            File file = new File(getRealPathFromURI(path.get(i)));

            final ImageModel imageModel = new ImageModel("", "", i, GetTime.getUTCTimetamp(), false);

            Luban.with(context)
                    .load(file)
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
//                            toastStyle.positiveToast("Start uploading images");
                        }

                        @Override
                        public void onSuccess(File file) {
                            uploadProcess.uploadMessegeImages(file, imageModel, message_id, chat_id);

                        }

                        @Override
                        public void onError(Throwable e) {
//                            toastStyle.negativeToast("Faild in uploading");
                        }
                    }).launch();


        }
    }


    public void uploadingSingleImages(final ArrayList<Uri> path) {

        for (int i = 0; i < path.size(); i++) {

            File file = new File(getRealPathFromURI(path.get(i)));

            Luban.with(context)
                    .load(file)
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
//                            toastStyle.positiveToast("Start uploading images");
                        }

                        @Override
                        public void onSuccess(File file) {
                            uploadProcess.uploadProfileImage(file);

                        }

                        @Override
                        public void onError(Throwable e) {
//                            toastStyle.negativeToast("Faild in uploading");
                        }
                    }).launch();


        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


}
