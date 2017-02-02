package blog.service;


import blog.bean.BlogMainPojo;
import blog.bean.CommentPojo;
import blog.bean.ReplyPojo;
import blog.dao.BlogMainDao;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import redisServer.service.IpLimit;
import server.PackingResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cao on 2017/1/10.
 */

@Service("blog/service/BlogMain")
public class BlogMainService {

    @Resource(name = "blog/dao/BlogMainDao")
    private BlogMainDao blogMainDao;

    @Resource(name = "server/PackingResult")
    private PackingResult packingResult;

    @Resource(name = "redisServer/service/IpLimit")
    private IpLimit ipLimit;

    public Map getBlogByPageNum(HttpServletRequest request,int pageNum, int getBlogNum) {
        if (ipLimit.isBlackIp(request,"10")){
            return packingResult.toWorngMap("Please stop!");
        }
        if (pageNum == 0 || getBlogNum == 0) {
            return packingResult.toWorngMap("参数错误");
        }
        Map map=new HashMap();
        map.put("getBlogNum",getBlogNum);
        map.put("startNum", (pageNum - 1) * getBlogNum);
        List<BlogMainPojo> list = blogMainDao.getWord(map);
        return packingResult.toSuccessMap(list);
    }

    public Map toWord(HttpServletRequest request,int id) {
        return blogMainDao.toWord(id);
    }

    public Map getComments(int id){
        List<CommentPojo> list= blogMainDao.getComments(id);
        JSONObject json=new JSONObject();

        for(int i=0;i<list.size();i++){
            CommentPojo comment=list.get(i);
            if (comment.getIsmaincomment()==1){
                comment.viceComment=new ArrayList<>();
                json.put(String.valueOf(comment.getId()),comment);
            }else{
                int vice_main_id=comment.getVicecomment_maincomment_id();
                CommentPojo viceComment=(CommentPojo)json.get(String.valueOf(vice_main_id));
                viceComment.viceComment.add(comment);
            }
        }
        return packingResult.toSuccessMap(json);
    }

    public Map putReply(HttpServletRequest request,ReplyPojo replyPojo){


        Map map=new HashMap();
        if (!(replyPojo.getIsNewMainComment().equals("1"))){
            map.put("toObserverName",replyPojo.getToObservername().equals("")?null:replyPojo.getToObservername());
            map.put("viceComment_mainComment_id",replyPojo.getMainCommentId());
        }
        map.put("wordId",replyPojo.getWordId());
        map.put("isMainComment",replyPojo.getIsNewMainComment().equals("1")?1:2);
        map.put("observerName",replyPojo.getNickname());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM月dd,yyyy　HH:mm:ss");
        map.put("date",simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
        map.put("value",replyPojo.getValue());
        map.put("email",replyPojo.getEmail());
        int result=blogMainDao.putReply(map);
        if (result>0){
            return packingResult.toSuccessMap(new JSONObject());
        }else{
            return packingResult.toWorngMap("服务器出现错误！");
        }
    }



}
