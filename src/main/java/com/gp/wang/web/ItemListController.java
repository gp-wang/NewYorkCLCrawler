package com.gp.wang.web;

import com.gp.wang.domain.Item;
import com.gp.wang.domain.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gwang on 10/13/2016.
 */
@Controller
@SessionAttributes({"itemListCommand", "items"})
public class ItemListController {

    @Autowired
    ItemRepository itemRepository;


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String getEvents(ModelMap model, HttpServletRequest request) {
        ItemListCommand itemListCommand = new ItemListCommand();

        String page = request.getParameter("page");
        PagedListHolder<Item> pagedItems;
        if ("previous".equals(page)) {
            pagedItems = (PagedListHolder<Item>) request.getSession().getAttribute("pagedProductList");
            pagedItems.previousPage();
        } else if ("next".equals(page)) {
            pagedItems = (PagedListHolder<Item>) request.getSession().getAttribute("pagedProductList");
            pagedItems.nextPage();
        } else {


            pagedItems = new PagedListHolder<>(itemRepository.findAll());
            pagedItems.setPageSize(10);
            request.getSession().setAttribute("pagedProductList", pagedItems);

        }



        model.put("itemListCommand", itemListCommand);
        model.put("items", pagedItems);
        return "index";

    }


    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public String onSubmit(ModelMap model, HttpServletRequest request, @ModelAttribute ItemListCommand itemListCommand, @ModelAttribute PagedListHolder<Item> items) {

        PagedListHolder<Item> pagedItems;

        List<Item> itemsList = null;
        String keyword = itemListCommand.getKeyword();
        if(keyword == null || keyword.isEmpty()) {
            itemsList = itemRepository.findAll();
        } else {
            itemsList = itemRepository.findAll().stream().filter(it->it.getTitle() != null && it.getTitle().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
        }

        items = new PagedListHolder<>(itemsList);
        items.setPageSize(10);
        request.getSession().setAttribute("pagedProductList", items);

        model.put("items", items);
        return "index";

    }



}
