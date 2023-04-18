package niffler.api.logging;

import io.qameta.allure.attachment.AttachmentData;

public class CookiesAttachment implements AttachmentData {
    private final String name;
    private final String cookies;

    public CookiesAttachment(String name, String cookies) {
        this.name = name;
        this.cookies = cookies;
    }

    public String getCookies() {
        return cookies;
    }

    @Override
    public String getName() {
        return name;
    }
}
