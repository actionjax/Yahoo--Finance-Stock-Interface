package groovy

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod

/**
 * @author actionjax
 * 
 * Credit goes to Corey Goldberg for creating ystockquote.  
 * This code below is basically a port of the same code to Groovy.
 * 
 * Copyright (c) 2007-2008, Corey Goldberg (corey@goldb.org)
 * 
 * license: GNU LGPL
 *      
 * This library is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public 
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *  
 * This is the "YahooStockQuoteInterface" module.
 * 
 * This module provides a Groovy API for retrieving stock data from Yahoo Finance.
 * 
 * sample usage:
 * >>> import ystockquote
 * >>> print ystockquote.get_price('GOOG')
 * 529.46 
 *  
 */
public class YahooStockQuoteInterface {

    private static __request(symbol, stat) {
        String url = "http://finance.yahoo.com/d/quotes.csv?s=$symbol&f=$stat"
        HttpClient client = new HttpClient()
        GetMethod get = new GetMethod(url)
        client.executeMethod(get)
        return get.getResponseBodyAsString().toString().replaceAll("\"","")
    }


    static get_all(symbol) {
        """
        Get all available quote data for the given ticker symbol.
        
        Returns a dictionary.
        """
        def values = __request(symbol, 'l1c1va2xj1b4j4dyekjm3m4rr5p5p6s7').split(',')
        def data = [:]
        data['price'] = values[0]
        data['change'] = values[1]
        data['volume'] = values[2]
        data['avg_daily_volume'] = values[3]
        data['stock_exchange'] = values[4]
        data['market_cap'] = values[5]
        data['book_value'] = values[6]
        data['ebitda'] = values[7]
        data['dividend_per_share'] = values[8]
        data['dividend_yield'] = values[9]
        data['earnings_per_share'] = values[10]
        data['52_week_high'] = values[11]
        data['52_week_low'] = values[12]
        data['50day_moving_avg'] = values[13]
        data['200day_moving_avg'] = values[14]
        data['price_earnings_ratio'] = values[15]
        data['price_earnings_growth_ratio'] = values[16]
        data['price_sales_ratio'] = values[17]
        data['price_book_ratio'] = values[18]
        data['short_ratio'] = values[19]
        return data
    }
        
    static get_price(symbol) {
        return __request(symbol, 'l1')
    }

    static get_change(symbol) {
        return __request(symbol, 'c1')
    }
        
    static get_volume(symbol) {
        return __request(symbol, 'v')
    }

    static get_avg_daily_volume(symbol) {
        return __request(symbol, 'a2')
    }
        
    static get_stock_exchange(symbol) {
        return __request(symbol, 'x')
    }
        
    static get_market_cap(symbol) {
        return __request(symbol, 'j1')
    }
       
    static get_book_value(symbol) {
        return __request(symbol, 'b4')
    }

    static get_ebitda(symbol) { 
        return __request(symbol, 'j4')
    }
        
    static get_dividend_per_share(symbol) {
        return __request(symbol, 'd')
    }

    static get_dividend_yield(symbol) { 
        return __request(symbol, 'y')
    }
        
    static get_earnings_per_share(symbol) { 
        return __request(symbol, 'e')
    }

    static get_52_week_high(symbol) { 
        return __request(symbol, 'k')
    }
        
    static get_52_week_low(symbol) { 
        return __request(symbol, 'j')
    }

    static get_50day_moving_avg(symbol) { 
        return __request(symbol, 'm3')
    }
        
    static get_200day_moving_avg(symbol) { 
        return __request(symbol, 'm4')
    }
        
    static get_price_earnings_ratio(symbol) { 
        return __request(symbol, 'r')
    }

    static get_price_earnings_growth_ratio(symbol) { 
        return __request(symbol, 'r5')
    }
    
    static get_price_sales_ratio(symbol) { 
        return __request(symbol, 'p5')
    }
        
    static get_price_book_ratio(symbol) { 
        return __request(symbol, 'p6')
    }
           
    static get_short_ratio(symbol) { 
        return __request(symbol, 's7')
    }
        
    static get_historical_prices(symbol, start_date, end_date) {
        """
        Get historical prices for the given ticker symbol.
        Date format is 'YYYYMMDD'
        
        Returns a nested list.
        """
        
        int end_day = Integer.valueOf(end_date[4..5] - 1)
        def end_month = end_date[6..7]
        def end_year = end_date[0..3]
        
        int start_day = Integer.valueOf(start_date[4..5] - 1)
        def start_month = start_date[6..7]
        def start_year = start_date[0..3]
        
        String url = "http://ichart.yahoo.com/table.csv?s=$symbol&" + 
        "d=$end_day&" +
        "e=$end_month&" +
        "f=$end_year&" +
        "g=d&" + 
        "a=$start_day&" +
        "b=$start_month&" +
        "c=$start_year&"+
        "ignore=.csv"
        
        HttpClient client = new HttpClient()
        GetMethod get = new GetMethod(url)
        client.executeMethod(get)
        BufferedReader reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream()))
        ArrayList<String> data = new ArrayList<String>()
        reader.eachLine( { day-> data.add(day.split(","))} )  
        return data
    }

//    /**
//     * @param args
//     */
//    public static void main(def args) {
//        for(datum in YahooStockQuoteInterface.get_historical_prices("GOOG","20070101","20080101"))
//            println datum    }
}

