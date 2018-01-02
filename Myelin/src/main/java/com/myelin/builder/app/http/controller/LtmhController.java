package com.myelin.builder.app.http.controller;

import org.springframework.web.bind.annotation.RestController;

import com.myelin.builder.app.http.service.LtmhService;
import com.myelin.builder.core.util.StringUtil;
import com.myelin.builder.framework.util.OpenStringUtils;
import com.myelin.builder.server.dto.LtmhContent;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LtmhController {
	private static final Logger logger = LogManager.getLogger(LtmhController.class);

	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@Autowired
	private LtmhService ltmhService;

	@RequestMapping("/ltmh/success")
	@ResponseBody
	public String success() throws Exception {
		// System.out.println(helloWorldService.getHelloMessage());
		ltmhService.getHelloMessage();

		return "Greetings from Spring Boot23343!";
	}

	@RequestMapping(value = { "/content/summit" }, method = RequestMethod.POST, produces = "text/html; charset=utf8")
	public String submitContentAction(@ModelAttribute LtmhContent content, Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String success = "SUCCESS";
		// if (!isFarmId(request)) success = "FAIL";

		System.out.println("good list funtion");
		logger.info("good222 content: " + content);
		if (StringUtil.equals(success, "SUCCESS")) {
			// List<Cctv> cctvList = new ArrayList<Cctv>();
			// cctv.setUserId(cctv.getLoginUserId());
			// cctv.setFarmId(farmId);
			// cctv.setUseYn(CommValue.FLAG_Y);
			//
			// try {
			// cctv.setTotListSize(cctvService.count(cctv));
			// cctvList = cctvService.list(cctv);
			// } catch (Exception e) {
			// logger.error("DB: {}", e.getMessage());
			// }
			//
			// model.addAttribute("cctv", cctv);
			// model.addAttribute("cctvList", cctvList);
		}
		ltmhService.addLtmhContentPlan(content);
		model.addAttribute("success", success);

		return null;
	}

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "index";
	}

	@RequestMapping("/myelin/createpartitions/{datestr}")
	@ResponseBody
	public String createPartition(@PathVariable("datestr") String datestr) throws Exception {
		// System.out.println(helloWorldService.getHelloMessage());
		String startTime = OpenStringUtils.getCurrentTimeFullDisplayHmmss();
		
		ltmhService.createPartitions(datestr);

		String endTime = OpenStringUtils.getCurrentTimeFullDisplayHmmss();
		
		return "startTime: "+startTime+" endTime: "+endTime;
	}
}
