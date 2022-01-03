<template>
    <v-card class="my-2 pa-0">
        <v-card-text primary-title>{{message.id}} {{message.text}}</v-card-text>
        <v-card-actions>
            <v-btn @click="edit" small text>Edit</v-btn>
            <v-btn icon @click="del" small text><v-icon>mdi-delete</v-icon></v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        props: ['message', 'messages', 'editMessage', 'deleteMessage'],
        methods: {
            edit() {
                this.editMessage(this.message);
            },
            del() {
                this.deleteMessage(this.message)
            }
        }
    }
</script>

<style>

</style>