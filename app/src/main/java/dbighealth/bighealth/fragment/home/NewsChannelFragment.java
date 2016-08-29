package dbighealth.bighealth.fragment.home;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RadioGroup;
import android.widget.TextView;

import dbighealth.bighealth.R;

/**
 * A simple {@link Fragment} subclass.
 * 首页——推荐页面
 */
public class NewsChannelFragment extends Fragment {

    private String channelName;
    private String url;

    @Override
    public void setArguments(Bundle args) {
        channelName=args.getString("cname");
        url=args.getString("url");
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    private TextView view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view==null){
            view=new TextView(super.getActivity());
            view.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT));
            view.setText("推荐");

        }
        ViewGroup parent=(ViewGroup)view.getParent();
        if(parent!=null){
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


}
