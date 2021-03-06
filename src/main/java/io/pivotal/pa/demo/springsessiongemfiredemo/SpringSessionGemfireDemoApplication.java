package io.pivotal.pa.demo.springsessiongemfiredemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.session.data.gemfire.config.annotation.web.http.EnableGemFireHttpSession;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePool;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.session.data.gemfire.config.annotation.web.http.EnableGemFireHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

@SpringBootApplication
@Controller
@ClientCacheApplication
@EnablePool(name = "gemfirePool")
@EnableGemFireHttpSession(poolName = "gemfirePool",
		regionName = "test",
		maxInactiveIntervalInSeconds = 180
)
@EnableSecurity
public class SpringSessionGemfireDemoApplication {

	static final String INDEX_TEMPLATE_VIEW_NAME = "index";
	static final String PING_RESPONSE = "PONG";
	static final String REQUEST_COUNT_ATTRIBUTE_NAME = "requestCount";


	public static void main(String[] args) {
		SpringApplication.run(SpringSessionGemfireDemoApplication.class, args);
	}





	@ExceptionHandler
	@ResponseBody
	public String errorHandler(Throwable error) {
		StringWriter writer = new StringWriter();
		error.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/ping")
	@ResponseBody
	public String ping() {
		return PING_RESPONSE;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/session")
	public String session(HttpSession session, ModelMap modelMap,
						  @RequestParam(name = "attributeName", required = false) String name,
						  @RequestParam(name = "attributeValue", required = false) String value) { // <7>

		modelMap.addAttribute("sessionAttributes",
				attributes(setAttribute(updateRequestCount(session), name, value)));

		return INDEX_TEMPLATE_VIEW_NAME;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public String index() {
		return INDEX_TEMPLATE_VIEW_NAME;
	}
// end::class[]

	@SuppressWarnings("all")
	HttpSession updateRequestCount(HttpSession session) {

		synchronized (session) {
			Integer currentRequestCount = (Integer) session.getAttribute(REQUEST_COUNT_ATTRIBUTE_NAME);
			session.setAttribute(REQUEST_COUNT_ATTRIBUTE_NAME, nullSafeIncrement(currentRequestCount));
			return session;
		}
	}

	Integer nullSafeIncrement(Integer value) {
		return (nullSafeIntValue(value) + 1);
	}

	int nullSafeIntValue(Number value) {
		return Optional.ofNullable(value).map(Number::intValue).orElse(0);
	}

	HttpSession setAttribute(HttpSession session, String attributeName, String attributeValue) {

		if (isSet(attributeName, attributeValue)) {
			session.setAttribute(attributeName, attributeValue);
		}

		return session;
	}

	boolean isSet(String... values) {

		boolean set = true;

		for (String value : values) {
			set &= StringUtils.hasText(value);
		}

		return set;
	}

	Map<String, String> attributes(HttpSession session) {

		Map<String, String> sessionAttributes = new HashMap<>();

		for (String attributeName : toIterable(session.getAttributeNames())) {
			sessionAttributes.put(attributeName, String.valueOf(session.getAttribute(attributeName)));
		}

		return sessionAttributes;
	}

	<T> Iterable<T> toIterable(Enumeration<T> enumeration) {

		return () -> Optional.ofNullable(enumeration).map(CollectionUtils::toIterator)
				.orElseGet(Collections::emptyIterator);
	}



}
