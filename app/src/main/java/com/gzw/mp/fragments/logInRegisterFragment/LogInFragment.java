package com.gzw.mp.fragments.logInRegisterFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzw.mp.MyApplication;
import com.gzw.mp.R;
import com.gzw.mp.activities.MainActivity;
import com.gzw.mp.bean.LogInBean;
import com.gzw.mp.bean.User;
import com.gzw.mp.callBack.CallString;
import com.gzw.mp.utils.JsonParser;
import com.gzw.mp.utils.LogInDialog;
import com.gzw.mp.utils.NetUtil;
import com.gzw.mp.utils.SharedPreferenceUtil;
import com.gzw.mp.utils.UIHelper;

/**
 * Created by Mr.Wang on 2015/11/13 15:18
 */
public class LogInFragment extends Fragment {

    private Handler mainHandler;

    public LogInFragment(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        initView(view);
        editTextTextListener(view);
        return view;
    }

    private void initView(View view) {
        TextView tv_toRegister = (TextView) view.findViewById(R.id.log_in_to_regiser);
        tv_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.arg1 = 1;
                mainHandler.sendMessage(msg);
            }
        });
    }

    /*
     *接收消息显示对话框
    */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Log.i("=handler=", msg.obj.toString() + "==");
                LogInDialog.errorDialog(getActivity(), msg.obj.toString());
            } else if (msg.what == 1) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                User user = (User) msg.obj;
                intent.putExtra("user", user);
                intent.putExtra("activity", "LogInRegisterActivity");
                startActivity(intent);
            }
        }
    };

    //输入框内容变化的监听---下一步按钮状态的改变
    private void editTextTextListener(View view) {
        final EditText et_phone = (EditText) view.findViewById(R.id.login_et_phone);
        final EditText et_pwd = (EditText) view.findViewById(R.id.lonin_et_pwd);
        //登陆按钮
        final Button bt_load = (Button) view.findViewById(R.id.bt_login_load);
        //输入框内容变化的监听---下一步按钮状态的改变
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = et_pwd.getText().toString().trim();
                if (s.length() > 5 && pwd.length() > 5) {
                    bt_load.setEnabled(true);
                } else {
                    bt_load.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("=onTextChanged=", "=count=" + count + "==");
                String phone = et_phone.getText().toString();
                if (phone.length() > 5 && s.length() > 5) {
                    //下一步--按钮变色
                    bt_load.setEnabled(true);
                } else {
                    bt_load.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //登陆按钮的监听
        //根据nextToDoFlag判断下一步要做什么
        //nextToDoFlag===0登陆=1注册第一步=2注册第二步==3注册第三步
        bt_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = et_phone.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                bt_load.setEnabled(false);
                if (!NetUtil.isNetWork()) {
                    bt_load.setEnabled(false);
                    et_pwd.setText("");
                }
                UIHelper.Login(phoneNumber, pwd, new CallString() {
                    @Override
                    public void getString(String data) {
                        loadResult(data);
                    }
                });
            }
        });
    }

    /*
    *nextToDoFlag == 0
    * 此时为登陆
     *接收消息显示对话框
     * 点击下一步按钮后  需要做什么
     */
    public void loadResult(String data) {
        Message msg = Message.obtain();
        Log.i("=nextToDo=", "====");
        //登陆不成功
        if (data.startsWith(": {\"access_token\"")) {
            JSONObject errorObject = JSON.parseObject(data);
            String errorReason = errorObject.getString("error");
            msg.what = 0;//标记登陆不成功
            msg.obj = errorReason;
        } //登陆成功
        else {
            Log.i("=handler=", "=: {\"access_token\"=");
            LogInBean logInBean = JsonParser.getLogInInfo(data);
            User user = logInBean.getUser();
            String id = user.getId();
            String access_token = logInBean.getAccess_token();
            SharedPreferenceUtil.writeString(getActivity(), "access_token", access_token);
            SharedPreferenceUtil.writeString(getActivity(), "id", id);
            //改变登陆标记
            MyApplication.loginFlag = true;
            SharedPreferenceUtil.writeString(getActivity(), "loginFlag", "false");
            msg.what = 1;//标记登陆不成功
            msg.obj = user;
            Log.i("=handler=", msg.obj.toString() + "==");
        }
        handler.sendMessage(msg);
    }
}
