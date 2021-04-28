package org.king2.log.schedule;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.king2.log.config.SystemFilterConfiguration;
import org.king2.log.constant.LogAccessTypeEnum;
import org.king2.log.constant.LogTypeEnum;
import org.king2.log.entity.AccessLog;
import org.king2.log.mapper.AccessLogExtendMapper;
import org.king2.log.utils.AddressUtils;
import org.king2.log.utils.ApplicationUtil;
import org.king2.log.utils.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * 描述: 日志日志定时调度
 * @author 刘梓江
 * @Date 2021/3/19 19:56
 */
@Component
public class LogServerSchedule {

    private final Logger logger= LoggerFactory.getLogger(LogServerSchedule.class);

    /**
     * 创建访问系统记录日志容器
     */
    private static final ConcurrentLinkedDeque<AccessLog> logInfo=new ConcurrentLinkedDeque<>();

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private AccessLogExtendMapper accessLogExtendMapper;

    /**
     *  每隔一分钟执行一次
     */
    @Scheduled(cron=" 0 */1 * * * ? ")
    public void refreshContainer() {
        logger.info("开启日志刷新操作！"+logInfo.size()+"--"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<AccessLog> backupLog=new ArrayList<>();
        int countNumber=0;
        boolean insertDataBase=false;
        try{

            if(logInfo.peekFirst()!=null){
                //创建一个批量插入请求对象
                BulkRequest request=new BulkRequest();
                while (logInfo.peekFirst()!=null){
                    countNumber++;
                    AccessLog accessLog = logInfo.pollFirst();
                    if(accessLog.getIsInsert().intValue()==0){
                        accessLog.setIsInsert(1);
                        backupLog.add(accessLog);
                    }
                    request.add(new IndexRequest("log-server").id(accessLog.getId().toString()).timeout("1s").source(JSON.toJSONString(accessLog),XContentType.JSON));
                    //每次批量如果超过500数量日志就先插入一次
                    if(backupLog.size()>500){

                        try{
                            accessLogExtendMapper.batchInsert(backupLog);
                        }catch (Exception e){
                            //获取异常信息
                            String exceptionMessage= ApplicationUtil.getExceptionMessage(e);
                            logger.error("异常信息："+exceptionMessage);
                            insertDataBase=true;
                            throw e;
                        }
                        BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
                        if(bulk.status().getStatus()==200){countNumber=0;backupLog.clear();}
                    }
                }

                //每次批量如果超过500数量日志就先插入一次
                if(backupLog.size()>0){
                    try{
                        accessLogExtendMapper.batchInsert(backupLog);
                    }catch (Exception e){
                        //获取异常信息
                        String exceptionMessage= ApplicationUtil.getExceptionMessage(e);
                        logger.error("异常信息："+exceptionMessage);
                        insertDataBase=true;
                        throw e;
                    }
                    BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
                    if(bulk.status().getStatus()==200){backupLog.clear();}
                }
            }

        }catch (Exception e){
            //获取异常信息
            String exceptionMessage= ApplicationUtil.getExceptionMessage(e);
            logger.error("异常信息："+exceptionMessage);
            packAccessLog(new Date(),LogAccessTypeEnum.SYSTEM.code, LogTypeEnum.EXCEPTION.code,exceptionMessage);//将本次准备待插入的记录信息重新
        }finally {
            if(backupLog.size()>0){
                //将备份日志信息重新加入到容器中等待下一次添加到ES中
                for(AccessLog e:backupLog){
                    if(insertDataBase){
                        e.setIsInsert(0); //代表没有插入到数据库数据
                    }
                    logInfo.addLast(e);
                }
            }
        }
    }

    /**
     * 封装日志信息
     * @param resultInfo
     */
    public static void packAccessLog(Date accessDate,Integer accessType,Integer logType,String resultInfo){
        HttpServletRequest request= SystemFilterConfiguration.contentRequest.get();
        //封装日志信息
        AccessLog accessLog=new AccessLog();
        accessLog.setId(IdUtil.nextId());           //记录id
        accessLog.setAccessType(accessType);        //访问类型  系统 查看 编辑 删除
        accessLog.setAccessLogType(logType);        //日志类型：异常日志、操作日志、登录日志
        accessLog.setAccessDate(accessDate);        //日志访问时间
        accessLog.setAccessResult(resultInfo);      //访问结果
        if(request!=null){
            Enumeration<String> paraNames=request.getParameterNames();
            StringBuilder requestArgs=new StringBuilder();
            for(Enumeration<String> e=paraNames;e.hasMoreElements();){
                String thisName=e.nextElement().toString();
                String thisValue=request.getParameter(thisName);
                requestArgs.append(thisName + "=" + thisValue + "&");
            }
            accessLog.setAccessParameter(requestArgs.toString());   //获取参数
            accessLog.setAccessPath(request.getServletPath());      //获取当前请求路径(获取接口地址(不包含项目名))
            //获取IP
            String requestIp = AddressUtils.getHostIpAddress(request);
            String areaInfo = AddressUtils.areaInfoByIp(requestIp);
            accessLog.setAccessIp(requestIp);                       //获取IP
            accessLog.setAccessArea(areaInfo);                      //获取区域信息
        }
        logInfo.addLast(accessLog);
    }

}
