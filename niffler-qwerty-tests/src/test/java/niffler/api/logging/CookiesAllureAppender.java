package niffler.api.logging;

import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;

public class CookiesAllureAppender {

    private final static String cookiesTemplatePath = "cookies-value.ftl";

    private final AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();

    public void addAttachment(CookiesAttachment attachment) {
        processor.addAttachment(attachment, new FreemarkerAttachmentRenderer(cookiesTemplatePath));
    }
}
