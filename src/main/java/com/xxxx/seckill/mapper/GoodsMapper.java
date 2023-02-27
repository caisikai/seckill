package com.xxxx.seckill.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.seckill.pojo.Goods;
import com.xxxx.seckill.vo.GoodsVo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sky
 * @since 2023-02-26
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List <GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
