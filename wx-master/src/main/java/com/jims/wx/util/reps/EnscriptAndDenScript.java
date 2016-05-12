package com.jims.wx.util.reps;

/**
 * HIS的加密解密算法
 * Created by heren on 2015/11/2.
 */
public class EnscriptAndDenScript {

    /**
     * 加密算法
     * @param str
     * @return
     */
    public static String enScript(String str) {
        int k, l;
        String a = "";
        if (str == null) {
            return "";
        }
        str = str.trim();
        System.out.println(str);
        for (int i = 0; i < str.length(); i++) {
            l = i + 1;
            if (l % 2 == 0) {
                k = (int) str.charAt(i) + l - 32;
            } else {
                k = (int) str.charAt(i) - l + 8;
            }
            a = a + (char) k;
        }
        return a;
    }
     /**
     * 解密算法
     * @param str
     * @return
     */
    //public static String denScript(String str) {
    //    if(str==null||"".equals(str)){
    //        return "" ;
    //    }
    //
    //    int x = Integer.parseInt(str.substring(62));
    //    String temp = "";
    //    String newPwd = "";
    //
    //    for (int i = 2; i < str.length() - 1; i = i + 3) {
    //        temp = temp + str.charAt(i);
    //    }
    //    newPwd = temp.substring(0, x);
    //    return newPwd;
    //}

    /**
     * 正确的解密算法
     * @param str
     * @return
     */
    public static String denscriptFromHis(String str){
        if("".equals(str)||str==null){
            return "" ;
        }

        //str := as_pass;
        //For i IN 1 .. Length(str) LOOP
        //lsstr2 := SUBSTR(str,i,1);
        //if  mod(i,2) = 0 then
        //k:=ASCII(lsstr2) - i +32;
        //else
        //k:=ASCII(lsstr2) + i - 8;
        //end if;
        //lsstr1:=lsstr1 || CHR(k);
        //END LOOP;
        //Result := lsstr1 ;

        StringBuffer stringBuffer = new StringBuffer() ;
        for(int i =1 ;i<=str.length();i++){
            char c = str.charAt(i - 1);
            char temp  ;
            if(i%2==0){
                temp = (char)((int)c -i +32) ;
            }else{
                temp = (char)((int)c+i -8) ;
            }
            stringBuffer.append(temp) ;
        }
        return stringBuffer.toString() ;
    }

}
