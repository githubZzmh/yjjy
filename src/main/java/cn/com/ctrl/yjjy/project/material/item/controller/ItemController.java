package cn.com.ctrl.yjjy.project.material.item.controller;
import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
import cn.com.ctrl.yjjy.project.material.item.domain.Item;
import cn.com.ctrl.yjjy.project.material.item.service.ItemService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
/**
 * 设备明细操作处理
 *
 * @author zzmh
 * @date 2018-11-28
 */
@Data
@Log4j2
@Controller
@RequestMapping("/material/item")
public class ItemController extends BaseController {
    private String prefix = "/material/item";
    @Autowired
    private ItemService itemService;
    @RequiresPermissions("material:item:view")
    @GetMapping()
    public String view(ModelMap mmap) {
        mmap.put("transport",itemService.selectItemTransport());
        mmap.put("storage",itemService.selectItemStorage());
        mmap.put("liable",itemService.selectItemLiable());
        return prefix + "/view" ;
    }
    /**
     * 查询设备明细列表
     */
    @RequiresPermissions("material:item:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Item item) {
        startPage();
        List<Item> list = itemService.selectItemList(item);
        return getDataTable(list);
    }
    /**
     * 新增设备明细
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        return prefix + "/add";
    }
    /**
     * 新增保存设备明细
     */
    @RequiresPermissions("material:item:view")
    @Log(title = "设备明细" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Item item) {
        String uuid = UUID.randomUUID().toString();
        item.setId(uuid);
        return toAjax(itemService.insertItem(item));
    }
    /**
     * 删除设备明细
     */
    @RequiresPermissions("material:item:view")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(itemService.deleteItemById(Arrays.asList(ids.split(","))));
    }
    /**
     * 修改设备明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap) {
        Item item = itemService.selectItemById(id);
        mmap.put("item" , item);
        return prefix + "/edit" ;
    }
    /**
     * 修改保存设备明细
     */
    @RequiresPermissions("material:item:view")
    @Log(title = "设备明细" , businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Item item) {
        return toAjax(itemService.updateItem(item));
    }
}