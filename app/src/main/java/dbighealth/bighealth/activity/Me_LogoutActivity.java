package dbighealth.bighealth.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
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
  /*  @Bind(R.id.iv_touxiang)
    ImageView ivTouxiang;*/
    @Bind(R.id.rcv_article_photo)
    com.facebook.drawee.view.SimpleDraweeView rcvArticlePhoto;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        picUrl = extras.getString("picUrl");
        uid = extras.getString("uid");
        if(picUrl !=null){
            Uri uri = Uri.parse(picUrl);
            rcvArticlePhoto.setImageURI(uri);

        }
        name = extras.getString("name");

        sp = getSharedPreferences("potrait", Activity.MODE_PRIVATE);
        edit = sp.edit();
        lastIntent = getIntent();
        setView();
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

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, GET_GALLEY);
                overridePendingTransition(R.anim.activity_translate_in,
                        R.anim.activity_translate_out);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode==RESULT_OK) {
                    rcvArticlePhoto.setImageURI(Uri.fromFile(tempFile));

                    new Thread() {
                        @Override
                        public void run() {
                            Map<String, String> textMap = new HashMap<String, String>();
                            textMap.put("name", "testname");
                            Map<String, String> fileMap = new HashMap<String, String>();
                            fileMap.put("file", getPath(null,Uri.fromFile(tempFile)));
                            String getPicUrl = HttpPostUploadUtil.formUpload(UrlUtils.UPLOADPIC, textMap, fileMap);
                            edit.putString("touxiang",getPicUrl);
                            edit.commit();
                            BaseApplication.photoPic =getPicUrl;
                            BaseApplication.username = name;
                            BaseApplication.userid = uid;
                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            intent.putExtra("photoUrl", getPicUrl);
                            intent.putExtra("username",name);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                        }
                    }.start();

                }

                break;
            case GET_GALLEY:
                System.out.println("data获取到的信息" + data);
                Bitmap bm = null;
                // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getContentResolver();
                try {
                    if(data!=null){
                        final Uri originalUri = data.getData(); // 获得图片的uri
                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片
                        // 这里开始的第二部分，获取图片的路径：
                        String[] proj = {MediaStore.Images.Media.DATA};
                        // 好像是android多媒体数据库的封装接口，具体的看Android文档
                       Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        // 按我个人理解 这个是获得用户选择的图片的索引值
                        path = getPath(cursor,originalUri);
                        rcvArticlePhoto.setImageURI(originalUri);
                        new Thread() {

                            @Override
                            public void run() {
                                Map<String, String> textMap = new HashMap<String, String>();
                                textMap.put("name", "testname");
                                Map<String, String> fileMap = new HashMap<String, String>();
                                fileMap.put("file", path);
                                String getPicUrl = HttpPostUploadUtil.formUpload(UrlUtils.UPLOADPIC, textMap, fileMap);
                                edit.putString("touxiang",getPicUrl);
                                edit.commit();
                                BaseApplication.photoPic =getPicUrl;
                                BaseApplication.username = name;
                                BaseApplication.userid = uid;
                                System.out.println("返回图片的地址"+getPicUrl);
                                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                                intent.putExtra("photoUrl", getPicUrl);
                                intent.putExtra("username",name);
                                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            }
                        }.start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public String getPath(Cursor cursor,Uri uri){
        if(cursor == null){
            String str = uri.toString();
            System.out.println(str);
            if(str.contains("file:///")){
                str = str.substring(7);
                return str;
            }
        }

        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(index);
        return path;
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
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
            // 指定调用相机拍照后照片的储存路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(intent, TAKE_PICTURE);

        } else {
            Toast.makeText(getApplicationContext(), "内存卡不存在",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
