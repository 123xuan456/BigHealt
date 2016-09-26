package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.SecondAdapter;
import dbighealth.bighealth.bean.SecondCommntBean;
import dbighealth.bighealth.view.ListViewForScrollView;
import okhttp3.Call;
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
    private int picId;

    private int SECOND = 101;
    private int COMMENT_USER = 102;
    private String userid;

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
        picId = intent.getIntExtra("picId", 0);
        userid = BaseApplication.userid;
        InitInternet();

    }

    /**
     * 请求网络
     */
    public void InitInternet() {
       /* OkHttpUtils.get()
                   .url(UrlUtils.FORMATIONDETAIL)
                   .addParams()*/

        Log.i("mhysa-->", UrlUtils.FORMATIONDETAIL + "?id=" + picId);
        OkHttpUtils.get()
                .url(UrlUtils.FORMATIONDETAIL)
                .id(SECOND)
                .addParams("id", String.valueOf(picId))
                .build()
                .execute(MyStringCallBack);
    }

    private StringCallback MyStringCallBack = new StringCallback() {

        private List<SecondCommntBean.ComListBean> comList;
        private SecondCommntBean secondCommntBean;
        private SecondAdapter secondAdapter;

        @Override
        public void onError(Call call, Exception e, int id) {

            if(id==COMMENT_USER){
                Toast.makeText(getApplicationContext(),"提交失敗",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {

            if (id == SECOND) {
                Gson gson = new Gson();
                secondCommntBean = gson.fromJson(response, SecondCommntBean.class);
                if (secondCommntBean.getCode() == 200) {
                    String title = secondCommntBean.getTitle();
                    String images = secondCommntBean.getImages();
                    String content = secondCommntBean.getContent();
                    comList = secondCommntBean.getComList();
                    if(title.isEmpty()){
                        tvTitle.setText("匿名");
                    }else{
                        tvTitle.setText(title);
                    }

                    Glide.with(getApplicationContext())
                            .load(images)
                            .centerCrop()
                            .crossFade()
                            .into(ivSecondPic);
                    tvSecondDesciption.setText("   " + content);
                    secondAdapter = new SecondAdapter(getApplicationContext(), comList);
                    mcomment.setAdapter(secondAdapter);
                }

            }
            if(id==COMMENT_USER){
                Toast.makeText(getApplicationContext(),"提交成功！",Toast.LENGTH_SHORT).show();

                /**
                 *   private int id;
                 private int articleId;
                 private String comment;
                 private String time;
                 private int userid;
                 private String name;
                 */
                SecondCommntBean.ComListBean commentList = new  SecondCommntBean.ComListBean();
                commentList.setName(BaseApplication.username);
                commentList.setComment(txtedit.getText().toString());
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String format = sdf.format(date);
                commentList.setTime(format);
                comList.add(0,commentList);
                secondAdapter.notifyDataSetChanged();
                txtedit.setText("");
                Log.i("mhysa-->",BaseApplication.username+"用戶名");
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.btnpinglun:
                if(BaseApplication.userid.isEmpty()){
                    Toast.makeText(getApplicationContext(),"请登录！",Toast.LENGTH_SHORT).show();
                }else{

                    if(txtedit.getText().length()!=0){
                        OkHttpUtils.get()
                                .url(UrlUtils.COMMENT)
                                .addParams("articleId",String.valueOf(picId))
                                .addParams("comment",txtedit.getText().toString())
                                .addParams("userid",BaseApplication.userid)
                                .id(COMMENT_USER)
                                .build()
                                .execute(MyStringCallBack);
                    }else{
                        Toast.makeText(getApplicationContext(),"请先输入评论内容！！",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }
}
