package com.example.demo.bishnu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convertor {
  public static Date stringToDate(String value) throws ParseException {

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     // Date date = sdf.parse(value);
        return sdf.parse(value);
    } 
}


