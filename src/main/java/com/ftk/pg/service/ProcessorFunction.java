package com.ftk.pg.service;

import java.util.Map;

import com.ftk.pg.responsevo.RuResponseTxnVo;

@FunctionalInterface
interface ProcessorFunction {
    RuResponseTxnVo apply(Map<String, String> requestMap, Map<String, String> paramMap);
    
}