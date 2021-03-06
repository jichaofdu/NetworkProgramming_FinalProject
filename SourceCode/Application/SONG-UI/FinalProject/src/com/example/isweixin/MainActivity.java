package com.example.isweixin;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import client.ChatMessage;
import client.Client;
import client.Tweet;
import client.User;

public class MainActivity extends Activity implements OnViewChangeListener, OnClickListener{
	private MyScrollLayout mScrollLayout;	
	private LinearLayout[] mImageViews;	
	private int mViewCount;	
	private int mCurSel;
	private ImageView set;
	private ImageView add;
	private TextView chatTextView;
	private TextView discoveryTextView;
	private TextView contactListTextView;
	private ListView paintingListView;
	private ListView friendListView;
	private ListView contactListView;
	SelectPicPopupWindow menuInfoWindow;
	SelectAddPopupWindow menuAddTweetOrFriend;
	private Button sendButton;
	private EditText massageInputEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		chatTextView = (TextView)findViewById(R.id.liaotian);
		discoveryTextView = (TextView)findViewById(R.id.faxian);
		contactListTextView = (TextView)findViewById(R.id.tongxunlu);
		paintingListView = (ListView)findViewById(R.id.listView1);
		friendListView = (ListView)findViewById(R.id.listView2);
		contactListView = (ListView)findViewById(R.id.listView3);
		PaintingAdapter ha = new PaintingAdapter(this,getPainting());
		paintingListView.setAdapter(ha);
		paintingListView.setCacheColorHint(0);
		FriendAdapter py = new FriendAdapter(this,getFriends());
		friendListView.setAdapter(py);
		friendListView.setCacheColorHint(0);

		ContactAdapter hc = new ContactAdapter(this,getContact());
		contactListView.setAdapter(hc);
		contactListView.setCacheColorHint(0);

    	mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout); 	
    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lllayout);   	
    	mViewCount = mScrollLayout.getChildCount();
    	mImageViews = new LinearLayout[mViewCount];

		for(int i = 0; i < mViewCount; i++)    	{
    		mImageViews[i] = (LinearLayout) linearLayout.getChildAt(i);
    		mImageViews[i].setEnabled(true);
    		mImageViews[i].setOnClickListener(this);
    		mImageViews[i].setTag(i);
    	}    	
    	mCurSel = 0;
    	mImageViews[mCurSel].setEnabled(false);    	
    	mScrollLayout.SetOnViewChangeListener(this);
    	set = (ImageView)findViewById(R.id.set);
    	add = (ImageView)findViewById(R.id.add);
    	set.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				uploadSetting(MainActivity.this);
			}
		});
    	add.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
    			uploadAdd(MainActivity.this);
    		}
    	});
    	sendButton = (Button)findViewById(R.id.sendButton);
		massageInputEditText = (EditText)findViewById(R.id.messageInput);
		sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String inputMsg = massageInputEditText.getText().toString();
				Client.getClient().sendChatMessage(inputMsg);
				massageInputEditText.setText("");
				Intent intent = new Intent(MainActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
    }

	private ArrayList<Painting> getPainting(){
		ArrayList<Painting> hhList = new ArrayList<Painting>();
		Vector<ChatMessage> messageList = Client.getClient().getChatMessage();
		int count = messageList.size();
		//----------------------------Loop Unit---------------------------------------
		for(int i = 0;i < count;i++){
			Painting h0 = new Painting();
			h0.setName(messageList.get(i).getUsername());
			h0.setLastContent(messageList.get(i).getContent());
			h0.setLastTime(messageList.get(i).getTime());
			hhList.add(h0);
		}
		Painting h0 = new Painting();
		h0.setName("");
		h0.setLastContent("");
		h0.setLastTime("");
		hhList.add(h0);
		//-------------------------------------------------------------------
		return hhList;
	}

	private ArrayList<Friend> getFriends(){
		ArrayList<Friend> hhList = new ArrayList<>();
		Vector<Tweet> tweetList = Client.getClient().getTweets(Client.getClient().getLocalUser().getId());
		int count = tweetList.size();
		//--------------------------Loop Unit---------------------------------------
		for(int i = 0;i < count;i++){
			Friend h1 = new Friend();
			h1.setTxPath(R.drawable.icon+"");
			h1.setName(tweetList.get(i).getWriterName());
			h1.setLastContent(tweetList.get(i).getContent());
			h1.setLastTime(tweetList.get(i).getPostTime());
			hhList.add(h1);
		}
		//-------------------------------------------------------------------
		return hhList;
	} 
	private ArrayList<ContactP> getContact(){
		ArrayList<ContactP> hcList = new ArrayList<>();
		Vector<User> friendList = Client.getClient().getFriendList(Client.getClient().getLocalUser().getId());
		int count = friendList.size();
		//----------------------------Loop Unit---------------------------------------
		for(int i = 0;i < count;i++){
			ContactP c0 = new ContactP();
			c0.setTxPath(R.drawable.icon+"");
			c0.id = friendList.get(i).getId();
			c0.setName(friendList.get(i).getNickname());
			hcList.add(c0);
		}
		//-------------------------------------------------------------------
		return hcList;
	}
	
	 public void uploadSetting(final Activity context){
		 menuInfoWindow = new SelectPicPopupWindow(MainActivity.this, itemsOnClick);
		 menuInfoWindow.showAtLocation(MainActivity.this.findViewById(R.id.set), Gravity.TOP|Gravity.RIGHT, 10, 230);
		 menuInfoWindow.changeInfoButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 System.out.println("Click Change Info");
				 Intent intent = new Intent(MainActivity.this,ModifyUserInfo.class);
				 startActivity(intent);
				 menuInfoWindow.dismiss();
				 menuInfoWindow = null;
			 }
		 });
		 menuInfoWindow.logoutButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 System.out.println("Click Change Logout");
				 Client client = Client.getClient();
				 client.logout();
				 Intent intent = new Intent(MainActivity.this,Login.class);
				 startActivity(intent);
				 menuInfoWindow.dismiss();
				 menuInfoWindow = null;
				 finish();
			 }
		 });
		 menuInfoWindow.refreshButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 System.out.println("Click refresh");
				 
				 Intent intent = new Intent(MainActivity.this,MainActivity.class);
				 startActivity(intent);
			 }
		 });
		 
		
	 }

	 public void uploadAdd(final Activity context){
		 menuAddTweetOrFriend = new SelectAddPopupWindow(MainActivity.this, itemsOnClick2);
		 menuAddTweetOrFriend.showAtLocation(MainActivity.this.findViewById(R.id.add), Gravity.TOP|Gravity.RIGHT, 10, 230);
		 menuAddTweetOrFriend.addFriendButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 System.out.println("Click Add Friend");
				 Intent intent = new Intent(MainActivity.this,AddFriend.class);
				 startActivity(intent);
				 menuAddTweetOrFriend.dismiss();
				 menuAddTweetOrFriend = null;
			 }
		 });
		 menuAddTweetOrFriend.addTweetButton.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 System.out.println("Click Add Tweet");
				 Intent intent = new Intent(MainActivity.this,AddTweet.class);
				 startActivity(intent);
				 menuAddTweetOrFriend.dismiss();
				 menuAddTweetOrFriend = null;
			 }
		 });
	 }

	private OnClickListener  itemsOnClick = new OnClickListener(){
		public void onClick(View v) {
				menuInfoWindow.dismiss();
			}
	};

	private OnClickListener  itemsOnClick2 = new OnClickListener(){
		public void onClick(View v) {
				menuAddTweetOrFriend.dismiss();
	    	}
	};
	    
	private void setCurPoint(int index) {
    	if (index < 0 || index > mViewCount - 1 || mCurSel == index){
    		return ;
    	}    	
    	mImageViews[mCurSel].setEnabled(true);
    	mImageViews[index].setEnabled(false);    	
    	mCurSel = index;
    	if(index == 0){
			chatTextView.setTextColor(0xff228B22);
			discoveryTextView.setTextColor(Color.BLACK);
			contactListTextView.setTextColor(Color.BLACK);
    	}else if(index == 1){
			chatTextView.setTextColor(Color.BLACK);
			discoveryTextView.setTextColor(0xff228B22);
			contactListTextView.setTextColor(Color.BLACK);
    	}else{
			chatTextView.setTextColor(Color.BLACK);
			discoveryTextView.setTextColor(Color.BLACK);
			contactListTextView.setTextColor(0xff228B22);
    	}
    }

    @Override
	public void OnViewChange(int view) {
		// TODO Auto-generated method stub
		setCurPoint(view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int pos = (Integer)(v.getTag());
		setCurPoint(pos);
		mScrollLayout.snapToScreen(pos);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if ((keyCode == KeyEvent.KEYCODE_MENU)) {       
	            return true;
	        }
		return super.onKeyDown(keyCode, event);
	}

}
