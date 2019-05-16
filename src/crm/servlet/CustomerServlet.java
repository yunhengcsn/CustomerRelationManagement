package crm.servlet;

import crm.domain.Customer;
import crm.service.CustomerService;
import tools.commons.CommonUtils;
import tools.servlet.BaseServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CustomerServlet extends BaseServlet {
    private CustomerService customerService = new CustomerService();
    //  添加客户
    public String add(HttpServletRequest req, HttpServletResponse resp) {
        Customer form = CommonUtils.toBean(req.getParameterMap(),Customer.class);
        form.setCid(CommonUtils.uuid());
        customerService.add(form);
        req.setAttribute("msg","添加客户成功！");
        return "f:/jsps/msg.jsp";
    }
    //查询所有客户
    public String findAll(HttpServletRequest req, HttpServletResponse resp) {
        List<Customer> cmList = customerService.findAll();
        req.setAttribute("list",cmList);
        return "f:/jsps/crm/list.jsp";
    }
    //载入要编辑的用户
    public String preEdit(HttpServletRequest req, HttpServletResponse resp) {
        String cid = req.getParameter("cid");
        Customer c = customerService.findByCid(cid);
        req.setAttribute("ctm",c);
        return "f:/jsps/crm/edit.jsp";
    }
    //修改用户信息
    public String edit (HttpServletRequest req, HttpServletResponse resp) {
        String cid = req.getParameter("cid");
        Customer form = CommonUtils.toBean(req.getParameterMap(),Customer.class);
        form.setCid(cid);
        customerService.edit(form);
        req.setAttribute("msg","编辑客户成功！");
        return "f:/jsps/msg.jsp";
    }
    //删除用户
    public String delete(HttpServletRequest req,HttpServletResponse resp) {
        String cid = req.getParameter("cid");
        customerService.delete(cid);
        req.setAttribute("msg","删除用户成功！");
        return "f:/jsps/msg.jsp";
    }
    //条件查询
    public String query(HttpServletRequest req,HttpServletResponse resp) {
        Customer cond = CommonUtils.toBean(req.getParameterMap(),Customer.class);
        System.out.println(cond.toString());
        List<Customer> condList = customerService.query(cond);
        req.setAttribute("list",condList);
        return "f:/jsps/crm/list.jsp";
    }
}
