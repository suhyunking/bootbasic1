package com.simple.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.simple.basic.command.BoardVO;
import com.simple.basic.service.board.BoardService;

@Controller
@RequestMapping("/memo")
public class MemoController {
	
	//멤버변수
	
	@Autowired
	@Qualifier("이름") //서비스의 이름을 강제연결
	MomoService memoService;
	//등록화면
		@GetMapping("/memoRegist")
		public String memoRegist() {
			return "memo/memoRegist";
		}
		//목록화면
		@GetMapping("/memoList")
		public String memoList(Model model) {
			
			//데이터 가지고 나간다.
			List<BoardVO> list = memoService.getList();
			model.addAttribute("list", list); //화면에 데이터를 전달
			
			return "memo/memoList";
		}
		//결과화면
		@GetMapping("/memoResult")
		public String memoResult() {
			
			return "memo/memoResult";
		}
		
		@PostMapping("/memoForm")
		public String memoForm(MemoVO vo) {
			
			boolean result = memoService.insert(vo);
			
			
			System.out.println("성공실패:" + result);
			//화면에 데이터를 가져나갈 필요가 없다면 리다이렉트 방식으로 처리
			//스프링에서 리다이렉트 방식은 다시 컨트롤러를 태워보내는 방식.
			return "redirect:/memo/memoResult"; //결과화면으로
		}
		
		//상세화면
		@GetMapping("/memoDetail")
		public String memoDetail(@RequestParam("bno") int bno,
								  Model model) {
			//글 1개를 가지고 화면으로 나간다.
			MemoVO vo = memoService.getDetail(bno);
			model.addAttribute("vo", vo);
			
			
			return "memo/memoDetail";
		}
		
		//글 삭제
		@GetMapping("/memoDelete")
		public String memoDelete(@RequestParam("bno") int bno,
								  RedirectAttributes RA) {
			
			boolean result = memoService.delete(bno);
			System.out.println("성공실패:" + result);
			//리다이렉트시에 간단한 메시지 전달방법 (1회성 메시지)
			//RedirectAttributes
			RA.addFlashAttribute("msg", "정상 처리되었습니다");
			
			
			//삭제한 이후에는 
			//데이터를 가지고 화면으로
			return "redirect:/memo/memoList";
		}
}
