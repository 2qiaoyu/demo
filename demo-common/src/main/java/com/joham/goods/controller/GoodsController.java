package com.joham.goods.controller;

import com.joham.annotation.RepeatSubmitValidate;
import com.joham.excel.ExportGoodsList;
import com.joham.goods.bean.Goods;
import com.joham.goods.bean.GoodsBrand;
import com.joham.goods.service.GoodsBrandService;
import com.joham.goods.service.GoodsService;
import com.joham.util.PageBean;
import com.joham.util.SelectBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GoodsController {

    @Resource(name = "GoodsService")
    private GoodsService goodsService;
    @Resource(name = "GoodsBrandService")
    private GoodsBrandService goodsBrandService;

    /**
     * 获得商品列表
     *
     * @param request
     * @param response
     * @param pb
     * @param selectBean
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView findGoodsList(HttpServletRequest request, HttpServletResponse response, PageBean pb, SelectBean selectBean) {
        //判断查询文本是否为空 若为空 将条件也设置为空
        if ("".equals(selectBean.getSearchText())) {
            selectBean.setCondition("");
        }

        //参数设置到作用域中
        request.setAttribute("selectBean", selectBean);
        return new ModelAndView("jsp/list", "pb", this.goodsService.findGoodsList(pb, selectBean));
    }

    /**
     * 删除商品
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("/delGoods")
    public ModelAndView delGoods(Long goodsId) {
        goodsService.deleteGoodsById(goodsId);
        return new ModelAndView(new RedirectView("list.htm"));
    }

    /**
     * 跳转到修改页面
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("/toModifyGoods")
    @RepeatSubmitValidate(create=true)
    public ModelAndView toModifyGoods(Long goodsId) {
        List<GoodsBrand> list = new ArrayList<GoodsBrand>();
        try {
            list = goodsBrandService.findGoodsBrandList();
            return new ModelAndView("jsp/update")
                    .addObject("goodsId", goodsId)
                    .addObject("brandList", list);
        } catch (Exception exception) {
            return new ModelAndView("error");
        }
    }

    /**
     * 通过id获得商品信息
     *
     * @param goodsId
     * @return
     */
    @RequestMapping("/findGoodsByGoodsId")
    @ResponseBody
    public Goods findGoodsByGoodsId(Long goodsId) {
        return goodsService.findGoodsByGoodsId(goodsId);
    }

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    @RequestMapping("/updateGoods")
    @RepeatSubmitValidate(destroy=true)
    public ModelAndView updateGoods(Goods goods) {
        this.goodsService.updateGoods(goods);
        System.out.println("执行了一次");
        return new ModelAndView(new RedirectView("list.htm"));
    }

    /**
     * 批量删除商品
     *
     * @param goodsIds
     * @return
     */
    @RequestMapping("/batchDelGoods")
    public ModelAndView batchDelGoods(Long[] goodsIds) {
        this.goodsService.batchDeleteGoods(goodsIds);
        return new ModelAndView(new RedirectView("list.htm"));
    }

    /**
     * 获得品牌列表
     *
     * @return
     */
    @RequestMapping("/getgoodsBrandList")
    @ResponseBody
    public List<GoodsBrand> getgoodsBrandList() {
        return this.goodsBrandService.findGoodsBrandList();
    }

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    @RequestMapping("/addGoods")
    @RepeatSubmitValidate(create=true)
    public ModelAndView addGoods(@Valid Goods goods) {
        goods.setGoodsDelflag("0");
        this.goodsService.addGoods(goods);
        return new ModelAndView(new RedirectView("list.htm"));
    }

    /**
     * 导出全部商品信息
     *
     * @return
     */
    @RequestMapping("/exportGoodsList")
    public void exportGoodsList(HttpServletResponse response) {
        ExportGoodsList.exportGoodsList(
                this.goodsService.queryAllGoodsToExport(), response);
    }

    /**
     * 导出当页商品信息
     */
    @RequestMapping("/exportGoodsCheck")
    public void exportGoodsCheck(HttpServletResponse response, Long[] goodsIds) {
        ExportGoodsList.exportGoodsList(this.goodsService.queryGoodsListVoListForExportByGoodsIds(goodsIds), response);
    }
}

