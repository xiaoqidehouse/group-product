package com.ruoyi.controller;

import com.ruoyi.config.ExcelConfig;
import com.ruoyi.domain.Deliverylog;
import com.ruoyi.domain.Goods;
import com.ruoyi.domain.Stash;
import com.ruoyi.domain.SysUser;
import com.ruoyi.service.DeliverylogService;
import com.ruoyi.service.GoodsService;
import com.ruoyi.service.StashService;
import com.ruoyi.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("inv")
public class InvebtoryController {

    @RequestMapping("test")
    public String test(){
        return "测试成功";
    }

    @Autowired
    StashService stashService;

    @RequestMapping("staustype")
    public Result staustype(){

        List<Stash> list = stashService.list();

        return Result.success(list);
    }

    @RequestMapping("changbyid")
    public Result changbyid(String id){

       List <Stash> list = stashService.changebyid(id);

        return Result.success(list);
    }

    @Autowired
    GoodsService goodsService;

    @RequestMapping("listall")
    public Result listall(){

        List<Goods> list = goodsService.listAll();

        return Result.success(list);

    }

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("invupdate")
    public Result updateinv(@RequestBody Goods goods){

        Goods byId = goodsService.getById(goods.getId());

        System.out.println("djiadaida"+goods);

        if (byId.getNum()<goods.getNum()){

            return Result.fail("库存不足");

        }

        Boolean flag = redisTemplate.opsForValue().setIfAbsent("goods", goods.toString(), 2, TimeUnit.MINUTES);

            if (flag){

                goodsService.updateBygoods(goods);
                return Result.success("更新成功");

            }else {

                return Result.fail("请不要重复提交");

            }

    }

    // 根据状态查找符合的数据(flag)
    @RequestMapping("selByFlag")
    public Result selByFlag(String flag){

        List<Goods> list = goodsService.seleByFlag(flag);

        return Result.success(list);
    }

    // 更改flag值
    @RequestMapping("delinvByid")
    public void delinvByid(String id){

        goodsService.delinvByid(id);

    }

    // 下拉框
    @RequestMapping("invstash")
    public Result invstashlist(){

        List<Stash> list = stashService.list();

        return Result.success(list);
    }



    @Autowired
    DeliverylogService deliverylogService;
    //导出300万数据
    @RequestMapping("userExcel")
    public void userExcel(Deliverylog deliverylog, HttpServletResponse response, Model model){
        List<SysUser> list = deliverylogService.userListExcel();
        ExcelConfig.exportToExcel(list);
    }

}
