package com.jelipo.blog.ctrl

import com.jelipo.blog.service.BlogMainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/**
 * @author Jelipo
 * @date 2018/7/7 14:18
 */
@Controller
class BlogMainCtrl {

    @Autowired
    private lateinit var blogMainService: BlogMainService

    @GetMapping("/")
    fun index(modelMap: ModelMap): String {
        val wordList = blogMainService.getIndexWordList()
        modelMap["list"] = wordList
        return "index"
    }

    @GetMapping("/toNone.do")
    fun toNone(request: HttpServletRequest): String = "none"

    @GetMapping("/messages")
    fun toMessageBook(request: HttpServletRequest): String = "messageBook"

}