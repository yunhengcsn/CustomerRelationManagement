package crm.domain;

import java.util.List;

/**
 * T类的分页数据
 * */
public class Page<T> {

    //表示从currIndex开始的共pageSize行记录

    private List<T> data;//数据
    private int totalRecord;//总记录数
    private int currPage;//当前页
    private int pageSize;//每页记录数
    private int totalPage;//总页数，只有get没有set
    private int currIndex;//当前页起始行的标号，只有get没有set

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        int tp = totalRecord/pageSize;
        if(totalRecord % pageSize != 0) tp++;
        return tp;
}

    public int getCurrIndex() {
        return (currPage-1)*pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "data=" + data +
                ", totalRecord=" + totalRecord +
                ", currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", currIndex=" + currIndex +
                '}';
    }
}
