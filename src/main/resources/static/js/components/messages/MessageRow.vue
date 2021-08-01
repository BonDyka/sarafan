<template>
    <div>
        <b>{{message.id}}</b>&nbsp;<i>{{message.text}}</i>
        <span style="position: absolute; right: 0">
                <input type="button" value="Edit" @click="edit"/>
                <input type="button" value="X" @click="del"/>
            </span>
    </div>
</template>

<script>
    export default {
        props: ['message', 'messages', 'deleteMessage', 'editMessage'],
        methods: {
            edit() {
                this.editMessage(this.message)
            },
            del() {
                this.deleteMessage(this.message)
            }
        }
    }
</script>

<style>

</style>