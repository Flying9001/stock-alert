package com.ljq.stock.alert.web.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.param.stocksource.*;
import com.ljq.stock.alert.model.vo.StockIndexVo;
import com.ljq.stock.alert.service.StockSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 股票源
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/stock/source")
@Api(value = "股票源", tags = "股票源")
public class StockSourceController {

	@Autowired
	private StockSourceService stockSourceService;

    /**
     * 查询某一支股票实时数据
     *
     * @param infoRealTimeParam
     * @return
     */
    @GetMapping(value = "/info/realTime", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "查询某一支股票实时数据",  notes = "查询某一支股票实时数据")
    public ResponseEntity<ApiResult<StockSourceEntity>> infoRealTime(@Validated StockSourceInfoRealTimeParam
            infoRealTimeParam) {
        return ResponseEntity.ok(ApiResult.success(stockSourceService.infoRealTime(infoRealTimeParam)));
    }

    /**
     * 分页查询实时股票数据
     *
     * @param listRealTimeParam
     * @return
     */
    @GetMapping(value = "/page/realTime", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "分页查询实时股票数据",  notes = "分页查询实时股票数据")
    public ResponseEntity<ApiResult<IPage<StockSourceEntity>>> pageRealTime(@Validated StockSourceListRealTimeParam
                        listRealTimeParam) {
        return ResponseEntity.ok(ApiResult.success(stockSourceService.pageRealTime(listRealTimeParam)));
    }

    /**
     * 查询股票指数实时行情
     *
     * @return
     */
    @GetMapping(value = "/index/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "查询股票指数实时行情",  notes = "查询股票指数实时行情")
    public ResponseEntity<ApiResult<List<StockIndexVo>>> queryIndexList() {
        return ResponseEntity.ok(stockSourceService.queryIndexList());
    }



}
