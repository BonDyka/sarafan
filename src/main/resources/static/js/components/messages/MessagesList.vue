<template>
    <div style="position: relative; width:300px">
        <message-form :messages="messages" :messageAttr="message"/>
        <h3>List</h3>
        <message-row v-for="message in messages"
                     :key="message.id"
                     :editMessage="editMessage"
                     :deleteMessage="deleteMessage"
                     :messages="messages"
                     :message="message"/>
    </div>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'

    export default {
        props: ['messages'],
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message:null
            };
        },
        methods: {
            editMessage(message) {
                this.message = message
            },
            deleteMessage(message) {
                this.$resource('/message{/id}').delete({id: message.id}).then(resp => {
                        if (resp.ok) {
                            this.messages.splice(this.messages.indexOf(this.message), 1);
                        }
                    }
                )
            }
        }
    }
</script>

<style>

</style>