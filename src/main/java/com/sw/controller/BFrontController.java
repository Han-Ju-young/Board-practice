package com.sw.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sw.command.BoardService;
import com.sw.command.BoardServiceImpl;
import com.sw.dto.BDto;

/**
 * Servlet implementation class BFrontController
 */
@WebServlet("*.do")
public class BFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BFrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(request, response);
	}
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("actionDo");
		request.setCharacterEncoding("euc-kr");
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length()); // /xxxx.do
		
		String viewPage = null;
		BoardService bCmd = new BoardServiceImpl();
		HttpSession session = request.getSession();
		
		System.out.println("actionDo - "+com);
		if(com.equals("/list.do")) {
			ArrayList<BDto> bList = bCmd.showBoardList();
			session.setAttribute("bList", bList); // board_list.jsp 에서 넘겨받을 때 bLIst -> 이걸 그대로 써서 넘겨받아야함 주의!!
			viewPage = "board_list.jsp";
		} else if(com.equals("/write.do")) {
			int ret = 0;
			String bName = request.getParameter("bName");
			String bTitle = request.getParameter("bTitle");
			String bContent = request.getParameter("bContent");
			
			BDto bto = new BDto(0, bName, bTitle, bContent, null, 0); // bId, bHit:아무 int값이나 상관 없음 -> bDaoimpl에서 넣어주기 때문 & bDate도 마찬가지 but 객체이므로 null값
			ret = bCmd.writeContent(bto);
			
			viewPage = "list.do";
		} else if(com.equals("/content_view.do")) {
			String sId = request.getParameter("bId");
			int bId = Integer.parseInt(sId);
			BDto bdto = bCmd.viewContent(bId);
			session.setAttribute("content_view", bdto);
			viewPage = "content_view.jsp";
		} else if(com.equals("/modify.do")) {
			String sId = request.getParameter("bId");
			int bId = Integer.parseInt(sId);
			String bName = request.getParameter("bName");
			String bTitle = request.getParameter("bTitle");
			String bContent = request.getParameter("bContent");
			
			BDto bdto = new BDto(bId, bName, bTitle, bContent, null, 0);
			bCmd.modifyContent(bdto);
			viewPage = "list.do";
		} else if(com.equals("/delete.do")) {
			String sId = request.getParameter("bId");
			int bId = Integer.parseInt(sId);
			
			bCmd.deleteContent(bId);
			viewPage = "list.do";
		}
		response.sendRedirect(viewPage);
	}
}
