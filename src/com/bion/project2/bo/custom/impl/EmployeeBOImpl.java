package com.bion.project2.bo.custom.impl;

import com.bion.project2.bo.custom.EmployeeBO;
import com.bion.project2.dao.DAOFactory;
import com.bion.project2.dao.custom.EmployeeDAO;
import com.bion.project2.dto.EmployeeDTO;
import com.bion.project2.entity.Employee;
import com.bion.project2.enums.DAOType;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Date;

public class EmployeeBOImpl implements EmployeeBO {
    private final EmployeeDAO employeeDAO = DAOFactory.getInstance().getDAO(DAOType.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDTO dto) throws Exception {

        return employeeDAO.save(new Employee(
                dto.getEmployeeID(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getMobile(),
                dto.getImagePath(),
                dto.getUsername(),
                BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()),
                dto.getNic(),
                dto.getQuestion(),
                dto.getAnswer(),
                dto.getDate()
        ));
    } // Done

    @Override
    public EmployeeDTO getEmployee(String username) throws Exception {
        Employee emp = employeeDAO.findByUserName(username);

        if (emp!=null){
            return new EmployeeDTO(
                    emp.getEmployeeID(),
                    emp.getFirstName(),
                    emp.getLastName(),
                    emp.getAddress(),
                    emp.getMobile(),
                    emp.getImagePath(),
                    emp.getUsername(),
                    emp.getPassword(),
                    emp.getNic(),
                    emp.getQuestion(),
                    emp.getAnswer(),
                    emp.getDate()
            );
        }
        return null;
    } // Done

    @Override
    public boolean isPasswordChanged(String username, String password) throws Exception {
        Employee emp = employeeDAO.findByUserName(username);

        if (emp!=null){
            employeeDAO.update(new Employee(
                    emp.getEmployeeID(),
                    emp.getFirstName(),
                    emp.getLastName(),
                    emp.getAddress(),
                    emp.getMobile(),
                    emp.getImagePath(),
                    emp.getUsername(),
                    BCrypt.hashpw(password, BCrypt.gensalt()),
                    emp.getNic(),
                    emp.getQuestion(),
                    emp.getAnswer(),
                    new Date()
            ));
            return true;
        }
        return false;
    } // Done

    @Override
    public boolean isValidLogin(String username, String password) throws Exception {
        Employee emp = employeeDAO.findByUserName(username);
        if(emp != null){
            return BCrypt.checkpw(password, emp.getPassword());
        }
        return false;
    } // Done

    @Override
    public boolean isUserExist(String username) throws Exception {
        return employeeDAO.findByUserName(username)!=null;
    } // Done

    @Override
    public boolean isSecretAnswerCorrect(String username, String question, String answer) throws Exception {
        Employee employee = employeeDAO.findByUserName(username);

        if(employee!=null){
            if (question.equals(employee.getQuestion())){
                return answer.equals(employee.getAnswer());
            }
        }
        return false;
    } // Done

    @Override
    public String generateEmployeeID() throws SQLException, ClassNotFoundException{
        return employeeDAO.generateEmployeeID();
    } // Done
}
