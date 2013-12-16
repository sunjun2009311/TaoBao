package org.jan.taobao.utils;

import org.jan.taobao.entity.TaobaoUser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class SaxXmlContentHandler extends DefaultHandler {
	private TaobaoUser user;
	private String tagName;
	StringBuilder builder;
	public TaobaoUser getUser() {
		Log.d("xmlhandler->", "getUser=>"+this.user.toString());
		return this.user;
	}

	public void setUser(TaobaoUser user) {
		this.user = user;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch,start,length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equals("userName")){
			Log.d("xmlhandler->", "userName=>"+builder.toString());
			this.user.setUserName(builder.toString());
		}else if(localName.equals("userPwd")){
			Log.d("xmlhandler->", "userPwd=>"+builder.toString());
			this.user.setUserPwd(builder.toString());
		}
	}

	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("user")){
			this.user = new TaobaoUser();
			builder=new StringBuilder();
		}
		this.tagName = localName;
		builder.setLength(0);
	}

	@Override
	public void endDocument() throws SAXException {
		setUser(user);
		super.endDocument();
	}
	
	
}
