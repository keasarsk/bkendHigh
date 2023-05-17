package com.sk.mix.config;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonCodecWrapper;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: bkendHigh
 * @description: 配置 redisson
 * @author: kaesar
 * @create: 2023-04-29 16:48
 **/
@Configuration
public class redissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        //1.实例化配置对象
        Config config=new Config();
        //2.设置Redis服务器的地址和密码
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        // 配置 json 序列化
        config.setCodec(new JsonJacksonCodec());

        //3.实例化客户端对象
        return Redisson.create(config);
    }
    // //String类型
    // //添加字符串类型
    // public static void setStr(String key,Object val){
    //     client.getBucket(key).set(val);
    // }
    // public static void setStr(String key,Object val,long seconds){
    //     client.getBucket(key).set(val,seconds, TimeUnit.SECONDS);
    // }
    // public static void addIncr(String key){
    //     int i= (int) client.getBucket(key).get();
    //     i++;
    //     client.getBucket(key).set(i);
    // }
    // //BitMap
    // public static void setBitMap(String key,int index){
    //     client.getBitSet(key).set(index,true);
    // }
    // //List
    // public static void setList(String key,Object val){
    //     client.getList(key).add(val);
    // }
    // public static void setList(String key,int index,Object val){
    //     client.getList(key).add(index,val);
    // }
    // public static void setList(String key, List<Object> vals){
    //     client.getList(key).addAll(vals);
    // }
    // public static Object getList(String key,int index){
    //     return client.getList(key).get(index);
    // }
    // public static Object getList(String key){
    //     return client.getList(key).remove(0);
    // }
    // //获取bitmap1的数量
    // public static long getBitMap(String key){
    //     return client.getBitSet(key).cardinality();
    // }
    // public static void expire(String key,long seconds){
    //     client.getKeys().expire(key,seconds,TimeUnit.SECONDS);
    // }
    // public static long ttl(String key){
    //     return client.getKeys().remainTimeToLive(key);
    // }
    // public static void setSet(String key,String val)
    // {
    //     client.getSet(key).add(val);
    // }
    // //获取字符串类型
    // public static Object getStr(String key){
    //     return client.getBucket(key).get();
    // }
    // //Set类型
    // public static Set<Object> getSet(String key){
    //     return client.getSet(key).readAll();
    // }
    // public static int getSize(String key){
    //     return client.getSet(key).size();
    // }
    // public static boolean exists(String key,Object val){
    //     return client.getSet(key).contains(val);
    // }
    // //Hash类型
    // public static void setHash(String key,String field,Object obj){
    //     client.getMap(key).put(field,obj);
    // }
    // public static Object getHash(String key,String field){
    //     return client.getMap(key).get(field);
    // }
    // public static void delField(String key,String field){
    //     client.getMap(key).remove(field);
    // }
    // //删除
    // public static void delSet(String key,Object val){
    //     client.getSet(key).remove(val);
    // }
    // public static void delKey(String... keys){
    //     client.getKeys().delete(keys);
    // }
    // //校验key
    // public static boolean existsField(String key,String field){
    //     return client.getMap(key).containsKey(field);
    // }
    // public static boolean checkKey(String key){
    //     return client.getKeys().countExists(key)>0;
    // }
    // public static String getKey(String key){
    //     Iterator<String> keys=client.getKeys().getKeys().iterator();
    //     while (keys.hasNext()){
    //         String k=keys.next();
    //         if(k.startsWith(key)){
    //             return k;
    //         }
    //     }
    //     return null;
    // }
    // /**
    //  * 基于Redisson的分布式锁
    //  * @param key 分布式锁的key*/
    // public static RLock getLock(String key){
    //     return client.getLock(key);
    // }

}
