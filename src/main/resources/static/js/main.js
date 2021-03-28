var messageAPI = Vue.resource('/message{/id}');

Vue.component('message-row',{
    props: ['message'],
    template: '<div><b>{{message.id}}</b>&nbsp;<i>{{message.text}}</i></div>'
});

Vue.component('messages-list', {
    props: ['messages'],
    template:
        '<div>' +
            '<h3>List</h3>' +
            '<message-row v-for="message in messages" :key="message.id" :message="message" />' +
        '</div>',
    created: function () {
        messageAPI.get().then(resp => resp.json().then( data =>
            data.forEach(message => this.messages.push(message))
        ))
    }
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages : []
    }
});