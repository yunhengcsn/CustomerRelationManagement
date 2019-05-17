package crm.dao;

import crm.domain.Customer;
import crm.domain.Page;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import tools.jdbc.TxQueryRunner;

import javax.jnlp.IntegrationService;
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

        try {
            return qr.query(sql.toString(),new BeanListHandler<>(Customer.class),params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException("高级搜索用户失败！");
        }

    }
    /**
     * 计算总记录数
     * @return cnt
     * */
    public int calRecords() {
        String sql = "select count(*) from t_customer";
        try {
            Long cnt = (Long)qr.query(sql, new ScalarHandler());
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException("查询总记录数失败！");
        }
    }

    /**
     * 计算按条件查询的总记录数
     * @param cond
     * @return cnt
     * */
    public int calRecords(Customer cond) {

        StringBuilder sql = new StringBuilder();
        ArrayList<String> params = new ArrayList<>();
        sql.append("select count(*) from t_customer where ");
        //增加条件
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

        try {
            Long cnt = (Long)qr.query(sql.toString(), new ScalarHandler(),params.toArray());
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException("高级搜索总记录数失败！");
        }
    }

    /**
     * 计算条件查询的分页记录
     * @param page,cond
     * @return List<Customer>
     * */
    public List<Customer> queryByPage(Page<Customer> page, Customer cond) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> params = new ArrayList<>();
        sql.append("select * from t_customer where ");
        if(cond.getCname() != null && cond.getCname().trim().length() != 0) {
            sql.append("cname like ? ");
            params.add("%" + cond.getCname() + "%");
        }
        if(cond.getGender() != null && cond.getGender().trim().length() != 0) {
            if(params.size() != 0) sql.append("and ");
            sql.append("gender like ? ");
            params.add("%" + cond.getGender() + "%");
        }
        if(cond.getCellphone() != null && cond.getCellphone().trim().length() != 0) {
            if(params.size() != 0) sql.append("and ");
            sql.append("cellphone like ? ");
            params.add("%" +cond.getCellphone() + "%");
        }
        if(cond.getEmail() != null && cond.getEmail().trim().length() != 0) {
            if(params.size() != 0) sql.append("and ");
            sql.append("email like ? ");
            params.add("%" + cond.getEmail() + "%");
        }
        Integer beginIndex = page.getCurrIndex();
        params.add(beginIndex.toString());
        Integer size = page.getPageSize();
        params.add(size.toString());
        sql.append("limit ?,?");
        try {
            return qr.query(sql.toString(),new BeanListHandler<>(Customer.class),params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException("高级搜索本页用户失败！");
        }
    }

    /**
     * 计算分页记录
     * @param page
     * @return List<Customer>
     * */
    public List<Customer> findAllByPage(Page<Customer> page) {
        int beginIndex = page.getCurrIndex();
        int size = page.getPageSize();
        String sql = "select * from t_customer limit ?,?";
        Object[] params = {beginIndex,size};
        try {
            return qr.query(sql,new BeanListHandler<>(Customer.class),params);
        } catch (SQLException e) {
            throw new RuntimeException("查询本页用户失败！");
        }
    }

}
