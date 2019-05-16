package crm.service;

import crm.dao.CustomerDao;
import crm.domain.Customer;

import java.util.List;

public class CustomerService {
    private CustomerDao customerDao = new CustomerDao();

    public void add(Customer c) {
        customerDao.add(c);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public Customer findByCid(String cid) {
        return customerDao.findByCid(cid);
    }

    public void edit(Customer form) {
        customerDao.edit(form);
    }

    public void delete(String cid) {
        customerDao.delete(cid);
    }

    public List<Customer> query(Customer cond) {
        return customerDao.query(cond);
    }
}
