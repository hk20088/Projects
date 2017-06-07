package com.newspace.aps.mapper.actiCode;

import java.util.List;

import com.newspace.aps.model.ActiCode;

public interface ActiCodeMapper {

	List<ActiCode> queryActiCode(int userId);
}
