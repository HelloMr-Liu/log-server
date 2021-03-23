package org.king2.log.controller;

import org.king2.log.annotation.AccessDelete;
import org.king2.log.annotation.AccessEditor;
import org.king2.log.annotation.AccessShow;
import org.king2.log.entity.dto.QueryLogDto;
import org.king2.log.service.LogService;
import org.king2.log.valid.QueryValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 * @author 刘梓江
 * @Date 2021/3/23 13:30
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 获取日志类型
     * @param queryLogDto
     * @return
     */
    @AccessShow
    @GetMapping("/query/list")
    public Object findLogByCondition(@Validated(QueryValid.class) QueryLogDto queryLogDto){
        Object logByCondition = logService.findLogByCondition(queryLogDto);
        return logByCondition;
    }

    @AccessEditor
    @GetMapping("/add")
    public Object add(){
        Object add = logService.add();
        return add;
    }

    @AccessEditor
    @GetMapping("/update")
    public Object update(){
        Object update = logService.update();
        return update;
    }

    @AccessDelete
    @GetMapping("/delete")
    public Object delete(){
        Object delete = logService.delete();
        return delete;
    }
}
