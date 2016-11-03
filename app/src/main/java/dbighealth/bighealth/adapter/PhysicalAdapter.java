package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.PhysicalBean;

/**
 * Created by mhysa on 2016/8/31.
 */
public class PhysicalAdapter extends BaseAdapter implements RadioGroup.OnCheckedChangeListener{

    private LayoutInflater mInflater = null;
    private Context context;
    private List  <PhysicalBean.ContentBean.ResultBean> list;
    private RadioGroup rg_selector;
    private RadioGroup rg_selector1;
    private RadioButton btn_no;
    private RadioButton btn_rarely;
    private RadioButton btn_sometimes;
    private RadioButton often;
    private TextView problem;


    public PhysicalAdapter(Context context,List<PhysicalBean.ContentBean.ResultBean> list) {
            this.context = context;
            this.list = list;
            this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.constitution_item, null);
            holder.problemDescribe = (TextView) convertView.findViewById(R.id.problem);
            holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.rg_selector);
            holder.rbNo = (RadioButton) convertView.findViewById(R.id.btn_no);
            holder.rbRarely = (RadioButton) convertView.findViewById(R.id.btn_rarely);
            holder.rbSometimes = (RadioButton) convertView.findViewById(R.id.btn_sometimes);
            holder.rbOffen = (RadioButton) convertView.findViewById(R.id.often);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag*/
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.problemDescribe.setText(list.get(position).getSymptom());

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //病症号
                int symptomId = list.get(position).getSymptomId();

            }
        });

        return convertView;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


    }
    public class ViewHolder{
        TextView problemDescribe;
        RadioGroup radioGroup;
        RadioButton rbNo;
        RadioButton rbSometimes;
        RadioButton rbRarely;
        RadioButton rbOffen;
    }
}
