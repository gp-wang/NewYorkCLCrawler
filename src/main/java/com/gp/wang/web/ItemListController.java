package com.gp.wang.web;

import com.gp.wang.domain.Item;
import com.gp.wang.domain.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by gwang on 10/13/2016.
 */
@Controller
public class ItemListController {

    @Autowired
    ItemRepository itemRepository;


    @RequestMapping("/items")
    @ResponseBody
    public List<Item> getEvents(ModelMap model, @RequestParam(name="cutoff", required=false) @DateTimeFormat(pattern="yyyyMMdd'T'HHmmss'Z'") Date cutoff) {

        final List<Item> allItems = itemRepository.findAll();
        return allItems;

    }



}
