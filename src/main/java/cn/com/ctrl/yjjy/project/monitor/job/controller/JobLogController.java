//package cn.com.ctrl.yjjy.project.monitor.job.controller;
//
//import cn.com.ctrl.yjjy.framework.aspectj.lang.annotation.Log;
//import cn.com.ctrl.yjjy.framework.aspectj.lang.enums.BusinessType;
//import cn.com.ctrl.yjjy.project.monitor.job.service.IJobLogService;
//import cn.com.ctrl.yjjy.common.utils.poi.ExcelUtil;
//import cn.com.ctrl.yjjy.framework.web.controller.BaseController;
//import cn.com.ctrl.yjjy.framework.web.domain.AjaxResult;
//import cn.com.ctrl.yjjy.framework.web.page.TableDataInfo;
//import cn.com.ctrl.yjjy.project.monitor.job.domain.JobLog;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
///**
// * 调度日志操作处理
// *
// * @author yjjy
// */
//@Controller
//@RequestMapping("/monitor/jobLog")
//public class JobLogController extends BaseController {
//    private String prefix = "monitor/job" ;
//
//    @Autowired
//    private IJobLogService jobLogService;
//
//    @RequiresPermissions("monitor:job:view")
//    @GetMapping()
//    public String jobLog() {
//        return prefix + "/jobLog" ;
//    }
//
//    @RequiresPermissions("monitor:job:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public TableDataInfo list(JobLog jobLog) {
//        startPage();
//        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
//        return getDataTable(list);
//    }
//
//    @Log(title = "调度日志" , businessType = BusinessType.EXPORT)
//    @RequiresPermissions("monitor:job:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(JobLog jobLog) {
//        List<JobLog> list = jobLogService.selectJobLogList(jobLog);
//        ExcelUtil<JobLog> util = new ExcelUtil<JobLog>(JobLog.class);
//        return util.exportExcel(list, "jobLog");
//    }
//
//    @Log(title = "调度日志" , businessType = BusinessType.DELETE)
//    @RequiresPermissions("monitor:job:remove")
//    @PostMapping("/remove")
//    @ResponseBody
//    public AjaxResult remove(String ids) {
//        return toAjax(jobLogService.deleteJobLogByIds(ids));
//    }
//
//    @Log(title = "调度日志" , businessType = BusinessType.CLEAN)
//    @RequiresPermissions("monitor:job:remove")
//    @PostMapping("/clean")
//    @ResponseBody
//    public AjaxResult clean() {
//        jobLogService.cleanJobLog();
//        return success();
//    }
//}
