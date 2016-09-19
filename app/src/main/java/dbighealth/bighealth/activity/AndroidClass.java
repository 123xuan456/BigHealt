package dbighealth.bighealth.activity;



import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class AndroidClass {

	
	 public static AlarmManager getAlarmManager(Context context)
	 {
	      return (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
	 }
	 public static NotificationManager getNotificationManager(Context context)
	 {
		 return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	 }
	 public static  AlertDialog.Builder getListDialogBuilder(Context c,String[] s,String t,
			 OnClickListener o)
	 {
		 final String[] items =s;   
		 return  new AlertDialog.Builder(c)
         .setTitle(t)  
//         .setView(v)
		 .setItems(items,o);

	 }
	 public static  AlertDialog.Builder getListDialogBuilder(Context context)
	 {  
		 return  new AlertDialog.Builder(context);
	 }
	 
	
	 
	 

}
