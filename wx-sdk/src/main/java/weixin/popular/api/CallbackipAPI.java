package weixin.popular.api;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import weixin.popular.bean.callbackip.Callbackip;
import weixin.popular.client.LocalHttpClient;

/**
 * 获取微信服务器IP地址
 * @author LiYi
 *
 */
public class CallbackipAPI extends BaseAPI{

	/**
	 * 获取微信服务器IP地址
	 * @param access_token
	 * @return
	 */
	public static Callbackip getcallbackip(String access_token){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(BASE_URI + "/cgi-bin/getcallbackip")
				.addParameter(getATPN(),access_token)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,Callbackip.class);
	}
}
