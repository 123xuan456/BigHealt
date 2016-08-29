package dbighealth.bighealth.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dbighealth.bighealth.R;

/**
 * A simple {@link Fragment} subclass.
 * https://git.oschina.net/qishaoxuan/new.git
 * 治疗页面
 */
public class TreatmentFragment extends Fragment {
    public TreatmentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout r= (RelativeLayout) inflater.inflate(R.layout.fragment_treatment,null);
        TextView tvTab= (TextView) r.findViewById(R.id.tvTab);
        tvTab.setText("治疗");

        return r;
    }


}
