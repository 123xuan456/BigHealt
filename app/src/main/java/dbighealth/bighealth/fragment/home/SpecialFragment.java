package dbighealth.bighealth.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.InfoAdapter;
import dbighealth.bighealth.adapter.ItemAdapter;
import dbighealth.bighealth.view.BaseAdapter;
import dbighealth.bighealth.view.PullBaseView;
import dbighealth.bighealth.view.PullRecyclerView;

/**
 * 首页特殊布局
 */
public class SpecialFragment extends Fragment implements BaseAdapter.OnItemClickListener, BaseAdapter.OnItemLongClickListener,
        BaseAdapter.OnViewClickListener, PullBaseView.OnRefreshListener{


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
        /*itemAdapter = new ItemAdapter(getActivity(),mDatas,this);

        itemAdapter.setOnItemClickListener(this);
        itemAdapter.setOnItemLongClickListener(this);
        rv_spcific.setAdapter(itemAdapter);*/
        return view;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {

    }

    @Override
    public void onFooterRefresh(PullBaseView view) {

    }

    @Override
    public void onViewClick(int position, int viewtype) {

    }
}
