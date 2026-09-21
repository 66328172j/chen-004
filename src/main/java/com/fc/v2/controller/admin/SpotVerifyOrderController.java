package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSpotVerifyOrder;
import com.fc.v2.service.ITSpotVerifyOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 图斑核查处置单 Controller（state-machine 形状：流转入口）
 *
 * @author fuce
 * @date 2026-09-14
 */
@Api(value = "图斑核查处置单")
@Controller
@RequestMapping("/spotVerifyOrder")
public class SpotVerifyOrderController extends BaseController {

    private final String prefix = "admin/spotVerifyOrder";

    @Autowired
    private ITSpotVerifyOrderService spotVerifyOrderService;

    @ApiOperation(value = "流转台账跳转", notes = "流转台账跳转")
    @GetMapping("/view")
    @RequiresPermissions("spotVerifyOrder:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    @Log(title = "图斑核查处置单流转台账", action = "list")
    @ApiOperation(value = "流转台账", notes = "流转台账")
    @GetMapping("/list")
    @RequiresPermissions("spotVerifyOrder:list")
    @ResponseBody
    public ResultTable list(TSpotVerifyOrder record) {
        QueryWrapper<TSpotVerifyOrder> queryWrapper = new QueryWrapper<TSpotVerifyOrder>();
        startPage();
        com.github.pagehelper.PageInfo<TSpotVerifyOrder> page =
                new com.github.pagehelper.PageInfo<TSpotVerifyOrder>(spotVerifyOrderService.selectTSpotVerifyOrderList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    @Log(title = "图斑核查处置单推进", action = "advance")
    @ApiOperation(value = "推进一档", notes = "推进一档")
    @PostMapping("/advance")
    @RequiresPermissions("spotVerifyOrder:advance")
    @ResponseBody
    public AjaxResult advance(Long id, String remark) {
        return toAjax(spotVerifyOrderService.advance(id, remark) != null ? 1 : 0);
    }

    @Log(title = "图斑核查处置单回退", action = "rollback")
    @ApiOperation(value = "回退一档", notes = "回退一档")
    @PostMapping("/rollback")
    @RequiresPermissions("spotVerifyOrder:rollback")
    @ResponseBody
    public AjaxResult rollback(Long id, String remark) {
        return toAjax(spotVerifyOrderService.rollback(id, remark) != null ? 1 : 0);
    }
}
