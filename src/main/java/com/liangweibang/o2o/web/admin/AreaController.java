package com.liangweibang.o2o.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liangweibang.o2o.entity.Area;
import com.liangweibang.o2o.service.AreaService;

@Controller
@RequestMapping("/admin")
public class AreaController {

	Logger Logger = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "area", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listArea() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> list = new ArrayList<>();
		
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch(Exception e) {
			e.printStackTrace();
			modelMap.put("errno", 0);
			modelMap.put("errmsg", e.toString());
		}
	
		return modelMap;
	}
}
