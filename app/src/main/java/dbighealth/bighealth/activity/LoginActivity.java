package dbighealth.bighealth.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.LoginokBean;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

import static android.Manifest.permission.READ_CONTACTS;

/**
 *  登录页面
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> ,OnClickListener{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     *  dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Button button,email_sign_in_button,email_register_button;
    private static int LOGINID;
    private ImageView arrow_left,right_add;
    private TextView tvTab;


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);
        tvTab=(TextView)findViewById(R.id.tvTab);
        tvTab.setText("登录");
        right_add=(ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);

        mLoginFormView = findViewById(R.id.login_form);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        email_sign_in_button= (Button) findViewById(R.id.email_sign_in_button);//登录
        email_sign_in_button.setOnClickListener(this);
        email_register_button= (Button) findViewById(R.id.email_register_button);
        email_register_button.setOnClickListener(this);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * 判断用户输入的是否是有用的登录名和密码.
     */
    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_null_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 一个有效的手机号码
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            focusView = mEmailView;
            mEmailView.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }

        if (cancel) {
            // 　　有一个错误,不要尝试登录和第一个焦点　　
            //表单字段和一个错误。
            focusView.requestFocus();
        } else {
            //用户输入的规范正确，向服务器发送判断是否是正确用户
            BaseApplication.password=password;
            BaseApplication.regphone=email;
            String url= UrlUtils.LOGIN;
            OkHttpUtils.get().url(url).id(LOGINID)
                    .addParams("regphone",email)
                    .addParams("password",password)
                    .build().execute(MyStringLogin);
        }


    }
    public Context context;
    private LoginokBean log;
    StringCallback MyStringLogin =new StringCallback() {
        //失败
        @Override
        public void onError(Call call, Exception e, int id) {
            System.out.println("传递失败原因="+e);
        }
            //成功
        @Override
        public void onResponse(String response, int id) {
            System.out.println("传递成功="+response);
            Gson gson=new Gson();
            log=gson.fromJson(response, LoginokBean.class);
            if(log.getCode()==200){

                System.out.println("d登录返回接口："+response);
                String hint=log.getHint();
                String username=log.getUsername();
                String imgurl = log.getImgurl();
                String sex=log.getSex();
                String age=log.getAge();
                String id1=String.valueOf(log.getId());
                SharedPreferencesUtils.saveString(context,UrlUtils.LOGIN, log.getId()+"");//把id存储到了sp中
//                BaseApplication.userid=id1;//把id传到
                BaseApplication.sex=sex;//把性别传到
                BaseApplication.username=username;
                BaseApplication.photoPic = imgurl;
                BaseApplication.age = age;
                Toast.makeText(getApplicationContext(),hint,Toast.LENGTH_LONG).show();
                //登录成功之后发送一个广播
//                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
//                intent.putExtra("username",username);
//                intent.putExtra("photoUrl", imgurl);
//                System.out.println("过去！！username"+username);
//                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
               sendIntent(username,sex,imgurl,age);

                finish();
                return;
            }else if(log.getCode()==400){
                String hint=log.getHint();
                Toast.makeText(getApplicationContext(),hint,Toast.LENGTH_LONG).show();
                return;
            }
        }

        private void sendIntent(String username, String sex,String imgUrl,String age) {
            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
            intent.putExtra("username",username);
            intent.putExtra("photoUrl", imgUrl);
            System.out.println("过去！！username"+username);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

            Intent i = new Intent("android.intent.action.CART_SEX");
            i.putExtra("sex",sex);
            System.out.println("过去！！性别1"+sex);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
            Intent i1 = new Intent("android.intent.action.CART_YEAR");
            i1.putExtra("year",age);
            System.out.println("过去！！年龄1"+age);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i1);
        }
    };
    private boolean isEmailValid(String email) {
          /*  Pattern p = Pattern
                    .compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(email);
            System.out.println(m.matches() + "---");
            return m.matches();*/
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}";
        if (TextUtils.isEmpty(email)) return false;
        else return email.matches(telRegex);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }



    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button://忘记密码
                Intent i=new Intent(this,LostPasswordActivity.class);
                startActivity(i);
                break;
            case  R.id.arrow_left:
                finish();
                break;
            case  R.id.email_register_button://注册
                Intent i1=new Intent(this,RegisterActivity.class);
                startActivity(i1);
                finish();
                break;
            case  R.id.email_sign_in_button://登录
                attemptLogin();

                break;

        }
    }
}

