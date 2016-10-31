package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.CollectionAdapter;
import dbighealth.bighealth.bean.ColllectionBean;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 我的收藏
 */
public class CollectionActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.lv_collection)
    ListView lvCollection;
    @Bind(R.id.view)
    View view1;
    @Bind(R.id.iv_trash)
    ImageView ivTrash;
    @Bind(R.id.tv_listCount)
    TextView tvListCount;
    @Bind(R.id.rl_trash)
    RelativeLayout rlTrash;
    private int COLLECTION = 101;
    private int DELETE = 102;
    private CollectionAdapter collectionAdapter;
    Boolean  isChecked = false;
    private int count =0;
    Map<Integer, Boolean> isCheckMap =  new HashMap<Integer, Boolean>();
   Map<Integer, List<Integer>> isDeleteDataMap =  new HashMap<Integer, List<Integer>>();
    List<Integer> deleteIndex = new ArrayList<Integer>();
    public static  boolean flage =false;
    private List<ColllectionBean.MessageBean> message;
    private ColllectionBean colllectionBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        arrowLeft.setOnClickListener(this);
        tit.setText("我的收藏");
        rightTv.setText("编辑");
        rightTv.setOnClickListener(this);
        ivTrash.setOnClickListener(this);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_COLLECTION");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {

                int productposition = intent.getIntExtra("productposition", 0);

                Log.i("postion",productposition+"");
                int articleId = message.get(productposition).getArticleId();
                if( CollectionAdapter.getIsSelected().get(productposition)){
                    isCheckMap.put(articleId,true);
                    deleteIndex.add(productposition);
                    isDeleteDataMap.put(articleId,deleteIndex);
                    tvListCount.setText("("+(count+1)+")");
                    count+=1;

                }else{
                    isCheckMap.put(articleId,false);
                    // holder.cbcollecion.setChecked(false);
                    if(deleteIndex.size()==1){
                        deleteIndex.remove(0);
                    }else {
                        for (int i = 0; i < deleteIndex.size(); i++) {
                            if (deleteIndex.get(i) == productposition) {
                                deleteIndex.remove(i);
                            }
                        }
                    }
                    tvListCount.setText("("+(count-1)+")");
                    count-=1;
                }

            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);

        InitInternet();
        if(rightTv.getText().toString().equals("编辑")){
            lvCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CollectionActivity.this,SecondActivity.class);
                    intent.putExtra("picId",message.get(position).getArticleId());
                    startActivity(intent);
                }
            });

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        flage =false;
    }

    /**
     * 请求网络接口
     */
    public void InitInternet() {
        Log.i("mhysa-->","打印内容："+UrlUtils.COLLECTIONSHOW+"?userId="+SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""));
        OkHttpUtils.get()
                .url(UrlUtils.COLLECTIONSHOW)
                .addParams("userId", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                .id(COLLECTION)
                .build()
                .execute(MyStringCallBack);
    }

    StringCallback MyStringCallBack = new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {
            if (id == COLLECTION) {

            }
        }

        @Override
        public void onResponse(String response, int id) {
            //显示收藏
            if (id == COLLECTION) {
                Gson gson = new Gson();
                colllectionBean = gson.fromJson(response, ColllectionBean.class);
                message = colllectionBean.getMessage();
                Log.i("mhysa-->","显示我的收藏内容："+message.toString());
                collectionAdapter = new CollectionAdapter(getApplicationContext(), message,flage);
                lvCollection.setAdapter(collectionAdapter);
            }
            //删除我的收藏
            if(id == DELETE){
                Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            //编辑
            case R.id.right_tv:
                if (rightTv.getText().toString().equals("编辑")) {
                    rightTv.setText("取消");
                    /**
                     * listview为非编辑界面
                     */
                    view1.setVisibility(View.VISIBLE);
                    rlTrash.setVisibility(View.VISIBLE);
                    int childCount = lvCollection.getChildCount();
                    flage = true;
                    InitInternet();
                    collectionAdapter.notifyDataSetChanged();
                    lvCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            rlTrash.setVisibility(View.VISIBLE);
                            CollectionAdapter.ViewHolder holder = (CollectionAdapter.ViewHolder) view.getTag();
                            holder.cbcollecion.toggle();

                        }
                    });

                }else if(rightTv.getText().toString().equals("取消")){
                    view1.setVisibility(View.GONE);
                    rlTrash.setVisibility(View.GONE);
                    rightTv.setText("编辑");
                    flage = false;
                    int childCount = lvCollection.getChildCount();
                    if(childCount!=0){
                        for(int i= 0;i<childCount;i++){
                            CheckBox checkBox = (CheckBox) lvCollection.getChildAt(i).findViewById(R.id.cb_collecion);
                            checkBox.setVisibility(View.GONE);
                            int articleId = message.get(i).getArticleId();
                            isCheckMap.put(articleId,false);
                        }
                    }
                }
                break;
            /**
             * 删除选中收藏
             */
            case R.id.iv_trash:
                //存储articleID
                if(count ==0){
                    Toast.makeText(getApplicationContext(),"请选择要删除的文章",Toast.LENGTH_SHORT).show();
                }else{
                    String saveAritcleId = "";
                    for(int i=0;i<deleteIndex.size();i++){
                        if(i==0){
                            int index =deleteIndex.get(i);
                            if(i!=deleteIndex.size()-i-1){
                                saveAritcleId+=message.get(index).getArticleId()+",";
                            }else{
                                saveAritcleId+=message.get(index).getArticleId();
                            }
                            message.remove(index);
                        }else{

                            int index =deleteIndex.get(i)-i;
                            if(i!=deleteIndex.size()-1){
                                saveAritcleId+=message.get(index).getArticleId()+",";
                            }else{
                                saveAritcleId+=message.get(index).getArticleId();
                            }
                            message.remove(index);
                        }
                    }
                    /**
                     * 根据不同articleId的选中情况设置checkBox避免了删除后不同articleId位置的变化
                     */

                 for(int i =0 ;i<message.size();i++){
                       Log.i("mhysa-->","lvCollection.getChildAt(i)="+i+lvCollection.getChildAt(i%5)+message.size());
                        CheckBox ischecked = (CheckBox) lvCollection.getChildAt(i%5).findViewById(R.id.cb_collecion);
                        ischecked.setChecked(false);
                    }
                    collectionAdapter.notifyDataSetChanged();
                    tvListCount.setText("("+0+")");
                    count = 0;
                    //将存储选中的集合清空，避免第二次删除时集合长度递增的情况
                    deleteIndex.clear();
                    /**
                     * 调用删除数据接口
                     */
                    Log.i("mhysa-->","删除了的articleId:"+saveAritcleId);
                    OkHttpUtils.get()
                            .url(UrlUtils.DELETECOLLECTION)
                            .addParams("userId",SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, ""))
                            .addParams("articleId",saveAritcleId)
                            .id(DELETE)
                            .build()
                            .execute(MyStringCallBack);
                }
                break;
        }
    }
}
