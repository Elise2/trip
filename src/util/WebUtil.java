
package util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
    public WebUtil() {
    }

    public static String getServerUrl(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath;
    }
//
//    public static PagerBean getPagerParams(HttpServletRequest request) {
//        String page = request.getParameter("page");
//        String rows = request.getParameter("rows");
//        if(page == null || page.equals("")) {
//            page = "1";
//        }
//
//        if(rows == null || rows.equals("")) {
//            rows = "10";
//        }
//
//        int ipage = Integer.parseInt(page);
//        int irows = Integer.parseInt(rows);
//        PagerBean pb = new PagerBean();
//        pb.setPage(ipage);
//        pb.setRows(irows);
//        return pb;
//    }
}
