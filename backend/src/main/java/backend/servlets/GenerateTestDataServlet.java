package backend.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.apis.MerchantApi;
import backend.merchants.Merchant;

/**
 * Created by Muhammad on 19/08/2017.
 */

public class GenerateTestDataServlet extends HttpServlet {
    MerchantApi merchantApi = new MerchantApi();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        merchantApi.createRandomMerchants();
        resp.getWriter().write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}
