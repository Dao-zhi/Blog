package com.daozhi.blog.web;

import com.daozhi.blog.service.BlogService;
import com.daozhi.blog.service.TagService;
import com.daozhi.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    /*@GetMapping("/{id}/{name}")
    public String index(@PathVariable Integer id, @PathVariable String name){
        //int i = 0/0;    //用于测试500页面
        //测试资源找不到异常
        *//*String Blog = null;
        if(Blog == null){
            throw new NotFoundException("博客不存在");
        }*//*
        //System.out.println("----------index----------");
        return "index";
    }*/
    /*@GetMapping()
    public String index(){
        return "index";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }*/
    //private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        //logger.info("打印博客：" + blogService.listRecommendBlogTop(3).toString());
        return "_fragments :: newBlogList";
    }
}
