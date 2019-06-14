package life.majiang.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	@Autowired
	private GithubProvider githubProvider; // 自动注入类
	@GetMapping("/callback")
	public String callback (@RequestParam(name = "code") String code) {
		// 跳转携带code的时候返回
		// System.out.println(code);
		
		// 创建GithubProvider类 发送post请求
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		// String jsonStr = "{'client_id': '840e14b3990e1a66b140','client_secret': 'f0bbc0eecef590d7abbd24515320573630701364'}";
		accessTokenDTO.setClient_id("'840e14b3990e1a66b140'");
		accessTokenDTO.setClient_secret("f0bbc0eecef590d7abbd24515320573630701364");
		accessTokenDTO.setCode(code);
		githubProvider.getAccessToken(accessTokenDTO);
		
		return "index";
	}
}
