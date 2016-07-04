package weixin.popular.bean.message.massmessage;


public class Filter {

	private boolean is_to_all;

	private String tag_id;

	public Filter(boolean is_to_all, String tag_id) {
		super();
		this.is_to_all = is_to_all;
		this.tag_id = tag_id;
	}

	public Filter(String tag_id){
		super();
		this.tag_id = tag_id;
	}

	public boolean isIs_to_all() {
		return is_to_all;
	}

	public void setIs_to_all(boolean is_to_all) {
		this.is_to_all = is_to_all;
	}

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }
}
