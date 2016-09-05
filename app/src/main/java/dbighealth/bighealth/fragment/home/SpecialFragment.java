package dbighealth.bighealth.fragment.home;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ItemAdapter;
import dbighealth.bighealth.bean.CommonHealth;
import dbighealth.bighealth.view.BaseAdapter;
import dbighealth.bighealth.view.ItemBaseAdapter;
import dbighealth.bighealth.view.PullBaseView;
import dbighealth.bighealth.view.PullRecyclerView;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 首页特殊布局
 */
public class SpecialFragment extends Fragment implements BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener,
        BaseAdapter.OnViewClickListener, PullBaseView.OnRefreshListener{


    private int SPECIAL_HOME = 1;
    private ImageView iv_spcific;
    private PullRecyclerView rv_spcific;
    private ItemAdapter itemAdapter;

    public SpecialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_specific,container,false);
        iv_spcific = (ImageView) view.findViewById(R.id.iv_spcific);
        rv_spcific = (PullRecyclerView)view.findViewById(R.id.rv_spcific);
        rv_spcific.setOnRefreshListener(this);

        rv_spcific.setLayoutManager(new LinearLayoutManager(getActivity()));

        initInternet();
        /*itemAdapter = new ItemAdapter(getActivity(),mDatas,this);
        itemAdapter.setOnItemClickListener(this);
        itemAdapter.setOnItemLongClickListener(this);
        rv_spcific.setAdapter(itemAdapter);*/
        return view;
    }

    /**
     * 初始化网络接口
     *
     */
   public void  initInternet(){
       Log.e("mhysa","特殊页的地址"+UrlUtils.SpecialHome);

       OkHttpUtils.get()
                  .url("http://192.168.0.38:8080/JianKangChanYe/homepictures/showhealthknowledge")
                  .id(SPECIAL_HOME)
               .build()
               .execute(MyStringCallBack);

   }
    public StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

            Log.e("mhysa","请求失败");
        }

        @Override
        public void onResponse(String response, int id) {

            Log.e("mhysa","首页特殊页展示"+response);
            Gson gson = new Gson();
            CommonHealth commonHealth = gson.fromJson(response, CommonHealth.class);
            String code = commonHealth.getCode();
            if(id==SPECIAL_HOME){
                if(code.equals("200")){
                    String imgUrl = commonHealth.getImgUrl();
                    Glide.with(getActivity())
                            .load(imgUrl)
                            .centerCrop()
                            .placeholder(R.mipmap.home)
                            .error(R.mipmap.home)
                            .crossFade()
                            .into(iv_spcific);

                    List<CommonHealth.ItemListBean> itemList = commonHealth.getItemList();
                    itemAdapter = new ItemAdapter(getActivity(),itemList);
                 //   itemAdapter.setOnItemClickListener((ItemBaseAdapter.OnItemClickListener) getActivity().getApplicationContext());
                  /*  LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(OrientationHelper.VERTICAL);
                  //  rv_spcific.setHasFixedSize(true);
                    rv_spcific.setLayoutManager(llm);*/
                    rv_spcific.setAdapter(itemAdapter);

                }
            }

        }
    };

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
                itemAdapter.notifyDataSetChanged();
                rv_spcific.onHeaderRefreshComplete();
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
                itemAdapter.notifyDataSetChanged();
                rv_spcific.onFooterRefreshComplete();
            }
        }, 1500);
    }
    @Override
    public void onViewClick(int position, int viewtype) {

    }
}
