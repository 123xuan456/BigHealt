package dbighealth.bighealth.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.PipedOutputStream;
import java.util.HashMap;
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


    private static HashMap<Integer,Boolean> isSelected;
    private static HashMap<Integer,Boolean> NO;
    private static HashMap<Integer,Boolean> REALY;
    private static HashMap<Integer,Boolean> OFEN;
    private static HashMap<Integer,Boolean> SOMETIME;
    private boolean flagCheck = false;
    private static HashMap<Integer,Integer> flag;
    public PhysicalAdapter(Context context,List<PhysicalBean.ContentBean.ResultBean> list) {
            this.context = context;
            this.list = list;
            isSelected = new HashMap<Integer, Boolean>();
            NO = new HashMap<Integer, Boolean>();
            REALY = new HashMap<Integer, Boolean>();
            OFEN = new HashMap<Integer, Boolean>();
            SOMETIME = new HashMap<Integer, Boolean>();
            flag = new HashMap<Integer, Integer>();
            for(int i=0; i<list.size();i++) {
                getIsSelected().put(i,false);
            }
        for(int i=0; i<list.size();i++) {
            NO.put(i,false);
        }
        for(int i=0; i<list.size();i++) {
            REALY.put(i,false);
        }
        for(int i=0; i<list.size();i++) {
          OFEN.put(i,false);
        }
        for(int i=0; i<list.size();i++) {
            SOMETIME.put(i,false);
        }

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

    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }
    public static HashMap<Integer,Boolean> getNo() {
        return NO;
    }
    public static HashMap<Integer,Boolean> getREALY() {
        return REALY;
    }
    public static HashMap<Integer,Boolean> getOFEN() {
        return OFEN;
    }
    public static HashMap<Integer,Boolean> getSOMETIME() {
        return SOMETIME;
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
        holder.radioGroup.setTag(position);
        holder.rbNo.setTag(position);
        if (holder.rbNo.equals("没有"))
        {
            holder.radioGroup.check(holder.rbNo.getId());
        }else if(holder.rbRarely.equals("很少")){
            holder.radioGroup.check(holder.rbRarely.getId());
        }else if(holder.rbSometimes.equals("有时")){
            holder.radioGroup.check(holder.rbRarely.getId());
        }else if(holder.rbOffen.equals("经常")){
            holder.radioGroup.check(holder.rbOffen.getId());
        } else
        {
            holder.radioGroup.clearCheck();
        }
        final RadioGroup radioGroup = holder.radioGroup;
      /*  holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                PhysicalBean.ContentBean.ResultBean resultBean = list.get((Integer) group.getTag());
                //病症号
                if(group==radioGroup){
                    switch (checkedId){
                        case R.id.btn_no:
                            resultBean.setSymptomId(list.get((Integer) group.getTag()).getSymptomId());
                            break;
                    }
                }
                Log.i("mhysa-->",list.get(position).getSymptomId()+"");
               // isSelected.put(position,isChecked);
              //  isSelected.put(position,flag.put(position,true));
                flag.put(position,checkedId);
                isSelected.put(position,true);
                int symptomId = list.get(position).getSymptomId();

            }
        });*/
        holder.radioGroup.setOnCheckedChangeListener(null);
        holder.rbNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.i("save",isChecked+""+"weizhi="+position);
                if(isChecked){
                    isSelected.put(position,isChecked);
                    NO.put(position,isChecked);
                    SOMETIME.put(position,false);
                    REALY.put(position,false);
                    OFEN.put(position,false);
                }
            }
        });
        holder.rbNo.setChecked(NO.get(position));

        holder.rbSometimes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    isSelected.put(position,isChecked);
                    SOMETIME.put(position,isChecked);
                    NO.put(position,false);
                    REALY.put(position,false);
                    OFEN.put(position,false);
                }
            }
        });
        holder.rbRarely.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    REALY.put(position,isChecked);

                    isSelected.put(position,isChecked);
                    SOMETIME.put(position,false);
                    NO.put(position,false);
                    OFEN.put(position,false);


                }

            }
        });

        holder.rbOffen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    OFEN.put(position,isChecked);
                    isSelected.put(position,isChecked);
                    SOMETIME.put(position,false);
                    NO.put(position,false);
                    REALY.put(position,false);

                }

            }
        });
        holder.rbSometimes.setChecked(SOMETIME.get(position));
        holder.rbRarely.setChecked(REALY.get(position));
        holder.rbOffen.setChecked(OFEN.get(position));

       /* if(flag.get(position)!=null){
            RadioButton rb = (RadioButton) holder.radioGroup.getChildAt(flag.get(position));
            if(rb!=null){
                rb.setChecked(isSelected.get(position));
            }

        }*/
/*
        holder.rbNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NO.put(position,isChecked);

            }
        });

        holder.rbSometimes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    SOMETIME.put(position,isChecked);
                }
            }
        });
        holder.rbRarely.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                REALY.put(position,isChecked);
            }
        });

        holder.rbOffen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                OFEN.put(position,isChecked);
            }
        });
        holder.rbNo.setChecked(NO.get(position));
        holder.rbSometimes.setChecked(SOMETIME.get(position));
        holder.rbRarely.setChecked(REALY.get(position));
        holder.rbOffen.setChecked(OFEN.get(position));*/
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
