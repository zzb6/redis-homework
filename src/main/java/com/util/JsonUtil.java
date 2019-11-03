package com.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dto.Article;
import redis.clients.jedis.Jedis;


public class JsonUtil {
    public static void main(String[] args){

        //���ӱ��ص� Redis ����
        Jedis jedis = new Jedis("localhost");

        Article article=new Article();
        article.setTittle("����");
        article.setContent("����");

          save(jedis,article); //��
          del(jedis,2);   //ɾ
          update(jedis,1);//��
          find(jedis,1);  //��


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
