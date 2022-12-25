package truekai.cc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import truekai.cc.service.MsCategoryService;
import truekai.cc.vo.Result;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author truekai
 * @since 2022-12-25
 */
@RestController
@RequestMapping("/categorys")
public class MsCategoryController {

    @Autowired
    private MsCategoryService categoryService;

    @GetMapping
    public Result categories(){
        return categoryService.findAll();
    }

    @GetMapping("detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }


    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id) {
        return categoryService.categoryDetailById(id);
    }

}

