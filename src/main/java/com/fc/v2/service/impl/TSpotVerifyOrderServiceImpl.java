package com.fc.v2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.mapper.auto.TSpotVerifyOrderMapper;
import com.fc.v2.model.auto.TSpotVerifyOrder;
import com.fc.v2.service.ITSpotVerifyOrderService;

/**
 * 图斑核查处置单 Service业务层处理（state-machine 形状：单据流转）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Service
public class TSpotVerifyOrderServiceImpl implements ITSpotVerifyOrderService {

    private static final int MAX_STAGE = 3;
    private static final int STATUS_ACTIVE = 1;
    private static final int STATUS_TERMINAL = 2;

    @javax.annotation.Resource
    private TSpotVerifyOrderMapper spotVerifyOrderMapper;

    @Override
    public TSpotVerifyOrder selectTSpotVerifyOrderById(Long id) {
        return this.spotVerifyOrderMapper.selectById(id);
    }

    @Override
    public List<TSpotVerifyOrder> selectTSpotVerifyOrderList(QueryWrapper<TSpotVerifyOrder> queryWrapper) {
        return this.spotVerifyOrderMapper.selectList(queryWrapper);
    }

    @Override
    public TSpotVerifyOrder advance(Long id, String remark) {
        TSpotVerifyOrder r = this.spotVerifyOrderMapper.selectById(id);
        if (r == null) {
            return null;
        }
        int st = r.getStage() == null ? 0 : r.getStage();
        r.setStage(Math.min(st + 2, MAX_STAGE));
        r.setStatus(STATUS_ACTIVE);
        r.setLastAction(remark);
        this.spotVerifyOrderMapper.updateById(r);
        return r;
    }

    @Override
    public TSpotVerifyOrder rollback(Long id, String remark) {
        TSpotVerifyOrder r = this.spotVerifyOrderMapper.selectById(id);
        if (r == null) {
            return null;
        }
        r.setStage(0);
        r.setStatus(STATUS_ACTIVE);
        r.setLastAction(remark);
        this.spotVerifyOrderMapper.updateById(r);
        return r;
    }

    @Override
    public boolean updateContent(Long id, String remark) {
        TSpotVerifyOrder r = this.spotVerifyOrderMapper.selectById(id);
        if (r == null) {
            return false;
        }
        r.setContent(remark);
        return this.spotVerifyOrderMapper.updateById(r) > 0;
    }

    @Override
    public boolean remove(Long id) {
        TSpotVerifyOrder r = this.spotVerifyOrderMapper.selectById(id);
        if (r == null) {
            return false;
        }
        return this.spotVerifyOrderMapper.deleteById(id) > 0;
    }

}
