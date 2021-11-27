package com.bigdata.store.web.filter;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


class MyRequest extends HttpServletRequestWrapper {
	
	private HttpServletRequest request;
	private boolean flag = true;

	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	/**
	    *   获得指定名称的第一个参数
	 */
	@Override
	public String getParameter(String name) {
		String[] values = getParameterValues(name);
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}


	/**
	    *   获得指定名称的所有参数
	 */
	public String[] getParameterValues(String name) {
		Map<String, String[]> map = getParameterMap();
		if (map == null || map.size() == 0) {
			return null;
		}

		return map.get(name);
	}

	
	/**
	   *   获得所有的内容 ， key: 指定的名称： value: 指定名称对象的所有值 
	 */
	@Override
	public Map<String, String[]> getParameterMap() {

		/**
		    *    首先判断请求方式  get post
		 */
		String method = request.getMethod();
		if ("post".equalsIgnoreCase(method)) {
			try {
				request.setCharacterEncoding("utf-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		} else if ("get".equalsIgnoreCase(method)) {
			Map<String, String[]> map = request.getParameterMap();
			if (flag) {
				for (String key : map.keySet()) {
					String[] arr = map.get(key);
					// 继续遍历数组
					for (int i = 0; i < arr.length; i++) {
						// 编码
						try {
							arr[i] = new String(arr[i].getBytes("iso-8859-1"), "utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
				flag = false;
			}
			// 需要遍历map 修改value的每一个数据的编码
			return map;
		}

		return super.getParameterMap();
	}
}