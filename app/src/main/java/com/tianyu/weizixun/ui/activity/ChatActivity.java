package com.tianyu.weizixun.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.EMMessageAdapter;
import com.tianyu.weizixun.base.BaseActivity;
import com.tianyu.weizixun.common.Constants;
import com.tianyu.weizixun.utils.AudioUtil;
import com.tianyu.weizixun.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_record)
    Button btnRecord;
    @BindView(R.id.btn_send_audio)
    Button btnSendAudio;
    @BindView(R.id.btn_send_location)
    Button btnSendLocation;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    private String toName;
    private String curName;
    private ArrayList<EMMessage> list;
    private EMMessageAdapter adapter;
    private EMMessageListener msgListener;
    private String mPath;
    private long mTime;
    private LocationClient mLocationClient;
    private BaiduMap baiduMap;
    private MapView mapView;
    private BDLocation mLocation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @OnClick({R.id.btn_send, R.id.btn_record, R.id.btn_send_audio, R.id.btn_send_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                send();
                break;
            case R.id.btn_record:
                record();
                break;
            case R.id.btn_send_audio:
                sendAudio();
                break;
            case R.id.btn_send_location:
                sendLocation();
                break;
        }
    }

    /**
     * 发送位置信息
     */
    private void sendLocation() {
        new Thread(new Runnable() {

            private double longitude;
            private double latitude;

            @Override
            public void run() {
                if (mLocation != null) {
                    latitude = mLocation.getLatitude();
                    longitude = mLocation.getLongitude();
                } else {
                    openGPS();
                }
                //latitude为纬度，longitude为经度，locationAddress为具体位置内容
                EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, "位置描述", toName);
                //如果是群聊，设置chattype，默认是单聊
                EMClient.getInstance().chatManager().sendMessage(message);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add(message);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    /**
     * 初始化定位
     * 通过LocationClient发起定位
     * 在ondestroy 关闭 mLocationClient.stop
     */
    private void initLocation() {
        mapView = new MapView(this);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
    }

    /**
     * 构造地图数据
     * 我们通过继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，并将其传给MapView。
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            Log.e("TAG", "onReceiveLocation: " + location);
            //用户位置信息
            mLocation = location;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
        }
    }

    /**
     * 发送录音
     */
    private void sendAudio() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //voiceUri为语音文件本地资源标志符，length为录音时间(秒)
                EMMessage message = EMMessage.createVoiceSendMessage(mPath, (int) mTime, toName);
                EMClient.getInstance().chatManager().sendMessage(message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //录音添加到消息中
                        list.add(message);
                        //刷新适配器
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    /**
     * 录音
     */
    private void record() {
        //是否正在录音
        boolean isRecording = AudioUtil.isRecording;
        if (isRecording) {
            btnRecord.setText("开始录音");
            AudioUtil.stopRecord();
        } else {
            btnRecord.setText("停止录音");
            AudioUtil.startRecord(new AudioUtil.ResultCallBack() {
                @Override
                public void onSuccess(String path, long time) {
                    mPath = path;
                    mTime = time;
                }

                @Override
                public void onFail(String msg) {
                    Log.e("TAG", msg);
                }
            });
        }
    }

    /**
     * 发送消息
     */
    private void send() {
        String content = etContent.getText().toString();

        if (content.isEmpty()) {
            Toast.makeText(ChatActivity.this, "请输入消息", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, toName);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add(message);
                        adapter.notifyDataSetChanged();
                        etContent.setText("");
                    }
                });
            }
        }).start();
    }

    @Override
    protected void initView() {
        super.initView();
        //获取到的name是被接收信息的user  ：  toName  问题：发送消息的位置不对
        toName = getIntent().getStringExtra(Constants.NAME);
        curName = (String) SharedPreferencesUtil.getParam(this, Constants.NAME, "a");
        //设置聊天标题
        tvTitle.setText(curName + "正在和" + toName + "聊天中...");
        //EMMessage 发送的消息
        list = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EMMessageAdapter(list, this, toName, curName);
        rv.setAdapter(adapter);
        //点击列表的消息判断点击的是那种消息  播放语言
        adapter.setOnItemClick(new EMMessageAdapter.OnItemClick() {
            @Override
            public void onItemClick(String localUrl, EMMessageBody body) {
                if (body instanceof EMVoiceMessageBody) {
                    //如果是音乐就播放音乐
                    playAudio(localUrl);
                } else if (body instanceof EMLocationMessageBody) {
                    Intent intent = new Intent(ChatActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            }
        });

        initLocation();
    }

    /**
     * 历史消息
     */
    @Override
    protected void initListener() {
        super.initListener();
        //通过注册消息监听来接收消息。
        initReceiver();
        //保存历史消息
        initHistory();
    }

    /**
     * 播放语言
     *
     * @param localUrl
     */
    private void playAudio(String localUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            boolean playing = mediaPlayer.isPlaying();
            if (playing) {
                mediaPlayer.pause();
            }
            if (TextUtils.isEmpty(localUrl)) {
                return;
            }
            mediaPlayer.setDataSource(localUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initHistory() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toName);
        //获取此会话的所有消息
        if (conversation == null) {
            return;
        }
        List<EMMessage> messages = conversation.getAllMessages();
        list.addAll(messages);
        adapter.notifyDataSetChanged();
    }

    private void initReceiver() {
        msgListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                list.addAll(messages);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    /**
     * 记得在不需要的时候移除listener，如在activity的onDestroy()时
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    /**
     * 打开GPS服务
     */
    private void openGPS() {
        new AlertDialog.Builder(ChatActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.information)
                .setMessage("没有开启定位")
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 887);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    /**
     * 打开GPS回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 887:
                //开启GPS，重新添加地理监听
                initLocation();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}