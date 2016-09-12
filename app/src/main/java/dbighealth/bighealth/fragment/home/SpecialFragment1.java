package dbighealth.bighealth.fragment.home;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ItemAdapter;
import dbighealth.bighealth.adapter.ItemAdapter1;
import dbighealth.bighealth.bean.CommonHealth;
import dbighealth.bighealth.view.BaseAdapter;
import dbighealth.bighealth.view.PullBaseView;
import dbighealth.bighealth.view.PullRecyclerView;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 首页特殊布局
 */
public class SpecialFragment1 extends Fragment implements BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener,
        BaseAdapter.OnViewClickListener, PullBaseView.OnRefreshListener{


    private int SPECIAL_HOME = 1;
    private ImageView iv_spcific;
    private PullRecyclerView rv_spcific;
    private ItemAdapter itemAdapter;
    private ListView rv_spcific1;

    public SpecialFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_specific1,container,false);
        iv_spcific = (ImageView) view.findViewById(R.id.iv_spcific);
        rv_spcific1 = (ListView)view.findViewById(R.id.rv_spcific);

     /*   LinearLayoutManager  linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        rv_spcific.setLayoutManager(linearLayoutManager);*/
        initInternet();
       // rv_spcific1.setOnRefreshListener(this);
        return view;
    }

    /**
     * 初始化网络接口
     *
     */
   public void  initInternet(){
       Log.e("mhysa","特殊页的地址"+UrlUtils.SpecialHome);

       OkHttpUtils.get()
                  .url(UrlUtils.SpecialHome)
                  .id(SPECIAL_HOME)
                  .build()
                  .execute(MyStringCallBack);

   }
    public StringCallback MyStringCallBack = new StringCallback() {
        private ItemAdapter1 itemAdapter11;
        private ItemAdapter1 itemAdapter1;

        @Override
        public void onError(Call call, Exception e, int id) {

            Log.e("mhysa","特殊页请求失败");
        }

        @Override
        public void onResponse(String response, int id) {
           if(id==SPECIAL_HOME){

             //  Bundle data = msg.getData();
             //  String result = data.getString("result");
               Log.e("mhysa","首页特殊页展示"+response);
               Gson gson = new Gson();
               CommonHealth commonHealth = gson.fromJson(response, CommonHealth.class);
               String code = commonHealth.getCode();

               if(code.equals("200")){
                   String imgUrl = commonHealth.getImgUrl();
                   Log.i("mhysa-->","首页图片地址是"+imgUrl);
                   Glide.with(getActivity())
                           .load(imgUrl)
                           .centerCrop()
                           .placeholder(R.mipmap.home)
                           .error(R.mipmap.home)
                           .crossFade()
                           .into(iv_spcific);

                   List<CommonHealth.ItemListBean> itemList = commonHealth.getItemList();
                   itemAdapter11 = new ItemAdapter1(getActivity(),itemList);
                   //   itemAdapter.setOnItemClickListener((ItemBaseAdapter.OnItemClickListener) getActivity().getApplicationContext());
                  /*  LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    llm.setOrientation(OrientationHelper.VERTICAL);
                  //  rv_spcific.setHasFixedSize(true);
                    rv_spcific.setLayoutManager(llm);*/
                   rv_spcific1.setAdapter(itemAdapter11);
               }
           }
        }
    };

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    Bundle data = msg.getData();
                    String result = data.getString("result");
                    Log.e("mhysa","首页特殊页展示"+result);
                    Gson gson = new Gson();
                    CommonHealth commonHealth = gson.fromJson(result, CommonHealth.class);
                    String code = commonHealth.getCode();

                        if(code.equals("200")){
                            String imgUrl = commonHealth.getImgUrl();
                            Log.i("mhysa-->","首页图片地址是"+imgUrl);
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
                            rv_spcific1.setAdapter((ListAdapter) itemAdapter);
                        }

                    break;
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
