package com.fc.v2.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fc.v2.model.auto.TSpotSpotBill;

import java.util.List;

/**
 * 卫片图斑建档单 Service接口
 *
 * @author fuce
 * @date 2026-09-12
 */
public interface ITSpotSpotBillService {

    /** 按主键查询 */
    TSpotSpotBill selectTSpotSpotBillById(Long id);

    /** 按条件查询列表（分页由调用方统一处理） */
    List<TSpotSpotBill> selectTSpotSpotBillList(Wrapper<TSpotSpotBill> queryWrapper);

    /** 新增 */
    int insertTSpotSpotBill(TSpotSpotBill record);

    /** 修改 */
    int updateTSpotSpotBill(TSpotSpotBill record);

    /** 批量删除 */
    int deleteTSpotSpotBillByIds(String ids);

    /** 按主键删除 */
    int deleteTSpotSpotBillById(Long id);
}
