package weixin.popular.util;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import weixin.popular.bean.pay.PayFeedback;
import weixin.popular.bean.pay.PayNativeInput;
import weixin.popular.bean.pay.PayNotify;
import weixin.popular.bean.pay.PayWarn;
import weixin.popular.bean.paymch.MchPayNotify;
import weixin.popular.bean.paymch.PapayEntrustwebNotify;

public class SignatureUtil {

	/**
	 * 生成 package 字符串
	 * @param map
	 * @param paternerKey
	 * @return
	 */
	public static String generatePackage(Map<String, String> map,String paternerKey){
		String sign = generateSign(map,paternerKey);
		Map<String,String> tmap = MapUtil.order(map);
		String s2 = MapUtil.mapJoin(tmap,false,true);
		return s2+"&sign="+sign;
	}

	/**
	 * 生成sign MD5 加密 toUpperCase
	 * @param map
	 * @param paternerKey
	 * @return
	 */
	public static String generateSign(Map<String, String> map,String paternerKey){
		Map<String, String> tmap = MapUtil.order(map);
		if(tmap.containsKey("sign")){
			tmap.remove("sign");
		}
		String str = MapUtil.mapJoin(tmap, false, false);
		return DigestUtils.md5Hex(str+"&key="+paternerKey).toUpperCase();
	}

	/**
	 * 生成 paySign
	 * @param map
	 * @param paternerKey
	 * @return
	 */
	public static String generatePaySign(Map<String, String> map,String paySignKey){
		if(paySignKey!=null){
			map.put("appkey",paySignKey);
		}
		Map<String, String> tmap = MapUtil.order(map);
		String str = MapUtil.mapJoin(tmap,true,false);
		return DigestUtils.shaHex(str);
	}




	/**
	 * 生成事件消息接收签名
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static String generateEventMessageSignature(String token, String timestamp,String nonce) {
		String[] array = new String[]{token,timestamp,nonce};
		Arrays.sort(array);
		String s = StringUtils.arrayToDelimitedString(array, "");
		return DigestUtils.shaHex(s);
	}


	/**
	 * 验证  pay feedback appSignature 签名
	 * @param payFeedback
	 * @param paySignKey 公众号支付请求中用于加密的密钥Key,
	 * 					  可验证商户唯一身份,对应于支付场景中的 appKey 值
	 * @return
	 */
	public static boolean validateAppSignature(PayFeedback payFeedback,String paySignKey) {
		Map<String, String> map = MapUtil.objectToMap(payFeedback,
														"msgtype",
														"appsignature",
														"signmethod",
														"feedbackid",
														"transid",
														"reason",
														"solution",
														"extinfo",
														"picInfo");
		return payFeedback.getAppsignature().equals(generatePaySign(map,paySignKey));
	}


	/**
	 * 验证  pay native appSignature 签名
	 * @param payNativeInput
	 * @param paySignKey 公众号支付请求中用于加密的密钥Key,
	 * 					  可验证商户唯一身份,对应于支付场景中的 appKey 值
	 * @return
	 */
	public static boolean validateAppSignature(PayNativeInput payNativeInput,String paySignKey) {
		Map<String, String> map = MapUtil.objectToMap(payNativeInput, "appsignature","signmethod");
		return payNativeInput.getAppsignature().equals(generatePaySign(map,paySignKey));
	}


	/**
	 * 验证  pay notify appSignature 签名
	 * @param payNotify
	 * @param paySignKey 公众号支付请求中用于加密的密钥Key,
	 * 					  可验证商户唯一身份,对应于支付场景中的 appKey 值
	 * @return
	 */
	public static boolean validateAppSignature(PayNotify payNotify,String paySignKey) {
		Map<String, String> map = MapUtil.objectToMap(payNotify, "appsignature","signmethod");
		return payNotify.getAppsignature().equals(generatePaySign(map,paySignKey));
	}

	/**
	 * 验证  pay warn appSignature 签名
	 * @param payWarn
	 * @param paySignKey 公众号支付请求中用于加密的密钥Key,
	 * 					  可验证商户唯一身份,对应于支付场景中的 appKey 值
	 * @return
	 */
	public static boolean validateAppSignature(PayWarn payWarn,String paySignKey) {
		Map<String, String> map = MapUtil.objectToMap(payWarn, "appsignature","signmethod");
		return payWarn.getAppsignature().equals(generatePaySign(map,paySignKey));
	}

	/**
	 * 验证 mch pay notify sign 签名
	 * 使用更好的 SignatureUtil.validateSign
	 * @param mchPayNotify
	 * @param key mch key
	 * @return
	 */
	@Deprecated
	public static boolean validateAppSignature(MchPayNotify mchPayNotify,String key) {
		Map<String, String> map = MapUtil.objectToMap(mchPayNotify);
		return mchPayNotify.getSign().equals(generateSign(map,key));
	}

	/**
	 * 验证代扣签约 签名
	 * 使用更好的 SignatureUtil.validateSign
	 * @param papayEntrustwebNotify
	 * @param key mch key
	 * @return
	 */
	@Deprecated
	public static boolean validateAppSignature(PapayEntrustwebNotify papayEntrustwebNotify,String key) {
		Map<String, String> map = MapUtil.objectToMap(papayEntrustwebNotify);
		return papayEntrustwebNotify.getSign().equals(generateSign(map,key));
	}

	/**
	 * mch 支付、代扣异步通知签名验证，
	 * 该方法可以替代 mch 支付、代扣异步通知验证，用以防止官方返回参数与bean不一至而导致签名错误。
	 * @param map 参与签名的参数
	 * @param key mch key
	 * @return
	 */
	public static boolean validateSign(Map<String,String> map,String key){
		return map.get("sign").equals(generateSign(map,key));
	}

}
