package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.persistence.EntityManager;
import models.Task;
import utils.DBUtil;
import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        
        // get the page num for open (default: 1)
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}

        List<Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
        		.setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();
        
        long tasks_count = (long)em.createNamedQuery("getTasksCount", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("tasks", tasks);
        request.setAttribute("tasks_count", tasks_count);     // all
        request.setAttribute("page", page);                   // page num

        
        // if flush message has already set to session-scope, save to request-scope (deleted from session-scope)
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/index.jsp");
        rd.forward(request, response);
    }

}