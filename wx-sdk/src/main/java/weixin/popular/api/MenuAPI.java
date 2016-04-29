package weixin.popular.api;

import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.Menu;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.bean.menu.TrymatchResult;
import weixin.popular.bean.menu.selfmenu.CurrentSelfmenuInfo;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.JsonUtil;

public class MenuAPI extends BaseAPI{

	/**
	 * 创建菜单
	 * @param access_token
	 * @param menuJson 菜单json 数据 例如{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}
	 * @return
	 */
	public static BaseResult menuCreate(String access_token,String menuJson){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
										.setHeader(jsonHeader)
										.setUri(BASE_URI+"/cgi-bin/menu/create")
										.addParameter(getATPN(), access_token)
										.setEntity(new StringEntity(menuJson,Charset.forName("utf-8")))
										.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}

	/**
	 * 创建菜单
	 * @param access_token
	 * @param menuButtons
	 * @return
	 */
	public static BaseResult menuCreate(String access_token,MenuButtons menuButtons){
		String str = JsonUtil.toJSONString(menuButtons);
		return menuCreate(access_token,str);
	}

    /**
     * {
     "button": [
     {
     "type": "click",
     "name": "今日歌曲",
     "key": "V1001_TODAY_MUSIC"
     },
     {
     "name": "菜单",
     "sub_button": [
     {
     "type": "view",
     "name": "首页",
     "url": "https://open.weixin.qq.com/connect/oauth2/authorize?wx1b3cf470d135a830
     &redirect_uri=http://9tvafbgbdf.proxy.qqbrowser.cc/api/wx-service/test&response_type=code&scope=snsapi_base&state=1#wechat_redirect"
     },
     {
     "type": "view",
     "name": "视频",
     "url": "http://v.qq.com/"
     },
     {
     "type": "click",
     "name": "赞一下我们",
     "key": "V1001_GOOD"
     }
     ]
     }
     ]
     }
     */

    public static void main(String[] args){
        String menu="{\n" +
                "    \"menu\": {\n" +
                "        \"button\": [\n" +
                "            {\n" +
                "                \"name\": \"cxy\", \n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"view\", \n" +
                "                        \"name\": \"绑定患者\", \n" +
                "                        \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1b3cf470d135a830&redirect_uri=http://9tvafbgbdf.proxy.qqbrowser.cc/api/wx-service/user-       bangker&response_type=code&scope=snsapi_basestate=1#wechat_redirect\"\n" +
                "                    }, \n" +
                "                    {\n" +
                "                        \"type\": \"view\", \n" +
                "                        \"name\": \"挂号\", \n" +
                "                        \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1b3cf470d135a830&redirect_uri=http://9tvafbgbdf.proxy.qqbrowser.cc/api/wx-service/find-dept&response_type=code&scope=snsapi_basestate=1#wechat_redirect\"\n" +
                "                    }, \n" +
                "                    {\n" +
                "                        \"type\": \"view\", \n" +
                "                        \"name\": \"科室信息\", \n" +
                "                        \"url\": \"http://9tvafbgbdf.proxy.qqbrowser.cc/views/his/public/app-dept-dict-info.html\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }, \n" +
                "            {\n" +
                "                \"name\": \"zhuqi\", \n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"view\", \n" +
                "                        \"name\": \"调查问卷\", \n" +
                "                        \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1b3cf470d135a830&redirect_uri=http://9tvafbgbdf.proxy.qqbrowser.cc/api/wx-service/questionnaire-  survey&response_type=code&scope=snsapi_basestate=1#wechat_redirect\"\n" +
                "                    }, \n" +
                "                    {\n" +
                "                        \"type\": \"view\", \n" +
                "                        \"name\": \"近期就诊记录\", \n" +
                "                        \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1b3cf470d135a830&redirect_uri=http://9tvafbgbdf.proxy.qqbrowser.cc/api/wx-service/rcpt-list&response_type=code&scope=snsapi_basestate=1#wechat_&redirect\"\n" +
                "                    },\n" +
                "\t\t   {\n" +
                "                        \"type\": \"view\", \n" +
                "                        \"name\": \"住院记录查询\", \n" +
                "                        \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1b3cf470d135a830&redirect_uri=http://9tvafbgbdf.proxy.qqbrowser.cc/api/wx-service/pat-visit&response_type=code&scope=snsapi_basestate=1#wechat_redirect\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }, \n" +
                "            {\n" +
                "                \"name\": \"fyg\", \n" +
                "                \"sub_button\": [\n" +
                "                    {\n" +
                "                        \"type\": \"view\", \n" +
                "                        \"name\": \"医院介绍\", \n" +
                "                        \"url\": \"http://9tvafbgbdf.proxy.qqbrowser.cc/views/his/public/hospital-disc.html\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        String str="ULmuc0S_qm3LE5635QO4TmlWFNZkwHWPqBbGwaLor-YpZ3XG5CYnnTQlwsHrv_YCTC1S1ejW-mHyxwYzcGmoP_Bx_9r6Dpci5nw7owt2_z8_ape_ntzf_SnPWG4x7pSALKOiAJAQPT";
        menuCreate(str,menu);

    }
	/**
	 * 获取菜单
	 * @param access_token
	 * @return
	 */
	public static Menu menuGet(String access_token){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
					.setUri(BASE_URI+"/cgi-bin/menu/get")
					.addParameter(getATPN(), access_token)
					.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,Menu.class);
	}

	/**
	 * 删除菜单
	 * @param access_token
	 * @return
	 */
	public static BaseResult menuDelete(String access_token){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/cgi-bin/menu/delete")
				.addParameter(getATPN(), access_token)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}


	/**
	 * 获取自定义菜单配置
	 * 本接口将会提供公众号当前使用的自定义菜单的配置，
	 * 如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，
	 * 而如果公众号是在公众平台官网通过网站功能发布菜单，
	 * 则本接口返回运营者设置的菜单配置。
	 * @param access_token
	 * @return
	 */
	public static CurrentSelfmenuInfo get_current_selfmenu_info(String access_token){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BASE_URI+"/cgi-bin/get_current_selfmenu_info")
				.addParameter(getATPN(), access_token)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,CurrentSelfmenuInfo.class);
	}

	/**
	 * 创建个性化菜单
	 * @param access_token
	 * @param menuButtons
	 * @return
	 */
	public static BaseResult menuAddconditional(String access_token,MenuButtons menuButtons){
		String menuJson = JsonUtil.toJSONString(menuButtons);
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/cgi-bin/menu/addconditional")
				.addParameter(getATPN(), access_token)
				.setEntity(new StringEntity(menuJson,Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}

	/**
	 * 删除个性化菜单
	 * @param access_token
	 * @param menuid
	 * @return
	 */
	public static BaseResult menuDelconditional(String access_token,String menuid){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/cgi-bin/menu/delconditional")
				.addParameter(getATPN(), access_token)
				.setEntity(new StringEntity("{\"menuid\":\""+menuid+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
	}

	/**
	 * 测试个性化菜单匹配结果
	 * @param access_token
	 * @param user_id 可以是粉丝的OpenID，也可以是粉丝的微信号。
	 * @return
	 */
	public static TrymatchResult menuTrymatch(String access_token,String user_id){
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setHeader(jsonHeader)
				.setUri(BASE_URI+"/cgi-bin/menu/trymatch")
				.addParameter(getATPN(), access_token)
				.setEntity(new StringEntity("{\"user_id\":\""+user_id+"\"}",Charset.forName("utf-8")))
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,TrymatchResult.class);
	}


}