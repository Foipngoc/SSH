/**
 * 
 */
package com.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.common.exception.InvalidParameterException;
import com.common.exception.NetWorkException;

/**
 * HttpPost工具类
 * 
 * @author DongJun
 * 
 */
public class HttpPost {

	/**
	 * 以Post方式向web接口请求数据,编码为utf-8
	 * 
	 * @param actionUrl
	 * @param params
	 * @return
	 * @throws InvalidParameterException
	 * @throws NetWorkException
	 * @throws Exception
	 */
	public static String Post(String actionUrl, Map<String, Object> params)
			throws InvalidParameterException, NetWorkException {
		if (actionUrl == null || actionUrl.equals(""))
			throw new InvalidParameterException("actionUrl");
		if (params == null)
			throw new InvalidParameterException("params");
		String sb2 = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		try {
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			// 设置超时
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);

			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET
						+ LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}

			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			outStream.write(sb.toString().getBytes("UTF-8"));
			InputStream in = null;

			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();

			int res = conn.getResponseCode();
			if (res == 200) {
				in = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "UTF-8"));
				String line = "";
				for (line = br.readLine(); line != null; line = br.readLine()) {
					sb2 = sb2 + line;
				}
			}
			outStream.close();
			conn.disconnect();
			return sb2;
		} catch (Exception e) {
			throw new NetWorkException(e.getMessage());
		}
	}
}
