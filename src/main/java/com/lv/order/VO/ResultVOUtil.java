package com.lv.order.VO;

/**
 * @program: order
 * @Date: 2019/1/25 9:44
 * @Author: Mr.Deng
 * @Description:
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
}
