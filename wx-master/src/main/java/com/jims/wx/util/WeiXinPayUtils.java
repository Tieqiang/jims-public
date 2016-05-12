package com.jims.wx.util;

import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.Unifiedorder;
import weixin.popular.bean.paymch.UnifiedorderResult;
import weixin.popular.util.PayUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by cxy on 2016/4/26.
 */
public  class WeiXinPayUtils {
      // 公众号 appId
      private static  String APP_ID="wx890edf605415aaec";
      //公众号密钥 appSerect
      private static  String APP_SERECT="0e9446b7e5690138e6f8b1d07bb04a02";
      //微信支付商号
      private static  String MCH_ID = "1318000301";
      //API密钥 or 商户支付密钥
      private static  String KEY = "jmyruanjianyouxiangongsi84923632";
      //微信支付方式
      private static  String PAY_WAY="JSAPI";
//      /**
//      * 受保护的构造方法
//      */
//      protected WeiXinPayUtils(){
//
//      }

     public void init(String APP_ID,String APP_SERECT,String MCH_ID,String KEY){
         this.APP_ID=APP_ID;
         this.APP_SERECT=APP_SERECT;
         this.MCH_ID=MCH_ID;
         this.KEY=KEY;
     }

    /**
     * @description 页面调起JSAPI所需要的json字符串
     * @param price    商品价格 单位元
     * @param openId   用户的openId
     * @param body     商品的信息
     * @param notifyUrl  支付成功之后的回调页面
     * @param ip        发起支付请求的ip
     * @return
     */
      public static String weiXinPayNeedJson(String price,String openId,String body,String notifyUrl,String ip){
          String json="";
          Unifiedorder unifiedorder = new Unifiedorder();
          unifiedorder.setAppid(APP_ID);
          unifiedorder.setMch_id(MCH_ID);
          unifiedorder.setNonce_str(makeTradeNo());
//          try {
//              // 商品信息必须进行UTF-8 编码
//              unifiedorder.setBody(URLEncoder.encode("商品信息", "utf-8"));
//          } catch (UnsupportedEncodingException e) {
//              e.printStackTrace();
//          }
          unifiedorder.setBody(body);
          //订单号不能重复
          unifiedorder.setOut_trade_no(makeTradeNo());//订单号
          //totalFee 不能有小数点
//          String p=String.valueOf((price*10));
//          Integer pInt=Integer.parseInt(p);
          if(price.contains(".")){
              price=price.substring(0,price.indexOf("."));
          }
          unifiedorder.setTotal_fee(price);//单位分
          unifiedorder.setSpbill_create_ip(ip);//IP
          unifiedorder.setNotify_url(notifyUrl);
          unifiedorder.setTrade_type(PAY_WAY);//JSAPI，NATIVE，APP，WAP
          unifiedorder.setOpenid(openId);
          /**
           *  微信支付 基于V3.X 版本  同一下单
           * 1 设置签名
           * 2 将服务器返回的xml数据转换成对象 UnifiedorderResult
           */
          UnifiedorderResult unifiedorderResult = PayMchAPI.payUnifiedorder(unifiedorder, KEY);
          //预支付订单号
          String prepayId= unifiedorderResult.getPrepay_id();
           //预支付订单号不为空 说明下单成功
          /*
           * 生成支付JS请求对象
           *  1  package 值设置
           *  2  支付签名的生成 设置
           */
          json = PayUtil.generateMchPayJsRequestJson(prepayId, APP_ID, KEY);
//          System.out.println(json);
          return json;
      }

    /**
     * 生成订单号
     * @return
     */
     private static String makeTradeNo(){
         String noncestr = UUID.randomUUID().toString();
         return noncestr.replace("-", "");
     }







}
