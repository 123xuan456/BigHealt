package dbighealth.bighealth.fragment.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.LoginActivity;
import dbighealth.bighealth.adapter.InfoAdapter1;
import dbighealth.bighealth.bean.CommonHomeBean;
import dbighealth.bighealth.view.BaseAdapter;
import dbighealth.bighealth.view.PullBaseView;
import dbighealth.bighealth.view.PullRecyclerView;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 首页公共fragment
 * Created by mhysa
 */
public class CommonFragment extends Fragment implements BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener,
        BaseAdapter.OnViewClickListener, PullBaseView.OnRefreshListener {

    int HOME_COMMON=101;
    private Context context;
    PullRecyclerView recyclerView;
    List<Object> mDatas;
    InfoAdapter1 infoAdapter;
    private String url;
    private String params;
    public CommonFragment() {
        // Required empty public constructor
        context = getActivity();
    }

    @SuppressLint("ValidFragment")
    public CommonFragment(String url,String params){
//        Log.e("mhysa", UrlUtils.CommonHome);
        this.url = UrlUtils.CommonHome;
        this.params = params;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // initData();
        View  view= inflater.inflate(R.layout.fragment_common,container,false);
        recyclerView = (PullRecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager  linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        initInternet();
        recyclerView.setOnRefreshListener(this);

        return view;
    }

    /**
     * 解析网络请求
     */
    public void initInternet(){

        Log.e("mhysa","url:-->"+UrlUtils.CommonHome+"id"+params);
        OkHttpUtils
                .get()
                .url(UrlUtils.CommonHome)
                .id(HOME_COMMON)
                .addParams("id", params)
                .build()
                .execute(MyStringCallBack);
    }
    public StringCallback MyStringCallBack=new StringCallback(){

        @Override
        public void onError(Call call, Exception e, int id) {

            Log.e("mhysa-->","加载失败");
        }

        @Override
        public void onResponse(String response, int id) {

            Message msg = new Message();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putString("result",response);
            msg.setData(bundle);
            handler.sendMessage(msg);

            Log.e("mhysa-->",response.getBytes().toString());


        }
    };
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bundle data = msg.getData();
                    String result = data.getString("result");
                    Log.e("mhysa","请求到的接口"+result);
                    Gson gson = new Gson();
                    CommonHomeBean commonHomeBean = gson.fromJson(result, CommonHomeBean.class);
                    int code = commonHomeBean.getCode();
                    if(code ==200){
                        List<CommonHomeBean.ResultBean> result1 = commonHomeBean.getResult();
                        List<Object> feng = new ArrayList<Object>();
                        feng.add(result1);
                        infoAdapter = new InfoAdapter1(getActivity(),result1);
                      //  Log.e("mhysa",infoAdapter.toString());
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(infoAdapter);
                    }

                    break;
            }

        }
    };
    /*public void initData(){
        *//**
         * 暂时用假图片
         *//*
        mDatas = new ArrayList<>();
        List<Object> imageList = new ArrayList<>();
        imageList.add("http://avatar.csdn.net/3/B/9/1_baiyuliang2013.jpg");
        imageList.add("https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=a22d53b052fbb2fb2b2b5f127f482043/ac345982b2b7d0a2f7375f70ccef76094a369a65.jpg");
        imageList.add("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=57c485df7cec54e75eec1d1e893a9bfd/241f95cad1c8a786bfec42ef6009c93d71cf5008.jpg");
        imageList.add("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=f3f6ab70cc134954611eef64664f92dd/dcc451da81cb39db1bd474a7d7160924ab18302e.jpg");
        imageList.add("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg");
        imageList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1564533037,3918553373&fm=116&gp=0.jpg");
        for (int i = 1; i < 30; i++) {
            InfoBean info = new InfoBean();
            info.setText("糖尿病并发症眼症" + i);
            info.setImgList(imageList);
            mDatas.add(info);
        }
    }*/

    @Override
    public void onItemClick(int position) {

    }
    @Override
    public void onItemLongClick(int position) {

    }

    /**
     * 下拉刷新
     * @param view
     */
    @Override
    public void onHeaderRefresh(PullBaseView view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                infoAdapter.notifyDataSetChanged();
                recyclerView.onHeaderRefreshComplete();
            }
        }, 1500);
    }

    /**
     * 上拉加载
     * @param view
     */
    @Override
    public void onFooterRefresh(PullBaseView view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                infoAdapter.notifyDataSetChanged();
                recyclerView.onFooterRefreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onViewClick(int position, int viewtype) {


    }
}
