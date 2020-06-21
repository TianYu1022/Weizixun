package com.tianyu.weizixun.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tianyu.weizixun.R;
import com.tianyu.weizixun.ui.activity.MainActivity;
import com.tianyu.weizixun.ui.activity.SmartActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * @date：2020/6/16
 * @describe：极光推送，自定义推送
 *      {"data":"2","msg":"测试使用的推送","url":"https://cdn.jsdelivr.net/gh/TianYu1022/image/imgs/2.png"}
 * @author：TianYu
 */
public class WeizixunReceiver extends BroadcastReceiver {
    private static String TAG = "WeizixunReceiver";
    private Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        //JPush接收到的信息，自定义发送了一个带有图片的json串
        //{"data":"2","msg":"测试使用的推送","url":"https://cdn.jsdelivr.net/gh/TianYu1022/image/imgs/2.png"}
        String json = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        if (json == null) {
            return;
        }
        sendNofication(context, json);
    }

    private void sendNofication(Context context, String json) {
        //通知管理器
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            //源生解析发送来的json字符串
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            String msg = jsonObject.getString("msg");
            String url = jsonObject.getString("url");

            if (data.equals("1")) {
                intent = new Intent(context, MainActivity.class);
            } else {
                intent = new Intent(context, SmartActivity.class);
                intent.putExtra("url", url);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            String channelId = "1";
            String channelName = "Weizixun";

            //设置渠道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                //开启指示灯，如果有的话
                channel.enableLights(true);
                //设置指示灯颜色
                channel.setLightColor(Color.RED);
                //检测是否显示角标
                channel.setShowBadge(true);
                nm.createNotificationChannel(channel);
            }

            Notification notification = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_fab_calendar)//设置小图
                    .setTicker("通知")//设置标题
                    .setContentText(msg)//设置内容
                    .setDefaults(Notification.DEFAULT_ALL)//设置提示效果
                    .setContentIntent(pendingIntent)//设置延时意图
                    .setAutoCancel(true)//设置点击自动消失
                    .build();
            
            nm.notify(200, notification);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
