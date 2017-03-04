package com.gp.wang.reader;

import com.gp.wang.domain.Item;
import com.gp.wang.domain.ItemRepository;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;


public class CLItemReader extends AbstractHtmlItemReader {

    public final Logger LOGGER = Logger.getLogger(CLItemReader.class);
    public static final int MAX_TRIALS = 20;

    protected  String MAIN_ENTRY_URI="https://newyork.craigslist.org/search/bka";

    //@Autowired
    ItemRepository itemRepository;

    public CLItemReader(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    protected void init() {
        //clear previous data
        itemRepository.deleteAll();
    }

    @Override
    protected void processData() {

        String html = null;
        String url = null;
        int cnt = 0;
        while(cnt < 1000) {
            try {
                url = MAIN_ENTRY_URI + "?s=" + String.valueOf(cnt);
                html = Jsoup.connect(url).get().toString();
            } catch (IOException e) {
                LOGGER.warn("failed to get document from url: " + url);

            }
            Document htmlDoc = Jsoup.parse(html, "", Parser.htmlParser());
            for (Element li : htmlDoc.select("ul.rows>li")) {
                if(cnt >= 1000) break;

                Item item = new Item();
                Elements titles = li.select("a.result-title");
                if(!titles.isEmpty()) {
                    item.setTitle(titles.get(0).text());
                    item.setUrl(titles.get(0).attr("href"));
                    item.setClId(Long.valueOf(titles.get(0).attr("data-id")));
                }
                Elements prices = li.select("span.result-price");
                if(!prices.isEmpty()){
                    item.setPrice(Double.valueOf(prices.get(0).text().replace("$", "")));
                }
                item.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));
                try {
                    itemRepository.save(item);
                    cnt ++;
                } catch (Exception e) {
                    LOGGER.info("Could not save " + item.toString());
                }



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
