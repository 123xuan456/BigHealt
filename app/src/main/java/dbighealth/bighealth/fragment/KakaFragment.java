package dbighealth.bighealth.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.adapter.GridViewAdapter1;
import dbighealth.bighealth.bean.ProductBean;
import dbighealth.bighealth.ben.Model;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.GridViewAdapter;
import dbighealth.bighealth.ben.Type;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * simple {@link Fragment} subclass.
 */
public class KakaFragment extends Fragment {

    private ArrayList<Type> list;
    private GridView gridView;
    private GridViewAdapter1 adapter;
    private Type type;
    private String typename;
    private int icon;
    private static  int PRODUCT =1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kaka, null);
        gridView = (GridView) view.findViewById(R.id.listView);
        int index = getArguments().getInt("index");
        String params = index +"";
        Log.i("mhysa-->","产品页的展示"+index);
        /**
         * 网络请求
         */
        OkHttpUtils.get()
                .url(UrlUtils.PRODUCT)
                .id(PRODUCT)
                .addParams("id",params)
                .build()
                .execute(MyStringCallBack);

        /**
         * 图片名字和图片
         */
        typename = Model.toolsList[index];
     //   icon = Model.iconList[index];
        ((TextView) view.findViewById(R.id.toptype)).setText(typename);
      //  GetTypeList();
       // adapter = new GridViewAdapter(getActivity(), list);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
            }
        });

        return view;
    }

    private StringCallback MyStringCallBack = new StringCallback(){

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.i("mhysa--->","请求失败");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.i("mhysa--->",response);
            //解析json
            Gson gson =new Gson();
            ProductBean productBean = gson.fromJson(response, ProductBean.class);
            List<ProductBean.ProductList> productList = productBean.getProductList();
            adapter = new GridViewAdapter1(getActivity().getApplicationContext(), (ArrayList<ProductBean.ProductList>) productList);
            gridView.setAdapter(adapter);

        }
    };
   /* private void GetTypeList() {
        list = new ArrayList<Type>();
        for (int i = 1; i < 23; i++) {
            type = new Type(i, typename + i, icon);
            list.add(type);
        }
    }*/

}
