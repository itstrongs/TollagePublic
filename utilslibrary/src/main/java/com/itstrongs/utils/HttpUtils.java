package com.itstrongs.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

	private static OkHttpClient mClient = new OkHttpClient();

	public static void doLoadImage(final String url, final ImageCallback callback) {
		new AsyncTask<String ,Integer, String>() {
			@Override
			protected String doInBackground(String... params) {
				Request request = new Request.Builder().url(url).build();
				mClient.newCall(request).enqueue(new Callback() {
					@Override
					public void onResponse(Call arg0, Response response) throws IOException {
						final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
						callback.onSuccess(bitmap);
					}

					@Override
					public void onFailure(Call callback, IOException arg1) {
						LogUtils.d("图片加载失败");
					}
				});
				return null;
			}
		}.execute(url);
	}

	//okhttp 下载
	public static void doDownload(final File file, String url, final HttpsCallback callback) {
		new AsyncTask<String ,Integer, String>() {
			@Override
			protected String doInBackground(String... params) {
				LogUtils.d("下载URL：" + params[0]);
				Request request = new Request.Builder().url(params[0]).build();
				mClient.newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						LogUtils.e("下载失败！");
					}

					@Override
					public void onResponse(Call call, Response response) throws IOException {
						InputStream is = null;
						byte[] buf = new byte[2048];
						int len;
						FileOutputStream fos = null;
						// 储存下载文件的目录
						try {
							is = response.body().byteStream();
							fos = new FileOutputStream(file);
							while ((len = is.read(buf)) != -1) {
								fos.write(buf, 0, len);
							}
							fos.flush();
							callback.onSuccess(null);
						} catch (Exception e) {
							LogUtils.e("下载失败！");
						} finally {
							try {
								if (is != null)
									is.close();
							} catch (IOException e) {
							}
							try {
								if (fos != null)
									fos.close();
							} catch (IOException e) {
							}
						}
					}
				});
				return null;
			}

			@Override
			protected void onPostExecute(String s) {
//				callback.onSuccess(s);
			}
		}.execute(url);
	}

	//okhttp post
	public static void doPost(String url, final Map<String, String> map, final HttpsCallback callback) {
		new AsyncTask<String, Integer, String>() {
			@Override
			protected String doInBackground(String... params) {
				LogUtils.d("请求URL：" + params[0]);
				LogUtils.d("请求参数：" + map);
				FormBody.Builder builder = new FormBody.Builder();
				for (Entry<String, String> entry : map.entrySet()) {
					if (entry.getValue() == null) {
						LogUtils.e("参数" + entry.getKey() + "为空");
						return null;
					}
					builder.add(entry.getKey(), entry.getValue());
				}
				Request request = new Request.Builder().url(params[0]).post(builder.build()).build();
				String result = null;
				try {
					Response response = mClient.newCall(request).execute();
					result = response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return result;
			}

			@Override
			protected void onPostExecute(String s) {
				LogUtils.d("请求结果：" + s);
				callback.onSuccess(s);
			}
		}.execute(url);
	}

	public static void get(final Activity activity, final String path, final HttpsCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET"); // 必须大写
					conn.setConnectTimeout(5000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						String result = inputStreamToString(in);
						LogUtils.d("请求结果：" + result);
						callback.onSuccess(result);
					} else {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ToastUtils.show(activity, "网络异常...");
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void get(final String path, final HttpCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET"); // 必须大写
					conn.setConnectTimeout(5000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						String result = inputStreamToString(in);
						LogUtils.d("请求结果：" + result);
						callback.onSuccess(result);
					} else {
						callback.onFailed("code=" + code);
					}
				} catch (Exception e) {
					e.printStackTrace();
					callback.onFailed(e.getMessage());
				}
			}
		}).start();
	}

	public static void post(final String path, final Map<String, String> data, final HttpCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
		            URL url = new URL(path);
		            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		            conn.setRequestMethod("POST"); // 必须大写
		            conn.setConnectTimeout(5000);
		            StringBuilder form = new StringBuilder();
		            if (data != null) {
		            	for (Entry<String, String> entry : data.entrySet()) {
		            		form.append(entry.getKey() + "=" + entry.getValue() + "&");
		            	}
		            	LogUtils.d("请求参数：" + form.toString());
					}
		            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		            conn.setRequestProperty("Content-Length", form.length() + "");
		            conn.setDoOutput(true);
		            conn.getOutputStream().write(form.toString().getBytes());
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						String result = inputStreamToString(in);
						LogUtils.d("请求结果：" + result);
						callback.onSuccess(result);
					} else {
						callback.onFailed("code=" + code);
					}
				} catch (Exception e) {
					e.printStackTrace();
					callback.onFailed(e.getMessage());
				}
			}
		}).start();
	}

	private static String inputStreamToString(InputStream inputStream) throws IOException {
		if (inputStream != null) {
			StringBuilder sb = new StringBuilder();
			try {
				String line;
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				inputStream.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	public interface ImageCallback {

		void onSuccess(Bitmap bitmap);
	}

	public interface HttpsCallback {

		void onSuccess(String data);
	}

	public interface HttpCallback {

		void onSuccess(String data);

		void onFailed(String code);
	}
}
