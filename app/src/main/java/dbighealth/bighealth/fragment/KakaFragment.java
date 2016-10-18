package dbighealth.bighealth.fragment;


import android.content.Intent;
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


import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.ProductActivity;
import dbighealth.bighealth.adapter.GridViewAdapter1;
import dbighealth.bighealth.bean.ProductBean;
import dbighealth.bighealth.bean.TreatmentBean;
import dbighealth.bighealth.ben.Model;
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
    private List<ProductBean.ProductList> productList;
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
                Log.i("mhysa-->","productLists是否为空"+productList);
                String id1 = productList.get(arg2).getId()+"";
                Intent intent = new Intent(getActivity(),ProductActivity.class);
                intent.putExtra("imgId",id1);
                startActivity(intent);

            }
        });
        return view;
    }

    private StringCallback MyStringCallBack = new StringCallback(){

//        private String ids;

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
            productList = productBean.getProductList();
            if(productList!=null){
//                for(int i= 0 ; i<productList.size();i++){
//                    ids = productList.get(i).getId()+"";
//
//                }
//                results= productList.get(i).getResults();
                adapter = new GridViewAdapter1(getActivity(), productList);
                gridView.setAdapter(adapter);

            }






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
