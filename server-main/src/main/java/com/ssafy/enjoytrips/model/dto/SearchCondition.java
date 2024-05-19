package com.ssafy.enjoytrips.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCondition {

	public final int countPerPage = 200;

	private String key = "none";

	private String word;

	private String orderBy = "none";

	private String orderByDir = "asc";

 	private int currentPage; // 한 페이지당 개수

	private int offset; // 시작위치

	private boolean limit = true;

	public SearchCondition() {
	}

	public SearchCondition(String key, String word, String orderBy, String orderByDir, int currentPage, boolean limit) {
		this.key = key;
		this.word = word;
		this.orderBy = orderBy;
		this.orderByDir = orderByDir;
		this.currentPage = currentPage;
		this.limit = limit;
	}

	public SearchCondition(String key, String word, String orderBy, String orderByDir, int currentPage) {
		this(key, word, orderBy, orderByDir, currentPage, true);
	}

	public SearchCondition(String key, String word, String orderBy, String orderByDir) {
		this(key, word, orderBy, orderByDir, 1);
	}

	public SearchCondition(String key, String word, String orderBy) {
		this(key, word, orderBy, "asc");
	}

	public SearchCondition(String key, String word) {
		this(key, word, "none");
	}


	@Override
	public String toString() {
		return "SearchCondition [countPerPage=" + countPerPage + ", key=" + key + ", word=" + word + ", orderBy="
				+ orderBy + ", orderByDir=" + orderByDir + ", currentPage=" + currentPage + ", limit=" + limit + "]";
	}
}
