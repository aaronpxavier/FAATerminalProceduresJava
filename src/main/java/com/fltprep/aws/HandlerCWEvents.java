package com.fltprep.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.fltprep.dttp.DttpDownloads;

public class HandlerCWEvents implements RequestHandler<ScheduledEvent, String> {

    @Override
    public String handleRequest(ScheduledEvent scheduledEvent, Context context) {
        DttpDownloads.dloadPdfFiles("./dttp_pdfs");
        return null;
    }
}
