package com.joham.common;

import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.AbstractCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

@Controller("capthcaControllerSite")
public class CapthcaController {
  
    @RequestMapping("/patchca")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        MyCaptchaService cs = new MyCaptchaService();
        resp.setContentType("image/png");
        resp.setHeader("cache", "no-cache");
        HttpSession session = req.getSession(true);
        OutputStream os = resp.getOutputStream();
        String patcha = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);
        
        session.setAttribute("PATCHCA", patcha);
        os.flush();
        os.close();
        cs = null;
       
    }

    @RequestMapping("/patchcaSession")
    @ResponseBody
    protected String dopost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        return (String)req.getSession().getAttribute("PATCHCA");
        
       
    }
    private class MyCaptchaService extends AbstractCaptchaService{
        
        public MyCaptchaService(){
            String[] fontOption = {"Verdana","Tahoma"};
            wordFactory = new MyWordFactory();
            fontFactory = new RandomFontFactory(THIRTYONE,fontOption);
            textRenderer = new BestFitTextRenderer();
            backgroundFactory = new SingleColorBackgroundFactory();
            colorFactory = new SingleColorFactory(new Color(TWENTYFIVE,SIXTY,OSZ));
            filterFactory = new CurvesRippleFilterFactory(colorFactory);
            width = NINETY;
            height = THIRTY;
            
        }
    }
    private class MyWordFactory extends RandomWordFactory{
        
        public MyWordFactory(){
//            characters = "absdekmnowx23456789";
            characters = "123456789";
            minLength = FIVE;
            maxLength = SIX;
        }
    }    
//    private static final int FIVE = 5;
    private static final int FIVE = 4;
//    private static final int SIX = 6;
    private static final int SIX = 4;
    private static final int THIRTYONE = 31;
    private static final int TWENTYFIVE = 25;
    private static final int THIRTY = 30;
    private static final int SIXTY = 60;
    private static final int NINETY = 90;
    private static final int OSZ = 170;
}
