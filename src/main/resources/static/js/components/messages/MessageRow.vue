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
    import { mapActions } from 'vuex'

    export default {
        props: ['message', 'editMessage'],
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editMessage(this.message);
            },
            del() {
                this.removeMessageAction(this.message)
            }
        }
    }
</script>

<style>

</style>