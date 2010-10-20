package com.redhat.jboss.support.debug.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class DumpStackTraceFilter extends Filter {
   private static Logger log = Logger.getLogger(DumpStackTraceFilter.class);
   private String stringToMatch = null;
   public void setStringToMatch(String stringToMatch) {
       this.stringToMatch = stringToMatch;
   }
   public String getStringToMatch() {
       return stringToMatch;
   }
   public int decide(LoggingEvent event) {
       String msg = event.getRenderedMessage();
       if (msg == null ||  stringToMatch == null) {
           return Filter.NEUTRAL;
       }
       if (msg.indexOf(stringToMatch) != -1) {
           log.info("Found message: " + msg, new Exception());
       }
       return Filter.NEUTRAL;
   }
} 
