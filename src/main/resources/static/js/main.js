function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

let messageAPI = Vue.resource('/message{/id}');

Vue.component('message-form', {
    props: ['messages', 'messageAttr'],
    data: function () {
        return {text: '', id: ''};
    },
    watch: {
        messageAttr: function (newVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="write something" v-model="text" />' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function () {
            let message = {text: this.text};

            if (this.id) {
                messageAPI.update({id:this.id}, message).then(resp =>
                    resp.json().then(data => {
                        let index = getIndex(this.messages, data.id);
                        this.messages.splice(index, 1, data);
                        this.text = '';
                        this.id = '';
                    })
                );
            } else {
                messageAPI.save({}, message).then(resp =>
                    resp.json().then(data => {
                        this.messages.push(data);
                        this.text = '';
                    })
                )
            }
        }
    }
});

Vue.component('message-row',{
    props: ['message', 'messages', 'editMethod'],
    template: '<div>' +
            '<b>{{message.id}}</b>&nbsp;<i>{{message.text}}</i>' +
            '<span style="position: absolute; right: 0">' +
                '<input type="button" value="Edit" @click="edit" />' +
                '<input type="button" value="X" @click="del" />' +
            '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.message);
        },
        del: function () {
            messageAPI.delete({id: this.message.id}).then(resp => {
                    if (resp.ok) {
                        this.messages.splice(this.messages.indexOf(this.message), 1);
                    }
                }
            )
        }
    }
});

Vue.component('messages-list', {
    props: ['messages'],
    data: function () {
        return {message:null};
    },
    template:
        '<div style="position: relative; width:300px">' +
            '<message-form :messages="messages" :messageAttr="message" />' +
            '<h3>List</h3>' +
            '<message-row v-for="message in messages" :key="message.id" :editMethod="editMethod" :messages="messages" :message="message" />' +
        '</div>',
    created: function () {
        messageAPI.get().then(resp => resp.json().then( data =>
            data.forEach(message => this.messages.push(message))
        ))
    },
    methods: {
        editMethod: function (message) {
            this.message = message;
        }
    }
});

let app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages : []
    }
});