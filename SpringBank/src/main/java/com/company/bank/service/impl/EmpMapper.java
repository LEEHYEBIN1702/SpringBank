package com.company.bank.service.impl;

import java.util.List;
import java.util.Map;

public interface EmpMapper {
	public List<Map<String,Object>> getEmpList(); //단건이면 Map으로 끝내도 되지만 여러건이니까 앞에 List
    public List<Map<String, Object>> sumSale();
}
