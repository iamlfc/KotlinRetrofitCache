package iam.lfc.myretrofitcache.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.flyco.animation.BounceEnter.BounceEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import iam.lfc.myretrofitcache.R;
import iam.lfc.myretrofitcache.share.Params;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static android.text.TextUtils.isEmpty;


public class LUtils {


    /**
     * Save image to the SD card
     *
     * @param photoBitmap
     * @param photoName
     * @param path        圆形头像工具类
     */
    public static String savePhoto(Bitmap photoBitmap, String path,
                                   String photoName, Context ctx) {
        String localPath = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) { // 转换完成
                        localPath = photoFile.getPath();
                        fileOutputStream.flush();
                    }
                }
                updatePhotoMedia(photoFile, ctx);
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }

    //更新图库
    private static void updatePhotoMedia(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.1";
        }
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap  传入Bitmap对象
     * @param tempUri
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap, Uri tempUri) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
        // 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
        // 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }


    //     图片转码
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, bos);// 将bitmap放入字节数组流中
                bos.flush();// 将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();

                byte[] bitmapByte = bos.toByteArray();
                result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * @param @param path
     * @return String 返回类型
     * @Description: 根据图片路径转字符串
     */
    public static String bitmapToBase64(String path) {
        String result = null;
        ByteArrayOutputStream baos = null;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(path);
            if (bitmap == null) {
                byte[] data = getBytes(new URL("file://" + path).openStream());
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                int options = 60;
                while (baos.toByteArray().length / 1024 > 200) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                    baos.reset();//重置baos即清空baos
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                    options -= 10;//每次都减少10
                    if (options <= 10) {
                        break;
                    }
                }
//                byte[] bytes = baos.toByteArray();
//                bitmap = compressImage(bitmap, 250);

                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null)
                try {
                    baos.close();
                } catch (Exception e) {
                }
            if (bitmap != null)
                bitmap.recycle();
        }
        return result;
    }

    public static String bitmapToBase64(String path, int quality) {
        String result = null;
        ByteArrayOutputStream baos = null;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(path);
            if (bitmap == null) {
                byte[] data = getBytes(new URL("file://" + path).openStream());
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null)
                try {
                    baos.close();
                } catch (Exception e) {
                }
            if (bitmap != null)
                bitmap.recycle();
        }
        return result;
    }


    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // 用数据装
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.flush();
        outstream.close();
        // 关闭流一定要记得。
        return outstream.toByteArray();
    }

    private static Toast mToast = null;
    private static Toast ImgToast = null;


    public static void showToask(Context context, String text) {

        if (TextUtils.isEmpty(text) || text.equals("暂无数据"))
            return;
        if (mToast == null) {
            mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            mToast.setText(text);
        } else {
            try {
                mToast.setText(text);
            } catch (Exception e) {
                mToast = null;
                mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
                mToast.setText(text);
            }

//            mToast.setDuration(duration);
        }
//如果在后台 则不显示 dialog
        if (!isBackground(context)) {
            mToast.show();
        }

//        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToask(Context context, String text) {

        if (TextUtils.isEmpty(text) || text.equals("暂无数据"))
            return;
        if (mToast == null) {
            mToast = Toast.makeText(context, null, Toast.LENGTH_LONG);
            mToast.setText(text);
        } else {
            try {
                mToast.setText(text);
            } catch (Exception e) {
                mToast = null;
                mToast = Toast.makeText(context, null, Toast.LENGTH_LONG);
                mToast.setText(text);
            }

//            mToast.setDuration(duration);
        }
//如果在后台 则不显示 dialog
        if (!isBackground(context)) {
            mToast.show();
        }

//        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }

    public static void showToask(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, null, duration);
            mToast.setText(text);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }

        mToast.show();
    }

    public static void showExitToask(Context context, String text) {
        if (text.equals("暂无数据"))
            return;
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(text);
        //如果在后台 则不显示 dialog
        if (!isBackground(context)) {
            toast.show();
        }

    }

    public static void hideToask() {
        if (mToast == null) {
            return;
        } else {
            try {
                mToast.cancel();
//                mToast = null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("loan", "Exception  " + e.toString());
            }

        }

    }


    /**
     * 隐藏电话号中间 四位数字
     *
     * @param str_tel
     * @return
     */
    public static String HideTel(String str_tel) {
        String str_result = "";
        if (TextUtils.isEmpty(str_tel)) {
            return "";
        }
        if (str_tel.length() != 11) {
            return str_tel;
        }
        String str_midtel = str_tel.substring(3, 8);
        str_result = str_tel.replace(str_midtel, "*****");
        return str_result;
    }

  /*  public static void showImgToask2(Context context, String GsonTest) {
        if (ImgToast == null) {
            View view = View.inflate(context, R.layout.custom_toast, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_toast);
            ImageView iv = (ImageView) view.findViewById(R.id.img_toast);
//            iv.setImageResource(R.mipmap.logo_waiting);
            tv.setText(GsonTest);
            ImgToast = new Toast(context);
//            ImgToast.setDuration(Toast.LENGTH_SHORT);
            ImgToast.setDuration(Toast.LENGTH_LONG);
            ImgToast.setView(view);
        } else {
            TextView tv = (TextView) ImgToast.getView().findViewById(R.id.tv_toast);
            tv.setText(GsonTest);
        }
//如果在后台 则不显示 dialog
        if (!isBackground(context)) {
            ImgToast.show();
        }*/


//        View view = View.inflate(context, R.layout.custom_toast, null);
//        TextView tv = (TextView) view.findViewById(R.id.tv_toast);
//        ImageView iv = (ImageView) view.findViewById(R.id.img_toast);
////        iv.setImageResource(R.mipmap.logo_waiting);
//        tv.setText(GsonTest);
//        Toast toast = new Toast(context);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(view);
//        toast.show();


//}


    /**
     * @param mobile
     * @return true 手机号合法  false  手机号不合法
     */
    public static boolean isMobileNumber(String mobile) {
        if (!TextUtils.isEmpty(mobile) && mobile.length() == 11)
            return true;
        else {
            return false;
        }
    }

    /**
     * @param ctx
     * @param mobile
     * @return true   是手机号  false 不是手机号
     */
    public static boolean isMobileNumber(Context ctx, String mobile) {
        if (!TextUtils.isEmpty(mobile) && mobile.length() == 11)
            return true;
        else {
            LUtils.showToask(ctx, "请输入合法的手机号");
            return false;
        }
    }

/*
    public static boolean isMobileNO_Realy(String mobiles) {

        // ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
        Pattern p = Pattern
                .compile("^((\\d{7,8})|(0\\d{2,3}-\\d{7,8})|(1[34578]\\d{9}))$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }*/

    /**
     * 是否是固话
     */
    public static boolean isTel(String tel) {
        Pattern p = Pattern.compile("^((\\d{7,8})|(0\\d{2,3}-\\d{7,8})|(1[34578]\\d{9}))$");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /**
     * 是否是邮箱
     */
    public static boolean isEmail(String strEmail) {
        // String strPattern =
        // "^([logo_waiting-z0-9A-Z]+[-|\\.]?)+[logo_waiting-z0-9A-Z]@([logo_waiting-z0-9A-Z]+(-[logo_waiting-z0-9A-Z]+)?\\.)+[logo_waiting-zA-Z]{2,}$";
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 是否是网址
     */
    public static boolean isWeb(String strWeb) {
        String strPattern = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strWeb);
        return m.matches();
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    public static boolean IDCardValidate(String IDStr) throws ParseException {
        if (!TextUtils.isEmpty(IDStr)) {
            IDStr = IDStr.toLowerCase();
        }
        @SuppressWarnings("unused")
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位";
            return false;
        }
        // ================ 数字 除最后一位都为数字 ================
        if (IDStr.length() == 18)
            Ai = IDStr.substring(0, 17);
        else if (IDStr.length() == 15)
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        if (!isNumeric(Ai)) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字";
            return false;
        }
        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (!isDataFormat(strYear + "-" + strMonth + "-" + strDay)) {
            errorInfo = "身份证生日无效";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            try {
                if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                        || (gc.getTime().getTime() - s.parse(
                        strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                    errorInfo = "身份证生日不在有效范围";
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return false;
        }
        // ================ 地区码时候有效 ================
        @SuppressWarnings("rawtypes")
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误";
            return false;
        }
        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            if (!Ai.equals(IDStr)) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        } else
            return true;
        return true;
    }


    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str 字符串
     * @return 布尔值
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 验证日期字符串
     *
     * @param str 日期
     */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches())
            flag = true;
        return flag;
    }


    public static boolean isPayPassword(String password) {
        String str_rule = "^[0-9]{6}$";
        return Pattern.matches(str_rule, password);
    }

    /**
     * 校验密码
     *
     * @param
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPW(String str_ps) {
        // ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
        Pattern p = Pattern.compile("^[a-zA-Z0-9~!@#$%^&*.]{6,20}$");
        Matcher m = p.matcher(str_ps);
        return m.matches();

    }

    /**
     * 计算经纬度之间的距离
     *
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     * @return 米
     */
    private static final double EARTH_RADIUS = 6378137.0;

    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        return outMetrics.widthPixels;
        //return display.getWidth();
    }
//    获取屏幕宽度

    public static int getPhoneWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }

    //    获取屏幕高度
    public static int getPhoneHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        return height;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context
     * @param sp
     * @return
     */
    public static float spToPx(Context context, float sp) {
        float px = getAbsValue(context, sp, TypedValue.COMPLEX_UNIT_SP);
        return px;
    }

    private static float getAbsValue(Context context, float value, int unit) {
        return TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 为字符串加蓝色
     *
     * @param string
     * @return
     */
    public static String getBlueText(String string) {
        return String.format("<font color=\"#0096ff\">%s</font>", string); // string 会替换 %s
    }


    /**
     * 第二种方法
     */
    public static String getRandomString2() {
        return getRandomString2(30);
    }

    /**
     * 第二种方法
     */
    public static String getRandomString2(int allsize) {
        int length = 0;
        if (allsize == 0) {
            length = new Random().nextInt(10) + 5;
        } else
            length = allsize;
        //产生随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //循环length次
        for (int i = 0; i < length; i++) {
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                //如果number产生的是数字0；
                case 0:
                    //产生A-Z的ASCII码
                    result = Math.round(Math.random() * 25 + 65);
                    //将ASCII码转换成字符
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    //产生a-z的ASCII码
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    //产生0-9的数字
                    sb.append(String.valueOf
                            (new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }


    /**
     *      * 获取随机汉字
     *      * @return
     *     
     */

    public static String getRandomWord() {
        String str = "";
        int heightPos;
        int lowPos;
        Random rd = new Random();
        heightPos = 176 + Math.abs(rd.nextInt(39));
        lowPos = 161 + Math.abs(rd.nextInt(93));
        byte[] bt = new byte[2];
        bt[0] = Integer.valueOf(heightPos).byteValue();
        bt[1] = Integer.valueOf(lowPos).byteValue();
        try {
            str = new String(bt, "GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
        }
        return str;
    }

/*
    public static boolean IsUserLogin(Context context) {
        UserBeanM userBeanM = LUtils.getUserData(context);
        String str_token = PreferencesUtils.getString(context, Params.Token);
        String str_id = PreferencesUtils.getString(context, Params.UserID);
        if (TextUtils.isEmpty(str_token) || TextUtils.isEmpty(str_id) || userBeanM == null) {
//未登录
            Login_A.EnterThis(context, 1);
//            showLoginDialog(context);
            return false;
        } else {

            return true;
        }

    }*/

    /**
     * @param context
     * @return true 登录  false  未登录
     */
    public static boolean JudgeUserLogin(Context context) {
        String str_uid = PreferencesUtils.getString(context, Params.UserID);
        String str_Tel = PreferencesUtils.getString(context, Params.UserTel);
        if (!TextUtils.isEmpty(str_uid) && !TextUtils.equals(str_uid, "0") && !TextUtils.equals(str_uid, "-1") &&
                !TextUtils.isEmpty(str_Tel) && !TextUtils.equals(str_Tel, "0")) {
            return true;
        } else
            return false;

    }

   /* public static void showLoginDialog(final Context context) {

        final MaterialDialog materialDialog = new MaterialDialog(context);
        materialDialog.content("您还未登录,是否立即去登录？")
                .title("温馨提示")
                .btnText("再看看", "去登录")
                .btnTextColor(
                        context.getResources().getColor(R.color.text6),
                        context.getResources().getColor(R.color.blue))
                .showAnim(new BounceEnter())
                .show();
        materialDialog.setOnBtnClickL(
                new OnBtnClickL() { //left btn click listener
                    @Override
                    public void onBtnClick() {
                        materialDialog.dismiss();
                    }
                },
                new OnBtnClickL() { //right btn click listener
                    @Override
                    public void onBtnClick() {
                        materialDialog.dismiss();
                        Login_A.EnterThis(context, 1);
                    }
                }
        );
    }*/

    /**
     * 修改小米手机系统的
     *
     * @param window
     * @param dark
     * @return
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 魅族手机修改该字体颜色
     *
     * @param window
     * @param dark
     * @return
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static boolean isImg(String str_imgurl) {
        if (TextUtils.isEmpty(str_imgurl)) {
            return false;
        }
        if (str_imgurl.length() > 15) {
            String strImgHead = "";
            try {
                strImgHead = str_imgurl.substring(0, 15);
            } catch (Exception e) {
                e.printStackTrace();
                strImgHead = "";
            }

            return strImgHead.toLowerCase().contains(Params.OtherHttp) || (strImgHead.toLowerCase().contains(Params.OtherHttpS));

        } else {
            return false;
        }
//        if (str_imgurl.toLowerCase().contains(OtherHttp) || (str_imgurl.toLowerCase().contains(OtherHttpS))) {
////            str_img = HttpIp.BaseImgIp + str_img;
//            return true;
//        } else {
//            //                包含了 http://的域名 直接保存  不添加本地域名
//            return false;
//        }

    }

    /**
     * * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Resources resource = context.getResources();
            int resourceId = resource.getIdentifier("status_bar_height", "dimen", "Android");
            if (resourceId != 0) {
                return resource.getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
        }
        return 0;
    }

//    public void saveBitmapFile(Bitmap bitmap) {
////        File file = new File(e);//将要保存图片的路径
//        File file = new File("mnt/sdcard/uututorstudent" + File.separator + "uututor_logo.jpg");
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            bos.flush();
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 实体类  z转换为 json 字符
     *
     * @return
     */
    public static String Beans2Json(Object obj, Class<?> cls) {
        String str_json = "";
//        if (obj == null) {
//            obj = cls;
//        }
        try {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            str_json = gson.toJson(obj, cls);
        } catch (Exception e) {
            str_json = "";
        }

        return str_json;
    }


    public static void reflex(final TabLayout tabLayout, final int padding) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dp2px(tabLayout.getContext(), padding);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * bitmap 转 二进制
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 照片转byte二进制
     *
     * @param imagepath 需要转byte的照片路径
     * @return 已经转成的byte
     * @throws Exception
     */
    public static byte[] getPathByte(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    /**
     * 重置用户信息
     */
    public static void resetuserinfo(Activity basecontext) {
//        PreferencesUtils.putString(basecontext, Params.UserTel, "");
 /*       PreferencesUtils.putString(basecontext, Params.Token, "");
        PreferencesUtils.putString(basecontext, Params.UserID, "");
        PreferencesUtils.putObject(basecontext, Params.UserInfo, null);*/


    }

    /**
     * 重置用户信息
     */
    public static void resetuserinfo(Context basecontext) {
//        PreferencesUtils.putString(basecontext, Params.UserTel, "");
    /*    PreferencesUtils.putString(basecontext, Params.UserID, "");
        PreferencesUtils.putString(basecontext, Params.UserCardNo, "");
        PreferencesUtils.putString(basecontext, Params.UserImg, "");
        PreferencesUtils.putString(basecontext, Params.UserNickName, "");
        PreferencesUtils.putString(basecontext, Params.UserRealName, "");
        PreferencesUtils.putInt(basecontext, Params.UserSex, 1);
        PreferencesUtils.putString(basecontext, Params.Token, "");
        PreferencesUtils.putString(basecontext, Params.UserPW, "");
        PreferencesUtils.putInt(basecontext, Params.ISCompany, 1);
        PreferencesUtils.putInt(basecontext, Params.ISBan, 1);

        PreferencesUtils.putInt(basecontext, Params.Stars, 1);
        PreferencesUtils.putInt(basecontext, Params.UserLevel, 1);
        PreferencesUtils.putInt(basecontext, Params.Auth_Status, 1);
        PreferencesUtils.putString(basecontext, Params.CauseStatus, "");
        PreferencesUtils.putString(basecontext, Params.PayPass, "");
        PreferencesUtils.putString(basecontext, Params.WorkState, "");
        PreferencesUtils.putString(basecontext, Params.UserAccount, "");
        PreferencesUtils.putString(basecontext, Params.CompanyName, "");
        PreferencesUtils.putString(basecontext, Params.CompanyNo, "");
        PreferencesUtils.putString(basecontext, Params.CompanyImg, "");
        PreferencesUtils.putString(basecontext, Params.ShareRegisterUrl, "");
        PreferencesUtils.putString(basecontext, Params.UserBirth, "");*/


    }

/*
    *//**
     * 显示大图
     *
     * @param baseContext
     * @param mlist_img
     * @param position
     *//*
    public static void ShowLargeImg(Context baseContext, List<String> mlist_img, int position) {
        Intent intent = new Intent(baseContext, ImagePagerActivity.class);
        intent.putExtra(
                ImagePagerActivity.EXTRA_IMAGE_URLS,
                mlist_img.toArray(new String[mlist_img.size()]));
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_SAVE, false);
        baseContext.startActivity(intent);
    }*/


    /**
     * 三级 城市 变两级
     *
     * @param str_Panme
     * @param str_Cname
     * @param str_Dname
     * @return
     */
    public static String ShowPCD_name(String str_Panme, String str_Cname, String str_Dname) {
        String str_result = "";
        try {
            if (str_Panme.contains("北京") || str_Panme.contains("天津") || str_Panme.contains("上海") || str_Panme.contains("重庆")) {
                str_result = str_Panme + str_Dname;
            } else {
                str_result = str_Panme + str_Cname;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!TextUtils.isEmpty(str_Panme)) {
                str_result = str_Panme;
            }
            if (!TextUtils.isEmpty(str_Cname)) {
                str_result += str_Cname;
            }
            if (!TextUtils.isEmpty(str_Dname)) {
                str_result += str_Dname;
            }

        }
        return str_result;
    }

    /**
     * 设置删除线
     *
     * @param tv
     */
    public static void setDelLine(TextView tv) {
//        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //中间横线
        tv.getPaint().setAntiAlias(true);// 抗锯齿
    }

    /**
     * 千位分隔符,并且小数点后保留2位
     *
     * @param num
     * @return String
     */
    public static String MoneySplit(double num) {
        DecimalFormat df = new DecimalFormat("#,##0.000");
        String ss = df.format(num);
        return ss;
    }


    /**
     * 判断手机中是否安装指定包名的软件
     */
    public static boolean isInstallApk(Context context, String name) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.packageName.equals(name)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return
     */
    public static List<List<?>> splitList(List<?> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<?>> result = new ArrayList<List<?>>();

        int size = list.size();
        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    /**
     * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     * <p>
     * 渠道标志为：
     * 1，andriod（a）
     * <p>
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            // TODO: 2018/1/3  需要请求 读取电话 权限  android.permission.READ_PHONE_STATE
            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return deviceId.append("id").append(getUUID(context)).toString();
            }
            String imei = tm.getDeviceId();
            if (!isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                LgU.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }

            //序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                LgU.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            //wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                LgU.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                LgU.e("getDeviceId : ", deviceId.toString());
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        LgU.e("getDeviceId : ", deviceId.toString());
        return deviceId.toString();
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        String str_model = getDeviceBrand() + "-" + Build.MODEL;
        LgU.d("手机型号：" + str_model);
        return str_model;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        String uuid = PreferencesUtils.getString(context, "uuid", "");
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            PreferencesUtils.putString(context, "uuid", uuid);
        }
        LgU.e("--lfc", "getUUID : " + uuid);
        return uuid;
    }

    public static void ShowLocalImg(Context ctx, ImageView img, String img_url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.logo_wait)
                .signature(new ObjectKey(System.currentTimeMillis())) // 重点在这行
                .error(R.mipmap.logo_wait);
        Glide.with(ctx).applyDefaultRequestOptions(options).load(img_url).into(img);

    }

    public static void ShowImgHead(Context ctx, ImageView img, String img_url) {
        ShowImgHead(ctx, img, img_url, 1);
    }

    public static void Call(final Activity baseContext, String str_title, final String str_tel) {
        final NormalDialog dialog = new NormalDialog(baseContext);
        dialog.style(NormalDialog.STYLE_TWO)
                .title(str_title).widthScale(0.8f)
                .titleTextColor(Color.parseColor("#000000"))
                .titleTextSize(15)
                .content(str_tel)
                .contentTextSize(13)
                .contentTextColor(Color.parseColor("#666666"))
                .btnTextSize(15)
                .btnTextColor(Color.parseColor("#007aff"), Color.parseColor("#007aff"))
                .btnText("取消", "确定").showAnim(new BounceEnter()).dismissAnim(new SlideBottomExit()).show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
//                                    T.showShort(mContext, "left");
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
//                                    T.showShort(mContext, "right");
//                                    LUtils.showToask(baseContext, "取消！");
                        LUtils.CallPhone(baseContext, str_tel);
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 1
     *
     * @param ctx
     * @param img
     * @param Type 0 广告图 1 用户头像（圆形）   2 轮播图  3  栏目  4 商家  5九宫图  6 项目成员  7个人中心的 大胡子头像
     */
    public static void ShowImgHead(Context ctx, ImageView img, String img_url, int Type) {
//        if (TextUtils.isEmpty(img_url))
//            img_url = "error";
        RequestOptions options = getGlideOptions(Type);
        switch (Type) {
            case 0:
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
            case 1:
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
            case 2:
//             轮播图
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
            case 3:
//
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
            case 4:
//
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
            case 5:
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
            case 6:
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
            case 7:
                Glide.with(ctx).asBitmap().load(img_url).thumbnail(0.3f).apply(options).into(img);
                break;
        }
    }


    @NonNull
    public static RequestOptions getGlideOptions() {
        return getGlideOptions(1);
    }

    /**
     * @param type 类型 1  等待图片
     * @return
     */
    @NonNull
    public static RequestOptions getGlideOptions(int type) {
        int drawableid = R.mipmap.logo_wait;
        switch (type) {
            case 2:
                drawableid = R.mipmap.logo_wait;
                break;
            case 6:
                drawableid = R.mipmap.logo_wait;
                break;
            case 7:
                drawableid = R.mipmap.logo_wait;
                break;
            case 1:
            case 3:
            case 4:
            case 5:
            default:
                drawableid = R.mipmap.logo_wait;
                break;

        }
        return new RequestOptions()
                .placeholder(drawableid)
                .error(drawableid);
    }

    public static void CallPhone(final Activity ctx, String tel) {
        ctx.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)));
    }


    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 获取指定目录内所有文件路径
     *
     * @param dirPath 需要查询的文件目录
     * @param
     */
    public static JSONArray getAllFiles(String dirPath) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();

        if (files == null) {//判断权限
            return null;
        }

        JSONArray fileList = new JSONArray();
        for (File _file : files) {//遍历目录
            if (_file.isFile()) {
                String _name = _file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName().substring(0, _name.length() - 4);//获取文件名
//                Log.d("LOGCAT","fileName:"+fileName);
//                Log.d("LOGCAT","filePath:"+filePath);
                try {
                    JSONObject _fInfo = new JSONObject();
                    _fInfo.put("name", fileName);
                    _fInfo.put("path", filePath);
                    fileList.put(_fInfo);
                } catch (Exception e) {
                }
            } else if (_file.isDirectory()) {//查询子目录
                getAllFiles(_file.getAbsolutePath());
            } else {
            }
        }
        return fileList;
    }

    public static <B> B modelA2B(Object modelA, Class<B> bClass) {
        try {
            Gson gson = new Gson();
            String gsonA = gson.toJson(modelA);
            B instanceB = gson.fromJson(gsonA, bClass);

            LgU.d("modelA2B A=" + modelA.getClass() + " B=" + bClass + " 转换后=" + instanceB);
            return instanceB;
        } catch (Exception e) {
            LgU.e("modelA2B Exception=" + modelA.getClass() + " " + bClass + " " + e.getMessage());
            return null;
        }
    }

    /**
     * 判断字符串是否乱码
     *
     * @param strName
     * @return boolean
     * @author yang.shen
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

 /*   //    初始化省市县数据
    public static void initSSX(final Activity baseContext) {
        PADDataM padDataM = (PADDataM) PreferencesUtils.getObject(baseContext, Params.CityAllData);
        if (padDataM == null || padDataM.getList().size() == 0) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    initJsonData(baseContext);
                }
            }.start();
        }

    }*/

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */

/*
    private static void initJsonData(final Activity baseContext) {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = baseContext.getAssets().open("citys.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            String str_citydata = URLDecoder.decode(sb.toString());
            JSONObject mJsonObj = new JSONObject(str_citydata);
            PADDataM dataMClass = null;

            Gson gson = new Gson();
            dataMClass = gson.fromJson(mJsonObj.toString(), PADDataM.class);

            for (int i = 0; i < dataMClass.getList().size(); i++) {
                for (int t = 0; t < dataMClass.getList().get(i).getCity().size(); t++) {
                    String str_cityname = dataMClass.getList().get(i).getCity().get(t).getShort_name();
                    if (LUtils.isMessyCode(str_cityname)) {
                        dataMClass.getList().get(i).getCity().remove(t);
                    }
                    for (int k = 0; k < dataMClass.getList().get(i).getCity().get(t).getArea().size(); k++) {
                        String str_areaname = dataMClass.getList().get(i).getCity().get(t).getArea().get(k).getShort_name();
                        if (LUtils.isMessyCode(str_areaname)) {
                            dataMClass.getList().get(i).getCity().get(t).getArea().remove(k);
                        }
                    }
                }
            }
            LgU.d("josn数据：\n" + dataMClass.toString());
            PreferencesUtils.putObject(baseContext, Params.CityAllData, dataMClass);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 截取view
     **/
    public static Bitmap createViewBitmap(View v) {
        int height = LUtils.getViewHeight(v);
//        int width = LUtils.getViewWidth(v);
        int width = v.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
/*
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
*/
        LgU.d("getWidth: " + v.getWidth() + " getHeight: " + v.getHeight());
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }


    /**
     * 获取网络图片并转为Base64编码
     *
     * @param url 网络图片路径
     * @return base64编码
     * @throws Exception
     */
/*    public static String GetUrlImageToBase64(String url) {
        if (url == null || "".equals(url.trim()))
            return null;
        try {
            URL u = new URL(url);
            // 打开图片路径
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            // 设置请求方式为GET
            conn.setRequestMethod("GET");
            // 设置超时响应时间为5秒
            conn.setConnectTimeout(5000);
            // 通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            BASE64Encoder encode = new BASE64Encoder();
            String s = encode.encode(data);
            return s;
        } catch (Exception e) {
            return "";
        }

    }*/
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    //将list转换为带有 ， 的字符串
    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString().trim();
    }

 /*   public static boolean ISNewMsg(Context ctx, String str_msgcounts) {

        if (TextUtils.isEmpty(str_msgcounts)) {
            str_msgcounts = "0";
        }
        int S_MsgCounts = Integer.parseInt(str_msgcounts);
        if (S_MsgCounts != 0 && GetSaveMsgCounts(ctx) < S_MsgCounts) {
//            msgMap.put(strToken, S_MsgCounts);
//            PreferencesUtils.putHashMapData(ctx, Params.MsgMap, msgMap);
            return true;
        }
        return false;
    }*/

    /**
     * 获取本地消息数量
     *
     * @param ctx
     * @return
     */
/*    public static int GetSaveMsgCounts(Context ctx) {
        int L_msgcount = 0;
        Map<String, Integer> msgMap = PreferencesUtils.getHashMapData(ctx, Params.MsgMap);
        if (msgMap == null) {
            msgMap = new HashMap<String, Integer>();
        }
        String strToken = PreferencesUtils.getString(ctx, Params.Token);
        boolean issave = msgMap.containsKey(strToken);
        if (issave) {
            L_msgcount = msgMap.get(strToken);
        }
        return L_msgcount;
    }*/

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        if (true)
            return strURL;
//        支付宝返回的就是参数部分 下边的没啥卵用
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 判断当前界面是否是桌面
     */
    private boolean isAtHome(Context ctx) {
        ActivityManager mActivityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = mActivityManager.getRunningTasks(1);
        return getHomeApplicationList(ctx).contains(runningTaskInfos.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     * 返回包含所有包名的字符串列表数组
     *
     * @param ctx
     * @return
     */
    private List<String> getHomeApplicationList(Context ctx) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = ctx.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resolveInfos) {
            names.add(resolveInfo.activityInfo.packageName);
        }
        return names;
    }

    //着色器
    public static Drawable tintDrawable(Drawable drawable, int colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static void HtmlText(Context ctx, TextView tv, String str_1, String str_2, int color1, int color2, int txtSize1, int txtSize2) {
        HtmlText(ctx, tv, str_1, str_2, "", color1, color2, 0, txtSize1, txtSize2, 0);
    }

    public static void HtmlText(Context ctx, TextView tv, String str_1, String str_2, String str_3, int color1, int color2, int color3, int txtSize1, int txtSize2, int txtSize3) {
        String name1 = str_1;
        String name2 = str_2;
        String name3 = str_3;
        if (color1 == 0) {
            color1 = R.color.base_text;
        }
        if (color2 == 0) {
            color2 = R.color.base_text;
        }
        if (color3 == 0) {
            color3 = R.color.base_text;
        }
        if (txtSize1 == 0) {
            txtSize1 = 18;
        }
        if (txtSize2 == 0) {
            txtSize2 = 18;
        }
        if (txtSize3 == 0) {
            txtSize3 = 18;
        }
        String str = name1 + name2 + name3;
        final SpannableStringBuilder sp = new SpannableStringBuilder(str);
//        头
        sp.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(color1)), 0, name1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(txtSize1, true), 0, name1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//身子
//        sp.setSpan(new ForegroundColorSpan(0xFFFF0000), (name1 + name2).length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(color2)), name1.length(), (name1 + name2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(txtSize2, true), name1.length(), (name1 + name2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
//        尾巴
        sp.setSpan(new ForegroundColorSpan(ctx.getResources().getColor(color3)), (name1 + name2).length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(txtSize3, true), (name1 + name2).length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小

//        这个 SpannableStringBuilder 如果转为 tostring 就没有作用了！
        tv.setText(sp);
    }

    /**
     * 判断是否是全面屏
     */
    private volatile static boolean mHasCheckAllScreen;
    private volatile static boolean mIsAllScreenDevice;

    public static boolean isAllScreenDevice(Context context) {
        if (mHasCheckAllScreen) {
            return mIsAllScreenDevice;
        }
        mHasCheckAllScreen = true;
        mIsAllScreenDevice = false;
        // 低于 API 21的，都不会是全面屏。。。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            if (height / width >= 1.97f) {
                mIsAllScreenDevice = true;
            }
        }
        return mIsAllScreenDevice;
    }

    public static int getViewHeight(View view) {
        int height_v = 0;
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        height_v = view.getMeasuredHeight(); // 获取宽度view.getMeasuredHeight(); // 获取高度
        return height_v;
    }

    public static int getViewWidth(View view) {
        int width_v = 0;
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        width_v = view.getMeasuredWidth(); // 获取宽度view.getMeasuredHeight(); // 获取高度
        return width_v;
    }

    public static void SetEditScorll(EditText etView) {
        etView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //这句话说的意思告诉父View我自己的事件我自己处理
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    public static void CopyTxt(Activity baseContext, String str_info) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) baseContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(str_info);
        LUtils.showToask(baseContext, "复制成功！");
    }

    //设置不可编辑
    public static void setEditable(EditText etContent, boolean isEdit) {
        if (isEdit) {
            etContent.setFocusable(true);
            etContent.setFocusableInTouchMode(true);
        } else {
            etContent.setFocusable(false);
            etContent.setFocusableInTouchMode(false);
            // 如果之前没设置过点击事件，该处可省略
            etContent.setOnClickListener(null);
        }

    }

    /**
     * 将数据保留两位小数
     */
    public static double getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String yearString = dFormat.format(num);
        Double temp = Double.valueOf(yearString);
        return temp;
    }

    /**
     * 打印 请求参数
     *
     * @param mRequest_GetData
     */
 /*   public static void getRequestData(Request<String> mRequest_GetData) {
        if (!Const.ISDebug)
            return;
        Iterator<String> it = mRequest_GetData.getParamKeyValues().keySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            String str_Key = it.next();
            String strValue = (String) mRequest_GetData.getParamKeyValues().getFirstValue(str_Key);
            if (!TextUtils.isEmpty(strValue) && strValue.length() > 1000) {
                strValue = "base64 ? 长度为：" + strValue.length();
            }
            sb.append(str_Key + ":" + strValue + "  ||  ");
        }
        LgU.more(sb.toString());
    }*/

    /**
     * @param type        1 隐藏 中间  2  隐藏 姓
     * @param strNickName
     * @return
     */
    public static String HideName(int type, String strNickName) {
        if (!TextUtils.isEmpty(strNickName)) {
            if (type == 1) {
                if (strNickName.length() <= 3) {
                    strNickName = strNickName.replace("*", strNickName.substring(strNickName.length() - 1, strNickName.length()));
                } else {
                    strNickName = strNickName.substring(0, 2) + "*";
                }
            } else if (type == 2) {
                if (strNickName.length() <= 3) {
                    strNickName = strNickName.replace("*", strNickName.substring(0, 1));
                } else {
                    strNickName = strNickName.replace("*", strNickName.substring(0, 2));
                }
            }

        } else {
            strNickName = "";
        }
        return strNickName;
    }

    /**
     * 获取视频文件截图
     *
     * @return Bitmap 返回获取的Bitmap
     */

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * return  true 安装  false  未安装
     *
     * @param context
     * @return
     */
    public static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
//        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
//        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        activity.getWindow().setAttributes(attrs);
        //全屏
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
//        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
//        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //判断GPS是否开启
    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isMsgEmpty(Context ctx, String strValue, String strMsg) {
        if (TextUtils.isEmpty(strValue)) {
            LUtils.showToask(ctx, strMsg);
            return true;
        }
        return false;
    }

    /**
     * 获取用户信息
     *
     * @param ctx
     * @return
     */
   /* public static UserBeanM getUserData(Context ctx) {
        UserBeanM userBeanM = (UserBeanM) PreferencesUtils.getObject(ctx, Params.UserInfo);
//        if (userBeanM == null)
//            userBeanM = new UserBeanM();
        return userBeanM;
    }*/

    /**
     * 获取 系统设置
     *
     * @param ctx
     * @return
     */
   /* public static SystemSetM getSystemData(Context ctx) {
        SystemSetM systemSetM = (SystemSetM) PreferencesUtils.getObject(ctx, Params.SystemConfig);
        return systemSetM;
    }*/

    /**
     * 开始计时
     */
  /*  public static void startCountDown(final TextView tvGetCode, final Context baseContext) {
        startCountDown(tvGetCode, baseContext, 1);
    }*/

    /**
     * @param tvGetCode
     * @param baseContext
     * @param type        1 带蓝色框  2 直接蓝色字体
     */
   /* public static void startCountDown(final TextView tvGetCode, final Context baseContext, final int type) {
        final long codeTimes = Const.YZM_TIME;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(codeTimes - 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return codeTimes - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        tvGetCode.setEnabled(false);
                        tvGetCode.setClickable(false);
                        if (type == 1)
                            tvGetCode.setBackground(baseContext.getResources().getDrawable(R.drawable.bg_round_gray_5));
                        else if (type == 2)
                            tvGetCode.setTextColor(0xFF666666);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        tvGetCode.setText("剩余" + value + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        tvGetCode.setEnabled(true);
                        tvGetCode.setClickable(true);
                        tvGetCode.setText("获取验证码");
                        if (type == 1)
                            tvGetCode.setBackground(baseContext.getResources().getDrawable(R.drawable.bg_round_blue_5));
                        else if (type == 2)
                            tvGetCode.setTextColor(baseContext.getResources().getColor(R.color.main));
                    }
                });
    }*/

    /**
     * 去除 最后一个分割 符
     *
     * @param strValue
     * @return
     */
    public static String StringTrim(String strValue) {
        String strResult = "";
        if (TextUtils.isEmpty(strValue))
            strResult = "";
        else {
            if (strValue.length() <= 1) {
                strResult = strValue;
            } else {
                strResult = strValue.substring(0, strValue.length() - 1);
            }
        }
        return strResult;
    }

    /**
     * 格式化 m 显示带单位
     *
     * @param strDistance
     * @return
     */
    public static String FormatDistance(String strDistance) {
        String strResult = "";
        if (TextUtils.isEmpty(strDistance)) {
            strDistance = "0";
        }
        double d_distance = 0;
        try {
            d_distance = Double.parseDouble(strDistance);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (d_distance <= 500) {
            strResult = "<500m";
        } else if (d_distance > 500 && d_distance < 1000) {
            strResult = d_distance + "m";
        } else {
            strResult = d_distance / 1000 + "km";
        }

        return strResult;
    }

    public static void SnapHelper(RecyclerView rlvBase) {
        new LinearSnapHelper().attachToRecyclerView(rlvBase);
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isGPSOPen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 转到设置GPS界面
     *
     * @param context
     */
    public static final void gotoSetGPS(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // The Android SDK doc says that the location settings activity
            // may not be found. In that case show the general settings.
            // General settings activity
            intent.setAction(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }
}