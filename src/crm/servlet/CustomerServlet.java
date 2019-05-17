package crm.servlet;

import crm.domain.Customer;
import crm.domain.Page;
import crm.service.CustomerService;
import tools.commons.CommonUtils;
import tools.servlet.BaseServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    //分页显示查询的所有用户
    public String findAllByPage(HttpServletRequest req, HttpServletResponse resp) {

        int cp = getCurrPage(req);

        String url = getUrl(req);

        int totalRecords = customerService.calRecords();

        int pageSize = 10;
        Page<Customer> page = new Page<>();
        page.setCurrPage(cp);
        page.setTotalRecord(totalRecords);
        page.setPageSize(pageSize);
        page.setUrl(url);

        List<Customer> data = customerService.findAllByPage(page);
        page.setData(data);

        req.setAttribute("page",page);
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

        List<Customer> condList = customerService.query(cond);
        req.setAttribute("list",condList);
        return "f:/jsps/crm/list.jsp";
    }

    //分页条件查询
    public String queryByPage (HttpServletRequest req,HttpServletResponse resp)throws UnsupportedEncodingException {

        int cp = getCurrPage(req);

        String url = getUrl(req);

        Customer cond = CommonUtils.toBean(req.getParameterMap(),Customer.class);

        // 处理GET请求方式编码问题,BaseServlet已经处理了
        //cond = encCustomer(cond);

        int totalRecords = customerService.calRecords(cond);
        int pageSize = 10;
        Page<Customer> page = new Page<>();
        page.setCurrPage(cp);
        page.setTotalRecord(totalRecords);
        page.setPageSize(pageSize);
        page.setUrl(url);

        List<Customer> data = customerService.queryByPage(page,cond);
        page.setData(data);

        req.setAttribute("page",page);
        return "f:/jsps/crm/list.jsp";
    }

    //从req得到分页的当前页数
    private int getCurrPage(HttpServletRequest req) {
        String currPage = req.getParameter("currPage");
        int cp;
        if(currPage == null || currPage.trim().isEmpty()) cp = 1;
        else cp = Integer.parseInt(currPage);
        return cp;
    }

    //从req得到请求url（包括查询条件信息）
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI() + "?" + req.getQueryString();
        int currPageIndex = url.lastIndexOf("&currPage=");
        if(currPageIndex != -1) url = url.substring(0,currPageIndex);
        return url;
    }

    //修改编码
    private Customer encCustomer(Customer cond) throws UnsupportedEncodingException {
        String cname = cond.getCname();
        String gender = cond.getGender();
        String cellphone = cond.getCellphone();
        String description = cond.getDescription();

        if(cname != null && cname.trim().length() != 0) {
            String newCname = new String(cname.getBytes("ISO-8859-1"),"utf-8");
            cond.setCname(newCname);
        }
        if(gender != null && gender.trim().length() != 0) {
            String newGender = new String(gender.getBytes("ISO-8859-1"),"utf-8");
            cond.setCname(newGender);
        }
        if(cellphone != null && cellphone.trim().length() != 0) {
            String newCellphone = new String(cellphone.getBytes("ISO-8859-1"),"utf-8");
            cond.setCname(newCellphone);
        }
        if(description != null && description.trim().length() != 0) {
            String newDescription = new String(description.getBytes("ISO-8859-1"),"utf-8");
            cond.setCname(newDescription);
        }
        return cond;
    }
}
