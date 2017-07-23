package FilterAnalysis;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * フィルタクラス
 * 
 * @summary 訪問をインターセプトして扱う
 * @author Yokou
 *
 */
//@WebFilter(filterName = "tf", urlPatterns = "/tf")
public class TestFilter implements Filter {
	/**
	 * フィルタの配置クラス
	 */
	private FilterConfig filterConfig;

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * フィルタの存在をなくする
	 */
	public void destroy() {
		System.out.println("...filter destroy...");
	}

	/**
	 * 主にフィルタするプロセス
	 * 
	 * @param req
	 *            ServletRequest
	 * @param resp
	 *            ServletResponse
	 * @param chain
	 *            FilterChain
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("...filter doFilter...");
		// 新たなURLへジャンプする。URLは更新する。
		// HttpServletRequest request = (HttpServletRequest) req;
		// HttpServletResponse response = (HttpServletResponse) resp;
		// response.sendRedirect(request.getContextPath()+"/index.jsp");
		// 具体的な扱う方法
		Handler handler = new Handler();
		handler.handleUrl(req, resp);
		// 新たなURLを配る。URLは更新しないです。
		// req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	/**
	 * フィルタを初期化する。
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("...filter init...");
		this.filterConfig = filterConfig;
	}

}
