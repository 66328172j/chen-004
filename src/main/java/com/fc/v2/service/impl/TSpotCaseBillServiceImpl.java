package com.fc.v2.service.impl;

import org.springframework.stereotype.Service;

import com.fc.v2.mapper.auto.TSpotCaseBillMapper;
import com.fc.v2.model.auto.TSpotCaseBill;
import com.fc.v2.service.ITSpotCaseBillService;

/**
 * 违法用地处置方案逐级报批单 Service业务层处理（approval-chain 形状：多阶段签批）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Service
public class TSpotCaseBillServiceImpl implements ITSpotCaseBillService {

    private static final int MAX_NODE = 2;
    private static final int MODE_OR = 0;
    private static final int MODE_AND = 1;
    private static final int STATUS_RUNNING = 0;
    private static final int STATUS_PASS = 1;
    private static final int STATUS_VETO = 2;

    @javax.annotation.Resource
    private TSpotCaseBillMapper spotCaseBillMapper;

    @Override
    public TSpotCaseBill selectTSpotCaseBillById(Long id) {
        return this.spotCaseBillMapper.selectById(id);
    }

    @Override
    public TSpotCaseBill approve(Long id, String approver, String comment) {
        TSpotCaseBill r = this.spotCaseBillMapper.selectById(id);
        if (r == null || approver == null || approver.trim().isEmpty()) {
            return null;
        }
        r.setNodeNo(Integer.valueOf((r.getNodeNo() == null ? 0 : r.getNodeNo()) + 1));
        r.setStatus(Integer.valueOf(r.getNodeNo() >= MAX_NODE ? STATUS_PASS : STATUS_RUNNING));
        this.spotCaseBillMapper.updateById(r);
        return r;
    }

    @Override
    public TSpotCaseBill reject(Long id, String approver, String comment) {
        TSpotCaseBill r = this.spotCaseBillMapper.selectById(id);
        if (r == null) {
            return null;
        }
        this.spotCaseBillMapper.updateById(r);
        return r;
    }

    @Override
    public TSpotCaseBill rollback(Long id, String comment) {
        TSpotCaseBill r = this.spotCaseBillMapper.selectById(id);
        if (r == null) {
            return null;
        }
        int node = r.getNodeNo() == null ? 0 : r.getNodeNo();
        r.setNodeNo(Integer.valueOf(Math.max(0, node - 1)));
        this.spotCaseBillMapper.updateById(r);
        return r;
    }
}
