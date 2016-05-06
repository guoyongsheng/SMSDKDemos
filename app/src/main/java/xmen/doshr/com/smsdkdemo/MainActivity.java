package xmen.doshr.com.smsdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 短信验证
 */
public class MainActivity extends Activity
{
    private static final String APP_KEY = "110c4f622d751";
    private static final String APP_SECRET = "95dc692460dc7a113c686c42568d51ee";
    private static final String TAG = "MainActivity";
    private MyEventHandler eventHandler = new MyEventHandler();
    private EditText editCode;//输入的验证码

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCode = (EditText) findViewById(R.id.edit_phone);

        initSDK();
    }

    //初始化smssdk
    private void initSDK()
    {
        SMSSDK.initSDK(this, APP_KEY, APP_SECRET);
        SMSSDK.registerEventHandler(eventHandler);
        SMSSDK.getSupportedCountries();
        //SMSSDK.getVerificationCode("86", "18255215889");
    }

    //点击事件
    public void click(View view)
    {
        if (view == null)
        {
            return;
        }

        int key = view.getId();
        switch (key)
        {
        /**
         * 获取验证码
         */
        case R.id.button_check:
            SMSSDK.submitVerificationCode("86", "18255215889", editCode.getText().toString());
            break;

        default:
            break;
        }
    }

    //内部类
    private final class MyEventHandler extends EventHandler
    {
        @Override
        public void afterEvent(int event, int result, Object data)
        {
            if (result == SMSSDK.RESULT_COMPLETE)
            {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
                {
                    //提交验证码成功
                    Log.i(TAG, " 验证码验证成功 ");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
                {
                    //获取验证码成功
                    Log.i(TAG, " 验证码获取成功 ");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES)
                {
                    //返回支持发送验证码的国家列表
                    Log.i(TAG, " 国家列表获取成功 " + data);
                }
            } else
            {
                Log.i(TAG, " data = " + data);
            }
        }
    }


    @Override
    protected void onDestroy()
    {
        SMSSDK.unregisterEventHandler(eventHandler);
        super.onDestroy();
    }
}
