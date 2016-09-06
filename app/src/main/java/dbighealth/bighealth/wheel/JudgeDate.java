package dbighealth.bighealth.wheel;

import java.text.SimpleDateFormat;

import android.widget.TextView;

public class JudgeDate {


	public static  boolean isDate(String str_input,String rDateFormat){
		if (!isNull(str_input)) {
	         SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
	         formatter.setLenient(false);
	         try {
	             formatter.format(formatter.parse(str_input));
	         } catch (Exception e) {
	             return false;
	         }
	         return true;
	     }
		return false;
	}
	public static boolean isNull(String str){
		if(str==null)
			return true;
		else
			return false;
	}
	public static boolean isDate(TextView time, String rDateFormat) {
		// TODO Auto-generated method stub
		return false;
	}
}