package servlet;

import action.ActionOrder;
import entities.Order;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manager.MProduct;
import manager.MSession;
import util.Const;

@WebServlet(name = "product-history", urlPatterns = {"/product-history"})
public class ProductHistory extends HttpServlet {

  @Override  
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = MSession.getSession(request);
		
		List<entities.Item> items=MProduct.getAll();

		session.setAttribute("productList", items);
		request.getRequestDispatcher(Const.PATH_PAGE_ORDERHISTORY).forward(request, response);
	}


}
