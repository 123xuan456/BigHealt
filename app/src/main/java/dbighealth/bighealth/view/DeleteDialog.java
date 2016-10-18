package dbighealth.bighealth.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dbighealth.bighealth.R;

/**
 * Created by de on 2016/10/14.
 */
public class DeleteDialog extends Dialog{
    Context context;
    public DeleteDialog(Context context) {
        super(context);
    }
    public DeleteDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_delete);
        Button bt= (Button) findViewById(R.id.button6);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        bt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String addressId = String.valueOf(position);
//                String u= UrlUtils.DELETE_MANAGESITE+addressId;
//                OkHttpUtils.post().
//                        url(u).
//                        build().
//                        execute(new StringCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//                                System.out.println("删除地址失败="+e.toString());
//                            }
//
//                            @Override
//                            public void onResponse(String response, int id) {
//                                System.out.println("删除地址="+response);
//                                Intent i=new Intent("android.intent.action.SET");
//                                LocalBroadcastManager.getInstance(context).sendBroadcast(i);
//
//                            }
//                        });
//            }
//        });
    }
}
