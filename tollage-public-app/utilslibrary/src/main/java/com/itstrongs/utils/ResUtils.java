package com.itstrongs.utils;

import android.content.Context;

public class ResUtils {

	/**
	 * 通过资源id获取资源
	 * @param context 上下文环境
	 * @param name 资源ID
	 * @param defType 资源类型
	 * @return
	 */
	public static int getResById(Context context, String name, String defType){
		return context.getResources().getIdentifier(name, defType.toString(), context.getPackageName());
	}
}
