package utils;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;
/**
 * 封装判断网络是否可用
 * @author mhysa
 *
 */
public class JudgeIntenet {

	Context context;
	public JudgeIntenet(Context context) {
		this.context = context;
	}

	/**
	 * 网络可用就调用下一步需要进行的方法， 网络不可用则需设置
	 */
	public void initIntener() {
		// 判断网络是否可用
		if (true == isOpenNetwork()) {

			Log.i("mhysa","网络可用");
		} else {
			//Toast.makeText(context.getApplicationContext(), "网络状态不可用", 0).show();
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			//屏蔽返回和触摸
			builder.setCancelable(false);
			builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");

			builder.setPositiveButton("是",
					new DialogInterface.OnClickListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = null;

					try {
						String sdkVersion = android.os.Build.VERSION.SDK;
						if (Integer.valueOf(sdkVersion) > 10) {
							intent = new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						} else {
							intent = new Intent();
							ComponentName comp = new ComponentName(
									"com.android.settings",
									"com.android.settings.WirelessSettings");
							intent.setComponent(comp);
							intent.setAction("android.intent.action.VIEW");
						}
						context.startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			})
			.setNegativeButton("否",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int which) {
					dialog.cancel();
					// finish();//因为网络不可用的状态，也是不让自己的程序结束运行，
					// 这是根据个人需要设置。
					Toast.makeText(context,
							"", Toast.LENGTH_SHORT)
							.show();
					System.exit(0);// 这里是没有网络的时候，又不需要手动设置，则显示出来的一个静态页面，根据个人需要。
				}
			});
		
			builder.create().show();

		}
	}

	/**
	 * 对网络连接状态进行判断
	 * 
	 * @return true, 可用； false， 不可用
	 */
	private boolean isOpenNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}
}
