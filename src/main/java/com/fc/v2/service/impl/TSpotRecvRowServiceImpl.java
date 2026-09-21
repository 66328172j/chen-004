package com.fc.v2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.mapper.auto.TSpotRecvRowMapper;
import com.fc.v2.model.auto.TSpotRecvRow;
import com.fc.v2.service.ITSpotRecvRowService;

/**
 * 季度卫片图斑包接收明细 Service业务层处理（batch-process 形状：整批提交）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Service
public class TSpotRecvRowServiceImpl implements ITSpotRecvRowService {

    private static final int MAX_ROWS = 500;
    private static final int STATUS_OK = 1;
    private static final int STATUS_FAIL = 2;

    @javax.annotation.Resource
    private TSpotRecvRowMapper spotRecvRowMapper;

    @Override
    public TSpotRecvRow selectTSpotRecvRowById(Long id) {
        return this.spotRecvRowMapper.selectById(id);
    }

    @Override
    public int submitBatch(String batchNo, List<TSpotRecvRow> rows) {
        String no = rows.get(0).getBatchNo();
        java.util.List<TSpotRecvRow> errors = new java.util.ArrayList<TSpotRecvRow>();
        int seq = 0;
        for (TSpotRecvRow r : rows) {
            if (r.getItemCode() == null || r.getItemCode().trim().isEmpty()
                    || r.getQty() == null
                    || r.getQty().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                seq++;
                r.setRowNo(Integer.valueOf(seq));
                r.setBatchNo(no);
                r.setStatus(STATUS_FAIL);
                this.spotRecvRowMapper.insert(r);
                errors.add(r);
            }
        }
        if (!errors.isEmpty()) {
            return 0;
        }
        int ok = 0;
        for (TSpotRecvRow r : rows) {
            r.setBatchNo(no);
            r.setStatus(STATUS_OK);
            this.spotRecvRowMapper.insert(r);
            ok++;
        }
        return ok;
    }

    @Override
    public List<TSpotRecvRow> listErrors(String batchNo) {
        return this.spotRecvRowMapper.selectList(new QueryWrapper<TSpotRecvRow>()
                .eq("batch_no", batchNo).eq("status", STATUS_FAIL));
    }
}
