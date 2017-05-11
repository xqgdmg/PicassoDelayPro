package com.example.qhsj.picassodelaypro;

import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Chris on 2017/5/11.
 */
public class ImageHelp {

    private static CustomImageLoader loder;

    public static void displayImage(final String url, final ImageView imageView, int defaultImageId) {

        if (defaultImageId > 0) {
            imageView.setImageResource(defaultImageId);
        }
        if (loder == null) {
            loder = new CustomImageLoader();
        }

        createImageCacheDir(); // 创建空的文件夹
        loder.display(url, imageView); // 1111111111
    }

    /**
     * 创建缓存图片存放文件夹
     */
    public static void createImageCacheDir() {
        try {
            if (!isFileExist("")) {
                createSDDir("");
            }
            if (!isFileExist("image_cache/")) {
                createSDDir("image_cache/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 判断文件是否存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Chris/" + fileName);
        return file.exists();
    }

    /**
     * 创建文件夹
     *
     */
    public static void createSDDir(String dirName) throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Chris/" + dirName);
        dir.mkdirs(); // PWallet 居然没有写这个
    }
}
