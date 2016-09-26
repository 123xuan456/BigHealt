package dbighealth.bighealth;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BllDemo extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.rv_ball)
    RecyclerView rvBall;
    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private List<Integer> mDatas;
    private List list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bll_demo);
        ButterKnife.bind(this);

        initDatas();

      /*  List list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵球");*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvBall.setLayoutManager(linearLayoutManager);
        System.out.println("集合长度："+list1.size());
        //设置适配器
        mAdapter = new GalleryAdapter(this, mDatas,list1);

        rvBall.setAdapter(mAdapter);
        rvBall.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvBall, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // ...
                mAdapter.notifyItemRangeRemoved(position,position+1);
                Toast.makeText(getApplicationContext(),"删除了"+position,Toast.LENGTH_SHORT).show();
                list1.remove(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

    }
    private void initDatas()
    {
        mDatas = new ArrayList<>(Arrays.asList(R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher));
        list1 = new ArrayList<String>();
        for(int i=0;i<mDatas.size();i++){
            list1.add("图片"+i);
        }
    }

    @Override
    public void onClick(View v) {

    }


    public class GalleryAdapter extends
            RecyclerView.Adapter<GalleryAdapter.ViewHolder>
    {
        private LayoutInflater mInflater;
        private List<Integer> mDatas;
        private List<String> mList;
        public GalleryAdapter(Context context, List<Integer> datats,List<String> list)
        {
            mInflater = LayoutInflater.from(context);
            mDatas = datats;
            mList = list;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public ViewHolder(View arg0)
            {
                super(arg0);
            }

            ImageView mImg;
            TextView mTxt;
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        /**
         * 创建ViewHolder
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
        {
            View view = mInflater.inflate(R.layout.item_ball,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.mImg = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mTxt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
            return viewHolder;
        }

        /**
         * 设置值
         */
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i)
        {
            viewHolder.mImg.setImageResource(mDatas.get(i));
            System.out.println("接收到的集合的长度："+mList);
            viewHolder.mTxt.setText(mList.get(i));
        }

    }
}
