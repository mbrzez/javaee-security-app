package pl.brzezins.servlet;

import pl.brzezins.dao.Dao;
import pl.brzezins.dto.EmployeeDto;
import pl.brzezins.entity.Employee;
import pl.brzezins.mapper.EmployeeMapper;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ServletSecurity(@HttpConstraint(rolesAllowed = "admin_role"))
@WebServlet("/employees/*")
public class EmployeeServlet extends HttpServlet {
    @EJB(beanName="EmployeeDao")
    private Dao<Employee> employeeDao;

    @Inject
    private EmployeeMapper employeeMapper;

    private RequestDispatcher dispatcher;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = null;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            getProcessMainPage(request);
            view = "/WEB-INF/views/employees.jsp";
        } else {
            switch (pathInfo) {
                case "/edit":
                    getProcessEdit(request);
                    view = "/WEB-INF/views/edit_employee.jsp";
                    break;
                case "/create":
                    view = "/WEB-INF/views/create_employee.jsp";
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/employees");
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            switch (pathInfo) {
                case "/edit":
                    postProcessEdit(request);
                    break;
                case "/create":
                    postProcessCreate(request);
                    break;
                default:
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private void getProcessMainPage(HttpServletRequest request) {
        List<Employee> employees = employeeDao.getAll();
        List<EmployeeDto> employeesDto = employeeMapper.convertList(employees);

        request.setAttribute("employees", employeesDto);
    }

    private void getProcessEdit(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        Optional<Employee> employeeOptional = employeeDao.get(id);

        employeeOptional.ifPresent(entity -> {
            EmployeeDto employeeDto = new EmployeeDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getSurname(),
                    entity.getTelephone(),
                    null
            );

            request.setAttribute("employee", employeeDto);
        });
    }

    private void postProcessEdit(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        Optional<Employee> employeeOptional = employeeDao.get(id);

        employeeOptional.ifPresent(entity -> {
            entity.setName(request.getParameter("name"));
            entity.setSurname(request.getParameter("surname"));
            entity.setTelephone(request.getParameter("telephone"));

            employeeDao.update(entity);
        });
    }

    private void postProcessCreate(HttpServletRequest request) {
        employeeDao.save(new Employee(
                request.getParameter("name"),
                request.getParameter("surname"),
                request.getParameter("telephone"),
                null)
        );
    }
}
