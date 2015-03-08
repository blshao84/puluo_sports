package com.puluo.dao.impl;

import java.util.ArrayList;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.jdbc.DalTemplate;


public class PuluoEventMemoryDaoImpl extends DalTemplate implements PuluoEventMemoryDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<PuluoEventMemory> getEventMemoryByUUID(String event_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

}
