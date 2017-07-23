package FilterAnalysis;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * フィルタの扱う組み合わせ
 * 
 * @author Yokou
 *
 */
public class Handler {

	private Map<String, Map<String, String>> actionClasses = new HashMap<String, Map<String, String>>();

	private String packageName = "FilterAnalysis";
	private String packageDirPath = packageName.replace('.', '/');

	/**
	 * 初期化する方法は詳細的にクラスファイルを走査して"@Action"付きのクラスを収集する。それクラスはMVCフレームワークのコントローラーです。
	 */
	public Handler() {
		Enumeration<URL> dirs;
		try {
			// パッケージを走査する。っ
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageName);
			while (dirs.hasMoreElements()) {
				URL url = (URL) dirs.nextElement();
				// System.out.println("...url: "+url.getFile()+"...");
				scanPackage(url.getFile());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 走査方法
	 * 
	 * @param path
	 */
	public void scanPackage(String path) {
		File dir = new File(path);
		// 目録の存在か否かをチェックする。
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 目録の規則を声明する。目録とクラスファイルに限る。
		File[] dirfiles = dir.listFiles(new FileFilter() {

			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.isDirectory() || f.getName().endsWith(".class");
			}
		});
		// 走査し始めます。
		for (File f : dirfiles) {
			// 目録ならリカージョン的に走査し続く。
			if (f.isDirectory()) {
				scanPackage(f.getAbsolutePath());
			} else {
				// クラスならパッケージの名前がクラスの名前を加えて反射機能を利用して詳細な情報を取る。
				// System.out.println("...file: "+f.getName()+"...");
				String fileName = f.getName().substring(0, f.getName().length() - 6);
				try {
					String className = packageName + '.' + fileName;
					Class<?> cls = Class.forName(className);
					// クラスは Action の注釈があるか否かをチェックする。ないならこの書類を扱わない。
					boolean isNotEmpty = cls.isAnnotationPresent(FilterAnalysis.Action.class);
					if (isNotEmpty) {
						// System.out.println("...class: "+cls.getName()+"...");
						Method[] methods = cls.getMethods();
						for (Method m : methods) {
							// 方法は ActionMapping の注釈があるか否かをチェックする。ないならこのファンクションを扱わない。
							boolean hasMapping = m.isAnnotationPresent(FilterAnalysis.ActionMapping.class);
							if (hasMapping) {
								// System.out.println("...method: "+m.getName()+"...");
								Map<String, String> methodUrl = new HashMap<String, String>();
								methodUrl.put("className", className);
								methodUrl.put("methodName", m.getName());
								// 走査した結果は URL- CLASSNAME - METHODNAME のフォーマットで保存する。
								actionClasses.put(m.getAnnotation(FilterAnalysis.ActionMapping.class).value(),
										methodUrl);
								System.out.println("..." + m.getAnnotation(FilterAnalysis.ActionMapping.class).value()
										+ "..." + className + "..." + m.getName());
							} else {
								// System.out.println("...method: "+m.getName()+"...");
								continue;
							}
						}
					} else {
						// System.out.println("...class: "+cls.getName()+"...");
						continue;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * 訪れるリンクを分析して具体的な扱う方法をマッチングしてそうした方法を実行する。
	 * 
	 * @param request
	 * @param response
	 */
	public void handleUrl(ServletRequest request, ServletResponse response) {
		// 実のURLをとる。 http://localhost/項目の名前{url}
		String url = ((HttpServletRequest) request).getServletPath();
		// 特別のはURLが"/"である時、実に取ったURLは初期値の"/index.jsp"です。
		if (url.equals("/index.jsp")) {
			url = "/";
		}
		// 循環してマッチングする。訪れるリンクと走査した結果を比較する。
		for (String key : actionClasses.keySet()) {
			if (key.equals(url)) {
				System.out.println("...matched: " + key + "...");
				try {
					// 扱うクラスをとる。
					System.out.println("...class name: " + actionClasses.get(key).get("className"));
					Class<Controller> cls = (Class<Controller>) Class.forName(actionClasses.get(key).get("className"));
					// 扱うクラスを実例化する。
					Controller action = cls.newInstance();
					// Request と Response を配る。
					Method m = cls.getMethod("setRequest", HttpServletRequest.class);
					m.invoke(action, (HttpServletRequest) request);
					m = cls.getMethod("setResponse", HttpServletResponse.class);
					m.invoke(action, (HttpServletResponse) response);
					// 相応しいファンクションをコールする。
					m = cls.getMethod(actionClasses.get(key).get("methodName"));
					String viewName = (String) m.invoke(action);
					request.getRequestDispatcher(viewName).forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
