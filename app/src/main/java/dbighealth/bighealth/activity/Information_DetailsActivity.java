package dbighealth.bighealth.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.R;
import utils.UploadUtil;
import utils.UploadUtil.OnUploadProcessListener;

/**
 * 资讯详情
 */
public class Information_DetailsActivity extends Activity implements View.OnClickListener,OnUploadProcessListener{

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
    EditText problem;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.help)
    EditText help;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.imageView22)
    ImageView imageView22;
    private static final String TAG = "uploadImage";

    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2;  //
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 5;
    /***
     * 这里的这个URL是我服务器的javaEE环境URL
     */
    private static String requestURL = "http://192.168.0.43:8080/qq/file!upload";

    private String picPath = null;
    private ProgressDialog progressDialog;
    private TextView uploadImageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information__details);
        ButterKnife.bind(this);
        tit.setVisibility(View.GONE);
        rightTv.setText("提交");
        progressDialog = new ProgressDialog(this);
        uploadImageResult = (TextView) findViewById(R.id.uploadImageResult);


    }

    @OnClick({R.id.arrow_left, R.id.tit, R.id.imageView22,R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.imageView22://图片
                Intent intent = new Intent(this,SelectPicActivity.class);
                startActivityForResult(intent, TO_SELECT_PHOTO);
                break;

            case R.id.right_tv://提交
                if(picPath!=null)
                {
                    handler.sendEmptyMessage(TO_UPLOAD_FILE);
                }else{
                    Toast.makeText(this, "上传的文件路径出错", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
        {
            picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
            Log.i(TAG, "最终选择的图片="+picPath);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            imageView22.setImageBitmap(bm);
            //startPhotoZoom(data.getData(), 150);

        }


    }

    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

    }

    /**
     * 上传服务器响应回调
     */
    public void onUploadDone(int responseCode, String message) {
        progressDialog.dismiss();
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    private void toUploadFile()
    {
        uploadImageResult.setText("正在上传中...");
        progressDialog.setMessage("正在上传文件...");
        progressDialog.show();
        String fileKey = "pic";
        UploadUtil uploadUtil = UploadUtil.getInstance();;
        uploadUtil.setOnUploadProcessListener((UploadUtil.OnUploadProcessListener) this);  //设置监听器监听上传状态

        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", "11111");
        uploadUtil.uploadFile( picPath,fileKey, requestURL,params);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    toUploadFile();
                    break;

                case UPLOAD_INIT_PROCESS:
                    progressBar.setMax(msg.arg1);
                    Toast.makeText(getApplicationContext(), "上传的文件！！！！", Toast.LENGTH_LONG).show();
                    break;
                case UPLOAD_IN_PROCESS:
                    progressBar.setProgress(msg.arg1);

                    Toast.makeText(getApplicationContext(), "上传的文件中！！！！", Toast.LENGTH_LONG).show();
                    break;
                case UPLOAD_FILE_DONE:
                    String result = "响应码："+msg.arg1+"\n响应信息："+msg.obj+"\n耗时："+ UploadUtil.getRequestTime()+"秒";
                    uploadImageResult.setText(result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        handler.sendMessage(msg );
    }

    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        handler.sendMessage(msg );
    }




}
