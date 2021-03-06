package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.R;
import dbighealth.bighealth.imageUtils.AlbumActivity;
import dbighealth.bighealth.imageUtils.BaseActivity;
import dbighealth.bighealth.imageUtils.Bimp;
import dbighealth.bighealth.imageUtils.FileUtils;
import dbighealth.bighealth.imageUtils.GalleryActivity;
import dbighealth.bighealth.imageUtils.ImageItem;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 资讯详情
 */
public class Information_DetailsActivity extends Activity  {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.problem)
    EditText problem1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.help)
    EditText help1;
    @Bind(R.id.textView3)
    TextView textView3;

    // 上传的地址
    String uploadUrl = "http://192.168.0.43:8080/JianKangChanYe/homepictures/getAppLog?";
    GridView mGridView;
    @Bind(R.id.rl_grid_view)
    RelativeLayout parentView;

    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private GridAdapter adapter;
    public static Bitmap bimap;
    private static final int TAKE_PICTURE = 0;
    private String problem;
    private String help;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information__details);
        ButterKnife.bind(this);
        tit.setVisibility(View.GONE);
        rightTv.setText("提交");
        id= SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
        BaseActivity.activityList.add(this);
        initViews();

    }

    private void initViews() {
        initPopu();//提示框
        // 点击GridView时出现背景色设置为透明
        mGridView= (GridView) findViewById(R.id.grid_view);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        mGridView.setAdapter(adapter);
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
    }

    private void initPopu() {
        pop = new PopupWindow(Information_DetailsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Information_DetailsActivity.this,
                        AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in,//activity显示的动画
                        R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        //mGridView.setAdapter(adapter);
    }
    protected void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }
    }

    @OnClick({R.id.arrow_left, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;

            case R.id.right_tv://提交
                for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                    BaseActivity.fileList.add(new File(Bimp.tempSelectBitmap.get(i).getImagePath()));
                }
                doAn();

                present();
                break;

        }
    }

    private void doAn() {
        List<File> a = BaseActivity.fileList;
        for (int i=0;i<a.size();i++){
            File b = a.get(i);
            System.out.println("b="+b);
        }

    }

    private void present() {
        String url= UrlUtils.INFORMATION;
        OkHttpUtils.postString().url(url).content(getUserInfo()).
                build().
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("咨询上传失败"+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("咨询上传成功"+response);
                        Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent();
                        // 返回intent
                        setResult(RESULT_OK, i);
                        finish();

                    }
                });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < BaseActivity.activityList.size(); i++) {
                if (null != BaseActivity.activityList.get(i)) {
                    BaseActivity.activityList.get(i).finish();
                }
            }
            System.exit(0);
        }
        return true;
    }

    public String getUserInfo() {
        problem=problem1.getText().toString();
        help=help1.getText().toString();
        System.out.println("problem="+problem);
        System.out.println("help="+help);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",id);
            jsonObject.put("problem",problem);
            jsonObject.put("getHelp",help);
            jsonObject.put("helpPic","");
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    public class GridAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private boolean shape;

        public GridAdapter(Information_DetailsActivity information_detailsActivity) {
            inflater = LayoutInflater.from(information_detailsActivity);
        }


        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public void update() {
            loading();
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        private void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }

        @Override
        public int getCount() {
            if(Bimp.tempSelectBitmap.size() == 9){
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid_item,parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.grid_item_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position ==Bimp.tempSelectBitmap.size()) {

                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ddddddd", "----------");
                    if (position == Bimp.tempSelectBitmap.size()) {
                        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                                Information_DetailsActivity.this, R.anim.activity_translate_in));
                        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                    } else {
                        Intent intent = new Intent(Information_DetailsActivity.this,
                                GalleryActivity.class);
                        intent.putExtra("ID", position);
                        startActivity(intent);
                    }
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }
}