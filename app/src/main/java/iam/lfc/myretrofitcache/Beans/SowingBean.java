package iam.lfc.myretrofitcache.Beans;

public class SowingBean {
    /**
     * id : 9
     * orderindex : 1
     * status : 1
     * img : http://gcwl.weiruanmeng.commyadmin/20190119/f0578514bd57eaef38c305b1c3927201.png
     * flag : 0
     * object_id : 0
     * link : 1
     * user_type : 2
     * content : null
     * title :
     */

    private String id;
    private String orderindex;
    private String status;
    private String img;
    private String flag;
    private String object_id;
    private String link;
    private String user_type;
    private String content;
    private String title;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderindex() {
        return orderindex == null ? "" : orderindex;
    }

    public void setOrderindex(String orderindex) {
        this.orderindex = orderindex;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img == null ? "" : img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFlag() {
        return flag == null ? "" : flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getObject_id() {
        return object_id == null ? "" : object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getLink() {
        return link == null ? "" : link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUser_type() {
        return user_type == null ? "" : user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}