package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.SecondAdapter;
import dbighealth.bighealth.bean.SecondCommntBean;
import dbighealth.bighealth.view.ListViewForScrollView;
import okhttp3.Call;
import utils.ConfigUsers;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 点击的二级界面
 */
public class SecondActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.txtedit)
    EditText txtedit;
    @Bind(R.id.txtbottomshare)
    RelativeLayout txtbottomshare;
    @Bind(R.id.btnpinglun)
    TextView btnpinglun;
    @Bind(R.id.ndewbfhwb)
    RelativeLayout ndewbfhwb;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_secondPic)
    ImageView ivSecondPic;
    @Bind(R.id.tv_secondDesciption)
    TextView tvSecondDesciption;
    @Bind(R.id.mcomment)
    ListViewForScrollView mcomment;
    @Bind(R.id.iv_collction)
    ImageView ivCollction;
    @Bind(R.id.pr_total)
    PercentRelativeLayout prTotal;
    private int picId;
    private int SECOND = 101;
    private int COMMENT_USER = 102;
    private  int ADDCOLLECTION =103;
    private String userid;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_page_details);
        ButterKnife.bind(this);
        arrowLeft.setOnClickListener(this);
        tit.setVisibility(View.INVISIBLE);
        rightTv.setVisibility(View.INVISIBLE);
        btnpinglun.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        picId = extras.getInt("picId");
        id = extras.getInt("id");
        userid = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");

        InitInternet();
    }

    /**
     * 请求网络
     */
    public void InitInternet() {
       /* OkHttpUtils.get()
                   .url(UrlUtils.FORMATIONDETAIL)
                   .addParams()*/
      /*  OkHttpUtils.get()
                .url(UrlUtils.SPECIALITEM)
                .id(SECOND)
                .addParams("id", String.valueOf(picId))
                .addParams("userid", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                .build()
                .execute(MyStringCallBack);*/

        if (id == 1) {
            Log.i("mhysa-->", UrlUtils.FORMATIONDETAIL + "?id=" + picId);
            OkHttpUtils.get()
                    .url(UrlUtils.FORMATIONDETAIL)
                    .id(SECOND)
                    .addParams("id", String.valueOf(picId))
                    .addParams("userid", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                    .build()
                    .execute(MyStringCallBack);
        }else if (id == 2) {
            OkHttpUtils.get()
                    .url(UrlUtils.SPECIALITEM)
                    .id(SECOND)
                    .addParams("id", String.valueOf(picId))
                    .addParams("userid", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                    .build()
                    .execute(MyStringCallBack);
        }else{
            Log.i("mhysa-->", UrlUtils.FORMATIONDETAIL + "?id=" + picId);
            OkHttpUtils.get()
                    .url(UrlUtils.FORMATIONDETAIL)
                    .id(SECOND)
                    .addParams("id", String.valueOf(picId))
                    .addParams("userid", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                    .build()
                    .execute(MyStringCallBack);
        }

    }

    private StringCallback MyStringCallBack = new StringCallback() {

        private List<SecondCommntBean.ComListBean> comList;
        private SecondCommntBean secondCommntBean;
        private SecondAdapter secondAdapter;

        @Override
        public void onError(Call call, Exception e, int id) {

            if (id == COMMENT_USER) {
                Toast.makeText(getApplicationContext(), "提交失敗", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {

            if (id == SECOND) {
                Gson gson = new Gson();
                secondCommntBean = gson.fromJson(response, SecondCommntBean.class);
                if (secondCommntBean.getCode() == 200) {
                    int like = secondCommntBean.getLike();
                    String title = secondCommntBean.getTitle();
                    String images = secondCommntBean.getImages();
                    String content = secondCommntBean.getContent();
                    comList = secondCommntBean.getComList();
                    if(like==1){
                        ivCollction.setImageResource(R.mipmap.pentagram);
                    }else if(like ==0){
                        ivCollction.setImageResource(R.drawable.star);
                        ivCollction.setOnClickListener(SecondActivity.this);
                    }
                    if (title.isEmpty()) {
                        tvTitle.setText("匿名");
                    } else {
                        tvTitle.setText(title);
                    }

                    Glide.with(getApplicationContext())
                            .load(images)
                            .centerCrop()
                            .crossFade()
                            .into(ivSecondPic);
                    tvSecondDesciption.setText("   " + content);
                    Log.i("mhysa", "打印集合内容" + comList.toString());
                    secondAdapter = new SecondAdapter(getApplicationContext(), comList);
                    mcomment.setAdapter(secondAdapter);
                }

            }
            if (id == COMMENT_USER) {
                Toast.makeText(getApplicationContext(), "提交成功！", Toast.LENGTH_SHORT).show();

                /**
                 *   private int id;
                 private int articleId;
                 private String comment;
                 private String time;
                 private int userid;
                 private String name;
                 */
                SecondCommntBean.ComListBean commentList = new SecondCommntBean.ComListBean();
                commentList.setName(SharedPreferencesUtils.getString(SecondActivity.this, ConfigUsers.USERNAME, ""));
                commentList.setComment(txtedit.getText().toString());
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String format = sdf.format(date);
                commentList.setTime(format);
                comList.add(0, commentList);
                secondAdapter.notifyDataSetChanged();
                txtedit.setText("");
                txtedit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        Resources resources = getApplicationContext().getResources();
                        Drawable drawable = resources.getDrawable(R.drawable.write_bg);
                        txtedit.setCompoundDrawables(drawable, null, null, null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (txtedit.getText().equals("")) {
                            Log.i("mhysa", "方法为空");
                            Resources resources = getApplicationContext().getResources();
                            Drawable drawable = resources.getDrawable(R.drawable.write_bg);
                            txtedit.setCompoundDrawables(drawable, null, null, null);
                        } else {
                            Log.i("mhysa-->", "edittext返回值" + txtedit.getText().toString());
                            txtedit.setCompoundDrawables(null, null, null, null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Resources resources = getApplicationContext().getResources();
                        Drawable drawable = resources.getDrawable(R.drawable.write_bg);
                        txtedit.setCompoundDrawables(drawable, null, null, null);
                    }
                });
            }else if(id ==ADDCOLLECTION){
                Toast.makeText(getApplicationContext(),"添加收藏成功！",Toast.LENGTH_SHORT).show();
                ivCollction.setImageResource(R.mipmap.pentagram);
                ivCollction.setOnClickListener(null);
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            /**
             * 发送评论
             */
            case R.id.btnpinglun:
                if (SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "").isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请登录！", Toast.LENGTH_SHORT).show();
                } else {

                    if (txtedit.getText().length() != 0) {
                        String userid = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
                        OkHttpUtils.get()
                                .url(UrlUtils.COMMENT)
                                .addParams("articleId", String.valueOf(picId))
                                .addParams("comment", txtedit.getText().toString())
                                .addParams("userid", userid)
                                .id(COMMENT_USER)
                                .build()
                                .execute(MyStringCallBack);
                        Resources resources = getApplicationContext().getResources();
                        Drawable drawable = resources.getDrawable(R.drawable.write_bg);
                        txtedit.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        Toast.makeText(getApplicationContext(), "请先输入评论内容！！", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            //添加收藏
            case R.id.iv_collction:

                Log.i("mhysa-->","url="+UrlUtils.ADDCOLLECTIONS+"?userid="+userid+"articleId"+picId);
                OkHttpUtils.get()
                           .url(UrlUtils.ADDCOLLECTIONS)
                           .id(ADDCOLLECTION)
                           .addParams("userId",userid)
                           .addParams("articleId",String.valueOf(picId))
                           .build()
                           .execute(MyStringCallBack);
                break;
        }
    }
}
