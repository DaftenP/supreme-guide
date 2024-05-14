package com.ssafy.enjoytrips.model.dto;

public class Board {
	int boardId;
	String boardTitle;
	String boardContent;
	int boardView;
	String boardWriter;
	String boardCreatedtime;

	public Board() {
		super();
	}

	public Board(int board_id, String board_title, String board_content, int board_view, String board_writer,
			String board_created_time) {
		super();
		this.boardId = board_id;
		this.boardTitle = board_title;
		this.boardContent = board_content;
		this.boardView = board_view;
		this.boardWriter = board_writer;
		this.boardCreatedtime = board_created_time;
	}

	public int getBoard_id() {
		return boardId;
	}

	public void setBoard_id(int board_id) {
		this.boardId = board_id;
	}

	public String getBoard_title() {
		return boardTitle;
	}

	public void setBoard_title(String board_title) {
		this.boardTitle = board_title;
	}

	public String getBoard_content() {
		return boardContent;
	}

	public void setBoard_content(String board_content) {
		this.boardContent = board_content;
	}

	public int getBoard_view() {
		return boardView;
	}

	public void setBoard_view(int board_view) {
		this.boardView = board_view;
	}

	public String getBoard_writer() {
		return boardWriter;
	}

	public void setBoard_writer(String board_writer) {
		this.boardWriter = board_writer;
	}

	public String getBoard_created_time() {
		return boardCreatedtime;
	}

	public void setBoard_created_time(String board_created_time) {
		this.boardCreatedtime = board_created_time;
	}

	@Override
	public String toString() {
		return "BoardDto [board_id=" + boardId + ", board_title=" + boardTitle + ", board_content=" + boardContent
				+ ", board_view=" + boardView + ", board_writer=" + boardWriter + ", board_created_time="
				+ boardCreatedtime + "]";
	}

}
