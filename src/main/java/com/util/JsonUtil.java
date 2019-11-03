package com.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dto.Article;
import redis.clients.jedis.Jedis;


public class JsonUtil {
    public static void main(String[] args){

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");

        Article article=new Article();
        article.setTittle("标题");
        article.setContent("内容");

          save(jedis,article); //增
          del(jedis,2);   //删
          update(jedis,1);//改
          find(jedis,1);  //查


    }
    public static Long save(Jedis jedis,Article article){
        String articleJson=JSON.toJSONString(article);
        Long articleId=jedis.incr("article");
        jedis.set("article"+articleId+"Data",articleJson);
        return articleId;
    }
    public static void del(Jedis jedis,Integer id){

        jedis.del("article"+id+"Data");
    }
    public static void update(Jedis jedis,Integer id){

        String articlejson=jedis.get("article"+id+"Data");
        Article article=JSON.parseObject(articlejson,Article.class);
        article.setTittle("handsome");
        article.setContent("jake");
        String post=JSON.toJSONString(article);
        jedis.set("article"+id+"Data",post);
    }
    public static Article find(Jedis jedis,Integer id){

        String articlejson=jedis.get("article"+id+"Data");
        Article article=JSON.parseObject(articlejson,Article.class);
        return article;
    }

}
