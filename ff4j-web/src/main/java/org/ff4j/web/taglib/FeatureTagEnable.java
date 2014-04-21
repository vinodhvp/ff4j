package org.ff4j.web.taglib;

/*
 * #%L FlipTag.java (ff4j-web) by Cedrick LUNVEN %% Copyright (C) 2013 Ff4J %% Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. #L%
 */

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FeatureStore;

/**
 * Taglib to filter display based on {@link Feature} status within {@link FeatureStore}.
 * 
 * <p>
 * Sample use
 * <p>
 * 
 * <pre>
 * &lt;ff4j:enable featureid="mercure-desc"&gt;
 * here your html code
 *  &lt;/ff4j:enable@gt;
 * 
 * @author <a href="mailto:cedrick.lunven@gmail.com">Cedrick LUNVEN</a>
 */
public class FeatureTagEnable extends TagSupport {

    /** serial number. */
    private static final long serialVersionUID = -4924423673988080781L;

    /** attribute name. */
    public static final String FF4J_SESSIONATTRIBUTE_NAME = "FF4J";

    /** Error message constant. */
    private static final String ERROR_MSG_START = "<p><span style=\"color:red;font-weight:bold\">ERROR &lt;ff4j:*&gt; :";

    /** Error message constant. */
    private static final String ERROR_MSG_END = "</span>";

    /** Injected by JSP itSelf. */
    private String featureid = "";

    /** FF4j bean name. */
    private final String ff4jAttributeName = FF4J_SESSIONATTRIBUTE_NAME;

    /**
     * Default constructor.
     */
    public FeatureTagEnable() {}

    /** {@inheritDoc} */
    @Override
    public int doStartTag() throws JspException {
        try {

            FF4j ff4j = (FF4j) pageContext.findAttribute(getFf4jAttributeName());

            // Handle where no ff4j available
            if (ff4j == null) {
                pageContext.getOut().println(
                        ERROR_MSG_START + " Cannot find FF4J bean as attribute (" + getFf4jAttributeName() + ") in any scope."
                                + ERROR_MSG_END);
                return SKIP_BODY;
            }

            // Handle where feature doe not exist
            if (!ff4j.exist(getFeatureid())) {
                pageContext.getOut().println(
                        ERROR_MSG_START + " Cannot find feature (" + getFeatureid() + ") anywhere." + ERROR_MSG_END);
                return SKIP_BODY;
            }

            // Everytihing is OK
            if (ff4j.isFlipped(getFeatureid())) {
                return EVAL_BODY_INCLUDE;
            }
        } catch (IOException ioe) {
            throw new JspException("Error occur when processing TAG FF4J", ioe);
        }
        return SKIP_BODY;
    }

    /**
     * Getter accessor for attribute 'featureid'.
     * 
     * @return current value of 'featureid'
     */
    public String getFeatureid() {
        return featureid;
    }

    /**
     * Setter accessor for attribute 'featureid'.
     * 
     * @param featureid
     *            new value for 'featureid '
     */
    public void setFeatureid(String featureid) {
        this.featureid = featureid;
    }

    /**
     * Getter accessor for attribute 'ff4jAttributeName'.
     * 
     * @return current value of 'ff4jAttributeName'
     */
    public String getFf4jAttributeName() {
        return ff4jAttributeName;
    }

}
