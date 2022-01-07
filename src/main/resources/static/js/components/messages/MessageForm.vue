<template>
    <v-layout row>
        <v-text-field
                label="New message"
                placeholder="Write something..."
                v-model="text"/>
        <v-btn icon @click="save">
            <v-icon>mdi-send</v-icon>
        </v-btn>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        props: ['messages', 'messageAttr'],
        data() {
            return {text: '', id: ''}
        },
        watch: {
            messageAttr: function (newVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
          ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {
                const message = {
                    id:this.id,
                    text: this.text
                }

                if (this.id) {
                    this.updateMessageAction(message)
                } else {
                    this.addMessageAction(message)
                }

                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style>

</style>