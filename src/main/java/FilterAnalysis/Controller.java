package FilterAnalysis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����ȥ��`��`�Υ٩`�����饹 ���õĤ����Ԥȥե��󥯥����
 * 
 * @author Yokou
 *
 */
public class Controller {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
