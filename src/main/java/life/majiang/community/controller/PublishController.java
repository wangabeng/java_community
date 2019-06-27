package life.majiang.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;

@Controller
public class PublishController {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/publish")
	public String Publish () {
		return "publish";
	}
	
	@PostMapping("/publish")
	public String doPublish(
			HttpServletRequest request,
			Model model,
			@RequestParam("title")String title,
			@RequestParam("description")String description,
			@RequestParam("tag")String tag
			) {
		User user = null;

		// 读取cookie信息 中的token值
		Cookie[] cookies = request.getCookies();
		// System.out.print(cookies);
		if (cookies != null) { // 如果不加判断 如果cookies为空 就会报错 空指针异常
			for (Cookie cookie : cookies) {

				if (cookie.getName().equals("token")) {
					String token = cookie.getValue();
					// System.out.print(token + "cookie.getValue()e");
					user = userMapper.findByToken(token);

					if (user != null) {
						// System.out.print("yonghu cunzaie");
						request.getSession().setAttribute("user", user);
					}

					break;
				}
			}
		} else {
			System.out.println("cookie 为空");
		}
		
		if(user==null) {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}
		
		Question question = new Question();
		question.setTitle(title);
		question.setDescription(description);
		question.setTag(tag);
		question.setTitle(title);
		question.setCreator(user.getId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModified(question.getGmtCreate());
		questionMapper.create(question);;
		
		return "redirect:/";
	}
}
