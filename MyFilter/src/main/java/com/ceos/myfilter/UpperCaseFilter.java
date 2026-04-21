package com.ceos.myfilter;

import com.ceos.textfilter.TextFilter;
import org.openide.util.lookup.ServiceProvider;
/**
 *
 * @author Starblend
 */
@ServiceProvider(service=TextFilter.class)
public class UpperCaseFilter implements TextFilter {
    @Override
    public String process(String s) {
        return s.toUpperCase();
    }
} 