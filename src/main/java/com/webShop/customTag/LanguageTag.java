package com.webShop.customTag;

import com.webShop.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LanguageTag extends TagSupport {
    private static final String LANGUAGE_OPTION = "<option value=\"%s\" %s>%s</option>";
    private static final Logger LOG = LogManager.getLogger(LanguageTag.class);
    private static final String LANGUAGE_SELECTOR = "WEB-INF/jsp/language.jsp";

    @Override
    public int doStartTag() throws JspException {
        LOG.trace("LanguageTag start");
        List<Locale> supportedLocales = (List<Locale>) pageContext.getServletContext().getAttribute(Parameters.SUPPORTED_LOCALES);
        Locale currLocale = pageContext.getRequest().getLocale();
        LOG.info("currLocale: " + currLocale);
        JspWriter writer = pageContext.getOut();
        String languageSelector = readFile(LANGUAGE_SELECTOR);
        StringBuilder options = new StringBuilder("");
        try {
            for (Locale locale : supportedLocales) {
                options.append(String.format(LANGUAGE_OPTION,
                        locale.toLanguageTag(),
                        locale.getLanguage().equals(currLocale.getLanguage()) ? "selected" : "",
                        locale.getDisplayLanguage(Locale.ENGLISH))).append("\n");
            }
            writer.write(String.format(languageSelector, options));
        } catch (IOException exception) {
            LOG.error("Cannot write language selector");
        }
        return SKIP_BODY;
    }

    private String readFile(String path) {
        String text = null;
        path = pageContext.getServletContext().getRealPath(path);
        try {
            text = Files.lines(Paths.get(path), StandardCharsets.UTF_8).
                    collect(Collectors.joining("\n"));
        } catch (IOException exception) {
            LOG.error("Cannot read file", exception);
        }
        return text;
    }
}
