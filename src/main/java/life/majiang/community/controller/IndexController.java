package life.majiang.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;

@Controller
public class IndexController {
	@Autowired
	private UserMapper userMapper;

	@GetMapping("/")
	public String index(HttpServletRequest request) {
		// 读取cookie信息 中的token值
		Cookie[] cookies = request.getCookies();
		// System.out.print(cookies);
		if (cookies != null) { // 如果不加判断 如果cookies为空 就会报错 空指针异常
			for (Cookie cookie : cookies) {

				if (cookie.getName().equals("token")) {
					String token = cookie.getValue();
					// System.out.print(token + "cookie.getValue()e");
					User user = userMapper.findByToken(token);

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

		/*
		 * for (Cookie cookie: cookies) { if(cookie.getName().equals("token")) { String
		 * token = cookie.getValue(); User user = userMapper.findByToken(token); if
		 * (user != null) { request.getSession().setAttribute("user", user); }
		 * 
		 * break; } }
		 */

		return "index";
	}
}
