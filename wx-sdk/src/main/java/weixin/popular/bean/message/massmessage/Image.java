package weixin.popular.bean.message.massmessage;

/**
 * Created by admin on 2016/6/27.
 */
public class Image {
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Image(String mediaId) {
        this.mediaId = mediaId;
    }
}
