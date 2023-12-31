package com.sw.dao;

import java.util.ArrayList;
import com.sw.dto.BDto;

public interface BDao {
	public ArrayList<BDto> showBoardList();
	public int writeContent(BDto bdto);
	public BDto viewContent(int bId);
	public int modifyContent(BDto bdto);
	public int deleteContent(int bId);
}
