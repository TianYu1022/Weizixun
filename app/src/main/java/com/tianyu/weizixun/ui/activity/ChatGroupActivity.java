package com.tianyu.weizixun.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.adapter.EMMessageAdapter;
import com.tianyu.weizixun.common.Constants;
import com.tianyu.weizixun.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatGroupActivity extends AppCompatActivity {

    @BindView(R.id.btn_create_group)
    Button btnCreateGroup;
    @BindView(R.id.btn_join_group)
    Button btnJoinGroup;
    @BindView(R.id.btn_send_content)
    Button btnSendContent;
    @BindView(R.id.et_group_content)
    EditText etGroupContent;
    @BindView(R.id.et_group_id)
    EditText etGroupId;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    private String groupId;
    private String curName;
    private ArrayList<EMMessage> list;
    private EMMessageAdapter adapter;
    private EMMessageListener mMsgListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);
        ButterKnife.bind(this);
        initView();
        intReceiver();
    }

    @OnClick({R.id.btn_create_group, R.id.btn_join_group, R.id.btn_send_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_group:
                createGroup();
                break;
            case R.id.btn_join_group:
                joinGroup();
                break;
            case R.id.btn_send_content:
                send();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void send() {
        String content = etGroupContent.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(ChatGroupActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String groupId = etGroupId.getText().toString().trim();
        if (groupId.isEmpty()) {
            Toast.makeText(ChatGroupActivity.this, "群号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, groupId);
                //如果是群聊，设置chattype，默认是单聊
                message.setChatType(EMMessage.ChatType.GroupChat);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add(message);
                        adapter.notifyDataSetChanged();
                        etGroupContent.setText("");
                    }
                });
            }
        }).start();
    }

    private void joinGroup() {
        //加入群先获取群ID
        String groupId = etGroupId.getText().toString().trim();
        if (groupId.isEmpty()) {
            Toast.makeText(this, "群ID不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                    //EMClient.getInstance().groupManager().applyJoinToGroup(groupid, "求加入");//需异步处理
                    //如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                    EMClient.getInstance().groupManager().joinGroup(groupId);//需异步处理
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatGroupActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatGroupActivity.this, "加入失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 创建群组
     * groupName 群组名称
     * desc 群组简介
     * allMembers 群组初始成员，如果只有自己传空数组即可
     * reason 邀请成员加入的reason
     * option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see { EMGroupStyle}
     * option.inviteNeedConfirm表示邀请对方进群是否需要对方同意，默认是需要用户同意才能加群的。
     * option.extField创建群时可以为群组设定扩展字段，方便个性化订制。
     *
     * @return 创建好的group
     * @throws HyphenateException
     */
    private void createGroup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //EMGroupStylePrivateOnlyOwnerInvite——私有群，只有群主可以邀请人；
                //EMGroupStylePrivateMemberCanInvite——私有群，群成员也能邀请人进群；
                //EMGroupStylePublicJoinNeedApproval——公开群，加入此群除了群主邀请，只能通过申请加入此群；
                //EMGroupStylePublicOpenJoin ——公开群，任何人都能加入此群。
                EMGroupOptions option = new EMGroupOptions();
                option.maxUsers = 200;
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                try {
                    //群名  描述  群成员：为了演示默认添加好a和b  创建的原因
                    //得到一个群
                    EMGroup group = EMClient.getInstance().groupManager().createGroup("田宇的群", "聊天室", new String[]{"a", "b"}, "为了演示群聊", option);
                    //通过这个群对象获取对应的群ID
                    groupId = group.getGroupId();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //把获取的群ID放进EditText中
                            etGroupId.setText(groupId);
                            Toast.makeText(ChatGroupActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChatGroupActivity.this, "创建失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void initView() {
        //发送端姓名
        curName = (String) SharedPreferencesUtil.getParam(this, Constants.NAME, "a");
        //创建集合存放信息
        list = new ArrayList<>();
        //布局管理器
        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        //创建适配器  聊天的
        adapter = new EMMessageAdapter(list, this, "", curName);
        //设置适配器
        rvGroup.setAdapter(adapter);
    }

    /**
     * 通过注册消息监听来接收消息。
     */
    private void intReceiver() {

        mMsgListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(final List<EMMessage> messages) {
                //收到消息,任何消息都可以接受到的,需要过滤,只处理这个群的消息
                if (messages != null && messages.size() > 0) {
                    final ArrayList<EMMessage> arrayList = new ArrayList<>();
                    for (int i = 0; i < messages.size(); i++) {
                        EMMessage emMessage = messages.get(i);
                        Log.e("TAG", "run: " + emMessage.toString());
                        String to = emMessage.getTo();
                        //区分是否是这个群的消息
//                        if (!TextUtils.isEmpty(mGroupId) && mGroupId.equals(to)) {
                        arrayList.add(emMessage);
//                        }
                    }

                    //子线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //添加到消息集合中
                            list.addAll(arrayList);
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
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
        EMClient.getInstance().chatManager().addMessageListener(mMsgListener);
    }

    /**
     * 记得在不需要的时候移除listener，如在activity的onDestroy()时
     */
    @Override
    protected void
    onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mMsgListener);
    }
}