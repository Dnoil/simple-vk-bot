package com.github.dnoil.vkbot;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import java.util.List;
import java.util.Random;

public class Bot {
    public static void main(String[] args) throws ClientException, ApiException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vkApiClient = new VkApiClient(transportClient);
        GroupActor groupActor = new GroupActor(26520017L, "vk1.a.6pZtsgbfgg9jk1yrChOb2Fclr5oqK1zVgu2vO32lbz5U0SRBbJhN1GlFA8VtxIpBfq2XiNgf3qjRg2s4Zso1qrhbIQkxSsxyf_o1lX_8eqAvuMMKZlu9K_DaL8a1id4MGec6Jp3XM8YMrrkuqsS-sM5qvE3CWMIT3Ul-U4PH6oC6B4sc1-5knRcYwWMDvwndcc_Dwrk0vQllBvjHI4mXAw");
        Random random = new Random();
        Integer ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
        while (true) {
            MessagesGetLongPollHistoryQuery historyQuery = vkApiClient.messages().getLongPollHistory(groupActor).ts(ts);
            List<Message> messages = historyQuery.execute().getMessages().getItems();
            if (!messages.isEmpty()) {
                messages.forEach(message -> {
                    try {
                        String msg = message.getText();
                        vkApiClient.messages().sendDeprecated(groupActor).message("Вы сказали: " + msg).userId(message.getFromId()).randomId(random.nextInt()).execute();
                    } catch (ApiException | ClientException exception) {
                        exception.printStackTrace();
                    }
                });
            }
            ts = vkApiClient.messages().getLongPollServer(groupActor).execute().getTs();
        }
    }
}
