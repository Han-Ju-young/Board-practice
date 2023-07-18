package com.sw.command;

import java.util.ArrayList;
import com.sw.dto.BDto;

public interface BoardService {
	ArrayList<BDto> showBoardList();
	int writeContent(BDto bdto);
	BDto viewContent(int id);
	void modifyContent(BDto bdto);
	int deleteContent(int id);
}
