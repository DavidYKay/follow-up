package com.followupapp.reminders;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessageAwaitingReply extends Preference {
	private LayoutInflater mInflater;

	public MessageAwaitingReply(Context context) {
		super(context);
	}

	public MessageAwaitingReply(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MessageAwaitingReply(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		return super.onCreateView(parent);
//		View view = mInflater.inflate(R.layout.message_awaiting_reply, parent, false);
//	    return view;
	}
}
