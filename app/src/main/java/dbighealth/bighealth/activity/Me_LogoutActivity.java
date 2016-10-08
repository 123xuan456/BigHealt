package dbighealth.bighealth.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.imageUtils.BaseActivity;
import dbighealth.bighealth.imageUtils.Bimp;
import dbighealth.bighealth.imageUtils.FileUtils;
import dbighealth.bighealth.imageUtils.ImageItem;
import utils.BitmapUtils;
import utils.HttpPostUploadUtil;
import utils.UrlUtils;

/**
 * 退出登录
 */
public class Me_LogoutActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.rl_collection)
    RelativeLayout rlCollection;
    @Bind(R.id.rl_shoppingCart)
    RelativeLayout rlShoppingCart;
    @Bind(R.id.rcv_article_photo)
    SimpleDraweeView rcvArticlePhoto;
    private Button email_sign_in_button;
    private RelativeLayout remind;
    private TextView tvTab;
    private ImageView arrow_left;
    private ImageView right_add;
    private PopupWindow pop;
    private LinearLayout ll_popup;
    private RelativeLayout parent;
    private static final int TAKE_PICTURE = 101;
    private static final int GET_GALLEY = 102;
    private String path;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private String name;
    private String picUrl;
    private String uid;
    private Intent lastIntent;
    private Uri photoUri;
    private String picPath;
    public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath() + "/BigHealth/ImageCache/";// sd路径
    String cutPicName = "head.jpg";
    Bitmap headBitmap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String imgurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 2);
        }
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        picUrl = extras.getString("picUrl");
        uid = extras.getString("uid");
        imgurl = extras.getString("imgurl");
        if(imgurl!=null){
            Uri uri = Uri.parse(imgurl);
            rcvArticlePhoto.setImageURI(uri);
        }else if (picUrl != null) {
            Uri uri = Uri.parse(picUrl);
            rcvArticlePhoto.setImageURI(uri);
        }
        name = extras.getString("name");

        sp = getSharedPreferences("potrait", Activity.MODE_PRIVATE);
        edit = sp.edit();
        lastIntent = getIntent();
        //吧次activity放到集合里，等到修改密码成功之后统一取消
        BaseActivity.activityList.add(this);
        setView();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setView() {
        tvTab = (TextView) findViewById(R.id.tvTab);
        tvTab.setText("我的");
        arrow_left = (ImageView) findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);
        right_add = (ImageView) findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        email_sign_in_button.setOnClickListener(this);
        remind = (RelativeLayout) findViewById(R.id.remind);
        remind.setOnClickListener(this);
        rlCollection.setOnClickListener(this);
        rlShoppingCart.setOnClickListener(this);
        //  ivTouxiang.setOnClickListener(this);
        rcvArticlePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rcv_article_photo:
                initPopu();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        Me_LogoutActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(rlCollection, Gravity.CENTER, 10, 10);
                break;
            case R.id.arrow_left:
                finish();
                break;
            case R.id.email_sign_in_button:
                BaseApplication.userid = "";
                Toast.makeText(this, "退出成功", Toast.LENGTH_LONG).show();
                //退出成功之后发送一个广播
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("username", "");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                finish();
                break;
            case R.id.remind:
                Intent i = new Intent(this, RemindActivity.class);
                startActivity(i);
                break;
            case R.id.rl_collection:
                Toast.makeText(getApplicationContext(), "暂未开通，敬请期待！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_shoppingCart:
                Toast.makeText(getApplicationContext(), "暂未开通，敬请期待！", Toast.LENGTH_SHORT).show();
                break;
         /*   case R.id.user:
                Intent i1 = new Intent(this, EditdataActivity.class);
                startActivity(i1);
                break;*/
        }


    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initPopu() {
        pop = new PopupWindow(Me_LogoutActivity.this);
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

                /*Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image*//*");//相片类型
                startActivityForResult(intent, GET_GALLEY);
                overridePendingTransition(R.anim.activity_translate_in,
                        R.anim.activity_translate_out);*/
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
            case TAKE_PICTURE:


                if (resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);
                    final File f = new File(Environment.getExternalStorageDirectory()
                            + "/Photo_LJ/", fileName + ".JPEG");

                    new Thread() {
                        @Override
                        public void run() {
                            Map<String, String> textMap = new HashMap<String, String>();
                            textMap.put("name", "testname");
                            Map<String, String> fileMap = new HashMap<String, String>();
                            fileMap.put("file", getPath(null, Uri.fromFile(f)));
                            String getPicUrl = HttpPostUploadUtil.formUpload(UrlUtils.UPLOADPIC, textMap, fileMap);
                            Log.i("mhysa--->", "地址是22222:" + getPath(null, Uri.fromFile(f)));
                            edit.putString("touxiang", getPicUrl);
                            edit.commit();
                            BaseApplication.photoPic = Uri.fromFile(f).toString();
                            BaseApplication.username = name;
                            BaseApplication.userid = uid;
                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            intent.putExtra("photoUrl", getPicUrl);
                            intent.putExtra("username", name);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        }
                    }.start();
                    rcvArticlePhoto.setImageURI(Uri.fromFile(f));
                }

                break;
            case GET_GALLEY:
                System.out.println("data获取到的信息" + data);
                Bitmap bm = null;
                // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getContentResolver();
                try {
                    if (data != null) {
                        final Uri originalUri = data.getData(); // 获得图片的uri
                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
                        // 这里开始的第二部分，获取图片的路径：
                        String[] proj = {MediaStore.Images.Media.DATA};
                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        path = getPath(cursor, originalUri);
                        //  rcvArticlePhoto.setImageBitmap(bm);
                        //    rcvArticlePhoto.setImageURI(path);
                        new Thread() {

                            @Override
                            public void run() {
                                Map<String, String> textMap = new HashMap<String, String>();
                                textMap.put("name", "testname");
                                Map<String, String> fileMap = new HashMap<String, String>();
                                fileMap.put("file", path);
                                String getPicUrl = HttpPostUploadUtil.formUpload(UrlUtils.UPLOADPIC, textMap, fileMap);
                                edit.putString("touxiang", getPicUrl);
                                edit.commit();
                                BaseApplication.photoPic = getPicUrl;
                                BaseApplication.username = name;
                                BaseApplication.userid = uid;
                                System.out.println("返回图片的地址" + getPicUrl);
                                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                                intent.putExtra("photoUrl", getPicUrl);
                                intent.putExtra("username", name);
                                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            }
                        }.start();
                        rcvArticlePhoto.setImageURI(BaseApplication.photoPic);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;


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

                        setPicToView(headBitmap);// 保存在SD卡中
                        rcvArticlePhoto.setImageBitmap(BitmapUtils.toRoundBitmap(headBitmap));// 用ImageView显示出来
                        Log.i("mhysa--->","数据uri"+Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), headBitmap, null,null)));
                        final String path1 = getPath(null, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), headBitmap, null, null)));
                        final Uri imgUrl =Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), headBitmap, null, null));
                        new Thread() {
                            @Override
                            public void run() {
                                Map<String, String> textMap = new HashMap<String, String>();
                                textMap.put("name", "testname");
                                Map<String, String> fileMap = new HashMap<String, String>();
                                fileMap.put("file", path1);
                                String getPicUrl = HttpPostUploadUtil.formUpload(UrlUtils.UPLOADPIC, textMap, fileMap);

//                                Log.i("mhysa--->", "地址是22222:" + getPath(null, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), headBitmap, null,null))));
                                edit.putString("touxiang", getPicUrl);
                                edit.commit();
                                //  BaseApplication.photoPic = Uri.fromFile(f).toString();
                                BaseApplication.username = name;
                                BaseApplication.userid = uid;
                                BaseApplication.imgUrl = imgUrl;
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
        intent.putExtra("uri",uri.toString());
        startActivityForResult(intent, 3);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String picurl = data.getString("picurl");
            rcvArticlePhoto.setImageURI(picurl);
        }
    };

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
        CursorLoader cursorLoader = new CursorLoader(this, uri, pojo, null,null, null);
        cursor = cursorLoader.loadInBackground();
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(pojo[0]));
        if (path != null && path.length() > 0) {
            picPath = path;
        }

        return picPath;



      /*  String filePath;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
            String wholeID = DocumentsContract.getDocumentId(uri);
            String id = wholeID.split(":")[1];
            String[] column = { MediaStore.Images.Media.DATA };
            String sel = MediaStore.Images.Media._ID + =?;
             cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                    sel, new String[] { id }, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }else{
            String[] projection = { MediaStore.Images.Media.DATA };
           cursor = getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
        }return filePath;*/
    }

    // 创建一个以当前时间为名称的文件
    File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    // 使用系统当前日期加以调整作为照片的名称
    @SuppressLint("SimpleDateFormat")
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    protected void photo() {





        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
           /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
            // 指定调用相机拍照后照片的储存路径
            //   intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(intent, TAKE_PICTURE);*/

            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtils.createFile(ImageCachePath+cutPicName)));
            startActivityForResult(intent2, 2);

        } else {
            Toast.makeText(getApplicationContext(), "内存卡不存在",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
