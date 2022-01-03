<template>
    <v-layout align-space-around justify-start column>
        <message-form :messages="messages" :messageAttr="message"/>
        <v-spacer></v-spacer>
        <message-row v-for="message in sortedMessages"
                     :key="message.id"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage"
                     :messages="messages"
                     :message="message"/>
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import messagesApi from 'api/messages'

    export default {
        props: ['messages'],
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message:null
            }
        },
        computed: {
            sortedMessages() {
                return this.messages.sort((a, b) => (b.id - a.id))
            }
        },
        methods: {
            editMessage(message) {
                this.message = message
            },
            deleteMessage(message) {
                messagesApi.remove(message.id).then(resp => {
                        if (resp.ok) {
                            let index = this.messages.indexOf(message)
                            if (index > -1) {
                                this.messages.splice(index, 1)
                            }
                        }
                    }
                )
            }
        }
    }
</script>

<style>

</style>