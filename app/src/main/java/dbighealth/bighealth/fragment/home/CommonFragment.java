package dbighealth.bighealth.fragment.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.InfoAdapter;
import dbighealth.bighealth.bean.InfoBean;
import dbighealth.bighealth.view.BaseAdapter;
import dbighealth.bighealth.view.PullBaseView;
import dbighealth.bighealth.view.PullRecyclerView;

/**
 * 首页公共fragment
 * Created by mhysa
 */
public class CommonFragment extends Fragment implements BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener,
        BaseAdapter.OnViewClickListener, PullBaseView.OnRefreshListener {

    PullRecyclerView recyclerView;
    List<Object> mDatas;
    InfoAdapter infoAdapter;
    private String url;
    public CommonFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public CommonFragment(String url){
        this.url = url;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initData();
        View  view= inflater.inflate(R.layout.fragment_common,container,false);
        recyclerView = (PullRecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        infoAdapter = new InfoAdapter(getActivity(), mDatas,this);
        infoAdapter.setOnItemClickListener(this);
        infoAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(infoAdapter);
        return view;
    }

    /**
     * 解析网络请求
     */
    public void initInternet(){


    }
    public void initData(){
        /**
         * 暂时用假图片
         */
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


    }

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
                InfoBean info = new InfoBean();
                info.setText("新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增");
                mDatas.add(0, info);
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
                InfoBean info = new InfoBean();
                info.setText("更多更多更多更多更多更多更多更多更多更多更多更多更多更多更多更多更多");
                mDatas.add(info);
                infoAdapter.notifyDataSetChanged();
                recyclerView.onFooterRefreshComplete();
            }
        }, 1500);

    }

    @Override
    public void onViewClick(int position, int viewtype) {

    }
}
