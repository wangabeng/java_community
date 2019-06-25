package life.majiang.community.controller;


import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	@Autowired
	private GithubProvider githubProvider; // 自动注入类
	
	@Autowired
	private UserMapper userMapper;
	
	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	
	@GetMapping("/callback")
	public String callback (
			@RequestParam(name = "code") String code,
			// @RequestParam(name = "state") String state,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		// 跳转携带code的时候返回
		// System.out.println(code);
		
		// 创建GithubProvider类 发送post请求
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		// String jsonStr = "{'client_id': '840e14b3990e1a66b140','client_secret': 'f0bbc0eecef590d7abbd24515320573630701364'}";
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		
		// 获取到access token字符串 需要截取
		String acessToken = githubProvider.getAccessToken(accessTokenDTO);
		
		// 获取用户信息
		GithubUser githubUser = githubProvider.getUser(acessToken);
		// System.out.print("beigin--------");
		// System.out.print(user.getName()); //  成功
		// System.out.print("end--------");
		
		// 获取到user信息后存到session中
		if (githubUser != null) {
			// 创建user实体类并修改实体类值
			User user = new User();
			// 存储token
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			System.out.print("插入数据前--------");
			userMapper.insert(user);
			System.out.print("插入数据后--------");
			
			// 设置cookie
			response.addCookie(new Cookie("token", token));
			// 设置session
			// request.getSession().setAttribute("user", githubUser);
			System.out.print("redirect index--------");
			return "redirect:/";
		} else { // 登录失败 重新登录
			return "redirect:/";
		}
		
		
		// return "index";
	}
}
