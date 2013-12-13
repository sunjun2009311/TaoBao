package org.jan.taobao.utils;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.jan.taobao.entity.TaobaoUser;

public class SaxTaobaoUtils {
	/**
	 * 将实体类信息写成xml格式字符串
	 * @param user
	 * @return xmlString
	 */
	public static String parsetoXml(TaobaoUser user) throws Exception{
		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
		TransformerHandler handler = factory.newTransformerHandler();
		Transformer transformer = handler.getTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		handler.setResult(result);
		String uri = "";
		String localName="";
		handler.startDocument();
		handler.startElement(uri, localName, "user", null);
//		AttributesImpl attributes = new AttributesImpl();
		char[] chs=null;
		handler.startElement(uri, localName, "id", null);
		chs = String.valueOf(user.getId()).toCharArray();
		handler.characters(chs, 0, chs.length);
		handler.endElement(uri, localName, "id");
    	handler.startElement(uri, localName, "userName", null);
    	chs = user.getUserName().toCharArray();
    	handler.characters(chs, 0, chs.length);
    	handler.endElement(uri, localName, "userName");
    	handler.startElement(uri, localName, "userPwd", null);
    	chs = user.getUserPwd().toCharArray();
    	handler.characters(chs, 0, chs.length);
    	handler.endElement(uri, localName, "userPwd");
		handler.endElement(uri, localName, "user");
		handler.endDocument();
		return writer.toString();
	}
}
