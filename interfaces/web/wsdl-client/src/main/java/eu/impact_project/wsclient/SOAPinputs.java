/*
	
	Copyright 2011 The IMPACT Project
	
	@author Dennis Neumann

	Licensed under the Apache License, Version 2.0 (the "License"); 
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
 
  		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

*/

package eu.impact_project.wsclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Finds out all the inputs of a given web service operation.
 */
public class SOAPinputs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final Logger logger = LoggerFactory.getLogger(SOAPinputs.class);

	public SOAPinputs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		logger.info("Initializing Servlet SOAPinputs");
		super.init(config);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * Processes the SOAP operations and finds the input objects for the chosen
	 * operation
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("round2", "round2");

		response.setContentType("text/html");

		HttpSession session = request.getSession();

		List<SOAPoperation> soapOperations = null;
		if (session.getAttribute("soapOperations") != null) {

			soapOperations = (List<SOAPoperation>) session
					.getAttribute("soapOperations");

		}

		if (soapOperations != null) {
			// search for the right operation object
			for (SOAPoperation op : soapOperations) {
				if (op.getName().equals(
						request.getParameter("currentOperation"))) {
					session.setAttribute("currentOperation", op);
					break;
				}
			}
		}
		// session.setAttribute("currentOperation", currentOperation);

		String displayDefaults = request.getParameter("displayDefaults");
		if (displayDefaults != null) {
			session.setAttribute("displayDefaults", true);
		} else {
			session.setAttribute("displayDefaults", false);
		}

		
		// get back to JSP
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/interface.jsp");
		rd.forward(request, response);
	}

}