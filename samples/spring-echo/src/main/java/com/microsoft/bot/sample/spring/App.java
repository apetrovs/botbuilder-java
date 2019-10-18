package com.microsoft.bot.sample.spring;

import java.util.Arrays;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.connector.ConnectorClient;
import com.microsoft.bot.connector.rest.RestConnectorClient;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.Attachment;
import com.microsoft.bot.schema.BasicCard;
import com.microsoft.bot.schema.CardImage;
import com.microsoft.bot.schema.Fact;
import com.microsoft.bot.schema.ReceiptCard;
import com.microsoft.bot.schema.ReceiptItem;
import com.microsoft.bot.schema.adaptiveacard.AdaptiveCard;
import com.microsoft.bot.schema.adaptiveacard.Container;
import com.microsoft.bot.schema.adaptiveacard.FontSize;
import com.microsoft.bot.schema.adaptiveacard.FontWeight;
import com.microsoft.bot.schema.adaptiveacard.TextBlock;

public class App {

    public static void main(String[] args) {
//        String clientId = "efba4d95-b604-4c38-b8a2-a20d7a570273";
//        String clientSecret = "0A_l+2*cEyk_e8FGQoR2-a*0XjA-WLQa";
        String serviceUrl = "https://smba.trafficmanager.net/amer/";
//        String activityId = "1570464747224";
        String message = "hello!";
        String conversationRef = "a:1_0z0jpPPzVMTQ6eUXIeTJXWRF-_YMdunIUK4f4odb4Ltre0lrL14Sk1WwQURmlXHeJFZdzgHqoLOHCqF7GX-_LBh3FI0Y0yCKOICw6sZjB4DDBbI0-R7CtZZgmtljWEm";
//        String userRef = "29:1y6463kcH7vXKB6KJdeJqn3bk8oNIK39VDjQpRMXwBiI4VgNdkAsM98v_rPzW2A9GGJGa9-wuNOU4AGWmzWN8cg";
//        String tenant = "12ea2ed9-19bc-4a19-81a0-2a77e923221d";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6ImFQY3R3X29kdlJPb0VOZzNWb09sSWgydGlFcyIsImtpZCI6ImFQY3R3X29kdlJPb0VOZzNWb09sSWgydGlFcyJ9.eyJhdWQiOiJodHRwczovL2FwaS5ib3RmcmFtZXdvcmsuY29tIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvZDZkNDk0MjAtZjM5Yi00ZGY3LWExZGMtZDU5YTkzNTg3MWRiLyIsImlhdCI6MTU3MDYzMTAzOSwibmJmIjoxNTcwNjMxMDM5LCJleHAiOjE1NzA2MzQ5MzksImFpbyI6IjQyVmdZRkQ4Y2FiMm5sb3FFL3UvWE8rNFZSK0xBQT09IiwiYXBwaWQiOiJlZmJhNGQ5NS1iNjA0LTRjMzgtYjhhMi1hMjBkN2E1NzAyNzMiLCJhcHBpZGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9kNmQ0OTQyMC1mMzliLTRkZjctYTFkYy1kNTlhOTM1ODcxZGIvIiwidGlkIjoiZDZkNDk0MjAtZjM5Yi00ZGY3LWExZGMtZDU5YTkzNTg3MWRiIiwidXRpIjoiQUJrMUxCYkotMGFvbU9FWENsY2FBQSIsInZlciI6IjEuMCJ9.V2-_vWwg9w4PaMT079MFUJ7CyxagUezd-DK2hXyebZNV0kccHU9-vYGWx7nfkk24JR8DFfXAluzAQsuN2Lck85i3w3iLoNMaSUMtMDpQEdPZWsXLE_wG3uQqrSpL9PtkhI6HWeR_9ESMjDjmCmr541-9FOEPUqjsYZboAJ0no78Cyfnu4Yvvj2jFszZnguqpYyXd94ABBS2KmPRGvfBliZ3xo1ejXsPaqxlorpEei9AWmen0AXfoiD8M4GIsJNyC2LLog8URDHZATW-y5OuGVowKUKFBt_oro6lIlg6hLHrUQ8N1z2JjmrSFwcTjtIw28hr01ABdvplIa1ajW7ZAhg";

        TokenCredentials botToken = new TokenCredentials(token);
        ConnectorClient connector = new RestConnectorClient(serviceUrl, botToken);


        Attachment attachment = bookingConfirmationCard();
        Activity activity = MessageFactory.attachment(attachment, message);

//        Activity activity = MessageFactory.text(message);
        activity.setTimestamp(null);

        connector.getConversations()
            .sendToConversation(conversationRef, activity).join();


//        MicrosoftAppCredentials credentials = new MicrosoftAppCredentials(clientId, clientSecret);
//        ConnectorClient connector = new RestConnectorClient(serviceUrl, credentials);
//
//        Activity activity = MessageFactory.text(message);
//        // It's workaround for serialization  problem. Remove when microsoft fix it
//        activity.setTimestamp(null);
//
//        connector.getConversations()
//            .sendToConversation(conversationRef, activity)
//            .handle((r, e) -> {
//                if (e != null) {
//                    e.printStackTrace();
//                    return 0;
//                }
//
//                return 1;
//            }).join();
    }

    private static Attachment bookingConfirmationCard() {


        TextBlock titleText = new TextBlock();
        titleText.setText("Processing | Booking ABCDFG | Flight to SFO");
        titleText.setWeight(FontWeight.Bolder);
        titleText.setSize(FontSize.Medium);

        Container titleContainer = new Container();
        titleContainer.setItems(Arrays.asList(titleText));

        AdaptiveCard card = new AdaptiveCard();
        card.setBody(Arrays.asList(
            titleContainer
        ));

        Attachment attachment = new Attachment();
        attachment.setContent(card);
        attachment.setContentType("application/vnd.microsoft.card.adaptive");

        return attachment;
    }

    // Doesn't work
    private static Attachment basicCard() {
//        application/vnd.microsoft.card.adaptive

        BasicCard card = new BasicCard();
        card.setTitle("Title");
        card.setSubtitle("Subtitle");
        card.setText("Lorem ipsum");


        CardImage cardImage = new CardImage();
        cardImage.setUrl("https://static.flygrn.com/airlines/UA_128.png");
        card.setImages(Arrays.asList(cardImage));

        Attachment attachment = new Attachment();
        attachment.setContent(card);
        attachment.setContentType("application/vnd.microsoft.card");

        return attachment;
    }

    private static Attachment adaptiveCard() {
        Attachment attachment = new Attachment();
//        attachment.setContent(receiptCard);
        attachment.setContentType("application/vnd.microsoft.card.adaptive");

        return attachment;
    }

    private static Attachment receiptCard() {
        ReceiptItem item = new ReceiptItem();
        item.setPrice("$50");
        item.setTitle("Item Title");
        item.setText("Item text");
        item.setQuantity("1");

        Fact fact = new Fact();
        fact.setKey("key");
        fact.setValue("value");

        ReceiptCard receiptCard = new ReceiptCard();
        receiptCard.setTotal("$100.10");
        receiptCard.setTax("$10.10");
        receiptCard.setTitle("receipt");
        receiptCard.setItems(Arrays.asList(item));
        receiptCard.setFacts(Arrays.asList(fact));

        Attachment attachment = new Attachment();
        attachment.setContent(receiptCard);
        attachment.setContentType("application/vnd.microsoft.card.receipt");

        return attachment;
    }
}
