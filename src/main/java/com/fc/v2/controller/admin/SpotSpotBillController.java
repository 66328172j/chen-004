package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSpotSpotBill;
import com.fc.v2.service.ITSpotSpotBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 卫片图斑建档单 Controller
 *
 * @author fuce
 * @date 2026-09-12
 */
@Api(value = "卫片图斑建档单")
@Controller
@RequestMapping("/SpotSpotBillController")
public class SpotSpotBillController extends BaseController {

    private final String prefix = "admin/spotSpotBill";

    @Autowired
    private ITSpotSpotBillService spotSpotBillService;

    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("spot:spotSpotBill:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    @Log(title = "卫片图斑建档单集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("spot:spotSpotBill:list")
    @ResponseBody
    public ResultTable list(TSpotSpotBill record) {
        QueryWrapper<TSpotSpotBill> queryWrapper = new QueryWrapper<TSpotSpotBill>();
        startPage();
        com.github.pagehelper.PageInfo<TSpotSpotBill> page =
                new com.github.pagehelper.PageInfo<TSpotSpotBill>(spotSpotBillService.selectTSpotSpotBillList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    @Log(title = "卫片图斑建档单新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("spot:spotSpotBill:add")
    @ResponseBody
    public AjaxResult add(TSpotSpotBill record) {
        return toAjax(spotSpotBillService.insertTSpotSpotBill(record));
    }

    @Log(title = "卫片图斑建档单修改", action = "edit")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @PostMapping("/edit")
    @RequiresPermissions("spot:spotSpotBill:edit")
    @ResponseBody
    public AjaxResult editSave(TSpotSpotBill record) {
        return toAjax(spotSpotBillService.updateTSpotSpotBill(record));
    }

    @Log(title = "卫片图斑建档单删除", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("spot:spotSpotBill:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(spotSpotBillService.deleteTSpotSpotBillByIds(ids));
    }
}
