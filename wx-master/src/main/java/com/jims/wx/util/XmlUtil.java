package com.jims.wx.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;


import java.io.InputStream;
import java.io.Writer;

/**
 * Object和XML相互转化
 * Created by wangjing on 2016/2/26.
 */
public class XmlUtil {
    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    //从request里面取得xml文件，转化成xml文本
    //把xml文本转成xml文件


    /**
     * 将传入xml文本转换成指定类型的Java对象
     * @param xmlStr
     * @param cls xml对应的class类
     * @param <T>  xml对应的class类的实例对象
     * @return
     */
    public static <T> T xmlStringToBean(String xmlStr, Class<T> cls) {
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }

    /**
     * java对象转换成xml文本
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String beanToXmlString(Object obj) {

//          XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
//          XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释

        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }

}
