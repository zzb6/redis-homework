package com.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    public static void main(String[] args){

        // ��ȡ���ӳ����ö���
        JedisPoolConfig config = new JedisPoolConfig();
        // �������������
        config.setMaxTotal(30);
        // �������Ŀ���������
        config.setMaxIdle(10);

        // ������ӳ�: JedisPool jedisPool = new JedisPool(poolConfig,host,port);
        JedisPool jedisPool = new JedisPool(config,"127.0.0.1",6379);

        // ��ú��Ķ���jedis
        Jedis jedis = null;
        try{
            // ͨ�����ӳ����������
            jedis = jedisPool.getResource();
            // ��������
            jedis.set("name5","s5");
            // ��ȡ����
            String value = jedis.get("name5");
            System.out.println(value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // �ͷ���Դ
            if(jedis != null){
                jedis.close();
            }
            // �ͷ����ӳ�
            if(jedisPool != null){
                jedisPool.close();
            }
        }

    }

}
