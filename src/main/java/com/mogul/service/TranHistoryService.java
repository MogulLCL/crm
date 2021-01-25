package com.mogul.service;

import com.mogul.pojo.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranHistoryService {
    List<TranHistory> selectTranHistoryByTranId(String id);
    Map<String, Object> stageCount();
}
