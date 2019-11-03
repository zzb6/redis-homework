package com.util;

import com.dto.User;
import redis.clients.jedis.Jedis;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashUtil {
    public static void main(String[] args){

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");

        User user1=new User();
        user1.setName("zzb");
        user1.setAge("20");

        Long Id=save(jedis,user1);
        System.out.println("保存成功"+"userId："+Id);

        User user=get(jedis,Id);
        System.out.println("查询成功"+user.getName());

        update(jedis,Id);

        List list=pageId(jedis,2,1);
        System.out.print(list);

        del(jedis,Id);
    }
    public static Long save(Jedis jedis, User user1){

        Long userId=jedis.incr("userId");

        HashMap<String, String> user = new HashMap<>();
        user.put( "name",user1.getName() );
        user.put( "age",user1.getAge() );
        jedis.hmset( "user"+userId,user );
        jedis.rpush("userList","userId"+userId);
        return userId;

    }
    public static User get(Jedis jedis,Long id){
        Map<String, String> user1 = jedis.hgetAll( "user"+id );
        System.out.println(user1);
        User user=new User();
        user.setName(user1.get("name"));
        user.setAge(user1.get("age"));

        return user;
    }
    public static List pageId(Jedis jedis, Integer size, Integer now){
        List list=jedis.lrange("userList",(now-1)*size,now*size-1);
        return list;
    }
    public static void del(Jedis jedis,Long id){
        jedis.lrem("userList",1,"userId"+id);
        jedis.del("user"+id);
    }

    public static void update(Jedis jedis,Long id){

        jedis.hset("user"+id,"name","qwe");
        jedis.hset("user"+id,"age","666");

    }

}
