package com.jchs.expensestracker;

import java.util.Map;
import java.util.Stack;

public class NavigationHistory {

	private Stack<NavigationHistoryItem> items = new Stack<>();
	
	public void add(NavigationHistoryItem item) {
		items.add(item);
	}

	public NavigationHistoryItem getPreviousScreen() {
		items.pop();
		return items.pop();
	}
	
	public void updateHistoryItemModel(String sceneName, Map<String, Object> model) {
		items.stream()
			.filter(item -> item.getSceneName().equals(sceneName))
			.findAny()
			.get()
			.setModel(model);
	}
	
}
