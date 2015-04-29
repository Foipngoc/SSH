package com.yidong_app.interfaceURL;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.yidong_app.common.info.SystemInfo;

public class URL
{
	private Context context;
	private String url = "";
	private Map<String, Object> params = new HashMap<String, Object>();
	private Map<String, File> files = new HashMap<String, File>();
	private int method = METHOD_POST;

	public static int METHOD_GET = 0;
	public static int METHOD_POST = 1;

	/**
	 * >>>>>>>>>>>>>>>>>>>修改服务器地址请至SystemInfo里面修改
	 */
	public static String SERVER_URL = "http://" + SystemInfo.WEB_SERVER + "/";

	public URL(Context context) {
		this.setContext(context);
		// params.put("systemid", SystemInfo.zx_systemid);
		// params.put("sessionid", new
		// CurrentSessionPersistence(context).getSessionInfo()
		// .getSessionID());
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Map<String, Object> getParams()
	{
		return params;
	}

	@Override
	public String toString()
	{
		return "URL [context=" + context + ", url=" + url + ", params=" + params + ", files="
				+ files + ", method=" + method + "]";
	}

	public Object getParams(String key)
	{
		return params.get(key);
	}

	public void setParams(Map<String, Object> params)
	{
		this.params = params;
	}

	public void setParams(String key, Object value)
	{
		this.params.put(key, value);
	}

	public int getMethod()
	{
		return method;
	}

	public void setMethod(int method)
	{
		this.method = method;
	}

	public Context getContext()
	{
		return context;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	public Map<String, File> getFiles()
	{
		return files;
	}

	public void setFiles(Map<String, File> files)
	{
		this.files = files;
	}

	public static HttpGet getHttpGet(String url)
	{
		HttpGet request = new HttpGet(url);
		return request;
	}

	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException,
			IOException
	{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	public static String queryStringForGet(String url)
	{
		HttpGet request = URL.getHttpGet(url);
		String result = null;
		try
		{
			HttpResponse response = URL.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200)
			{
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
			result = null;
			return result;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			result = null;
			return result;
		}
		return null;
	}
}
