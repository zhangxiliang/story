package com.wole.story.presenter;

import org.json.JSONObject;

import com.wole.story.framework.TaskCallBack;

public abstract class  BasePresenter implements TaskCallBack{

	

	@Override
	public abstract void onSuccess(String text);

	@Override
	public void onError(Exception result) {
		
	}

}
