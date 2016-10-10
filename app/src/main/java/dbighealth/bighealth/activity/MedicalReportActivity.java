package dbighealth.bighealth.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.GridAdapter2;
import dbighealth.bighealth.imageUtils.AlbumActivity;
import dbighealth.bighealth.imageUtils.AlbumActivity1;
import dbighealth.bighealth.imageUtils.Bimp;
import dbighealth.bighealth.imageUtils.Bimp1;
import dbighealth.bighealth.imageUtils.FileUtils;
import dbighealth.bighealth.imageUtils.GalleryActivity;
import dbighealth.bighealth.imageUtils.GalleryActivity1;
import dbighealth.bighealth.imageUtils.ImageItem;
import okhttp3.Call;
import utils.HttpPostUploadUtil;
import utils.UrlUtils;

/**
 * 体检报告
 */
public class MedicalReportActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.grid_view1)
    GridView gridView1;
    private PopupWindow pop;
    private LinearLayout ll_popup;
    private RelativeLayout parent;
    private static final int TAKE_PICTURE = 1;
    private String picPath;
    private GridAdapter2 adapter;

    private int COMMIT_PIC = 1;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_report);
        ButterKnife.bind(this);
        tit.setText("体检报告");
        rightTv.setText("提交");
        sp = getSharedPreferences("hasCommitReport", MODE_PRIVATE);
        edit = sp.edit();
        arrowLeft.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        initViews();
    }


    private void initViews() {
        initPopu();//提示框
        // 点击GridView时出现背景色设置为透明
        gridView1.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter2(this);
        gridView1.setAdapter(adapter);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp1
                        .tempSelectBitmap.size()) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(
                            MedicalReportActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(gridView1, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(MedicalReportActivity.this,
                            GalleryActivity1.class);
                    intent.putExtra("id", position);
                    startActivity(intent);
                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void initPopu() {
        pop = new PopupWindow(MedicalReportActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        parent = (RelativeLayout) view.findViewById(R.id.parent);
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
                Intent intent = new Intent(MedicalReportActivity.this,
                        AlbumActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in,
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

    protected void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        gridView1.setAdapter(adapter);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp1.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                    Log.i("mhysa","data是否为空"+data);
                    String fileName = String.valueOf(System.currentTimeMillis());
                    final Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);
                    final String path1 = getPath(null, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bm, null, null)));
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp1.tempSelectBitmap.add(takePhoto);
                    new Thread() {
                        @Override
                        public void run() {
                            Map<String, String> textMap = new HashMap<String, String>();
                            textMap.put("name", "testname");
                            Map<String, String> fileMap = new HashMap<String, String>();
                            fileMap.put("file", path1);
                            String getPicUrl = HttpPostUploadUtil.formUpload(UrlUtils.UPLOADPIC, textMap, fileMap);
                            Bimp1.imgList.add(getPicUrl);
                            Log.i("mhysa","返回的imgurl是"+getPicUrl);
                        }
                    }.start();
                }
                break;
        }
    }

    public String getPath(Cursor cursor, Uri uri) {
        if (cursor == null) {
            String str = uri.toString();

            if (str.contains("file:///")) {
                str = str.substring(7);
                System.out.println(str);
                return str;
            }
        }
        ContentResolver resolver = this.getContentResolver();
        String[] pojo = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, pojo, null, null, null);
        cursor = cursorLoader.loadInBackground();
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(pojo[0]));
        if (path != null && path.length() > 0) {
            picPath = path;
        }

        return picPath;

    }
    // 使用系统当前日期加以调整作为照片的名称
    @SuppressLint("SimpleDateFormat")
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public String getImgList(){
        String str ="";
        for(int i=0;i<Bimp1.imgList.size();i++){
            String s = Bimp1.imgList.get(i);
            if(i!=Bimp1.imgList.size()-1){
                str =str+ s+",";
            }else{
                str =str+s;
            }

        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", BaseApplication.userid);
            jsonObject.put("url",str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /**
             * 返回
             */
            case R.id.arrow_left:
                edit.putBoolean("commitreport",false);
                edit.commit();
                finish();
                break;
            /**
             * 提交
             */
            case R.id.right_tv:
                OkHttpUtils.postString()
                        .url(UrlUtils.UPLOADREPORT)
                        .content(getImgList())
                        .id(COMMIT_PIC)
                        .build()
                        .execute(MyStringCallBack);
                break;
        }
    }

    StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            if(id==COMMIT_PIC){
                Log.i("mhysa--->",e.toString());
                Toast.makeText(getApplicationContext(),"上传失败，请稍后重试！",Toast.LENGTH_SHORT).show();
            }

        }
        @Override
        public void onResponse(String response, int id) {

            if(id==COMMIT_PIC){
                Toast.makeText(getApplicationContext(),"上传成功！",Toast.LENGTH_SHORT).show();

                edit.putBoolean("commitreport",true);
                edit.commit();
                MedicalReportActivity.this.finish();
            }

        }
    };
}
