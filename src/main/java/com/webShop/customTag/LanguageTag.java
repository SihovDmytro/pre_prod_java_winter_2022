package com.webShop.customTag;

import com.webShop.listener.ContextListener;
import com.webShop.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LanguageTag extends TagSupport {
    private static final String START_TAG = "<select id=\"select-lang\" name=\"select-lang\" onchange=\"changeLanguage()\">";
    private static final String LANGUAGE_OPTION = "<option value=\"%s\" %s>%s</option>";
    private static final String END_TAG = "</select>";
    private static final Logger LOG = LogManager.getLogger(LanguageTag.class);

    @Override
    public int doStartTag() throws JspException {
        LOG.trace("LanguageTag start");
        List<Locale> supportedLocales = (List<Locale>) pageContext.getServletContext().getAttribute(Parameters.SUPPORTED_LOCALES);
        Locale currLocale = pageContext.getRequest().getLocale();
        LOG.info("currLocale: " + currLocale);
        JspWriter writer = pageContext.getOut();
        try {
            writer.write(START_TAG);

            for (Locale locale : supportedLocales) {
                writer.write(String.format(LANGUAGE_OPTION, locale.toLanguageTag(), locale.getLanguage().equals(currLocale.getLanguage()) ? "selected" : "", locale.getDisplayLanguage(Locale.ENGLISH)));
            }
            writer.write(END_TAG);
        } catch (IOException exception) {
            LOG.error("Cannot write language selector");
        }
        return SKIP_BODY;
    }
}
