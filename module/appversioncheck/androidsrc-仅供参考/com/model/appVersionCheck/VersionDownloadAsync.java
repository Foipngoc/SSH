package com.model.appVersionCheck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

/**
 * 下载最新版本
 * 
 * @author DJ
 * 
 */
public abstract class VersionDownloadAsync extends
		AsyncTask<Void, Integer, Integer> {
	private ProgressDialog dialog;
	private String url;
	private Map<String, Object> params;
	protected String savepath;
	protected boolean cancelable;
	private VersionUpdateCallBack versionUpdateCallBack;

	private AppVersionInfo appVersionInfo;

	public VersionDownloadAsync(Context context, String url,
			Map<String, Object> params, String savepath, boolean cancelable,
			AppVersionInfo appVersionInfo, VersionUpdateCallBack callBack) {
		this.url = url;
		this.cancelable = cancelable;
		this.params = params;
		this.savepath = savepath;
		this.appVersionInfo = appVersionInfo;
		this.versionUpdateCallBack = callBack;

		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		if (this.cancelable) {
			dialog.setCancelable(true);
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					VersionDownloadAsync.this.cancel(true);
					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.DOWNLOAD_CANCEL,
							VersionDownloadAsync.this.appVersionInfo);
				}
			});
		} else
			dialog.setCancelable(false);
		dialog.setTitle("下载中");
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		dialog.setProgress(values[0]);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.show();
		versionUpdateCallBack.callBack(VersionUpdateCallBack.BEGIN_DOWNLOAD,
				appVersionInfo);
	}

	@Override
	protected Integer doInBackground(Void... p) {
		URL u = null;
		InputStream in = null;
		FileOutputStream out = null;
		HttpURLConnection conn = null;
		try {

			String fullurl = url + "?";
			for (Entry<String, Object> entry : params.entrySet()) {
				String key = entry.getKey();
				Object val = entry.getValue();
				fullurl = fullurl + key + "=" + val + "&";
			}
			fullurl = fullurl.substring(0, fullurl.length() - 1);

			u = new URL(fullurl);
			conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.connect();
			long fileLength = conn.getContentLength();
			if (fileLength > 0) {
				in = conn.getInputStream();
				File filePath = new File(savepath);
				if (!filePath.exists())
					filePath.createNewFile();
				out = new FileOutputStream(new File(savepath));
				byte[] buffer = new byte[1024];
				int len = 0;
				long readedLength = 0l;

				while ((len = in.read(buffer)) != -1 && !isCancelled()) {
					out.write(buffer, 0, len);
					readedLength += len;

					int progress = (int) (readedLength * 100.0 / fileLength);
					this.publishProgress(progress);

					versionUpdateCallBack.callBack(
							VersionUpdateCallBack.DOWNLOADING, progress);

					if (readedLength >= fileLength)
						break;
				}
				out.flush();
			} else {
				return -1;
			}
		} catch (Exception e) {
			return -1;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
				if (conn != null)
					conn.disconnect();
			} catch (Exception e2) {
			}
		}
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		dialog.dismiss();
	}
}
