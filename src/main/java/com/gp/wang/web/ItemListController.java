package com.gp.wang.web;

import com.gp.wang.domain.Item;
import com.gp.wang.domain.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by gwang on 10/13/2016.
 */
@Controller
public class ItemListController {

    @Autowired
    ItemRepository itemRepository;


    @RequestMapping("/items")
    public String getEvents(ModelMap model, @RequestParam(name="keyword", required=false) String keyword) {

        final List<Item> items = itemRepository.findAll();


        model.put("items", items);
        return "index";

    }



}
