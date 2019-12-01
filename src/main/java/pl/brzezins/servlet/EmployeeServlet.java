package pl.brzezins.servlet;

import org.slf4j.Logger;
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
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            List<Employee> employees = employeeDao.getAll();
            List<EmployeeDto> employeesDto = employeeMapper.convertList(employees);

            request.setAttribute("employees", employeesDto);
            dispatcher = request.getRequestDispatcher("/WEB-INF/views/employees.jsp");

        } else {
            switch (pathInfo) {
                case "/edit":
                    dispatcher = edit(request, response);
                    break;
                case "/create":
                    dispatcher = create(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/employees");
                    break;
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            switch (pathInfo) {
                case "/edit":
                    long id = Long.parseLong(request.getParameter("id"));
                    Optional<Employee> employeeOptional = employeeDao.get(id);

                    employeeOptional.ifPresent(entity -> {
                        entity.setName(request.getParameter("name"));
                        entity.setSurname(request.getParameter("surname"));
                        entity.setTelephone(request.getParameter("telephone"));

                        employeeDao.update(entity);
                    });

                    break;
                case "/create":
                    employeeDao.save(new Employee(
                            request.getParameter("name"),
                            request.getParameter("surname"),
                            request.getParameter("telephone"),
                            null));
                    break;
                default:
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private RequestDispatcher edit(HttpServletRequest request, HttpServletResponse response) {
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

        return request.getRequestDispatcher("/WEB-INF/views/edit_employee.jsp");
    }

    private RequestDispatcher create(HttpServletRequest request, HttpServletResponse response) {
        return request.getRequestDispatcher("/WEB-INF/views/create_employee.jsp");
    }
}
