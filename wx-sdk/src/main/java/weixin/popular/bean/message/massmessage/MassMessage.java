package weixin.popular.bean.message.massmessage;

import weixin.popular.bean.message.preview.MpnewsPreview;

import java.util.Set;

/**
 * 高级群发接口数据
 * @author LiYi
 *
 */
public  class MassMessage {

	public String msgtype;

	private Filter filter;//用于特定组

	private Set<String> touser;//用于指定用户

    private Mpnews mpnews;

    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private Text text;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Mpnews getMpnews() {
        return mpnews;
    }

    public void setMpnews(Mpnews mpnews) {
        this.mpnews = mpnews;
    }

    public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Set<String> getTouser() {
		return touser;
	}

	public void setTouser(Set<String> touser) {
		this.touser = touser;
	}


}
