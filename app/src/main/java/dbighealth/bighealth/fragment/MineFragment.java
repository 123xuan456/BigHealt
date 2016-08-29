package dbighealth.bighealth.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * 我的页面
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    View ra;
    private Button login;//点击登录按钮

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ra = inflater.inflate(R.layout.fragment_mine, container, false);
        TextView tvTab= (TextView) ra.findViewById(R.id.tvTab);
        tvTab.setText("我的");

        setView();

        return ra;
    }

    private void setView() {
        login=(Button)ra.findViewById(R.id.login);
       login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Intent i=new Intent(getContext(),LoginActivity.class);
                startActivity(i);
                break;


        }
    }
}
