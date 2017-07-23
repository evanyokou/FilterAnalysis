package FilterAnalysis;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ÔL¤ì¤ëÊÂ¼þ¤Î¥â¥Ë¥¿¥ê¥ó¥°
 */
//@WebListener
public class TestListener implements HttpSessionListener, ServletContextListener, ServletContextAttributeListener {

	public void attributeAdded(ServletContextAttributeEvent arg0) {
		System.out.println("...listener attribute added...");
	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		System.out.println("...listener attribute removed...");

	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		System.out.println("...listener attribute replaced...");

	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("...listener context destroyed...");

	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("...listener context init...");

	}

	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("...listener session created...");

	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("...listener session destroyed...");

	}

}
