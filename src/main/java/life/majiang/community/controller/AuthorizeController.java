package life.majiang.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	@Autowired
	private GithubProvider githubProvider; // 自动注入类
	
	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	
	@GetMapping("/callback")
	public String callback (@RequestParam(name = "code") String code) {
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
		GithubUser user = githubProvider.getUser(acessToken);
		// System.out.print("beigin--------");
		// System.out.print(user.getName()); //  成功
		// System.out.print("end--------");
		
		return "index";
	}
}
