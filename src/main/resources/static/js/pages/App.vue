<template>
    <v-app>
        <v-app-bar elevation="4" app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-spacer></v-spacer>
            <div v-if="profile">
                {{ profile.name }}&nbsp;
                <v-btn icon href="/logout">
                    <v-icon>mdi-exit-to-app</v-icon>
                </v-btn>
            </div>
        </v-app-bar>
        <v-main>
            <v-container v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></v-container>
            <v-container v-if="profile">
                <messages-list />
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import MessagesList from 'components/messages/MessagesList.vue'
    import { addHandler } from 'util/ws'

    export default {
        components: {
            MessagesList
        },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default: console.error(`Looks like the event type is unknown "${data.eventType}"`)
                    }
                } else {
                    console.error(`Looks like the object type is unknown "${data.objectType}"`)
                }
            })
        }
    }
</script>

<style>

</style>
