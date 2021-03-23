package org.king2.log.service.impl;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.king2.log.constant.LogAccessTypeEnum;
import org.king2.log.constant.LogTypeEnum;
import org.king2.log.constant.SystemResultCodeEnum;
import org.king2.log.entity.AccessLog;
import org.king2.log.entity.dto.QueryLogDto;
import org.king2.log.entity.vo.PageBeanVo;
import org.king2.log.result.SystemResult;
import org.king2.log.schedule.LogServerSchedule;
import org.king2.log.service.LogService;
import org.king2.log.utils.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Date;

/**
 * 描述:操作日志信息业务接口实现
 * @author 刘梓江
 * @Date 2021/3/19 15:44
 */
@Service
public class LogServiceImpl implements LogService {

    private static final Logger logger= LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Object findLogByCondition(QueryLogDto queryLogDto) {
        try{
            PageBeanVo<AccessLog> pageBeanVo=new PageBeanVo<>();

            //创建查询请求对象
            SearchRequest searchRequest=new SearchRequest("log-server");
            //构造搜索条件
            SearchSourceBuilder ssb=new SearchSourceBuilder();

            //创建多条件匹配对象(解释：先筛选出对应的日志类型,在从指定类型记录中的accessPath或accessArea符合对应搜索内容
            BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.termQuery("accessType",queryLogDto.getAccessType()));

            if(!StringUtils.isEmpty(queryLogDto.getSearchContent())){
                BoolQueryBuilder sBoolQueryBuilder1=QueryBuilders.boolQuery();
                sBoolQueryBuilder1.should(QueryBuilders.termQuery("accessPath",queryLogDto.getSearchContent()));
                sBoolQueryBuilder1.should(QueryBuilders.termQuery("accessArea",queryLogDto.getSearchContent()));
                boolQueryBuilder.must(sBoolQueryBuilder1);
            }

            // 范围查询 from:相当于开区间>= to:相当于闭区间<=;  gt:相当于开区间(>) gte:相当于闭区间 (>=) lt:开区间(<) lte:闭区间 (<=)
            if(!StringUtils.isEmpty(queryLogDto.getStartTime())&&!StringUtils.isEmpty(queryLogDto.getEndTime())){
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("accessDate").from(queryLogDto.getStartTime()).to(queryLogDto.getEndTime()));
            }

            //将查询添加到查询构造中
            ssb.query(boolQueryBuilder);
            //按照时间降序排列
            ssb.sort("accessDate",SortOrder.DESC);
            //分页
            ssb.from(queryLogDto.getCurrentPage()*queryLogDto.getPageSize()-queryLogDto.getPageSize()).size(queryLogDto.getPageSize());
            //将构造放入到请求中
            searchRequest.source(ssb);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] hits = searchResponse.getHits().getHits();
            for(SearchHit hit:hits){
                pageBeanVo.getList().add(JSON.parseObject(hit.getSourceAsString(),AccessLog.class));
            }
            pageBeanVo.setTotalCount(searchResponse.getHits().getTotalHits().value);//获取总数量
            return SystemResult.ok(pageBeanVo);
        }catch (Exception e){
            String exceptionMessage = ApplicationUtil.getExceptionMessage(e);
            logger.error("异常信息："+exceptionMessage);
            LogServerSchedule.packAccessLog(new Date(), LogAccessTypeEnum.FIND.code, LogTypeEnum.EXCEPTION.code,exceptionMessage);
            return SystemResult.build(SystemResultCodeEnum.ERROR.STATE,SystemResultCodeEnum.ERROR.MESS);
        }
    }

    @Override
    public Object add() {
        return SystemResult.ok("添加成功");
    }

    @Override
    public Object update() {
        return SystemResult.ok("修改成功");
    }

    @Override
    public Object delete() {
        return SystemResult.ok("删除成功");
    }
}
