package com.sydata.warn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.admin.domain.ReserveScale;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.organize.security.LoginUser;
import com.sydata.warn.domain.WarnFoodstuffMessage;
import com.sydata.warn.dto.WarnFoodstuffMessageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存数量-粮食库存异常告警Mapper接口
 *
 * @author fuql
 * @date 2023-04-28
 */
@DataPermissionExclude
public interface WarnFoodstuffMessageMapper extends BaseMapper<WarnFoodstuffMessage> {

    /**
     * 通过当前登录人获取储备规模数据
     *
     * @param param 登录人
     * @return 储备规模数据
     */
    List<ReserveScale> selectReserveScaleNoPermissionByParam(@Param("param")  LoginUser param);

    /**
     * 查询日库存数据
     *
     * @param param param
     * @return 日库存数据
     */
    WarnFoodstuffMessageDto selectStockAffiliation(@Param("param") ReserveScale param);

    /**
     * 获取储备规模数据
     *
     * @return 储备规模数据
     */
    List<ReserveScale> selectReserveScaleNoPermission();


}
