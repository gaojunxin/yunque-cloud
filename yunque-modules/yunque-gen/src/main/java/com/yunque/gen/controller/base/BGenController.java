package com.yunque.gen.controller.base;

import com.yunque.common.core.constant.basic.BaseConstants;
import com.yunque.common.web.entity.controller.BaseController;
import com.yunque.gen.domain.dto.GenTableDto;
import com.yunque.gen.domain.query.GenTableQuery;
import com.yunque.gen.service.IGenTableColumnService;
import com.yunque.gen.service.IGenTableService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 代码生成管理 业务处理
 *
 * @author xueyi
 */
public class BGenController extends BaseController<GenTableQuery, GenTableDto, IGenTableService> {

    @Autowired
    protected IGenTableColumnService subService;

    /**
     * 定义节点名称
     */
    @Override
    protected String getNodeName() {
        return "业务表";
    }

    /**
     * 生成zip文件
     */
    protected void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"yunque.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 前置校验 增加/修改
     */
    @Override
    protected void AEHandle(BaseConstants.Operate operate, GenTableDto table) {
        if (operate.isEdit())
            baseService.validateEdit(table);
    }
}
