package com.example.chapter07_client.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Utils {
    public  static  int dip2px(Context context, float dpValue) {
        // 获取当前手机的像素密度（1dp=？px）
       float scale =  context.getResources().getDisplayMetrics().density;
       return (int)(dpValue*scale + 0.5f);
    }

    public static  String getNowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss.SSS"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        SimpleDateFormat dateFormat = new SimpleDateFormat(("HH:mm:ss"));
        return  dateFormat.format(new Date());
    }

    // 隐藏键盘
   public  static  void hideOneInputMethod(Activity activity, View view) {
        // 从系统服务中获取输入法管理器
       InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
       // 关闭屏幕上的输入法软键盘
       imm.hideSoftInputFromWindow(view.getWindowToken(),0);
   }
   // 提示弹框
   public  static  void show (Context ctx,String desc) {
       Toast.makeText(ctx,desc,Toast.LENGTH_SHORT).show();
   }

   // 文件处理
    // 把字符串保存到指定路径的文本文件
    public  static  void saveText(String path,String txt) {
        BufferedWriter os = null;
        try {
            os = new BufferedWriter(new FileWriter(path));
            os.write(txt);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 从指定路径的文本文件读取出内容字符串
    public  static String openTxt(String path) {
        BufferedReader is = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            is = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line=is.readLine())!=null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    // 把图片数据保存到指定路径
    public static void saveImage(String path, Bitmap bitmap)  {
        FileOutputStream  fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            // 把位图数据压缩到文件输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream!=null) {
                try {
                    fileOutputStream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 获取文件夹目录下所有文件的名字
    public static List<String> getFileName(String path) {
        List<String> s= null;
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.i("wcy","空目录");
        } else {
            s=new ArrayList<>();
            for (int i=0;i< files.length;i++) {

                s.add(files[i].getName());
            }

        }
        return s;
    }

//    判断文件后缀是否是图片
    public static boolean checkIsImageFile(String fileName) {
        boolean isImageFile = false;
        // 获取扩展名
        String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase();
//         List[] images = new String[]{"jpg","png","gif","jpeg","bmp"};
//        if (images.contains(fileEnd)  ) {
//
//        }
        return  isImageFile;
    }

    // 从指定文件路径中读取位图数据
    public static Bitmap openImage(String path) {
        Bitmap bitmap = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            bitmap =  BitmapFactory.decodeStream(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fileInputStream !=null) {
                try {
                     fileInputStream.close();
            } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }


}
