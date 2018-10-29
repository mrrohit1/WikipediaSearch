package com.rohit.wikipediasearch.constant;

public class Constant {

    public static class UrlConstant {
        public static final String BASE_URL = "https://en.wikipedia.org/";
        public static final String SEARCH_URL = "w/api.php?action=query&formatversion=2&generator=prefixsearch&gpslimit=10&prop=pageimages%7Cpageterms&piprop=thumbnail&pithumbsize=150&pilimit=10&redirects=&wbptterms=description&format=json"; //&gpssearch=Albert%20Ei
        public static final String DETAIL_PAGE_URL = "https://en.wikipedia.org/?curid=";
    }

    public static class Query {
        public static final String SEARCH_QUERY = "gpssearch";
        public static final String OFFSET = "gpsoffset";
    }

}
