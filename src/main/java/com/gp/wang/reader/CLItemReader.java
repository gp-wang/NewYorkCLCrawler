package com.gp.wang.reader;

import com.gp.wang.domain.ItemRepository;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.IOException;


public class CLItemReader extends AbstractHtmlItemReader {

    public final Logger LOGGER = Logger.getLogger(CLItemReader.class);
    public static final int MAX_TRIALS = 20;

    protected  String MAIN_ENTRY_URI="https://newyork.craigslist.org/search/bka";

    public Integer cnt = 0;

    ItemRepository itemRepository;

    public CLItemReader(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void processData() {



        String html = null;
        String url = null;


        while(cnt < 1000) {
            try {
                url = MAIN_ENTRY_URI + "?s=" + String.valueOf(cnt);
                html = Jsoup.connect(url).get().toString();
            } catch (IOException e) {
                LOGGER.warn("failed to get document from url: " + url);

            }
            Document htmlDoc = Jsoup.parse(html, "", Parser.htmlParser());
            for (Element li : htmlDoc.select("ul.row>li")) {
                LOGGER.info(li.html());
                cnt ++;
            }
        }




    }


    @Override
    public void readData()  {
        try {
            if(!initialized.get()) {
                LOGGER.info("Start to initialize");
                init();
                initialized.set(true);
            }

            processData();
            //getTrainSet();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
