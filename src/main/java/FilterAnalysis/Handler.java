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
 * フィルタの�Qう�Mみ栽わせ
 * 
 * @author Yokou
 *
 */
public class Handler {

	private Map<String, Map<String, String>> actionClasses = new HashMap<String, Map<String, String>>();

	private String packageName = "FilterAnalysis";
	private String packageDirPath = packageName.replace('.', '/');

	/**
	 * 兜豚晒する圭隈は����議にクラスファイルを恠�砲靴�"@Action"原きのクラスを�Ъ�する。それクラスはMVCフレ�`ムワ�`クのコントロ�`ラ�`です。
	 */
	public Handler() {
		Enumeration<URL> dirs;
		try {
			// パッケ�`ジを恠�砲垢襦�っ
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
	 * 恠�坊酬�
	 * 
	 * @param path
	 */
	public void scanPackage(String path) {
		File dir = new File(path);
		// 朕�hの贋壓か倦かをチェックする。
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 朕�hの���tを蕗苧する。朕�hとクラスファイルに�泙襦�
		File[] dirfiles = dir.listFiles(new FileFilter() {

			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.isDirectory() || f.getName().endsWith(".class");
			}
		});
		// 恠�砲景爾瓩泙后�
		for (File f : dirfiles) {
			// 朕�hならリカ�`ジョン議に恠�砲珪Aく。
			if (f.isDirectory()) {
				scanPackage(f.getAbsolutePath());
			} else {
				// クラスならパッケ�`ジの兆念がクラスの兆念を紗えて郡符�C嬬を旋喘して����な秤�鵑鯣，襦�
				// System.out.println("...file: "+f.getName()+"...");
				String fileName = f.getName().substring(0, f.getName().length() - 6);
				try {
					String className = packageName + '.' + fileName;
					Class<?> cls = Class.forName(className);
					// クラスは Action の廣��があるか倦かをチェックする。ないならこの����を�Qわない。
					boolean isNotEmpty = cls.isAnnotationPresent(FilterAnalysis.Action.class);
					if (isNotEmpty) {
						// System.out.println("...class: "+cls.getName()+"...");
						Method[] methods = cls.getMethods();
						for (Method m : methods) {
							// 圭隈は ActionMapping の廣��があるか倦かをチェックする。ないならこのファンクションを�Qわない。
							boolean hasMapping = m.isAnnotationPresent(FilterAnalysis.ActionMapping.class);
							if (hasMapping) {
								// System.out.println("...method: "+m.getName()+"...");
								Map<String, String> methodUrl = new HashMap<String, String>();
								methodUrl.put("className", className);
								methodUrl.put("methodName", m.getName());
								// 恠�砲靴申Y惚は URL- CLASSNAME - METHODNAME のフォ�`マットで隠贋する。
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
	 * �Lれるリンクを蛍裂して醤悶議な�Qう圭隈をマッチングしてそうした圭隈を�g佩する。
	 * 
	 * @param request
	 * @param response
	 */
	public void handleUrl(ServletRequest request, ServletResponse response) {
		// �gのURLをとる。 http://localhost/��朕の兆念{url}
		String url = ((HttpServletRequest) request).getServletPath();
		// 蒙�eのはURLが"/"である�r、�gに函ったURLは兜豚�､�"/index.jsp"です。
		if (url.equals("/index.jsp")) {
			url = "/";
		}
		// 儉�hしてマッチングする。�Lれるリンクと恠�砲靴申Y惚を曳�^する。
		for (String key : actionClasses.keySet()) {
			if (key.equals(url)) {
				System.out.println("...matched: " + key + "...");
				try {
					// �Qうクラスをとる。
					System.out.println("...class name: " + actionClasses.get(key).get("className"));
					Class<Controller> cls = (Class<Controller>) Class.forName(actionClasses.get(key).get("className"));
					// �Qうクラスを�g箭晒する。
					Controller action = cls.newInstance();
					// Request と Response を塘る。
					Method m = cls.getMethod("setRequest", HttpServletRequest.class);
					m.invoke(action, (HttpServletRequest) request);
					m = cls.getMethod("setResponse", HttpServletResponse.class);
					m.invoke(action, (HttpServletResponse) response);
					// ��鬉靴ぅ侫．鵐�ションをコ�`ルする。
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
