package com.labplan.webapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.labplan.persistence.DatabaseConnectionFactory;
import com.labplan.webapp.handlers.DefaultResourceHandler;
import com.labplan.webapp.handlers.PatientsResourceHandler;

public class LabPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HandlerContainer handlers;

	@Override
	public void init() throws ServletException {
		DatabaseConnectionFactory.setProfile("production");

		handlers = new HandlerContainer();
		handlers.register("patients", new PatientsResourceHandler());
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		String[] path = getPath(request);
		ServletContext context = getServletContext();
		HandlerParameters params = new HandlerParameters(context, request, response, path);

		System.out.println("GET " + request.getRequestURI());
		
		ResourceHandler handler = obtainHandler(path);
		
		if (handler != null) {
			handler.doGet(params);
		}
	}
	
	ResourceHandler obtainHandler(String[] path) {
		if (path.length > 0) {
			String handlerName = path[0];
			ResourceHandler handler = handlers.obtain(handlerName);
			
			if (handler != null) {
				System.out.println("Invoking " + handlerName);
				return handler;
			}
		}
		
		System.out.println("Invoking default handler.");
		return new DefaultResourceHandler();
	}

	String[] getPath(HttpServletRequest request) {
		try {
			URI basePath = new URI(request.getContextPath());
			URI currentPath = new URI(request.getRequestURI());
			URI relativePath = basePath.relativize(currentPath);

			return relativePath.toString().split("/");
		} catch (URISyntaxException e) {
			throw new RuntimeException("Unable to determine server base URI.");
		}
	}

	@Override
	public void destroy() {
		// do nothing.
	}
}
