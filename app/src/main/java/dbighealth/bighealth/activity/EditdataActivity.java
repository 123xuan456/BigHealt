package dbighealth.bighealth.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.imageUtils.BaseActivity;
import dbighealth.bighealth.imageUtils.FileUtils;
import okhttp3.Call;
import utils.BitmapUtils;
import utils.ConfigUsers;
import utils.HttpPostUploadUtil;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;
/**
 * 个人信息
 * */
public class EditdataActivity extends Activity {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.textView54)
    TextView textView54;
    @Bind(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @Bind(R.id.textView55)
    TextView textView55;
    @Bind(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @Bind(R.id.textView56)
    TextView textView56;
    @Bind(R.id.imageView13)
    ImageView imageView13;
    @Bind(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    /* @Bind(R.id.image)
     RoundImageView image;*/
    @Bind(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @Bind(R.id.textView57)
    TextView textView57;
    @Bind(R.id.textView59)
    TextView textView59;
    @Bind(R.id.sex)
    TextView sex1;
    @Bind(R.id.relativeLayout5)
    RelativeLayout relativeLayout5;
    @Bind(R.id.year)
    EditText year1;
    @Bind(R.id.image)
    SimpleDraweeView image;
    private String name;
    private LinearLayout ll_popup;
    public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath() + "/BigHealth/ImageCache/";// sd路径
    String cutPicName = "head.jpg";
    Bitmap headBitmap;
    private String picPath;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            String name1 = b.getString("name");
            System.out.println("name1" + name1);
            textView59.setText(name1);
        }
    };
    private String sex;
    private String sexa;
    private PopupWindow pop;
    private RelativeLayout parent;
    private String photoPic;
    private Uri imgUrl;
    private String year;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        ButterKnife.bind(this);
        //吧次activity放到集合里，等到修改密码成功之后统一取消
        BaseActivity.activityList.add(this);
//        name = BaseApplication.username;
        name = SharedPreferencesUtils.getString(getApplicationContext(),ConfigUsers.USERNAME,"");
        sexa = SharedPreferencesUtils.getString(getApplicationContext(),ConfigUsers.USERSEX,"");
      //  sexa = BaseApplication.sex;//打开拿到性别
      //  photoPic = BaseApplication.photoPic;
        photoPic = SharedPreferencesUtils.getString(getApplicationContext(),ConfigUsers.USERPIC,"");
        imgUrl = BaseApplication.imgUrl;
        sex1.setText(sexa);
        year1.setText(SharedPreferencesUtils.getString(getApplicationContext(),ConfigUsers.USERYEAR,""));
        textView59.setText(name);
        tvTab.setText("个人信息");
        rightAdd.setVisibility(View.GONE);
        if (BaseApplication.bitmap!=null){
            image.setImageBitmap(BaseApplication.bitmap);

        }
        //  image.setType(RoundImageView.TYPE_ROUND);//圆角
        if(imgUrl!=null){
            image.setImageURI(imgUrl);
        } else if (photoPic!=null){
            Uri uri = Uri.parse(photoPic);
            image.setImageURI(uri);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 2);
                }
                initPopu();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        EditdataActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(relativeLayout5, Gravity.CENTER, 10, 10);
            }
        });
        year=year1.getText().toString();
        broadcast();//接收修改性别的广播

    }

    private void broadcast() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(EditdataActivity.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_SEX");//修改性别
        BroadcastReceiver mItemViewListClickReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                sex = intent.getStringExtra("sex");
                System.out.println("接收到了a=" + sex);
                sex1.setText(sex);
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver1, intentFilter);

    }
    @OnClick({R.id.relativeLayout1, R.id.relativeLayout2, R.id.relativeLayout3, R.id.relativeLayout5,
            R.id.relativeLayout4,R.id.arrow_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.relativeLayout1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 2);
                }
                initPopu();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        EditdataActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(relativeLayout5, Gravity.CENTER, 10, 10);
                break;
            case R.id.relativeLayout2://修改昵称
                //初始化一个自定义的Dialog
                Dialog dialog = new MyDialog(EditdataActivity.this,
                        R.style.MyDialog);
                dialog.show();
                break;
            case R.id.relativeLayout3://修改性别
                Intent i = new Intent(this, SexActivity.class);
                startActivity(i);
                break;
            case R.id.relativeLayout5:
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
    //关闭activity时把年龄修改
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONObject obj = new JSONObject();
        year=year1.getText().toString();
       // phone = BaseApplication.regphone;
        phone = SharedPreferencesUtils.getString(getApplicationContext(),ConfigUsers.USERPHONE,"");
        System.out.println("year=" + year);
        System.out.println("phone=" + phone);
        try {
            obj.put("regphone", phone);
            obj.put("age", year);
            String url = UrlUtils.CHANGYEAR;
            OkHttpUtils.postString().url(url).content(obj.toString()).
                    build().
                    execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("修改年龄失败" + e);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            System.out.println("修改年龄成功" + response);
                            //修改成功之后发送一个广播
                            Intent intent = new Intent("android.intent.action.CART_YEAR");
                            intent.putExtra("year", year);
                            System.out.println("过去！！year" + year);
                            SharedPreferencesUtils.saveString(getApplication(), ConfigUsers.USERYEAR, year);//把修改完的年龄存储到了sp中
                            //BaseApplication.age=year;
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 修改昵称的时候弹出dialog
    public class MyDialog extends Dialog {

        Context context;
        private EditText editText2;
        private String et;

        public MyDialog(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            this.context = context;
        }

        public MyDialog(Context context, int theme) {
            super(context, theme);
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.dialog);
            phone = SharedPreferencesUtils.getString(getApplicationContext(),ConfigUsers.USERPHONE,"");
            //phone = BaseApplication.regphone;
            editText2 = (EditText) findViewById(R.id.editText2);
            editText2.setText(name);
            CharSequence charSequence = editText2.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }

            Button b1 = (Button) findViewById(R.id.button4);
            Button b = (Button) findViewById(R.id.button5);//取消
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject obj = new JSONObject();
                    et = editText2.getText().toString();
                    System.out.println("et1=" + et);
                    try {
                        obj.put("regphone", phone);
                        obj.put("reguser", et);
                        String url = UrlUtils.CHANGUSER;
                        OkHttpUtils.postString().url(url).content(obj.toString()).
                                build().
                                execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        System.out.println("修改失败" + e);
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        System.out.println("修改成功" + response);
                                        Message message = new Message();
                                        Bundle bundle = new Bundle();
                                        System.out.println("et=" + et);
                                        bundle.putString("name", et);
                                        message.setData(bundle);
                                        handler.sendMessage(message);
                                        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_LONG).show();
                                        //修改成功之后发送一个广播
                                        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                                        intent.putExtra("username", et);
                                        System.out.println("过去！！username" + et);
//                                        BaseApplication.username=et;
                                        SharedPreferencesUtils.saveString(context, ConfigUsers.USERNAME, et);//把用户名存储到了sp中
                                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                                        dismiss();
                                    }
                                });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }


    }


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initPopu() {
        pop = new PopupWindow(EditdataActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        parent = (RelativeLayout) view.findViewById(R.id.parent);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.ECLAIR)
            public void onClick(View v) {

                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File file = FileUtils.createFile(ImageCachePath + cutPicName);
                    // FileUtils.createSDDir()
                    cropPhoto(Uri.fromFile(file));// 裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    headBitmap = extras.getParcelable("data");
                    if (headBitmap != null) {
                   //     setPicToView(headBitmap);// 保存在SD卡中
                        image.setImageBitmap(BitmapUtils.toRoundBitmap(headBitmap));// 用ImageView显示出来
                      //  Log.i("mhysa--->", "数据uri" + Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), headBitmap, null, null)));
                        final String spath = MediaStore.Images.Media.insertImage(getContentResolver(), headBitmap, null, null);
                        final Uri imgUrl = Uri.parse(spath);
                        final String path1 = getPath(null, imgUrl);

                    new Thread() {
                            @Override
                            public void run() {
                                Map<String, String> textMap = new HashMap<String, String>();
                                textMap.put("name", "testname");
                                Map<String, String> fileMap = new HashMap<String, String>();
                                fileMap.put("file", path1);
                                String getPicUrl = HttpPostUploadUtil.formUpload(UrlUtils.UPLOADPIC, textMap, fileMap);
//                                BaseApplication.username = name;
                                SharedPreferencesUtils.saveString(getApplicationContext(),ConfigUsers.USERNAME,name);
                                //  BaseApplication.userid = uid;
                                BaseApplication.imgUrl = imgUrl;
                               // SharedPreferencesUtils.saveString(getApplicationContext(),ConfigUsers.USERPIC,getPicUrl);
                                SharedPreferencesUtils.saveString(getApplicationContext(),ConfigUsers.USERPIC,spath);
                                BaseApplication.photoPic = getPicUrl;
                                BaseApplication.bitmap = BitmapUtils.toRoundBitmap(headBitmap);
                                Log.i("mhysa","本地地址："+getPicUrl);
                                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                                intent.putExtra("photoUrl", getPicUrl);
                                intent.putExtra("username", name);
                                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            }
                        }.start();

                    }
                }
        }
    }


    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;

        String fileName = ImageCachePath + cutPicName;// 图片名字
        System.out.println("保存的图片名字="+fileName);
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        intent.putExtra("uri", uri.toString());
        startActivityForResult(intent, 3);
    }

    public String getPath(Cursor cursor, Uri uri) {
        if (cursor == null) {
            String str = uri.toString();

            if (str.contains("file:///")) {
                str = str.substring(7);
                System.out.println(str);
                return str;
            }
        }


        ContentResolver resolver = this.getContentResolver();
        String[] pojo = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, pojo, null, null, null);
        cursor = cursorLoader.loadInBackground();
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(pojo[0]));
        if (path != null && path.length() > 0) {
            picPath = path;
        }

        return picPath;

    }

    // 创建一个以当前时间为名称的文件
//    File tempFile = new File(Environment.getExternalStorageDirectory(),
//            getPhotoFileName());
//
//    // 使用系统当前日期加以调整作为照片的名称
//    @SuppressLint("SimpleDateFormat")
//    private String getPhotoFileName() {
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "'IMG'_yyyyMMdd_HHmmss");
//        return dateFormat.format(date) + ".jpg";
//    }

    protected void photo() {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtils.createFile(ImageCachePath + cutPicName)));
            startActivityForResult(intent2, 2);

        } else {
            Toast.makeText(getApplicationContext(), "内存卡不存在",
                    Toast.LENGTH_SHORT).show();
        }

    }


}
