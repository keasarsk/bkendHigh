package com.sk.mix.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @program: bkendHigh
 * @description: redis作为分布式缓存锁的demo 从简单 setnx 到 zookeeper ---b 站图灵
 * 从一个减库存的 demo 操作 一步步分布式优化
 * @author: kaesar
 * @create: 2023-04-28 22:09
 **/

@RestController
public class redisLockController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //1. 初始未考虑并发下的减库存操作
    //      并发请求过来会产生多个请求减的同一个数值库存的情况
    @RequestMapping("/redisLockTest1")
    public String redisLockTest(){
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));

        // 减库存操作
        if(stock > 0){
            stock -= 1;
            stringRedisTemplate.opsForValue().set("stock",stock+"");
            System.out.println("减库存成功,剩余"+stock);
        }
        else System.out.println("库存不足");

        return "end";
    }

    // 2. 锁住此操作,使得并发下同时只有一个线程可以减库存成功
    // 但是单服务节点情况下可以这样,
    // 问题, 多个服务节点分布式下,每个节点只保证自己这一条线上的减库存数量的顺序,无法保证多个节点同时减同一个库存数量
    @RequestMapping("/redisLockTest2")
    public String redisLockTest2(){
        synchronized (this) {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));

            // 减库存操作
            if (stock > 0) {
                stock -= 1;
                stringRedisTemplate.opsForValue().set("stock", stock + "");
                System.out.println("减库存成功,剩余" + stock);
            } else System.out.println("库存不足");

            return "end";
        }
    }

    // 3. redis 分布式锁 -- 原始
    // 问题 一, 如果一个线程在减库存操作这一步中 **抛出异常了** ,那么这个锁就耗时很久
    // 问题 二, 如果一个线程在减库存这一步 **挂了** , 那么这个锁就永远释放不了了
    @RequestMapping("/redisLockTest3")
    public String redisLockTest3(){
        // 为当前 **业务** 创建一个名为 lockkey 的锁
        String lockkey = "redisLockTestLock";
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockkey, "lock");
        if(!result)return "error";
        // 进行到这里说明上一步加锁成功, 说明此时没有别的线程加了名为 lockkey 的锁

        // 减库存操作
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        if (stock > 0) {
            stock -= 1;
            stringRedisTemplate.opsForValue().set("stock", stock + "");
            System.out.println("减库存成功,剩余" + stock);
        } else System.out.println("库存不足");

        // 减完库存要释放锁 lockkey
        stringRedisTemplate.delete(lockkey);

        return "end";
    }

    // 4. redis 分布式锁 -- try finally + timeout
    // 解决问题一 ,可以给减库存操作设置 try finally, 让它可以不管异常否最后都释放锁
    // 解决问题二 , 可以给锁设置过期时间, 这样就算一个线程挂了, 锁到期之后就会释放
    // 问题三, 过期时间 长 于线程运行时间, 会导致其它线程也减了同一个数量
    // 问题四, 过期时间 短 于线程运行时间, 锁在执行过程中自己释放掉,线程后面会释放别人加的锁
    @RequestMapping("/redisLockTest4")
    public String redisLockTest4(){
        // 为当前 **业务** 创建一个名为 lockkey 的锁 同时设置过期时间
        String lockkey = "redisLockTestLock";
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockkey, "lock", 10, TimeUnit.SECONDS);
        if(!result)return "error";
        // 进行到这里说明上一步加锁成功, 说明此时没有别的线程加了名为 lockkey 的锁

        // 减库存操作
        try{
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stock -= 1;
                stringRedisTemplate.opsForValue().set("stock", stock + "");
                System.out.println("减库存成功,剩余" + stock);
            } else System.out.println("库存不足");
        }
        finally {
            // 减完库存要释放锁 lockkey
            stringRedisTemplate.delete(lockkey);
        }

        return "end";
    }

    @Resource
    private RedissonClient redisson;

    // 5. redis 分布式锁 -- 过期时间不准确 redisson
    // 解决问题三, 四, 设置锁自动续期能力: 过期时间为 10 的话就隔 5 秒检查一下加锁的线程是否还存在, 存在就重新加锁为 10
    // 使用封装好的 redisson
    // 问题五, redis 本质是单线程的, 所以如何提高性能,  redisson 加锁过程中其他线程一直在自选, 这样会造成 cpu 占用很高去维护自旋操作
    @RequestMapping("/redisLockTest5")
    public String redisLockTest5(){
        // 为当前 **业务** 创建一个名为 lockkey 的锁 同时设置过期时间
        String lockkey = "redisLockTestLock";
        // Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockkey, "lock", 10, TimeUnit.SECONDS);
        // if(!result)return "error";
        // // 进行到这里说明上一步加锁成功, 说明此时没有别的线程加了名为 lockkey 的锁

        //使用 redisson 加锁
        RLock redissonLock = redisson.getLock(lockkey);
        redissonLock.lock();// 加锁 ,这里底层 **lua脚本** 封装了 redis 加锁, 设置过期时间, 续期
        // 这里没用 if(加锁失败) return error; 是因为这个底层使那些加锁失败的线程进行 while 自旋获取锁.

        // 减库存操作
        try{
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stock -= 1;
                stringRedisTemplate.opsForValue().set("stock", stock + "");
                System.out.println("减库存成功,剩余" + stock);
            } else System.out.println("库存不足");
        }
        finally {
            // // 减完库存要释放锁 lockkey
            // stringRedisTemplate.delete(lockkey);

            // 使用 redisson
            redissonLock.unlock();
        }

        return "Test5";
    }

    // 6. redis 分布式锁 -- 优化 redisson
    // 问题五,
    //    1, 业务代码中不用加锁的部分就不要加锁
    //    2, 分段锁   将 stock 拆分成多个 redis 的 key, 让不同的线程去加不同的锁 lockkey_01, lockkey_02.....
    @RequestMapping("/redisLockTest6")
    public String redisLockTest6() {
        // 为当前 **业务** 创建一个名为 lockkey 的锁 同时设置过期时间
        String lockkey = "redisLockTestLock";

        //使用 redisson 加锁
        RLock redissonLock = redisson.getLock(lockkey);
        redissonLock.lock();// 加锁 ,这里底层 **lua脚本** 封装了 redis 加锁, 设置过期时间, 续期
        // 这里没用 if(加锁失败) return error; 是因为这个底层使那些加锁失败的线程进行 while 自旋获取锁.

        // 减库存操作
        try {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stock -= 1;
                stringRedisTemplate.opsForValue().set("stock", stock + "");
                System.out.println("减库存成功,剩余" + stock);
            } else System.out.println("库存不足");
        } finally {
            // 使用 redisson
            redissonLock.unlock();
        }
        return "end";
    }
}
