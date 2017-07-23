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
 * �ե��륿�ΒQ���M�ߺϤ碌
 * 
 * @author Yokou
 *
 */
public class Handler {

	private Map<String, Map<String, String>> actionClasses = new HashMap<String, Map<String, String>>();

	private String packageName = "FilterAnalysis";
	private String packageDirPath = packageName.replace('.', '/');

	/**
	 * ���ڻ����뷽����Ԕ���Ĥ˥��饹�ե�������ߖˤ���"@Action"�����Υ��饹�򅧼����롣���쥯�饹��MVC�ե�`���`���Υ���ȥ�`��`�Ǥ���
	 */
	public Handler() {
		Enumeration<URL> dirs;
		try {
			// �ѥå��`�����ߖˤ��롣��
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
	 * �ߖ˷���
	 * 
	 * @param path
	 */
	public void scanPackage(String path) {
		File dir = new File(path);
		// Ŀ�h�δ��ڤ��񤫤�����å����롣
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// Ŀ�h��Ҏ�t���������롣Ŀ�h�ȥ��饹�ե�������ޤ롣
		File[] dirfiles = dir.listFiles(new FileFilter() {

			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.isDirectory() || f.getName().endsWith(".class");
			}
		});
		// �ߖˤ�ʼ��ޤ���
		for (File f : dirfiles) {
			// Ŀ�h�ʤ�ꥫ�`�����Ĥ��ߖˤ��A����
			if (f.isDirectory()) {
				scanPackage(f.getAbsolutePath());
			} else {
				// ���饹�ʤ�ѥå��`������ǰ�����饹����ǰ��Ӥ��Ʒ���C�ܤ����ä���Ԕ��������ȡ�롣
				// System.out.println("...file: "+f.getName()+"...");
				String fileName = f.getName().substring(0, f.getName().length() - 6);
				try {
					String className = packageName + '.' + fileName;
					Class<?> cls = Class.forName(className);
					// ���饹�� Action ��עዤ����뤫�񤫤�����å����롣�ʤ��ʤ餳�Ε��Q��ʤ���
					boolean isNotEmpty = cls.isAnnotationPresent(FilterAnalysis.Action.class);
					if (isNotEmpty) {
						// System.out.println("...class: "+cls.getName()+"...");
						Method[] methods = cls.getMethods();
						for (Method m : methods) {
							// ������ ActionMapping ��עዤ����뤫�񤫤�����å����롣�ʤ��ʤ餳�Υե��󥯥�����Q��ʤ���
							boolean hasMapping = m.isAnnotationPresent(FilterAnalysis.ActionMapping.class);
							if (hasMapping) {
								// System.out.println("...method: "+m.getName()+"...");
								Map<String, String> methodUrl = new HashMap<String, String>();
								methodUrl.put("className", className);
								methodUrl.put("methodName", m.getName());
								// �ߖˤ����Y���� URL- CLASSNAME - METHODNAME �Υե��`�ޥåȤǱ��椹�롣
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
	 * �L����󥯤�������ƾ���ĤʒQ��������ޥå��󥰤��Ƥ�������������g�Ф��롣
	 * 
	 * @param request
	 * @param response
	 */
	public void handleUrl(ServletRequest request, ServletResponse response) {
		// �g��URL��Ȥ롣 http://localhost/�Ŀ����ǰ{url}
		String url = ((HttpServletRequest) request).getServletPath();
		// �؄e�Τ�URL��"/"�Ǥ���r���g��ȡ�ä�URL�ϳ��ڂ���"/index.jsp"�Ǥ���
		if (url.equals("/index.jsp")) {
			url = "/";
		}
		// ѭ�h���ƥޥå��󥰤��롣�L����󥯤��ߖˤ����Y������^���롣
		for (String key : actionClasses.keySet()) {
			if (key.equals(url)) {
				System.out.println("...matched: " + key + "...");
				try {
					// �Q�����饹��Ȥ롣
					System.out.println("...class name: " + actionClasses.get(key).get("className"));
					Class<Controller> cls = (Class<Controller>) Class.forName(actionClasses.get(key).get("className"));
					// �Q�����饹��g�������롣
					Controller action = cls.newInstance();
					// Request �� Response ����롣
					Method m = cls.getMethod("setRequest", HttpServletRequest.class);
					m.invoke(action, (HttpServletRequest) request);
					m = cls.getMethod("setResponse", HttpServletResponse.class);
					m.invoke(action, (HttpServletResponse) response);
					// ���ꤷ���ե��󥯥����򥳩`�뤹�롣
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
