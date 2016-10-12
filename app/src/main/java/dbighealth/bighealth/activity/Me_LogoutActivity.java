package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.imageUtils.BaseActivity;
import utils.ConfigUsers;
import utils.SharedPreferencesUtils;
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
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        picUrl = extras.getString("picUrl");
//        uid = extras.getString("uid");
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

        getBraodCast();

    }

    /**
     * 接受广播
     */
    public void getBraodCast(){
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");//修改昵称
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                if (BaseApplication.bitmap!=null){
                    rcvArticlePhoto.setImageBitmap(BaseApplication.bitmap);

                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
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


                Intent i1 = new Intent(this, EditdataActivity.class);
                i1.putExtra("imgurl1",imgurl);
                startActivity(i1);
/*
                initPopu();
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        Me_LogoutActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(rlCollection, Gravity.CENTER, 10, 10);*/
                break;
            case R.id.arrow_left:
                finish();
                break;
            case R.id.email_sign_in_button:
//                BaseApplication.userid = "";
                SharedPreferencesUtils.saveString(this, UrlUtils.LOGIN, "");
                BaseApplication.bitmap=null;
                BaseApplication.photoPic="";
                SharedPreferencesUtils.saveString(getApplicationContext(), ConfigUsers.USERPIC,"");
                BaseApplication.imgUrl = null;
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
            /**
             * 我的收藏
             */
            case R.id.rl_collection:
            //    Toast.makeText(getApplicationContext(), "暂未开通，敬请期待！", Toast.LENGTH_SHORT).show();
                Intent collectionInent= new Intent(Me_LogoutActivity.this,CollectionActivity.class);
                startActivity(collectionInent);
                break;
            case R.id.rl_shoppingCart:
                Toast.makeText(getApplicationContext(), "暂未开通，敬请期待！", Toast.LENGTH_SHORT).show();
                break;

        }


    }

}
