package com.bion.project2.dao.custom.impl;

import com.bion.project2.HibernateUtil;
import com.bion.project2.dao.custom.EmployeeDAO;
import com.bion.project2.entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee employee) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(employee);

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public boolean update(Employee employee) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.update(employee);

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public Employee find(String employeeID) throws Exception {
        Session session = HibernateUtil.getSession();

        Employee employee = session.find(Employee.class, employeeID);

        session.close();

        return employee;
    }

    @Override
    public List<Employee> findAll() throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();


        Query query = session.createQuery("FROM Employee");

        List<Employee> employeeList = query.list();

        transaction.commit();
        session.close();

        return employeeList;
    } // Done

    @Override
    public boolean delete(String username) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        session.save(session.find(Employee.class, username));

        transaction.commit();
        session.close();

        return true;
    } // Done

    @Override
    public Employee findByUserName(String username) throws Exception {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Employee WHERE username=:uName");
        query.setParameter("uName", username);

        Employee employee = (Employee) query.uniqueResult();

        transaction.commit();
        session.close();

        return employee;
    } // Done

    @Override
    public String generateEmployeeID() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Query query = session.createQuery("FROM Employee ORDER BY employeeID DESC");

        List<Employee> employeeList = query.list();

        session.close();

        if(!employeeList.isEmpty()){
            String lastID = employeeList.get(0).getEmployeeID();

            try {
                String id = lastID.split("[A-Z]")[1];
                int newID = Integer.parseInt(id) + 1;
                lastID = String.format("E%03d", newID);
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
            return lastID;
        }
        return "E001";
    } // Done
}
