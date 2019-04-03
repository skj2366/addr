package com.addr.db;

import java.util.List;
import java.util.Map;

public interface AddressDAO {

	public int insertAddressList(List<Map<String,String>> addrList);
	
}
