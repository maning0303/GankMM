package com.maning.gankmm.utils;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@Deprecated
public class Permission5Utils {

    //--------------------6.0以下权限处理-------------------------

    /**
     * 相机是否可用
     * 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean isCameraPermission() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    /**
     * 录音是否可用
     * 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean isVoicePermission() {
        AudioRecord record = null;
        try {
            record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    AudioRecord.getMinBufferSize(22050,
                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState = record.getRecordingState();
            if (recordingState == AudioRecord.RECORDSTATE_STOPPED) {
                return false;
            }
            //第一次  为true时，先释放资源，在进行一次判定
            //************
            record.release();
            record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    AudioRecord.getMinBufferSize(22050,
                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState1 = record.getRecordingState();
            if (recordingState1 == AudioRecord.RECORDSTATE_STOPPED) {
            }
            //**************
            //如果两次都是true， 就返回true  原因未知
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (record != null) {
                record.release();
            }
        }

    }

    /**
     * 联系人列表是否可用
     * 返回true 表示可以使用  返回false表示不可以使用
     */
    public static boolean isContactsListPermission(Context context) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 是否有读写权限
     *
     * @return
     */
    public static boolean isWritePermission() {
        boolean isCanUser = true;
        File file = Environment.getExternalStorageDirectory();
        File newfile = new File(file, "1.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(newfile);
            fw.flush();
            fw.write("123");
            isCanUser = true;
        } catch (Exception e) {
            isCanUser = false;
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                    isCanUser = true;
                } catch (IOException e) {
                    isCanUser = false;
                }
            }
        }
        return isCanUser;
    }

    public static boolean isSmsPermission(Context context) {
        String SMS_URI_ALL = "content://sms/";
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            cursor = context.getContentResolver().query(uri, projection, null,
                    null, "date desc"); // 获取手机内部短信
            //这里需要注意，当没有权限时拿到的count可能是0，也许记录被删除了，这里需要注意下！
            if (cursor != null && cursor.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
