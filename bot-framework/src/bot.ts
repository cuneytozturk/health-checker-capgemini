// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

import { ActivityHandler, TurnContext } from 'botbuilder';

export class EchoBot extends ActivityHandler {
    public conversationReferences1: any;
    constructor(conversationReferences) {
        super();
        // Dependency injected dictionary for storing ConversationReference objects used in NotifyController to proactively message users
        this.conversationReferences1 = conversationReferences;

        this.onConversationUpdate(async (context, next) => {
            addConversationReference(context.activity);

            await next();
        });
        // See https://aka.ms/about-bot-activity-message to learn more about the message and other activity types.
        this.onMessage(async (context, next) => {
            addConversationReference(context.activity);
            // Echo back what the user said
            await context.sendActivity(`You sent '${ context.activity.text }'`);
            await next();
        });

        this.onMembersAdded(async (context, next) => {
            const membersAdded = context.activity.membersAdded;
            for (const member of membersAdded) {
                if (member.id !== context.activity.recipient.id) {
                    const welcomeMessage = 'Welkom bij de Health Checker bot! Navigeer naar http://localhost:4200 om je voorkeuren in te stellen.';
                    await context.sendActivity(welcomeMessage);
                }
            }
            // By calling next() you ensure that the next BotHandler is run.
            await next();
        });

        function addConversationReference(activity): void {
            const conversationReference = TurnContext.getConversationReference(activity);
            conversationReferences[conversationReference.conversation.id] = conversationReference;
        }
    }
}
