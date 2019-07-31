package com.face.backend.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.face.backend.exception.BusinessException;
import com.face.backend.pojo.SearchResultInfo;

public class BaseController {
    final protected Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    public SearchResultInfo handleExceptionAndReturnMsg(BusinessException exception) {
        log.debug(exception.toString());
        return new SearchResultInfo();
    }
}
