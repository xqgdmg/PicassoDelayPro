package com.example.qhsj.picassodelaypro;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Chris on 2017/5/11.
 */
public class CustomImageLoader {

    private static ArrayList<ImageView> ivList = new ArrayList<ImageView>();
    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //handler用于显示下载好的图片
    static Handler han = new ShowBitmapHandler();

    public CustomImageLoader() {
    }

    //单例对象使用的方法
    public void display(final String url, final ImageView ivUI) {

         // ivUI 绑定 url
        ivUI.setTag(new ImageHolder(url));

        //首先查看是否是下载好的图片，下载好的直接显示就可以了
         // 多张相同 url 的图片只会缓存一张 file
        File imageFile = new File(getFilePath(url));
        if (imageFile.exists()) {

            Picasso.with(MyApplication.getContext()).load(imageFile).into(ivUI);

            return;
        }

        //加入待显示队列,查看是否这个 url 正在下载，如果正在下载，就不需要再运行下面的下载代码
         // ivList 中添加 ImageView，iv 的 tag 是 url
        int size = ivList.size();
        for (int i = 0; i < size + 10; i++) { // 加十张是啥意思
            if (i < ivList.size()) {
                ImageView ivInList = ivList.get(i);
                if (ivInList == ivUI) {
                    if (ivInList.getTag().equals(ivUI.getTag())) {//这个任务已经存在了，不需要重复处理
                        return;
                    } else {//这个图片组件换了要显示的图片地址了
                        ivInList.setTag(new ImageHolder(url));
                    }
                }
                if (ivInList != null && ((ImageHolder) ivInList.getTag()).url.equals(url)) {
                    ivList.add(ivUI);
                    return;
                }
            } else {
                ivList.add(ivUI); // 加十张是啥意思
                break;
            }
        }

        /*
         * 下载图片
         */
        mExecutorService.submit(new Runnable() {

            @Override
            public void run() {
                if (downloadImage(url)) {
                    Message msg = new Message();
                    msg.obj = url;
                    han.sendMessage(msg);
                }
            }
        });
    }

    private static boolean downloadImage(String imageUrl) {

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

             // 必须先创建文件夹，不然会报错,在这个类的构造方法中初始化了
//            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/Chris/" + "image_cache/" );
//            if (!dir.exists()){
//                dir.mkdirs();
//            }

            //创建下载中文件并写入
            File f = new File(getFilePath(imageUrl) + ".dl");
            if (f.exists()) {
                f.delete();
            }
            InputStream inputStream = null;
            FileOutputStream outputStream = null;

            boolean success = false;

            try {
                inputStream = conn.getInputStream();
                outputStream = new FileOutputStream(f); // /storage/emulated/0/Chris/image_cache/6C1A745F124D6A9848049AF2D5903487.png.cache.dl: open failed: ENOENT (No such file or directory)
                byte[] buffer = new byte[1024*2];
                int lenght = 0 ;
                int sum = 0;
                while (-1 !=(lenght= inputStream.read(buffer))) {
                    sum+=lenght;
                    outputStream.write(buffer,0,lenght);
                }
                //下载完成后重命名去掉下载中状态的后缀名
                if (sum>0){
                    f.renameTo(new File(getFilePath(imageUrl)));
                    success = true;
                }

            } catch (Exception e) {
                Log.e("haha",e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

            conn.disconnect();
            return success;
        } catch (Exception e) {
             // 出现异常，把上面的 ImageView 中的集合清空
            int size = ivList.size() + 10;
            for (int i = size; i > -1; i--) {
                if (i < ivList.size() && ivList.get(i) != null) {
                    ImageView iv = ivList.get(i);
                    if (iv != null && ((ImageHolder) iv.getTag()).url.equals(imageUrl)) {
                        ivList.remove(i);
                    }
                }
            }
        }
        return false;
    }

    public static String getFilePath(String url) {
        return  Environment.getExternalStorageDirectory().getPath() + "/Chris/" + "image_cache/" + MD5.MD5(url) + ".png.cache";
    }


    /*
     * 下载完成后，在 UI 线程中显示图片
     */
    private static class ShowBitmapHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String url = (String) msg.obj;
            int size = ivList.size();

            // 遍历下载好的所有图片，显示图片
            for (int i = 0; i < size; i++) {
                if (i < ivList.size()) {
                    ImageView iv = ivList.get(i);
                    if (iv != null) {
                        ImageHolder holder = (ImageHolder) iv.getTag();
                        if (url.equals(holder.url)) {  // 加载本地de图片

                            Picasso.with(MyApplication.getContext()).load(new File(getFilePath(url))).into(iv);

                            try {
                                ivList.remove(iv);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            ivList.remove(iv);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    class ImageHolder {
        public String url;

        public ImageHolder(String url) {
            this.url = url;
        }
    }


}
