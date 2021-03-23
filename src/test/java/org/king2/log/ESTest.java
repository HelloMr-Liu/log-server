package org.king2.log;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.king2.log.entity.AccessLog;
import org.king2.log.entity.dto.QueryLogDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述: ES客户端测试
 *
 * @author 刘梓江
 * @Date 2021/3/19 14:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTest {


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    //判断索引是否存在
    @Test
    public void indexExist() throws Exception{

        //指定对应的索引
        GetIndexRequest indexRequest=new GetIndexRequest("log-server");
        boolean exists = restHighLevelClient.indices().exists(indexRequest, RequestOptions.DEFAULT);
        System.out.println("测试索引是否存在-----"+exists);

        //指定对应的索引
        GetIndexRequest indexRequest2=new GetIndexRequest("log-server1");
        boolean exists2 = restHighLevelClient.indices().exists(indexRequest2, RequestOptions.DEFAULT);
        System.out.println("测试索引是否存在-----"+exists2);
    }


    //判断文档是否存在
    @Test
    public void documentExist() throws Exception{
        GetRequest request= new GetRequest("log-server","12314234423246");
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println("测试索引文档是否存在-----"+exists);
    }


    //获取指定文档
    @Test
    public void documentGet() throws Exception{
        GetRequest request= new GetRequest("log-server","12314234423244");
        GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        if(documentFields.isExists()){
            AccessLog accessLog = JSON.parseObject(documentFields.getSourceAsString(), AccessLog.class);
            System.out.println("测试索引文档是否存在-----"+JSON.toJSONString(accessLog));
        }else{
            System.out.println("该文档信息不存在-----");
        }
    }

    //添加文档
    @Test
    public void documentAdd()throws Exception{
        AccessLog log=new AccessLog();
        log.setId(12314234423249l);
        log.setAccessArea("上海");
        log.setAccessDate(new Date());
        log.setAccessIp("192.1.1.2");
        log.setAccessType(1);
        log.setAccessParameter("dwadawdawdawd");
        log.setAccessPath("/xadawd/adwad/Log");
        log.setAccessUserId(12314234423231L);

        IndexRequest request = new IndexRequest("log-server","_doc","12314234423249");
        request.timeout("1s");//设置超时时间
        request.source(JSON.toJSONString(log),XContentType.JSON);

        //发送请求
        IndexResponse response = restHighLevelClient.index(request,RequestOptions.DEFAULT);
        System.out.println("添加文档-------"+response.toString());
        System.out.println("添加文档-------"+response.status());
    }


    //批量添加文档
    @Test
    public void documentBatchAdd()throws Exception{

        //创建批量请求操作对象
        BulkRequest bulkRequest=new BulkRequest();
        for(int index=1;index<=10;index++){
            AccessLog log=new AccessLog();
            log.setId((long) index);
            log.setAccessArea("上海"+index);
            log.setAccessDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-0"+index));
            log.setAccessIp("192.1.1.2");
            log.setAccessType(1);
            log.setAccessParameter("dwadawdawdawd");
            log.setAccessPath("/xadawd/adwad/Log");
            log.setAccessUserId(12314234423231L);
            bulkRequest.add(new IndexRequest("log-server").id(index+"").timeout("1s").source(JSON.toJSONString(log),XContentType.JSON));
        }
        //批量请求
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("添加文档-------"+bulk.toString());
        System.out.println("添加文档-------"+bulk.status());
    }


    //修改文档
    @Test
    public void documentUpdate()throws Exception{
        AccessLog log=new AccessLog();
        log.setAccessType(2);
        log.setAccessPath("/xadawd/adwad/Log/2");

        UpdateRequest request=new UpdateRequest("log-server","5");
        request.timeout("1s");//设置超时时间
        request.doc(JSON.toJSONString(log),XContentType.JSON);

        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println("测试修改文档-----"+response);
        System.out.println("测试修改文档-----"+response.status());
    }

    //批量修改文档
    @Test
    public void documentBatchUpdate()throws Exception{

        //创建批量请求对象
        BulkRequest request=new BulkRequest();
        for(int index=1;index<=5;index++){
            AccessLog log=new AccessLog();
            log.setAccessArea("上饶"+index);
            request.add(new UpdateRequest("log-server2",index+"").timeout("1s").doc(JSON.toJSONString(log),XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println("测试修改文档-----"+bulk);
        System.out.println("测试修改文档-----"+bulk.status());
    }



    //刪除文档
    @Test
    public void documentDelete()throws Exception{
        DeleteRequest request=new DeleteRequest("log-server","12314234423247");
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println("测试修改文档-----"+response);
        System.out.println("测试修改文档-----"+response.status());
    }

    //批量删除文档
    @Test
    public void documentBatchDelete()throws Exception{

        //创建批量请求对象
        BulkRequest request=new BulkRequest();
        for(int index=1;index<=10;index++){
            request.add(new DeleteRequest("log-server",index+""));
        }
        BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println("测试修改文档-----"+bulk);
        System.out.println("测试修改文档-----"+bulk.status());

    }


    /**
     * 多条件查询
     */
    @Test
    public void searchContent() throws  Exception{
        QueryLogDto queryLogDto=new QueryLogDto();
        queryLogDto.setAccessType(3);
        queryLogDto.setSearchContent("黄浦区");

        //创建查询请求对象
        SearchRequest searchRequest=new SearchRequest("log-server");

        //构造搜索条件
        SearchSourceBuilder ssb=new SearchSourceBuilder();

        BoolQueryBuilder sBoolQueryBuilder1=QueryBuilders.boolQuery();
        sBoolQueryBuilder1.should(QueryBuilders.termQuery("accessPath",queryLogDto.getSearchContent()));
        sBoolQueryBuilder1.should(QueryBuilders.termQuery("accessArea",queryLogDto.getSearchContent()));

        //创建多条件匹配对象(解释：先筛选出对应的日志类型,在从指定类型记录中的accessPath或accessArea符合对应搜索内容
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("accessType",queryLogDto.getAccessType()));
        boolQueryBuilder.must(sBoolQueryBuilder1);

        // 范围查询 from:相当于开区间>= to:相当于闭区间<=;  gt:相当于开区间(>) gte:相当于闭区间 (>=) lt:开区间(<) lte:闭区间 (<=)
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("accessDate").from("2021-03-01").to("2021-03-30"));

        //将查询添加到查询构造中
        ssb.query(boolQueryBuilder);
        //按照时间降序排列
        ssb.sort("accessDate", SortOrder.DESC);
        //分页
        ssb.from(0).size(10);

        //将构造放入到请求中
        searchRequest.source(ssb);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getHits().getTotalHits().value);
        System.out.println(JSON.toJSONString(searchResponse.getHits(), true));
        System.out.println(searchResponse);
        for (SearchHit documentFields : searchResponse.getHits()) {
            System.out.println("测试查询文档--遍历参数--"+documentFields.getSourceAsMap());
        }
    }
}
