package com.konvi.utils;

import com.konvi.vo.ResultVO;

/**
 * HTTP 请求返回的最外层对象ResultVO工具类 --ResultVOUtil
 * @author konvi
 * @version 1.0
 * @date 2021/8/10
 */
public class ResultVOUtil
{
    public static ResultVO success(Object object)
    {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(Integer code, String msg)
    {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO success()
    {
        return success(null);
    }
}
