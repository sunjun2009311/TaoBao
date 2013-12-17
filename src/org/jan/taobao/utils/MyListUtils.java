package org.jan.taobao.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyListUtils {

	public static ArrayList<HashMap<String, Object>> sortList(
			List<HashMap<String, Object>> list) {
		ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		newList = (ArrayList<HashMap<String, Object>>) list;
		Collections.sort(newList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
				BigDecimal bd1 = new BigDecimal(lhs.get("item_price")
						.toString().substring(1));
				BigDecimal bd2 = new BigDecimal(rhs.get("item_price")
						.toString().substring(1));
				return bd1.compareTo(bd2);
			}
		});

		return newList;

	}
}
