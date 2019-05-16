package crm.dao;

import crm.domain.Customer;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import tools.jdbc.TxQueryRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author csn
 * */
public class CustomerDao {
    private TxQueryRunner qr = new TxQueryRunner();

    /**
     * add Customer
     * @param c
     * */
    public void add(Customer c) {
        String sql = "insert into t_customer values(?,?,?,?,?,?,?)";
        Object[] params = {c.getCid(),c.getCname(),c.getGender(),c.getBirthday(),c.getCellphone(),c.getEmail(),c.getDescription()};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
           throw new RuntimeException("添加客户失败！");
        }

    }

    /**
     * find all customers
     * @return List<Customer>
     * */
    public List<Customer> findAll() {
        String sql = "select * from t_customer";
        try {
            return qr.query(sql,new BeanListHandler<>(Customer.class));
        } catch (SQLException e) {
            throw new RuntimeException("查询客户失败！");
        }
    }

    /**
     * find Customer by cid
     * @param cid
     * @return Customer
     * */
    public Customer findByCid(String cid) {
        String sql = "select * from t_customer where cid=?";
        Object[] param = {cid};
        try {
            return qr.query(sql,new BeanHandler<>(Customer.class),param);
        } catch (SQLException e) {
            throw new RuntimeException("载入客户失败！");
        }
    }

    /**
     * edit Customer
     * @param form
     * */
    public void edit(Customer form) {
        String sql = "update t_customer set cname=?,gender=?,birthday=?,cellphone=?,email=?,description=? where cid=?";
        Object[] params = {form.getCname(),form.getGender(),form.getBirthday(),form.getCellphone(),form.getEmail(),form.getDescription(),form.getCid()};
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException("编辑客户失败！");
        }
    }

    /**
     * delete Customer by cid
     * @param cid
     * */
    public void delete(String cid) {
        String sql = "delete from t_customer where cid=?";
        Object[] param = {cid};
        try {
            qr.update(sql,param);
        } catch (SQLException e) {
            throw new RuntimeException("删除用户失败！");
        }
    }

    /**
     * query customers by condition
     * @param cond
     * @return List<Customer>
     * */
    public List<Customer> query(Customer cond) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> params = new ArrayList<>();
        sql.append("select * from t_customer where ");
        if(cond.getCname() != null && cond.getCname().trim().length() != 0) {
            sql.append("cname like ?");
            params.add("%" + cond.getCname() + "%");
        }
        if(cond.getGender() != null && cond.getGender().trim().length() != 0) {
            if(params.size() != 0) sql.append("and ");
            sql.append("gender like ?");
            params.add("%" + cond.getGender() + "%");
        }
        if(cond.getCellphone() != null && cond.getCellphone().trim().length() != 0) {
            if(params.size() != 0) sql.append("and ");
            sql.append("cellphone like ?");
            params.add("%" +cond.getCellphone() + "%");
        }
        if(cond.getEmail() != null && cond.getEmail().trim().length() != 0) {
            if(params.size() != 0) sql.append("and ");
            sql.append("email like ?");
            params.add("%" + cond.getEmail() + "%");
        }
        System.out.println(sql.toString());
        try {
            return qr.query(sql.toString(),new BeanListHandler<>(Customer.class),params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException("高级搜索用户失败！");
        }

    }
}
